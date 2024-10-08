package CRUDs;

import POJOs.Docentes;
import POJOs.Grados;
import POJOs.Secciones;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

public class CDocentes {

    // Método para listar todos los docentes activos (borradoLogico = true)
    public static List<Docentes> listarDocentes() {
        Session session = HibernateUtil.HibernateUtil.getSessionFactory().getCurrentSession();
        List<Docentes> lista = null;
        try {
            session.beginTransaction();
            Criteria criteria = session.createCriteria(Docentes.class);
            criteria.createAlias("grados", "g"); // Alias para la relación con Grados
            criteria.createAlias("secciones", "s"); // Alias para la relación con Secciones
            criteria.add(Restrictions.eq("borradoLogico", true));  // Solo listar docentes activos
            criteria.setProjection(Projections.projectionList()
                    .add(Projections.property("cui"))
                    .add(Projections.property("nombreCompleto"))
                    .add(Projections.property("nombreUsuario"))
                    .add(Projections.property("g.nombreGrado"))  // Alias para el nombre del grado
                    .add(Projections.property("s.nombreSeccion")) // Alias para el nombre de la sección
            );
            criteria.addOrder(Order.asc("nombreCompleto")); // Ordenar por nombre completo
            lista = criteria.list();
        } catch (Exception e) {
            System.out.println("Error: " + e);
        } finally {
            session.getTransaction().commit();
        }
        return lista;
    }

    // Método para crear un nuevo docente
public static boolean crearDocente(String nombreCompleto, String cui, String nombreUsuario, String contrasenia, String rol, int gradoId, int seccionId) {
    boolean flag = false;
    Session session = HibernateUtil.HibernateUtil.getSessionFactory().openSession();
    Transaction transaction = null;

    try {
        transaction = session.beginTransaction();

        // Verificar si ya existe un docente con el mismo nombre de usuario
        Criteria criteriaUsuario = session.createCriteria(Docentes.class);
        criteriaUsuario.add(Restrictions.eq("nombreUsuario", nombreUsuario));
        Docentes docenteExistente = (Docentes) criteriaUsuario.uniqueResult();

        if (docenteExistente != null) {
            // Si ya existe un docente con el mismo nombre de usuario, no permitir crear
            throw new RuntimeException("El nombre de usuario '" + nombreUsuario + "' ya está en uso.");
        }

        // Obtener el grado y la sección existentes por sus IDs
        Grados grado = (Grados) session.get(Grados.class, gradoId);
        Secciones seccion = (Secciones) session.get(Secciones.class, seccionId);

        // Verificar si los grados y secciones existen
        if (grado == null) {
            throw new RuntimeException("El grado con ID " + gradoId + " no existe.");
        }
        if (seccion == null) {
            throw new RuntimeException("La sección con ID " + seccionId + " no existe.");
        }

        // Verificar si ya hay un docente asignado a la misma sección y grado
        Criteria criteriaAsignacion = session.createCriteria(Docentes.class);
        criteriaAsignacion.add(Restrictions.eq("grados", grado));
        criteriaAsignacion.add(Restrictions.eq("secciones", seccion));
        Docentes docenteAsignado = (Docentes) criteriaAsignacion.uniqueResult();

        if (docenteAsignado != null) {
            // Si ya existe un docente asignado a ese grado y sección, no permitir crear
            throw new RuntimeException("Ya existe un docente asignado al grado '" + grado.getNombreGrado() + "' y sección '" + seccion.getNombreSeccion() + "'.");
        }

        // Crear el nuevo docente si pasa todas las validaciones
        Docentes docente = new Docentes();
        docente.setNombreCompleto(nombreCompleto);
        docente.setCui(cui);
        docente.setNombreUsuario(nombreUsuario);
        docente.setContrasenia(contrasenia);
        docente.setRol(rol);
        docente.setGrados(grado);
        docente.setSecciones(seccion);

        // El docente está activo por defecto (borrado lógico = true)
        docente.setBorradoLogico(true);

        // Guardar el nuevo docente
        session.save(docente);
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


    // Método para actualizar un docente existente
    public static boolean actualizarDocente(int usuarioId, String nuevoNombreUsuario, String nuevaContrasenia, String nuevoRol, int nuevoGradoId, int nuevaSeccionId) {
        boolean flag = false;
        Session session = HibernateUtil.HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();

            // Obtener el docente existente
            Docentes docente = (Docentes) session.get(Docentes.class, usuarioId);
            if (docente != null) {
                // Obtener el grado y la sección por sus IDs
                Grados nuevoGrado = (Grados) session.get(Grados.class, nuevoGradoId);
                Secciones nuevaSeccion = (Secciones) session.get(Secciones.class, nuevaSeccionId);

                if (nuevoGrado == null) {
                    throw new RuntimeException("El grado con ID " + nuevoGradoId + " no existe.");
                }
                if (nuevaSeccion == null) {
                    throw new RuntimeException("La sección con ID " + nuevaSeccionId + " no existe.");
                }

                // Actualizar los datos del docente
                docente.setNombreUsuario(nuevoNombreUsuario);
                docente.setContrasenia(nuevaContrasenia);
                docente.setRol(nuevoRol);
                docente.setGrados(nuevoGrado); // Asignación directa del grado existente
                docente.setSecciones(nuevaSeccion); // Asignación directa de la sección existente

                // Actualizar el docente
                session.update(docente);
                flag = true;
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

    // Método para eliminar (borrado lógico) un docente
    public static boolean eliminarDocente(int usuarioId) {
        boolean flag = false;
        Session session = HibernateUtil.HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();

            // Obtener el docente existente
            Docentes docente = (Docentes) session.get(Docentes.class, usuarioId);
            if (docente != null) {
                docente.setBorradoLogico(false);  // Borrado lógico
                session.update(docente);
                flag = true;
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

    // Método para reactivar un docente (borrado lógico)
    public static boolean reactivarDocente(int usuarioId) {
        boolean flag = false;
        Session session = HibernateUtil.HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();

            // Obtener el docente existente
            Docentes docente = (Docentes) session.get(Docentes.class, usuarioId);
            if (docente != null && !docente.isBorradoLogico()) {
                docente.setBorradoLogico(true);  // Reactivar el docente
                session.update(docente);
                flag = true;
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

    // Método para obtener un docente por su ID
    public static Docentes obtenerDocentePorId(int usuarioId) {
        Session session = HibernateUtil.HibernateUtil.getSessionFactory().openSession();
        Docentes docente = null;
        try {
            session.beginTransaction();
            docente = (Docentes) session.get(Docentes.class, usuarioId);
        } catch (Exception e) {
            System.out.println("Error: " + e);
        } finally {
            session.getTransaction().commit();
            session.close();
        }
        return docente;
    }
}
