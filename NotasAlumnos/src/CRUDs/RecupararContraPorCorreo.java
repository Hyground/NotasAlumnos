package CRUDs;


import POJOs.Docentes; 
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import java.security.SecureRandom;
import java.util.List;

public class RecupararContraPorCorreo {

    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private static final int PASSWORD_LENGTH = 8;

    public static String generatePassword() {
        SecureRandom random = new SecureRandom();
        StringBuilder password = new StringBuilder(PASSWORD_LENGTH);
        for (int i = 0; i < PASSWORD_LENGTH; i++) {
            password.append(CHARACTERS.charAt(random.nextInt(CHARACTERS.length())));
        }
        return password.toString();
    }

    public static String recuperarContrasenia(String cui, String nombreUsuario, String nombreCompleto) {
        Session session = HibernateUtil.HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        String nuevaContrasenia = null;

        try {
            transaction = session.beginTransaction();

            // Agregar mensajes para ver los valores que estás buscando
            System.out.println("Buscando docente con CUI: " + cui + ", Usuario: " + nombreUsuario + ", Nombre Completo: " + nombreCompleto);

            // Obtener el docente mediante CUI, nombre de usuario y nombre completo
            Criteria criteria = session.createCriteria(Docentes.class);
            criteria.add(Restrictions.eq("cui", cui));
            criteria.add(Restrictions.eq("nombreUsuario", nombreUsuario));
            criteria.add(Restrictions.eq("nombreCompleto", nombreCompleto));

            List<Docentes> listaDocentes = criteria.list();

            if (listaDocentes.isEmpty()) {
                System.out.println("No se encontró ningún docente con esos datos");
                return null;
            }

            if (listaDocentes.size() > 1) {
                System.out.println("Se encontraron múltiples docentes con los mismos datos");
                return null;
            }

            // Generar la nueva contraseña
            Docentes docente = listaDocentes.get(0);
            nuevaContrasenia = generatePassword();
            docente.setContrasenia(nuevaContrasenia);

            // Actualizar la nueva contraseña en la base de datos
            session.update(docente);
            transaction.commit();

       
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
            return null;
        } finally {
            session.close();
        }

        System.out.println("Contraseña generada con éxito para el docente: " + nuevaContrasenia);
        return nuevaContrasenia;
    }
}
