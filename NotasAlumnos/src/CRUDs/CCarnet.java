/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CRUDs;

import HibernateUtil.HibernateUtil;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author USUARIO
 */
public class CCarnet {
    public static Object[] obtenerDatosCarnet(String cui) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Object[] resultado = null;

        try {
            Transaction tx = session.beginTransaction();
            
            String sql = "SELECT e.CUI, e.Nombre, e.Apellido, g.NombreGrado, s.NombreSeccion "                                 
                    + "FROM eduscore.estudiantes e "
                    + "LEFT JOIN eduscore.grados g ON e.GradoID = g.GradoID "
                    + "LEFT JOIN eduscore.secciones s ON e.SeccionID = s.SeccionID "
                    + "WHERE e.CUI = :cui";
            
            List<Object[]> lista = session.createSQLQuery(sql)
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

