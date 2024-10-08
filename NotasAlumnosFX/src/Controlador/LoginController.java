/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import POJOs.Docentes;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

/**
 * FXML Controller class
 *
 * @author hygro
 */
public class LoginController implements Initializable {

    @FXML
    private Button btnIngreso;
    @FXML
    private CheckBox docenteOn;
    @FXML
    private Label label1;
    @FXML
    private Button btnLimpiar;
    @FXML
    private Label label2;
    @FXML
    private CheckBox adminOn;
    @FXML
    private TextField txtUser;
    @FXML
    private TextField txtContra;
    @FXML
    private Label lblUser;
    @FXML
    private Label lblContra;
    @FXML
    private Label label3;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO

    }
//ARREGLALO SI ESTA MAL XD
    public static Docentes validarUsuario(String nombreUsuario, String contrasenia, CheckBox checkboxDocente, CheckBox checkboxAdmin) {
        Session session = HibernateUtil.HibernateUtil.getSessionFactory().openSession();
        Docentes docente = null;
        try {
            session.beginTransaction();

            // Buscar al usuario por nombre de usuario
            Criteria criteria = session.createCriteria(Docentes.class);
            criteria.add(Restrictions.eq("nombreUsuario", nombreUsuario));
            docente = (Docentes) criteria.uniqueResult();

            // Si se encuentra el usuario, validar su rol y contraseña
            if (docente != null) {
                if (docente.getContrasenia().equals(contrasenia)) {
                    String rol = docente.getRol();

                    // Validar el rol según el checkbox seleccionado
                    if (checkboxDocente.isSelected() && rol.equalsIgnoreCase("docente")) {
                        cargarFXML("Docente.fxml");
                    } else if (checkboxAdmin.isSelected() && rol.equalsIgnoreCase("admin")) {
                        cargarFXML("Admin.fxml");
                    } else {
                        System.out.println("Rol o selección no reconocidos.");
                    }
                } else {
                    System.out.println("Contraseña incorrecta.");
                }
            } else {
                System.out.println("Usuario no encontrado.");
            }

        } catch (Exception e) {
            System.out.println("Error: " + e);
        } finally {
            session.getTransaction().commit();
            session.close();
        }

        return docente;
    }
    // Método para cargar el FXML

    private static void cargarFXML(String fxml) {
        try {
            FXMLLoader loader = new FXMLLoader(Docentes.class.getResource("/Controlador/"+fxml));  // Ajustar según tu clase
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //TODO PARA EL CSS NO TOCAR XD
    @FXML
    private void cambio(javafx.event.ActionEvent event) {
        // Lógica para permitir solo una checkbox seleccionada a la vez
        if (event.getSource() == docenteOn) {
            if (docenteOn.isSelected()) {
                adminOn.setSelected(false); // Deselecciona el otro checkbox
            }
        } else if (event.getSource() == adminOn) {
            if (adminOn.isSelected()) {
                docenteOn.setSelected(false); // Deselecciona el otro checkbox
            }
        }
        // Lógica para cambiar los textos
        if (docenteOn.isSelected()) {
            label1.setText("Bienvenido docente");
            label2.setText("Por favor inicia sesión con los");
            label3.setText("datos que se te proporcionaron");
        } else if (adminOn.isSelected()) {
            label1.setText("BIENVENIDO ADMIN");
            label2.setText("INGRESA TU USUARIO ADMIN");
            label3.setText("");
        }
    }

    private boolean animacionEjecutada = false; // Variable de control para la animación
    private boolean animacionEjecutada2 = false; // Variable de control para la animación2

    @FXML
    private void mover(javafx.scene.input.MouseEvent event) {
        // Verifica si la animación ya se ejecutó
        if (!animacionEjecutada) {
            // Configura la animación
            TranslateTransition move = new TranslateTransition();
            move.setNode(lblUser);
            move.setDuration(Duration.millis(1000));
            move.setCycleCount(1); // Asegúrate de que se ejecute solo una vez
            move.setByY(-30);
            move.play();

            // Cambia el estado para evitar que se ejecute nuevamente
            animacionEjecutada = true;
        }
    }

    @FXML
    private void mover2(javafx.scene.input.MouseEvent event) {
        // Verifica si la animación ya se ejecutó
        if (!animacionEjecutada2) {
            // Configura la animación
            TranslateTransition move = new TranslateTransition();
            move.setNode(lblContra);
            move.setDuration(Duration.millis(1000));
            move.setCycleCount(1); // Asegúrate de que se ejecute solo una vez
            move.setByY(-30);
            move.play();

            // Cambia el estado para evitar que se ejecute nuevamente
            animacionEjecutada2 = true;
        }
    }

    @FXML
    private void limpiar(javafx.event.ActionEvent event) {
        txtContra.setText("");
        txtUser.setText("");
    }

    @FXML
    private void validarUsuario(javafx.event.ActionEvent event) {
    }
}
