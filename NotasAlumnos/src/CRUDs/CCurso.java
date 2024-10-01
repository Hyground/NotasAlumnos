package CRUDs;

import POJOs.Cursos;
import POJOs.Grados;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

public class CCurso {

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
                        System.out.println("Curso " + nombreCurso + " para grado " + nombreGrado + " creado.");
                    } else {
                        System.out.println("Curso " + nombreCurso + " para grado " + nombreGrado + " ya existe.");
                    }
                }
            } else {
                System.out.println("No se encontró el grado " + nombreGrado);
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
                        System.out.println("Curso " + nombreCurso + " para grado " + nombreGrado + " creado.");
                    } else {
                        System.out.println("Curso " + nombreCurso + " para grado " + nombreGrado + " ya existe.");
                    }
                }
            } else {
                System.out.println("No se encontró el grado " + nombreGrado);
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


    // Método para actualizar un curso existente
   /* 
    public static boolean actualizarCurso(int cursoId, String nuevoNombreCurso, int nuevoGradoId) {
        boolean flag = false;
        Session session = HibernateUtil.HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        
        try {
            transaction = session.beginTransaction();
            
            // Obtener el curso existente
            Cursos curso = (Cursos) session.get(Cursos.class, cursoId);
            if (curso != null) {
                curso.setNombreCurso(nuevoNombreCurso);

                // Obtener el nuevo grado
                Grados nuevoGrado = (Grados) session.get(Grados.class, nuevoGradoId);
                if (nuevoGrado == null) {
                    throw new RuntimeException("El grado con ID " + nuevoGradoId + " no existe.");
                }

                // Asignar el nuevo grado al curso
                curso.setGrados(nuevoGrado);
                
                // Actualizar el curso
                session.update(curso);
                flag = true;
            } else {
                System.out.println("No se encontró el curso con ID " + cursoId);
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

    // Método para eliminar un curso
    public static boolean eliminarCurso(int cursoId) {
        boolean flag = false;
        Session session = HibernateUtil.HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        
        try {
            transaction = session.beginTransaction();
            
            // Obtener el curso existente
            Cursos curso = (Cursos) session.get(Cursos.class, cursoId);
            if (curso != null) {
                session.delete(curso);  // Eliminar el curso
                flag = true;
            } else {
                System.out.println("No se encontró el curso con ID " + cursoId);
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
*/
    // Método para obtener un curso por su ID
    public static Cursos obtenerCursoPorId(int cursoId) {
        Session session = HibernateUtil.HibernateUtil.getSessionFactory().openSession();
        Cursos curso = null;
        try {
            session.beginTransaction();
            curso = (Cursos) session.get(Cursos.class, cursoId);
        } catch (Exception e) {
            System.out.println("Error: " + e);
        } finally {
            session.getTransaction().commit();
            session.close();
        }
        return curso;
    }
}
