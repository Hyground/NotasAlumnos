package CRUDs;

import POJOs.Bimestres;
import POJOs.Cursos;
import POJOs.Evaluaciones;
import POJOs.Grados;
import POJOs.Secciones;
import java.math.BigDecimal;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author Ivan
 */

public class CEvaluaciones {

    // Método para listar todas las evaluaciones filtradas por grado y sección
    public static List<Evaluaciones> universo(Integer gradoId, Integer seccionId) {
        Session session = HibernateUtil.HibernateUtil.getSessionFactory().getCurrentSession();
        List<Evaluaciones> lista = null;
        try {
            session.beginTransaction();
            Criteria criteria = session.createCriteria(Evaluaciones.class);

            // Filtrar por Grado y Sección
            if (gradoId != null) {
                criteria.createAlias("grados", "g");
                criteria.add(Restrictions.eq("g.gradoId", gradoId));
            }
            if (seccionId != null) {
                criteria.createAlias("secciones", "s");
                criteria.add(Restrictions.eq("s.seccionId", seccionId));
            }

            // Agregar alias para las relaciones adicionales
            criteria.createAlias("bimestres", "b");
            criteria.createAlias("cursos", "c");

            // Ordenar por ID de evaluación
            criteria.addOrder(Order.desc("evaluacionId"));
            lista = criteria.list();
        } catch (Exception e) {
            System.out.println("Error: " + e);
        } finally {
            session.getTransaction().commit();
        }
        return lista;
    }




    public static boolean crearEvaluacion(Integer bimestreId, Integer cursoId, int gradoId, int seccionId, String nombreEvaluacion, String tipo, BigDecimal ponderacion) {
        boolean flag = false;
        Session session = HibernateUtil.HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();

            // Crear la nueva evaluación y asignar las relaciones directamente por ID
            Evaluaciones insert = new Evaluaciones();
            
            Bimestres bimestres = new Bimestres();
            bimestres.setBimestreId(bimestreId);
            insert.setBimestres(bimestres); // Tructo del Profe :V 
            
            Cursos cursos = new Cursos();
            cursos.setCursoId(cursoId);
            insert.setCursos(cursos); // Tructo del Profe :V 
            
            Grados grados = new Grados();
            grados.setGradoId(gradoId);
            insert.setGrados(grados); // Tructo del Profe :V 
            
            Secciones secciones = new Secciones();
            secciones.setSeccionId(seccionId);
            insert.setSecciones(secciones); // Tructo del Profe :V 
            
            insert.setNombreEvaluacion(nombreEvaluacion);
            insert.setTipo(tipo);
            insert.setPonderacion(ponderacion);

            // Guardar la evaluación
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

    public static boolean actualizarEvaluacion(Integer evaluacionId, Integer bimestreId, Integer cursoId, Integer gradoId, Integer seccionId, String nombreEvaluacion, String tipo, BigDecimal ponderacion) {
        boolean flag = false;
        Session session = HibernateUtil.HibernateUtil.getSessionFactory().openSession();
        Criteria criteria = session.createCriteria(Evaluaciones.class);
        criteria.add(Restrictions.eq("evaluacionId", evaluacionId)); 
        Evaluaciones actualizar = (Evaluaciones) criteria.uniqueResult();
        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();

            if (actualizar != null) {
                // Asignar las relaciones directamente por ID
                Bimestres bimestres = new Bimestres();
                bimestres.setBimestreId(bimestreId);
                actualizar.setBimestres(bimestres); // Asignación directa
                
                Cursos cursos = new Cursos();
                cursos.setCursoId(cursoId);
                actualizar.setCursos(cursos); // Asignación directa
                
                Grados grados = new Grados();
                grados.setGradoId(gradoId);
                actualizar.setGrados(grados); // Asignación directa
                
                Secciones secciones = new Secciones();
                secciones.setSeccionId(seccionId);
                actualizar.setSecciones(secciones); // Asignación directa
                
                actualizar.setNombreEvaluacion(nombreEvaluacion);
                actualizar.setTipo(tipo);
                actualizar.setPonderacion(ponderacion);

                // Actualizar la evaluación
                session.update(actualizar);
                flag = true;
            } else {
                System.out.println("No se encontró la evaluación con ID " + evaluacionId);
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

    // Método para obtener una evaluación por su ID
    public static Evaluaciones obtenerEvaluacionPorId(int evaluacionId) {
        Session session = HibernateUtil.HibernateUtil.getSessionFactory().openSession();
        Evaluaciones evaluacion = null;
        try {
            session.beginTransaction();
            evaluacion = (Evaluaciones) session.get(Evaluaciones.class, evaluacionId);
        } catch (Exception e) {
            System.out.println("Error: " + e);
        } finally {
            session.getTransaction().commit();
            session.close();
        }
        return evaluacion;
    }
}
