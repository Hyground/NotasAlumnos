package CRUDs;

import POJOs.Notas;
import POJOs.Estudiantes;
import POJOs.Evaluaciones;
import java.math.BigDecimal;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

public class CNotas {

    // Método para listar todas las notas
    public static List<Notas> listarNotas() {
        Session session = HibernateUtil.HibernateUtil.getSessionFactory().openSession();
        List<Notas> lista = null;
        try {
            session.beginTransaction();
            Criteria criteria = session.createCriteria(Notas.class);
            lista = criteria.list();
        } catch (Exception e) {
            System.out.println("Error: " + e);
        } finally {
            session.getTransaction().commit();
            session.close();
        }
        return lista;
    }

    // Método para crear una nueva nota
    public static boolean crearNota(int CUI, int evaluacionId, BigDecimal nota) {
        boolean flag = false;
        Session session = HibernateUtil.HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        
        try {
            transaction = session.beginTransaction();

            // Obtener el estudiante por su CUI
            Estudiantes estudiante = (Estudiantes) session.get(Estudiantes.class, CUI);
            if (estudiante == null) {
                throw new RuntimeException("El estudiante con CUI " + CUI + " no existe.");
            }

            // Obtener la evaluación por su ID
            Evaluaciones evaluacion = (Evaluaciones) session.get(Evaluaciones.class, evaluacionId);
            if (evaluacion == null) {
                throw new RuntimeException("La evaluación con ID " + evaluacionId + " no existe.");
            }

            // Crear la nueva nota
            Notas nuevaNota = new Notas();
            nuevaNota.setEstudiantes(estudiante);
            nuevaNota.setEvaluaciones(evaluacion);
            nuevaNota.setNota(nota);

            // Guardar la nota en la base de datos
            session.save(nuevaNota);
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

    // Método para actualizar una nota existente
    public static boolean actualizarNota(int notaId, BigDecimal nuevaNota) {
        boolean flag = false;
        Session session = HibernateUtil.HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();

            // Obtener la nota existente
            Notas nota = (Notas) session.get(Notas.class, notaId);
            if (nota != null) {
                nota.setNota(nuevaNota);  // Actualizar la nota

                // Actualizar la nota en la base de datos
                session.update(nota);
                flag = true;
            } else {
                System.out.println("No se encontró la nota con ID " + notaId);
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

    // Método para eliminar una nota
    public static boolean eliminarNota(int notaId) {
        boolean flag = false;
        Session session = HibernateUtil.HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();

            // Obtener la nota existente
            Notas nota = (Notas) session.get(Notas.class, notaId);
            if (nota != null) {
                session.delete(nota);  // Eliminar la nota
                flag = true;
            } else {
                System.out.println("No se encontró la nota con ID " + notaId);
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

    // Método para obtener una nota por su ID
    public static Notas obtenerNotaPorId(int notaId) {
        Session session = HibernateUtil.HibernateUtil.getSessionFactory().openSession();
        Notas nota = null;
        try {
            session.beginTransaction();
            nota = (Notas) session.get(Notas.class, notaId);
        } catch (Exception e) {
            System.out.println("Error: " + e);
        } finally {
            session.getTransaction().commit();
            session.close();
        }
        return nota;
    }
}
