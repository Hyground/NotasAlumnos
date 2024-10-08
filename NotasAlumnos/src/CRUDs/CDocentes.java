package CRUDs;

import POJOs.Docentes;
import POJOs.Grados;
import POJOs.Secciones;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

public class CDocentes {

    // Método para listar todos los docentes activos (borradoLogico = true)
    public static List<Docentes> listarDocentes() {
        Session session = HibernateUtil.HibernateUtil.getSessionFactory().openSession();
        List<Docentes> lista = null;
        try {
            session.beginTransaction();
            Criteria criteria = session.createCriteria(Docentes.class);
            criteria.add(Restrictions.eq("borradoLogico", true));  // Solo listar docentes activos
            lista = criteria.list();
        } catch (Exception e) {
            System.out.println("Error: " + e);
        } finally {
            session.getTransaction().commit();
            session.close();
        }
        return lista;
    }

    // Método para crear un nuevo docente
   public static boolean crearDocente(String nombreCompleto, String cui, String nombreUsuario, String contrasenia, String rol, int gradoId, int seccionId) {
    boolean flag = false;
    Session session = HibernateUtil.HibernateUtil.getSessionFactory().openSession();
               Criteria criteria=session.createCriteria(Docentes.class);
            criteria.add(Restrictions.eq("nombreUsuario", nombreUsuario));
            criteria.add(Restrictions.eq("estado", true));
            Docentes insert = (Docentes)criteria.uniqueResult();
            Transaction transaction = null;

    try {
        transaction = session.beginTransaction();
       if (insert==null) {
        // Crear el nuevo docente
        insert = new Docentes();
        insert.setNombreCompleto(nombreCompleto);
        insert.setCui(cui);
        insert.setNombreUsuario(nombreUsuario);
        insert.setContrasenia(contrasenia);
        insert.setRol(rol);

        // Aquí es donde relaciono la entidad Grados
        Grados grado = new Grados();
        insert.setGrados(grado); // Relaciono el docente con el grado

        // Aquí es donde relaciono la entidad Secciones
        Secciones seccion = new Secciones();
        insert.setSecciones(seccion); // Relaciono el docente con la sección

        // El docente está activo por defecto (borrado lógico = true)
        insert.setBorradoLogico(true);

        // Guardar el nuevo docente
        session.save(insert);
        flag = true;
                        }

        transaction.commit();
    } catch (Exception e) {
            transaction.rollback();
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
                docente.setNombreUsuario(nuevoNombreUsuario);
                docente.setContrasenia(nuevaContrasenia);
                docente.setRol(nuevoRol);

                // Obtener el nuevo grado
                Grados nuevoGrado = (Grados) session.get(Grados.class, nuevoGradoId);
                if (nuevoGrado == null) {
                    throw new RuntimeException("El grado con ID " + nuevoGradoId + " no existe.");
                }

                // Obtener la nueva sección
                Secciones nuevaSeccion = (Secciones) session.get(Secciones.class, nuevaSeccionId);
                if (nuevaSeccion == null) {
                    throw new RuntimeException("La sección con ID " + nuevaSeccionId + " no existe.");
                }

                // Asignar el nuevo grado y la nueva sección
                docente.setGrados(nuevoGrado);
                docente.setSecciones(nuevaSeccion);

                // Actualizar el docente
                session.update(docente);
                flag = true;
            } else {
                System.out.println("No se encontró el docente con ID " + usuarioId);
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
            } else {
                System.out.println("No se encontró el docente con ID " + usuarioId);
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
            } else if (docente == null) {
                System.out.println("No se encontró el docente con ID " + usuarioId);
            } else {
                System.out.println("El docente ya está activo.");
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
