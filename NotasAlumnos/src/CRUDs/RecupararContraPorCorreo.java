package CRUDs;

import POJOs.Docentes;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.mindrot.jbcrypt.BCrypt;

import java.security.SecureRandom;
import java.util.List;

public class RecupararContraPorCorreo {

    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private static final int PASSWORD_LENGTH = 8;

    // Genera una nueva contraseña temporal en texto plano
    public static String generatePassword() {
        SecureRandom random = new SecureRandom();
        StringBuilder password = new StringBuilder(PASSWORD_LENGTH);
        for (int i = 0; i < PASSWORD_LENGTH; i++) {
            password.append(CHARACTERS.charAt(random.nextInt(CHARACTERS.length())));
        }
        return password.toString();
    }

    // Asigna una nueva contraseña hasheada al docente si coincide por cui, usuario y nombre completo
    public static String recuperarContrasenia(String cui, String nombreUsuario, String nombreCompleto) {
        Session session = HibernateUtil.HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        String nuevaContrasenia = null;

        try {
            transaction = session.beginTransaction();

            Criteria criteria = session.createCriteria(Docentes.class);
            criteria.add(Restrictions.eq("cui", cui));
            criteria.add(Restrictions.eq("nombreUsuario", nombreUsuario));
            criteria.add(Restrictions.eq("nombreCompleto", nombreCompleto));

            List<Docentes> listaDocentes = criteria.list();

            if (listaDocentes.size() != 1) {
                return null;
            }

            Docentes docente = listaDocentes.get(0);
            nuevaContrasenia = generatePassword();

            // 🔐 Aplicar hash con BCrypt
            String hash = BCrypt.hashpw(nuevaContrasenia, BCrypt.gensalt(10));
            docente.setContrasenia(hash);

            session.update(docente);
            transaction.commit();

        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
            return null;
        } finally {
            session.close();
        }

        // Retornamos la contraseña en texto plano (para mostrarla temporalmente al usuario, si aplica)
        return nuevaContrasenia;
    }
}
