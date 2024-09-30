package CRUDs;

import POJOs.Secciones;
import HibernateUtil.HibernateUtil;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Session;

public class CSeccion {

    // Método para listar todas las secciones
    public static List<Secciones> listarSecciones() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<Secciones> lista = null;
        try {
            session.beginTransaction();
            Criteria criteria = session.createCriteria(Secciones.class);
            lista = criteria.list();
            session.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error al listar secciones: " + e.getMessage());
            if (session.getTransaction().isActive()) {
                session.getTransaction().rollback();
            }
        } finally {
            if (session.isOpen()) {
                session.close();
            }
        }
        return lista;
    }

    // Método para obtener una sección por ID
    public static Secciones obtenerSeccionPorId(int seccionId) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Secciones seccion = null;
        try {
            session.beginTransaction();
            seccion = (Secciones) session.get(Secciones.class, seccionId);
            session.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error al obtener sección: " + e.getMessage());
            if (session.getTransaction().isActive()) {
                session.getTransaction().rollback();
            }
        } finally {
            if (session.isOpen()) {
                session.close();
            }
        }
        return seccion;
    }
}
