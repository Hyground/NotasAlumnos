package CRUDs;

import POJOs.Grados;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

public class CGrados {

    // Método para listar todos los grados
    public static List<Grados> listarGrados() {
        Session session = HibernateUtil.HibernateUtil.getSessionFactory().openSession();
        List<Grados> lista = null;
        try {
            session.beginTransaction();
            Criteria criteria = session.createCriteria(Grados.class);
            lista = criteria.list();
        } catch (Exception e) {
            System.out.println("Error: " + e);
        } finally {
            session.getTransaction().commit();
            session.close();
        }
        return lista;
    }

    // Método para crear un nuevo grado
public static boolean crearGrados() {
    boolean flag = false;
    Session session = HibernateUtil.HibernateUtil.getSessionFactory().openSession();
    Transaction transaction = null;

    // Lista de nombres de grados a crear
    String[] gradosNombres = {"Primero", "Segundo", "Tercero", "Cuarto", "Quinto", "Sexto"};

    try {
        transaction = session.beginTransaction();

        // Recorrer los nombres de los grados
        for (String nombreGrado : gradosNombres) {

            // Verificar si ya existe un grado con ese nombre
            Criteria criteria = session.createCriteria(Grados.class);
            criteria.add(Restrictions.eq("nombreGrado", nombreGrado));

            Grados existente = (Grados) criteria.uniqueResult();

            if (existente == null) {
                // Crear un nuevo grado si no existe uno con el mismo nombre
                Grados nuevoGrado = new Grados();
                nuevoGrado.setNombreGrado(nombreGrado);

                // Guardar el nuevo grado
                session.save(nuevoGrado);
                flag = true;
                System.out.println("Grado " + nombreGrado + " creado.");
            } else {
                System.out.println("Grado " + nombreGrado + " ya existe, no se creó.");
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


    public static Grados obtenerGradoPorId(int gradoId) {
        Session session = HibernateUtil.HibernateUtil.getSessionFactory().openSession();
        Grados grado = null;
        try {
            session.beginTransaction();
            grado = (Grados) session.get(Grados.class, gradoId);
        } catch (Exception e) {
            System.out.println("Error: " + e);
        } finally {
            session.getTransaction().commit();
            session.close();
        }
        return grado;
    }
}
