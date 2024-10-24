/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CRUDs;
import POJOs.Docentes;
import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author Frank
 */

public class CLogin {
        // Método para verificar el login
    public static boolean CLogin(String nombreUsuario, String contrasenia) {
        Session session = HibernateUtil.HibernateUtil.getSessionFactory().openSession();
        boolean isAuthenticated = false;

        try {
            session.beginTransaction();

            // Buscar el docente que coincida con el nombre de usuario y la contraseña
            Criteria criteria = session.createCriteria(Docentes.class);
            criteria.add(Restrictions.eq("nombreUsuario", nombreUsuario));
            criteria.add(Restrictions.eq("contrasenia", contrasenia));
            criteria.add(Restrictions.eq("borradoLogico", true));  // Solo docentes activos

            Docentes docente = (Docentes) criteria.uniqueResult();  // Obtener único resultado

            if (docente != null) {
                isAuthenticated = true;  // Login exitoso
            } else {
                System.out.println("Nombre de usuario o contraseña incorrectos.");
            }

            session.getTransaction().commit();
        } catch (Exception e) {
            if (session.getTransaction() != null) {
                session.getTransaction().rollback();
            }
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
            // no sabemos que hacer mas que forzar :V
            Hibernate.initialize(docente.getGrados());
            Hibernate.initialize(docente.getSecciones());
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

    return docente;
}

    
}
