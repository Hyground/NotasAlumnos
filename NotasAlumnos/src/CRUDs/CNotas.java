package CRUDs;

import POJOs.Notas;
import POJOs.Estudiantes;
import POJOs.Evaluaciones;
import java.math.BigDecimal;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;

public class CNotas {

    // Método para listar todas las notas
    public static List<Notas> listarNotas() {
        Session session = HibernateUtil.HibernateUtil.getSessionFactory().getCurrentSession();
        List<Notas> lista = null;
        try {
            session.beginTransaction();
            Criteria criteria = session.createCriteria(Notas.class);
            criteria.createAlias("estudiantes", "e"); // Relacionar con Estudiantes
            criteria.createAlias("evaluaciones", "ev"); // Relacionar con Evaluaciones
            criteria.setProjection(Projections.projectionList()
                    .add(Projections.property("notaId"))
                    .add(Projections.property("nota"))
                    .add(Projections.property("e.nombre")) // Alias para el nombre del estudiante
                    .add(Projections.property("ev.nombreEvaluacion")) // Alias para el nombre de la evaluación
            );
            criteria.addOrder(Order.desc("notaId"));
            lista = criteria.list();
        } catch (Exception e) {
            System.out.println("Error: " + e);
        } finally {
            session.getTransaction().commit();
        }
        return lista;
    }

    // Método para crear una nueva nota
    public static boolean crearNota(String cui, Integer evaluacionId, BigDecimal nota) {
        boolean flag = false;
        Session session = HibernateUtil.HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        
        try {
            transaction = session.beginTransaction();

            // Crear la nueva nota y asignar las relaciones directamente por ID
            Notas nuevaNota = new Notas();

            Estudiantes estudiante = new Estudiantes();
            estudiante.setCui(cui);  // Asignación directa del CUI
            nuevaNota.setEstudiantes(estudiante);

            Evaluaciones evaluacion = new Evaluaciones();
            evaluacion.setEvaluacionId(evaluacionId);  // Asignación directa del evaluacionId
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
    public static boolean actualizarNota(Integer notaId, BigDecimal nuevaNota) {
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

    /* Método para eliminar una nota
    public static boolean eliminarNota(Integer notaId) {
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
    }*/

    // Método para obtener una nota por su ID
    public static Notas obtenerNotaPorId(Integer notaId) {
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
