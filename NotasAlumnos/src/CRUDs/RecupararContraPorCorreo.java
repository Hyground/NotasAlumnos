package CRUDs;

import POJOs.Docentes; 
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import java.security.SecureRandom;
import java.util.List;

/**
 *
 * @author Carlos
 */

public class RecupararContraPorCorreo {

    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private static final int PASSWORD_LENGTH = 8;

    // Método para generar una nueva contraseña a nuestro ususaroios
    public static String generatePassword() {
        SecureRandom random = new SecureRandom();
        StringBuilder password = new StringBuilder(PASSWORD_LENGTH);
        for (int i = 0; i < PASSWORD_LENGTH; i++) {
            password.append(CHARACTERS.charAt(random.nextInt(CHARACTERS.length())));
        }
        return password.toString();
    }

    // Método para recuperar la contraseña y asignar una nueva
    public static String recuperarContrasenia(String cui, String nombreUsuario, String nombreCompleto) {
        Session session = HibernateUtil.HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        String nuevaContrasenia = null;

        try {
            transaction = session.beginTransaction();

            // Obtener el docente mediante CUI, nombre de usuario y nombre completo
            Criteria criteria = session.createCriteria(Docentes.class);
            criteria.add(Restrictions.eq("cui", cui));
            criteria.add(Restrictions.eq("nombreUsuario", nombreUsuario));
            criteria.add(Restrictions.eq("nombreCompleto", nombreCompleto));

            List<Docentes> listaDocentes = criteria.list();

            // Validamos de búsqueda de docentes
            if (listaDocentes.isEmpty() || listaDocentes.size() > 1) {
                return null; // Si no se encuentra o si hay más de un resultado, retornamos null
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

        return nuevaContrasenia;
    }
}
