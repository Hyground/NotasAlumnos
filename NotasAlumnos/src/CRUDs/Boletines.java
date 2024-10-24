package CRUDs;

import HibernateUtil.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import java.util.List;

/**
 *
 * @author MIguel
 */

public class Boletines {

    public static List<Object[]> obtenerBoletinEstudiante(String cui) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<Object[]> resultado = null;

        try {
            Transaction tx = session.beginTransaction();

            String sql = "SELECT e.CUI, e.Nombre, e.Apellido, g.NombreGrado, s.NombreSeccion, "
                       + "c.NombreCurso, "
                       + "SUM(CASE WHEN b.NombreBimestre = 'I UNIDAD' THEN n.Nota ELSE 0 END) AS Unidad1, "
                       + "SUM(CASE WHEN b.NombreBimestre = 'II UNIDAD' THEN n.Nota ELSE 0 END) AS Unidad2, "
                       + "SUM(CASE WHEN b.NombreBimestre = 'III UNIDAD' THEN n.Nota ELSE 0 END) AS Unidad3, "
                       + "SUM(CASE WHEN b.NombreBimestre = 'IV UNIDAD' THEN n.Nota ELSE 0 END) AS Unidad4 "
                       + "FROM eduscore.estudiantes e "
                       + "LEFT JOIN eduscore.notas n ON e.CUI = n.CUI "
                       + "LEFT JOIN eduscore.evaluaciones ev ON n.EvaluacionID = ev.EvaluacionID "
                       + "LEFT JOIN eduscore.cursos c ON ev.CursoID = c.CursoID "
                       + "LEFT JOIN eduscore.grados g ON e.GradoID = g.GradoID "
                       + "LEFT JOIN eduscore.secciones s ON e.SeccionID = s.SeccionID "
                       + "LEFT JOIN eduscore.bimestres b ON ev.BimestreID = b.BimestreID "
                       + "WHERE e.CUI = :cui "
                       + "GROUP BY e.CUI, e.Nombre, e.Apellido, g.NombreGrado, s.NombreSeccion, c.NombreCurso "
                       + "ORDER BY c.NombreCurso";

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
