package CRUDs;

import POJOs.Estudiantes;
import POJOs.Grados;
import POJOs.Secciones;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

public class CEstudiantes {

    // Método para listar todos los estudiantes activos (borradoLogico = true)
public static List<Estudiantes> ListarEstudiante() {
    Session session = HibernateUtil.HibernateUtil.getSessionFactory().openSession();
    List<Estudiantes> lista = null;
    try {
        session.beginTransaction();
        Criteria criteria = session.createCriteria(Estudiantes.class);
        criteria.createAlias("grados", "g"); // Relacionar con Grados
        criteria.createAlias("secciones", "s"); // Relacionar con Secciones
        criteria.add(Restrictions.eq("borradoLogico", true));  // Solo listar estudiantes activos
        criteria.addOrder(Order.asc("nombre"));  // Ordenar por nombre
        lista = criteria.list(); // Retornar una lista completa de Estudiantes
    } catch (Exception e) {
        System.out.println("Error: " + e);
    } finally {
        session.getTransaction().commit();
        session.close();
    }
    return lista;
}


    // Método para crear un nuevo estudiante
    public static boolean crearEstudiante(String cui, String codigoPersonal, String nombre, String apellido, Integer gradoId, Integer seccionId) {
        boolean flag = false;
        Session session = HibernateUtil.HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();

            // Crear el estudiante
            Estudiantes insert = new Estudiantes();
            insert.setCui(cui);
            insert.setCodigoPersonal(codigoPersonal);
            insert.setNombre(nombre);
            insert.setApellido(apellido);

            // Asignar el grado y la sección sin verificar su existencia
            Grados grados = new Grados();
            grados.setGradoId(gradoId);
            insert.setGrados(grados);  // Asignación directa
            Secciones secciones = new Secciones();
            secciones.setSeccionId(seccionId);
            insert.setSecciones(secciones);  // Asignación directa

            // Estudiante activo por defecto
            insert.setBorradoLogico(true);

            // Guardar el nuevo estudiante
            session.save(insert);
            flag = true;

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
        return flag;
    }

    // Método para actualizar un estudiante existente
    public static boolean actualizarEstudiante(String cui, String nuevoCodigoPersonal, String nuevoNombre, String nuevoApellido, Integer nuevoGradoId, Integer nuevaSeccionId) {
        boolean flag = false;
        Session session = HibernateUtil.HibernateUtil.getSessionFactory().openSession();
        Criteria criteria = session.createCriteria(Estudiantes.class);
        criteria.add(Restrictions.eq("cui", cui));  // Buscar el estudiante por CUI
        Estudiantes actualizar = (Estudiantes) criteria.uniqueResult();
        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();

            if (actualizar != null) {  // Si el estudiante existe, actualizarlo
                actualizar.setCodigoPersonal(nuevoCodigoPersonal);
                actualizar.setNombre(nuevoNombre);
                actualizar.setApellido(nuevoApellido);

                // Asignar el grado y la sección sin verificar su existencia
                Grados grados = new Grados();
                grados.setGradoId(nuevoGradoId);
                actualizar.setGrados(grados);  // Asignación directa
                Secciones secciones = new Secciones();
                secciones.setSeccionId(nuevaSeccionId);
                actualizar.setSecciones(secciones);  // Asignación directa

                // Actualizar el estudiante
                session.update(actualizar);
                flag = true;
            } else {
                System.out.println("No se encontró el estudiante con CUI " + cui);
            }

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
        return flag;
    }

    // Método para eliminar (borrado lógico) un estudiante
    public static boolean eliminarEstudiante(String cui) {
        boolean flag = false;
        Session session = HibernateUtil.HibernateUtil.getSessionFactory().openSession();
        Criteria criteria = session.createCriteria(Estudiantes.class);
        criteria.add(Restrictions.eq("cui", cui));  // Buscar el estudiante por CUI
        Estudiantes anular = (Estudiantes) criteria.uniqueResult();
        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();

            if (anular != null) {  // Si el estudiante existe, aplicar borrado lógico
                anular.setBorradoLogico(false);
                session.update(anular);
                flag = true;
            } else {
                System.out.println("No se encontró el estudiante con CUI " + cui);
            }

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
        return flag;
    }

    // Método para reactivar un estudiante (borrado lógico)
    public static boolean reactivarEstudiante(String cui) {
        boolean flag = false;
        Session session = HibernateUtil.HibernateUtil.getSessionFactory().openSession();
        Criteria criteria = session.createCriteria(Estudiantes.class);
        criteria.add(Restrictions.eq("cui", cui));  // Buscar el estudiante por CUI
        Estudiantes estudiante = (Estudiantes) criteria.uniqueResult();
        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();

            if (estudiante != null && !estudiante.isBorradoLogico()) {  // Reactivar solo si está inactivo
                estudiante.setBorradoLogico(true);
                session.update(estudiante);
                flag = true;
            } else if (estudiante == null) {
                System.out.println("No se encontró el estudiante con CUI " + cui);
            } else {
                System.out.println("El estudiante ya está activo.");
            }

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
        return flag;
    }

    // Método para obtener un estudiante por su CUI
    public static Estudiantes obtenerEstudiantePorCui(String cui) {
        Session session = HibernateUtil.HibernateUtil.getSessionFactory().openSession();
        Estudiantes estudiante = null;
        try {
            session.beginTransaction();
            estudiante = (Estudiantes) session.get(Estudiantes.class, cui);
        } catch (Exception e) {
            System.out.println("Error: " + e);
        } finally {
            session.getTransaction().commit();
            session.close();
        }
        return estudiante;
    }
}
