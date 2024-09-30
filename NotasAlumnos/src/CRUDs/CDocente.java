package CRUDs;

import POJOs.Docentes;
import POJOs.Grados;
import POJOs.Secciones;
import HibernateUtil.HibernateUtil;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

public class CDocente {

    // Método para listar todos los docentes activos
    public static List<Docentes> listarDocentes() {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        List<Docentes> lista = null;
        try {
            session.beginTransaction();
            Criteria criteria = session.createCriteria(Docentes.class);
            criteria.add(Restrictions.eq("borradoLogico", true));
            lista = criteria.list();
            session.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error al listar docentes: " + e.getMessage());
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

    // Método para crear un nuevo docente
    public static boolean crearDocente(String nombreUsuario, String contrasenia, String rol, int gradoID, int seccionID) {
        boolean flag = false;
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();

            // Verificar si ya existe un docente con el mismo nombre de usuario
            Criteria criteria = session.createCriteria(Docentes.class);
            criteria.add(Restrictions.eq("nombreUsuario", nombreUsuario));
            criteria.add(Restrictions.eq("borradoLogico", true));

            Docentes existente = (Docentes) criteria.uniqueResult();

            if (existente == null) {
                // Obtener el grado y la sección
                Grados grado = (Grados) session.get(Grados.class, gradoID);
                Secciones seccion = (Secciones) session.get(Secciones.class, seccionID);

                if (grado != null && seccion != null) {
                    // Si no existe, crear un nuevo docente
                    Docentes nuevoDocente = new Docentes();
                    nuevoDocente.setNombreUsuario(nombreUsuario);
                    nuevoDocente.setContrasenia(contrasenia);
                    nuevoDocente.setRol(rol);
                    nuevoDocente.setGrados(grado);
                    nuevoDocente.setSecciones(seccion);
                    nuevoDocente.setBorradoLogico(true);

                    // Guardar el nuevo docente
                    session.save(nuevoDocente);
                    transaction.commit();
                    flag = true;
                } else {
                    System.out.println("El grado o la sección especificada no existe.");
                    transaction.rollback();
                }
            } else {
                System.out.println("Ya existe un docente con el nombre de usuario especificado.");
                transaction.rollback();
            }

        } catch (Exception e) {
            System.out.println("Error al crear docente: " + e.getMessage());
            if (transaction != null) {
                transaction.rollback();
            }
        } finally {
            session.close();
        }
        return flag;
    }

}
