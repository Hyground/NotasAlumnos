package CRUDs;

import POJOs.Estudiantes;
import POJOs.Grados;
import POJOs.Secciones;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

public class CEstudiantes {

    // Método para listar todos los estudiantes activos (borradoLogico = true)
    public static List<Estudiantes> listarEstudiantes() {
        Session session = HibernateUtil.HibernateUtil.getSessionFactory().openSession();
        List<Estudiantes> lista = null;
        try {
            session.beginTransaction();
            Criteria criteria = session.createCriteria(Estudiantes.class);
            criteria.add(Restrictions.eq("borradoLogico", true));  // Solo listar estudiantes activos
            lista = criteria.list();
        } catch (Exception e) {
            System.out.println("Error: " + e);
        } finally {
            session.getTransaction().commit();
            session.close();
        }
        return lista;
    }

    // Método para crear un nuevo estudiante
    public static boolean crearEstudiante(String cui, String codigoPersonal, String nombre, String apellido, int gradoId, int seccionId) {
        boolean flag = false;
        Session session = HibernateUtil.HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        
        try {
            transaction = session.beginTransaction();

            // Obtener el grado al que pertenece el estudiante
            Grados grado = (Grados) session.get(Grados.class, gradoId);
            if (grado == null) {
                throw new RuntimeException("El grado con ID " + gradoId + " no existe.");
            }

            // Obtener la sección a la que pertenece el estudiante
            Secciones seccion = (Secciones) session.get(Secciones.class, seccionId);
            if (seccion == null) {
                throw new RuntimeException("La sección con ID " + seccionId + " no existe.");
            }

            // Crear el nuevo estudiante
            Estudiantes nuevoEstudiante = new Estudiantes();
            nuevoEstudiante.setCui(cui);
            nuevoEstudiante.setCodigoPersonal(codigoPersonal);
            nuevoEstudiante.setNombre(nombre);
            nuevoEstudiante.setApellido(apellido);
            nuevoEstudiante.setGrados(grado);
            nuevoEstudiante.setSecciones(seccion);
            nuevoEstudiante.setBorradoLogico(true);  // El estudiante está activo por defecto

            // Guardar el nuevo estudiante
            session.save(nuevoEstudiante);
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
    public static boolean actualizarEstudiante(String cui, String nuevoCodigoPersonal, String nuevoNombre, String nuevoApellido, int nuevoGradoId, int nuevaSeccionId) {
        boolean flag = false;
        Session session = HibernateUtil.HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();

            // Obtener el estudiante existente
            Estudiantes estudiante = (Estudiantes) session.get(Estudiantes.class, cui);
            if (estudiante != null) {
                estudiante.setCodigoPersonal(nuevoCodigoPersonal);
                estudiante.setNombre(nuevoNombre);
                estudiante.setApellido(nuevoApellido);

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
                estudiante.setGrados(nuevoGrado);
                estudiante.setSecciones(nuevaSeccion);

                // Actualizar el estudiante
                session.update(estudiante);
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
        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();

            // Obtener el estudiante existente
            Estudiantes estudiante = (Estudiantes) session.get(Estudiantes.class, cui);
            if (estudiante != null) {
                estudiante.setBorradoLogico(false);  // Borrado lógico
                session.update(estudiante);
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
        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();

            // Obtener el estudiante existente
            Estudiantes estudiante = (Estudiantes) session.get(Estudiantes.class, cui);
            if (estudiante != null && !estudiante.isBorradoLogico()) {
                estudiante.setBorradoLogico(true);  // Reactivar el estudiante
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
