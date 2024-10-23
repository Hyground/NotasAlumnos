package CRUDs;

import HibernateUtil.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import java.util.List;

/**
 * Clase Boletines que realiza el CRUD usando SQL nativo con Hibernate.
 */
public class Boletines {

    // Método para obtener el boletín de un estudiante usando su CUI
    public static List<Object[]> obtenerBoletinEstudiante(String cui) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<Object[]> resultado = null;

        try {
            Transaction tx = session.beginTransaction();

            // Consulta SQL nativa para obtener el boletín del estudiante
            String sql = "SELECT e.CUI, e.Nombre, e.Apellido, g.NombreGrado, s.NombreSeccion, "
                       + "c.NombreCurso, b.NombreBimestre, "
                       + "SUM(CASE WHEN ev.Tipo = 'Actividad' THEN n.Nota ELSE 0 END) AS SumaActividades "
                       + "FROM estudiantes e "
                       + "LEFT JOIN notas n ON e.CUI = n.CUI "
                       + "LEFT JOIN evaluaciones ev ON n.EvaluacionID = ev.EvaluacionID "
                       + "LEFT JOIN cursos c ON ev.CursoID = c.CursoID "
                       + "LEFT JOIN grados g ON e.GradoID = g.GradoID "
                       + "LEFT JOIN secciones s ON e.SeccionID = s.SeccionID "
                       + "LEFT JOIN bimestres b ON ev.BimestreID = b.BimestreID "
                       + "WHERE e.CUI = :cui "
                       + "GROUP BY e.CUI, e.Nombre, e.Apellido, g.NombreGrado, s.NombreSeccion, "
                       + "c.NombreCurso, b.NombreBimestre "
                       + "ORDER BY b.NombreBimestre, c.NombreCurso";

            // Ejecutamos la consulta SQL nativa con parámetros
            resultado = session.createSQLQuery(sql)
                               .setParameter("cui", cui)
                               .list();

            tx.commit();
        } catch (Exception e) {
            e.printStackTrace();
            if (session.getTransaction() != null) {
                session.getTransaction().rollback();
            }
        } finally {
            session.close();
        }
        return resultado;
    }
}
