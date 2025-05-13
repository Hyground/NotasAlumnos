package util;

import java.util.Properties;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class EmailService {

    public static void enviarCorreo(String destinatario, String nuevaContrasenia, String nombreCompleto, String cui, String nombreUsuario) {

        // Configurar propiedades del servidor de correo (SMTP) de Hostinger
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com"); //  Hostinger
        props.put("mail.smtp.socketFactory.port", "465"); // Puerto SMTP de Hostinger
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory"); // Encriptación SSL
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "465");

        // Credenciales del correo principal (de Hostinger)
        final String correoPrincipal = "edugt.info@gmail.com";  // Correo de Hostinger
        final String contraseniaPrincipal = "wknj hwas iwxa npit";  // Contraseña

        // Autenticación del correo desde el que se enviará
        Session session = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(correoPrincipal, contraseniaPrincipal);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(correoPrincipal)); // Correo principal
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(destinatario)); // Correo del destinatario
            message.setSubject("Recuperación de contraseña");

            // Construimos el cuerpo del correo con todos los datos
            String mensajeCorreo = "Hola " + nombreCompleto + ",\n\n" +
                    "Se ha solicitado una recuperación de contraseña para su cuenta.\n\n" +
                    "A continuación se encuentran los detalles de su cuenta:\n" +
                    "Nombre de Usuario: " + nombreUsuario + "\n" +
                    "CUI: " + cui + "\n\n" +
                    "Su nueva contraseña es: " + nuevaContrasenia + "\n\n" +
                    "Le recomendamos cambiar esta contraseña tras iniciar sesión.\n\n" +
                    "Atentamente,\nEl equipo de soporte.";

            message.setText(mensajeCorreo);

            // Enviar el correo
            Transport.send(message);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
