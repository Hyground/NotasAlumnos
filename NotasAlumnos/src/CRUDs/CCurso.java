package CRUDs;

import POJOs.Cursos;
import POJOs.Grados;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.NonUniqueResultException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CCurso {
    
private static final Logger logger = LoggerFactory.getLogger(CCurso.class);
    // Método para listar todos los cursos
public static List<Cursos> listarCursos() {
    Session session = HibernateUtil.HibernateUtil.getSessionFactory().openSession();
    List<Cursos> lista = null;
    try {
        session.beginTransaction();
        Criteria criteria = session.createCriteria(Cursos.class);
        lista = criteria.list();
    } catch (Exception e) {
        System.out.println("Error: " + e);
    } finally {
        session.getTransaction().commit();
        session.close();
    }
    return lista;
}


    // Método para crear un nuevo curso
public static boolean crearCursos() {
    boolean flag = false;
    Session session = HibernateUtil.HibernateUtil.getSessionFactory().openSession();
    Transaction transaction = null;

    // Definir los cursos para Primero a Tercero
    String[] cursosPrimeroATercero = {
        "L1. IDIOMA MATERNO",
        "L2. SEGUNDO IDIOMA",
        "L3. TERCER IDIOMA",
        "MATEMÁTICAS",
        "MEDIO SOCIAL Y NATURAL",
        "EXPRESIÓN ARTÍSTICA",
        "EDUCACIÓN FÍSICA",
        "FORMACIÓN CIUDADANA"
    };

    // Definir los cursos para Cuarto a Sexto
    String[] cursosCuartoASexto = {
        "L1. IDIOMA MATERNO",
        "L2. SEGUNDO IDIOMA",
        "L3. TERCER IDIOMA",
        "MATEMÁTICAS",
        "CIENCIAS NATURALES Y TECNOLOGÍA",
        "CIENCIAS SOCIALES",
        "EXPRESIÓN ARTÍSTICA",
        "EDUCACIÓN FÍSICA",
        "FORMACIÓN CIUDADANA",
        "PRODUCTIVIDAD Y DESARROLLO"
    };

    // Grados para Primero a Tercero
    String[] gradosPrimeroATercero = {"Primero", "Segundo", "Tercero"};
    
    // Grados para Cuarto a Sexto
    String[] gradosCuartoASexto = {"Cuarto", "Quinto", "Sexto"};

    try {
        transaction = session.beginTransaction();

        // Crear cursos para grados de Primero a Tercero
        for (String nombreGrado : gradosPrimeroATercero) {
            // Obtener el grado
            Criteria gradoCriteria = session.createCriteria(Grados.class);
            gradoCriteria.add(Restrictions.eq("nombreGrado", nombreGrado));
            Grados grado = (Grados) gradoCriteria.uniqueResult();

            if (grado != null) {
                for (String nombreCurso : cursosPrimeroATercero) {
                    // Verificar si ya existe un curso con el mismo nombre y grado
                    Criteria criteria = session.createCriteria(Cursos.class);
                    criteria.add(Restrictions.eq("nombreCurso", nombreCurso));
                    criteria.add(Restrictions.eq("grados.gradoId", grado.getGradoId()));

                    Cursos existente = (Cursos) criteria.uniqueResult();

                    if (existente == null) {
                        // Crear un nuevo curso si no existe uno con el mismo nombre y grado
                        Cursos nuevoCurso = new Cursos();
                        nuevoCurso.setNombreCurso(nombreCurso);
                        nuevoCurso.setGrados(grado);

                        // Guardar el nuevo curso
                        session.save(nuevoCurso);
                        flag = true;
                        logger.info("Curso " + nombreCurso + " para grado " + nombreGrado + " creado.");
                    } else {
                        logger.info("Curso " + nombreCurso + " para grado " + nombreGrado + " ya existe.");
                    }
                }
            } else {
                logger.error("No se encontró el grado " + nombreGrado);
            }
        }

        // Crear cursos para grados de Cuarto a Sexto
        for (String nombreGrado : gradosCuartoASexto) {
            // Obtener el grado
            Criteria gradoCriteria = session.createCriteria(Grados.class);
            gradoCriteria.add(Restrictions.eq("nombreGrado", nombreGrado));
            Grados grado = (Grados) gradoCriteria.uniqueResult();

            if (grado != null) {
                for (String nombreCurso : cursosCuartoASexto) {
                    // Verificar si ya existe un curso con el mismo nombre y grado
                    Criteria criteria = session.createCriteria(Cursos.class);
                    criteria.add(Restrictions.eq("nombreCurso", nombreCurso));
                    criteria.add(Restrictions.eq("grados.gradoId", grado.getGradoId()));

                    Cursos existente = (Cursos) criteria.uniqueResult();

                    if (existente == null) {
                        // Crear un nuevo curso si no existe uno con el mismo nombre y grado
                        Cursos nuevoCurso = new Cursos();
                        nuevoCurso.setNombreCurso(nombreCurso);
                        nuevoCurso.setGrados(grado);

                        // Guardar el nuevo curso
                        session.save(nuevoCurso);
                        flag = true;
                        logger.info("Curso " + nombreCurso + " para grado " + nombreGrado + " creado.");
                    } else {
                       logger.info("Curso " + nombreCurso + " para grado " + nombreGrado + " ya existe.");
                    }
                }
            } else {
                logger.error("No se encontró el grado " + nombreGrado);
            }
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


    public static Cursos obtenerCursoPorId(int cursoId) {
        Session session = HibernateUtil.HibernateUtil.getSessionFactory().openSession();
        Cursos curso = null;
        try {
            session.beginTransaction();
            curso = (Cursos) session.get(Cursos.class, cursoId);
        } catch (Exception e) {
            logger.error("Error: " + e);
        } finally {
            session.getTransaction().commit();
            session.close();
        }
        return curso;
    }
    public static List<Cursos> listarCursosPorGrado(int gradoId) {
    Session session = HibernateUtil.HibernateUtil.getSessionFactory().openSession();
    List<Cursos> lista = null;
    try {
        session.beginTransaction();
        Criteria criteria = session.createCriteria(Cursos.class);
        criteria.createAlias("grados", "g"); // Hacer un alias para la relación con Grados
        criteria.add(Restrictions.eq("g.gradoId", gradoId)); // Filtrar por gradoId
        lista = criteria.list();
    } catch (Exception e) {
        System.out.println("Error: " + e);
    } finally {
        session.getTransaction().commit();
        session.close();
    }
    return lista;
}
public static Cursos obtenerCursoPorNombreYGrado(String nombreCurso, Integer gradoId) {
    Session session = HibernateUtil.HibernateUtil.getSessionFactory().openSession();
    Cursos curso = null;
    try {
        session.beginTransaction();
        Criteria criteria = session.createCriteria(Cursos.class);
        criteria.add(Restrictions.eq("nombreCurso", nombreCurso));
        criteria.createAlias("grados", "g");
        criteria.add(Restrictions.eq("g.gradoId", gradoId)); // Filtrar también por el gradoId
        curso = (Cursos) criteria.uniqueResult(); // Esto ahora debería devolver solo un resultado
    } catch (NonUniqueResultException e) {
        System.out.println("Más de un curso encontrado con el mismo nombre y grado.");
    } catch (Exception e) {
        e.printStackTrace();
    } finally {
        session.getTransaction().commit();
        session.close();
    }
    return curso;
}


}
