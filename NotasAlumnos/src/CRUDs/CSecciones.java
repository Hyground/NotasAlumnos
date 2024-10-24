package CRUDs;

import POJOs.Secciones;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author Alonzo Morales
 */

public class CSecciones {

    // Método para listar todas las secciones
    public static List<Secciones> listarSecciones() {
        Session session = HibernateUtil.HibernateUtil.getSessionFactory().openSession();
        List<Secciones> lista = null;
        try {
            session.beginTransaction();
            Criteria criteria = session.createCriteria(Secciones.class);
            lista = criteria.list();
        } catch (Exception e) {
            System.out.println("Error: " + e);
        } finally {
            session.getTransaction().commit();
            session.close();
        }
        return lista;
    }
    
    

    // Método para crear una nueva sección
   public static boolean crearSecciones() {
    boolean flag = false;
    Session session = HibernateUtil.HibernateUtil.getSessionFactory().openSession();
    Transaction transaction = null;

    try {
        transaction = session.beginTransaction();

        // Recorrer las letras de "A" a "Z"
        for (char letra = 'A'; letra <= 'Z'; letra++) {
            String nombreSeccion = String.valueOf(letra);

            // Verificar si ya existe una sección con ese nombre
            Criteria criteria = session.createCriteria(Secciones.class);
            criteria.add(Restrictions.eq("nombreSeccion", nombreSeccion));

            Secciones existente = (Secciones) criteria.uniqueResult();

            if (existente == null) {
                // Crear una nueva sección si no existe una con el mismo nombre
                Secciones nuevaSeccion = new Secciones();
                nuevaSeccion.setNombreSeccion(nombreSeccion);

                // Guardar la nueva sección
                session.save(nuevaSeccion);
                flag = true;
                System.out.println("Sección " + nombreSeccion + " creada.");
            } else {
                System.out.println("Sección " + nombreSeccion + " ya existe, no se creó.");
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

    // Método para obtener una sección por su ID
    public static Secciones obtenerSeccionPorId(int seccionId) {
        Session session = HibernateUtil.HibernateUtil.getSessionFactory().openSession();
        Secciones seccion = null;
        try {
            session.beginTransaction();
            seccion = (Secciones) session.get(Secciones.class, seccionId);
        } catch (Exception e) {
            System.out.println("Error: " + e);
        } finally {
            session.getTransaction().commit();
            session.close();
        }
        return seccion;
    }
    public static Secciones obtenerSeccionPorNombre(String nombreSeccion) {
    Session session = HibernateUtil.HibernateUtil.getSessionFactory().openSession();
    Secciones seccion = null;
    try {
        session.beginTransaction();
        Criteria criteria = session.createCriteria(Secciones.class);
        criteria.add(Restrictions.eq("nombreSeccion", nombreSeccion));
        seccion = (Secciones) criteria.uniqueResult();
    } catch (Exception e) {
        e.printStackTrace();
    } finally {
        session.getTransaction().commit();
        session.close();
    }
    return seccion;
}
}
