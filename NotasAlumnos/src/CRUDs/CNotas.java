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
import org.hibernate.criterion.Restrictions;

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
            criteria.addOrder(Order.desc("notaId")); // Ordenar por notaId descendente
            lista = criteria.list(); // Aquí obtenemos toda la lista de notas con las entidades completas
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

            // Verificar si ya existe una nota para el estudiante y la evaluación
            Criteria criteria = session.createCriteria(Notas.class);
            criteria.createAlias("estudiantes", "e");
            criteria.createAlias("evaluaciones", "ev");
            criteria.add(Restrictions.eq("e.cui", cui));
            criteria.add(Restrictions.eq("ev.evaluacionId", evaluacionId));

            Notas notaExistente = (Notas) criteria.uniqueResult();

            if (notaExistente != null) {
                System.out.println("Ya existe una nota para este estudiante en esta evaluación.");
                return flag;
            }

            // Crear la nueva nota si no existe
            Notas nuevaNota = new Notas();

            Estudiantes estudiante = (Estudiantes) session.createCriteria(Estudiantes.class)
                    .add(Restrictions.eq("cui", cui)).uniqueResult();  // Obtener el estudiante por CUI
            nuevaNota.setEstudiantes(estudiante);

            Evaluaciones evaluacion = (Evaluaciones) session.createCriteria(Evaluaciones.class)
                    .add(Restrictions.eq("evaluacionId", evaluacionId)).uniqueResult();  // Obtener la evaluación por ID
            nuevaNota.setEvaluaciones(evaluacion);

            nuevaNota.setNota(nota);

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
            Notas nota = (Notas) session.get(Notas.class, notaId);
            if (nota != null) {
                nota.setNota(nuevaNota);
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

