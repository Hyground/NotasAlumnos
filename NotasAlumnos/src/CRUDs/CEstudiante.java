package CRUDs;

import POJOs.Estudiantes;
import POJOs.Grados;
import POJOs.Secciones;
import HibernateUtil.HibernateUtil;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

public class CEstudiante {

    // Método para listar todos los estudiantes activos
    public static List<Estudiantes> listarEstudiantes() {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        List<Estudiantes> lista = null;
        try {
            session.beginTransaction();
            Criteria criteria = session.createCriteria(Estudiantes.class);
            criteria.add(Restrictions.eq("borradoLogico", true));
            lista = criteria.list();
            session.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error al listar estudiantes: " + e.getMessage());
            if (session.getTransaction().isActive()) {
                session.getTransaction().rollback();
            }
        } finally {
            if (session.isOpen()) {
                session.close();
            }
        }
        return lista;
    }

    // Método para crear un nuevo estudiante
    public static boolean crearEstudiante(String cui, String codigoPersonal, String nombre, String apellido, int gradoID, int seccionID) {
        boolean flag = false;
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();

            // Verificar si ya existe un estudiante con el mismo CUI
            Criteria criteria = session.createCriteria(Estudiantes.class);
            criteria.add(Restrictions.eq("cui", cui));
            criteria.add(Restrictions.eq("borradoLogico", true));

            Estudiantes existente = (Estudiantes) criteria.uniqueResult();

            if (existente == null) {
                // Obtener el grado y la sección
                Grados grado = (Grados) session.get(Grados.class, gradoID);
                Secciones seccion = (Secciones) session.get(Secciones.class, seccionID);

                if (grado != null && seccion != null) {
                    // Si no existe, crear un nuevo estudiante
                    Estudiantes nuevoEstudiante = new Estudiantes();
                    nuevoEstudiante.setCui(cui);
                    nuevoEstudiante.setCodigoPersonal(codigoPersonal);
                    nuevoEstudiante.setNombre(nombre);
                    nuevoEstudiante.setApellido(apellido);
                    nuevoEstudiante.setGrados(grado);
                    nuevoEstudiante.setSecciones(seccion);
                    nuevoEstudiante.setBorradoLogico(true);

                    // Guardar el nuevo estudiante
                    session.save(nuevoEstudiante);
                    transaction.commit();
                    flag = true;
                } else {
                    System.out.println("El grado o la sección especificada no existe.");
                    transaction.rollback();
                }
            } else {
                System.out.println("Ya existe un estudiante con el CUI especificado.");
                transaction.rollback();
            }

        } catch (Exception e) {
            System.out.println("Error al crear estudiante: " + e.getMessage());
            if (transaction != null) {
                transaction.rollback();
            }
        } finally {
            session.close();
        }
        return flag;
    }

    // Método para actualizar un estudiante existente
    public static boolean actualizarEstudiante(String cui, String codigoPersonal, String nombre, String apellido, int gradoID, int seccionID) {
        boolean flag = false;
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();

            Estudiantes estudiante = (Estudiantes) session.get(Estudiantes.class, cui);
            if (estudiante != null && estudiante.isBorradoLogico()) {
                // Obtener el grado y la sección
                Grados grado = (Grados) session.get(Grados.class, gradoID);
                Secciones seccion = (Secciones) session.get(Secciones.class, seccionID);

                if (grado != null && seccion != null) {
                    // Actualizar los datos del estudiante
                    estudiante.setCodigoPersonal(codigoPersonal);
                    estudiante.setNombre(nombre);
                    estudiante.setApellido(apellido);
                    estudiante.setGrados(grado);
                    estudiante.setSecciones(seccion);

                    // Guardar el estudiante actualizado
                    session.update(estudiante);
                    transaction.commit();
                    flag = true;
                } else {
                    System.out.println("El grado o la sección especificada no existe.");
                    transaction.rollback();
                }
            } else {
                System.out.println("No se encontró el estudiante o está anulado.");
                transaction.rollback();
            }

        } catch (Exception e) {
            System.out.println("Error al actualizar estudiante: " + e.getMessage());
            if (transaction != null) {
                transaction.rollback();
            }
        } finally {
            session.close();
        }
        return flag;
    }

    // Método para anular un estudiante (borrado lógico)
    public static boolean anularEstudiante(String cui) {
        boolean flag = false;
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();

            Estudiantes estudiante = (Estudiantes) session.get(Estudiantes.class, cui);
            if (estudiante != null && estudiante.isBorradoLogico()) {
                estudiante.setBorradoLogico(false);
                session.update(estudiante);
                transaction.commit();
                flag = true;
            } else {
                System.out.println("No se encontró el estudiante o ya está anulado.");
                transaction.rollback();
            }

        } catch (Exception e) {
            System.out.println("Error al anular estudiante: " + e.getMessage());
            if (transaction != null) {
                transaction.rollback();
            }
        } finally {
            session.close();
        }
        return flag;
    }

    // Método para reactivar un estudiante anulado
    public static boolean reactivarEstudiante(String cui) {
        boolean flag = false;
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();

            Estudiantes estudiante = (Estudiantes) session.get(Estudiantes.class, cui);
            if (estudiante != null && !estudiante.isBorradoLogico()) {
                estudiante.setBorradoLogico(true);
                session.update(estudiante);
                transaction.commit();
                flag = true;
            } else if (estudiante == null) {
                System.out.println("No se encontró el estudiante con el CUI: " + cui);
                transaction.rollback();
            } else {
                System.out.println("El estudiante ya está activo.");
                transaction.rollback();
            }

        } catch (Exception e) {
            System.out.println("Error al reactivar estudiante: " + e.getMessage());
            if (transaction != null) {
                transaction.rollback();
            }
        } finally {
            session.close();
        }
        return flag;
    }
}
