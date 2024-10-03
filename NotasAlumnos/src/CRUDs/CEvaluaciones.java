package CRUDs;

import POJOs.Evaluaciones;
import POJOs.Bimestres;
import POJOs.Cursos;
import POJOs.Grados;
import POJOs.Secciones;
import java.math.BigDecimal;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

public class CEvaluaciones {

    // Método para listar todas las evaluaciones
    public static List<Evaluaciones> listarEvaluaciones() {
        Session session = HibernateUtil.HibernateUtil.getSessionFactory().openSession();
        List<Evaluaciones> lista = null;
        try {
            session.beginTransaction();
            Criteria criteria = session.createCriteria(Evaluaciones.class);
            lista = criteria.list();
        } catch (Exception e) {
            System.out.println("Error: " + e);
        } finally {
            session.getTransaction().commit();
            session.close();
        }
        return lista;
    }

    // Método para crear una nueva evaluación con idSeccion
    public static boolean crearEvaluacion(int bimestreId, int cursoId, int gradoId, int seccionId, String nombreEvaluacion, String tipo, BigDecimal ponderacion) {
        boolean flag = false;
        Session session = HibernateUtil.HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();

            // Obtener el bimestre al que pertenece la evaluación
            Bimestres bimestre = (Bimestres) session.get(Bimestres.class, bimestreId);
            if (bimestre == null) {
                throw new RuntimeException("El bimestre con ID " + bimestreId + " no existe.");
            }

            // Obtener el curso al que pertenece la evaluación
            Cursos curso = (Cursos) session.get(Cursos.class, cursoId);
            if (curso == null) {
                throw new RuntimeException("El curso con ID " + cursoId + " no existe.");
            }

            // Obtener el grado al que pertenece la evaluación
            Grados grado = (Grados) session.get(Grados.class, gradoId);
            if (grado == null) {
                throw new RuntimeException("El grado con ID " + gradoId + " no existe.");
            }

            // Obtener la sección al que pertenece la evaluación
            Secciones seccion = (Secciones) session.get(Secciones.class, seccionId);
            if (seccion == null) {
                throw new RuntimeException("La sección con ID " + seccionId + " no existe.");
            }

            // Crear la nueva evaluación
            Evaluaciones nuevaEvaluacion = new Evaluaciones();
            nuevaEvaluacion.setBimestres(bimestre);
            nuevaEvaluacion.setCursos(curso);
            nuevaEvaluacion.setGrados(grado);
            nuevaEvaluacion.setSecciones(seccion);
            nuevaEvaluacion.setNombreEvaluacion(nombreEvaluacion);
            nuevaEvaluacion.setTipo(tipo);
            nuevaEvaluacion.setPonderacion(ponderacion);

            // Guardar la evaluación
            session.save(nuevaEvaluacion);
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

    // Método para actualizar una evaluación existente con idSeccion
    public static boolean actualizarEvaluacion(int evaluacionId, int nuevoBimestreId, int nuevoCursoId, int nuevoGradoId, int nuevoSeccionId, String nuevoNombreEvaluacion, String nuevoTipo, BigDecimal nuevaPonderacion) {
        boolean flag = false;
        Session session = HibernateUtil.HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();

            // Obtener la evaluación existente
            Evaluaciones evaluacion = (Evaluaciones) session.get(Evaluaciones.class, evaluacionId);
            if (evaluacion != null) {
                evaluacion.setNombreEvaluacion(nuevoNombreEvaluacion);
                evaluacion.setTipo(nuevoTipo);
                evaluacion.setPonderacion(nuevaPonderacion);

                // Obtener el nuevo bimestre
                Bimestres nuevoBimestre = (Bimestres) session.get(Bimestres.class, nuevoBimestreId);
                if (nuevoBimestre == null) {
                    throw new RuntimeException("El bimestre con ID " + nuevoBimestreId + " no existe.");
                }

                // Obtener el nuevo curso
                Cursos nuevoCurso = (Cursos) session.get(Cursos.class, nuevoCursoId);
                if (nuevoCurso == null) {
                    throw new RuntimeException("El curso con ID " + nuevoCursoId + " no existe.");
                }

                // Obtener el nuevo grado
                Grados nuevoGrado = (Grados) session.get(Grados.class, nuevoGradoId);
                if (nuevoGrado == null) {
                    throw new RuntimeException("El grado con ID " + nuevoGradoId + " no existe.");
                }

                // Obtener la nueva sección
                Secciones nuevaSeccion = (Secciones) session.get(Secciones.class, nuevoSeccionId);
                if (nuevaSeccion == null) {
                    throw new RuntimeException("La sección con ID " + nuevoSeccionId + " no existe.");
                }

                // Asignar el nuevo bimestre, curso, grado y sección
                evaluacion.setBimestres(nuevoBimestre);
                evaluacion.setCursos(nuevoCurso);
                evaluacion.setGrados(nuevoGrado);
                evaluacion.setSecciones(nuevaSeccion);

                // Actualizar la evaluación
                session.update(evaluacion);
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

    // Método para eliminar una evaluación
    public static boolean eliminarEvaluacion(int evaluacionId) {
        boolean flag = false;
        Session session = HibernateUtil.HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();

            // Obtener la evaluación existente
            Evaluaciones evaluacion = (Evaluaciones) session.get(Evaluaciones.class, evaluacionId);
            if (evaluacion != null) {
                session.delete(evaluacion);  // Eliminar la evaluación
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
