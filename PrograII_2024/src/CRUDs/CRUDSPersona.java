/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CRUDs;


/**
 *
 * @author vicen
 */
import POJOs.Persona;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
/**
 *
 * @author USUARIO
 */
public class CRUDSPersona {
    public static List<Persona>universo(){
        Session session=HibernateUtil.HibernateUtil.getSessionFactory().getCurrentSession();
        List<Persona> lista=null;
        try {
            session.beginTransaction();
            Criteria criteria=session.createCriteria(Persona.class);
            criteria.add(Restrictions.eq("estado",true));
            criteria.setProjection(Projections.projectionList()
                    .add(Projections.property("idPersona"))
                    .add(Projections.property("nombre"))
                    .add(Projections.property("cedula"))
                    .add(Projections.property("edad"))
            );
            criteria.addOrder(Order.desc("idPersona"));
            lista =criteria.list();
                    
        } catch (Exception e) {
            System.out.println("error"+e);
        } finally{
        session.getTransaction().commit();
        
        }
        return lista;
    }
      public static boolean crear(String nombre, Integer cedula, Integer edad){
        
  boolean flag=false;
            Session session=HibernateUtil.HibernateUtil.getSessionFactory().openSession();
            Criteria criteria=session.createCriteria(Persona.class);
            criteria.add(Restrictions.eq("cedula", cedula));
            criteria.add(Restrictions.eq("estado", true));
            Persona insert = (Persona)criteria.uniqueResult();
            Transaction transaction = null;
            
            try {
                transaction = session.beginTransaction();
                
                if (insert==null) {
                    insert=new Persona();
                    insert.setNombre(nombre);
                    insert.setCedula(cedula);
                    insert.setEdad(edad);
                    insert.setEstado(true);
                    session.save(insert);
                    flag = true;
                }
                
                transaction.commit();
                
            } catch (Exception e) {
                transaction.rollback();
            }finally{
            session.close();
            }  
            return flag;
        }
        
public static boolean actualizar(Integer idPersona, String nombre, Integer cedula, Integer edad) {
    boolean flag = false;
    Session session = HibernateUtil.HibernateUtil.getSessionFactory().openSession();
    Criteria criteria = session.createCriteria(Persona.class);
    criteria.add(Restrictions.eq("idPersona", idPersona)); // Corregir a "idPersona"
    Persona insert = (Persona) criteria.uniqueResult(); // Obtener la persona existente
    Transaction transaction = null;

    try {
        transaction = session.beginTransaction();

        if (insert != null) { // Si la persona existe
            insert.setNombre(nombre);   // Actualiza los campos
            insert.setCedula(cedula);
            insert.setEdad(edad);
            session.update(insert);     // Guarda los cambios en la base de datos
            flag = true;                // Actualización exitosa
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

            public static boolean anular(Integer idPersona) {
    boolean flag = false;
    Session session = HibernateUtil.HibernateUtil.getSessionFactory().openSession();
    Criteria criteria = session.createCriteria(Persona.class);
    criteria.add(Restrictions.eq("idPersona", idPersona)); // Corregir el nombre a "idPersona"
    Persona anular = (Persona) criteria.uniqueResult(); // Buscar la persona por id

    Transaction transaction = null;

    try {
        transaction = session.beginTransaction();

        if (anular != null) { // Si la persona existe
            anular.setEstado(false); // Cambiar el estado a false
            session.update(anular);  // Actualizar el objeto en la base de datos
            flag = true;             // Operación exitosa
        } else {
            System.out.println("No se encontró la persona con idPersona: " + idPersona);
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

    
}