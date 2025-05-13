package CRUDs;

import POJOs.Docentes;
import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.mindrot.jbcrypt.BCrypt;

public class CLogin {

    public static boolean CLogin(String nombreUsuario, String contrasenia) {
        Session session = HibernateUtil.HibernateUtil.getSessionFactory().openSession();
        boolean isAuthenticated = false;

        try {
            session.beginTransaction();

            Criteria criteria = session.createCriteria(Docentes.class);
            criteria.add(Restrictions.eq("nombreUsuario", nombreUsuario));
            criteria.add(Restrictions.eq("borradoLogico", true));

            Docentes docente = (Docentes) criteria.uniqueResult();

            if (docente != null && BCrypt.checkpw(contrasenia, docente.getContrasenia())) {
                isAuthenticated = true;
            }

            session.getTransaction().commit();
        } catch (Exception e) {
            if (session.getTransaction() != null) session.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }

        return isAuthenticated;
    }

    public static Docentes obtenerDocentePorNombreUsuario(String nombreUsuario) {
        Session session = HibernateUtil.HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        Docentes docente = null;

        try {
            transaction = session.beginTransaction();

            Criteria criteria = session.createCriteria(Docentes.class);
            criteria.add(Restrictions.eq("nombreUsuario", nombreUsuario));
            docente = (Docentes) criteria.uniqueResult();

            if (docente != null) {
                Hibernate.initialize(docente.getGrados());
                Hibernate.initialize(docente.getSecciones());
            }

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }

        return docente;
    }
}
