package CRUDs;

import POJOs.Bimestres;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

public class CBimestres {

    // Método para listar todos los bimestres
    public static List<Bimestres> listarBimestres() {
        Session session = HibernateUtil.HibernateUtil.getSessionFactory().getCurrentSession();
        List<Bimestres> lista = null;
        try {
            session.beginTransaction();
            Criteria criteria = session.createCriteria(Bimestres.class);
            criteria.setProjection(Projections.projectionList()
                    .add(Projections.property("bimestreId"))
                    .add(Projections.property("nombreBimestre"))
            );
            criteria.addOrder(Order.desc("bimestreId"));
            lista = criteria.list();

        } catch (Exception e) {
            System.out.println("Error: " + e);
        } finally {
            session.getTransaction().commit();
            session.close();
        }
        return lista;
    }

    // Método para crear un nuevo bimestre
    public static boolean crearBimestres() {
        boolean flag = false;
        Session session = HibernateUtil.HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;

        // Definir los nombres de los bimestres
        String[] bimestresNombres = {"I UNIDAD", "II UNIDAD", "III UNIDAD", "IV UNIDAD"};

        try {
            transaction = session.beginTransaction();

            // Recorrer los nombres de los bimestres
            for (String nombreBimestre : bimestresNombres) {

                // Verificar si ya existe un bimestre con ese nombre
                Criteria criteria = session.createCriteria(Bimestres.class);
                criteria.add(Restrictions.eq("nombreBimestre", nombreBimestre));

                Bimestres existente = (Bimestres) criteria.uniqueResult();

                if (existente == null) {
                    // Crear un nuevo bimestre si no existe uno con el mismo nombre
                    Bimestres nuevoBimestre = new Bimestres();
                    nuevoBimestre.setNombreBimestre(nombreBimestre);

                    // Guardar el nuevo bimestre
                    session.save(nuevoBimestre);
                    flag = true;
                    System.out.println("Bimestre " + nombreBimestre + " creado.");
                } else {
                    System.out.println("Bimestre " + nombreBimestre + " ya existe.");
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

    // Método para obtener un bimestre por su ID
    public static Bimestres obtenerBimestrePorId(int bimestreId) {
        Session session = HibernateUtil.HibernateUtil.getSessionFactory().openSession();
        Bimestres bimestre = null;
        try {
            session.beginTransaction();
            bimestre = (Bimestres) session.get(Bimestres.class, bimestreId);
        } catch (Exception e) {
            System.out.println("Error: " + e);
        } finally {
            session.getTransaction().commit();
            session.close();
        }
        return bimestre;
    }
}
