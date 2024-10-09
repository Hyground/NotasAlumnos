package Controlador;

import CRUDs.CLogin;
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
    private Label label21;
    @FXML
    private Label errorUsPas; // Label para mostrar el error

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        errorUsPas.setVisible(false); // Ocultar el label de error al inicio
    }

    @FXML
    private void cambio(javafx.event.ActionEvent event) {
        // Lógica para permitir solo una checkbox seleccionada a la vez
        if (event.getSource() == docenteOn) {
            if (docenteOn.isSelected()) {
                adminOn.setSelected(false); // Deselecciona el otro checkbox
                label21.setVisible(false); // Oculta label21
            }
        } else if (event.getSource() == adminOn) {
            if (adminOn.isSelected()) {
                docenteOn.setSelected(false); // Deselecciona el otro checkbox
                label21.setVisible(false); // Oculta label21
            }
        }

        // Lógica para cambiar los textos
        if (docenteOn.isSelected()) {
            label1.setText("Bienvenido docente");
            label2.setText("INGRESA TU USUARIO DOCENTE");
        } else if (adminOn.isSelected()) {
            label1.setText("BIENVENIDO ADMIN");
            label2.setText("INGRESA TU USUARIO ADMIN");
        }
    }

    @FXML
    private void iniciar(javafx.event.ActionEvent event) {
        // Obtener el nombre de usuario y la contraseña
        String nombreUsuario = txtUser.getText();
        String contrasenia = txtContra.getText();

        // Validar que se ha seleccionado un checkbox
        if (!docenteOn.isSelected() && !adminOn.isSelected()) {
            System.out.println("Por favor, selecciona un rol.");
            return;
        }

        // Llamar al método de inicio de sesión
        boolean isAuthenticated = CLogin.CLogin(nombreUsuario, contrasenia);

        if (isAuthenticated) {
            // Cargar la vista correspondiente según el rol
            if (docenteOn.isSelected()) {
                cargarFXML("/Vista/MenuDocente.fxml");
            } else if (adminOn.isSelected()) {
                cargarFXML("/Vista/MenuAdmin.fxml");
            }
            errorUsPas.setVisible(false); // Ocultar el label de error si la autenticación es exitosa
        } else {
            errorUsPas.setVisible(true); // Mostrar el label de error
        }
    }

    private void cargarFXML(String fxml) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void limpiar(javafx.event.ActionEvent event) {
        txtContra.setText("");
        txtUser.setText("");
        errorUsPas.setVisible(false); // Ocultar el label de error al limpiar
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
}
