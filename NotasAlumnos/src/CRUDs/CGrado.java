package CRUDs;

import POJOs.Grados;
import HibernateUtil.HibernateUtil;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Session;

public class CGrado {

    // Método para listar todos los grados
    public static List<Grados> listarGrados() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<Grados> lista = null;
        try {
            session.beginTransaction();
            Criteria criteria = session.createCriteria(Grados.class);
            lista = criteria.list();
            session.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error al listar grados: " + e.getMessage());
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

    // Método para obtener un grado por ID
    public static Grados obtenerGradoPorId(int gradoId) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Grados grado = null;
        try {
            session.beginTransaction();
            grado = (Grados) session.get(Grados.class, gradoId);
            session.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error al obtener grado: " + e.getMessage());
            if (session.getTransaction().isActive()) {
                session.getTransaction().rollback();
            }
        } finally {
            if (session.isOpen()) {
                session.close();
            }
        }
        return grado;
    }
}
