package Controlador;

import CRUDs.CLogin;
import POJOs.Docentes;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
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
    private PasswordField txtContra;
    @FXML
    private Label lblUser;
    @FXML
    private Label lblContra;
    @FXML
    private Label label21;
    @FXML
    private Label errorUsPas; // Label para mostrar el error
    @FXML
    private Button recucontra;

    @Override
public void initialize(URL url, ResourceBundle rb) {
    errorUsPas.setVisible(false); // Ocultar el label de error al inicio
    mover();
    mover2();

    txtContra.setOnKeyPressed(event -> {
        if (event.getCode().toString().equals("ENTER")) {
            iniciar(null); 
        }
    });
}


    @FXML
    private void cambio(javafx.event.ActionEvent event) {
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

        // Cambiar los textos según el rol
        if (docenteOn.isSelected()) {
            label1.setText("Bienvenido");
            label2.setText("INGRESA SU USUARIO DOCENTE");
        } else if (adminOn.isSelected()) {
            label1.setText("BIENVENIDO ");
            label2.setText("INGRESA SU USUARIO ADMIN");
        }
    }

    @FXML
private void iniciar(javafx.event.ActionEvent event) {
    String nombreUsuario = txtUser.getText();
    String contrasenia = txtContra.getText();

    // Verificamos si el rol no está seleccionado
    if (!docenteOn.isSelected() && !adminOn.isSelected()) {
        errorUsPas.setText("Por favor, selecciona un rol.");
        errorUsPas.setVisible(true);
        return;
    }

    // Verificamos si el nombre de usuario o la contraseña están vacíos
    if (nombreUsuario.trim().isEmpty() || contrasenia.trim().isEmpty()) {
        errorUsPas.setText("Por favor, completa todos los campos.");
        errorUsPas.setVisible(true);
        return;
    }

    // Verificacmos específicas de rol
    if (docenteOn.isSelected() && (nombreUsuario.equalsIgnoreCase("admin") || nombreUsuario.trim().isEmpty())) {
        errorUsPas.setText("Usuario docente inválido.");
        errorUsPas.setVisible(true);
        return;
    }

    if (adminOn.isSelected() && !nombreUsuario.equalsIgnoreCase("admin")) {
        errorUsPas.setText("Usuario administrador inválido.");
        errorUsPas.setVisible(true);
        return;
    }

    // Intentamos autenticación
    boolean isAuthenticated = CLogin.CLogin(nombreUsuario, contrasenia);

    if (isAuthenticated) {
        errorUsPas.setVisible(false); // Ocultar el error si todo está correcto

        if (docenteOn.isSelected()) {
            // Obtener el docente con sus datos
            Docentes docente = CLogin.obtenerDocentePorNombreUsuario(nombreUsuario);

            // Cargar la vista de MenuDocente y pasar los datos
            cargarFXMLConDatos("/Vista/MenuDocente.fxml", docente);
        } else if (adminOn.isSelected()) {
            cargarFXML("/Vista/MenuAdmin.fxml");
        }
    } else {
        errorUsPas.setText("Usuario o contraseña incorrectos.");
        errorUsPas.setVisible(true);
    }
}


    // Método para cargar el FXML y pasar los datos
 private void cargarFXMLConDatos(String fxml, Docentes docente) {
    try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml));
        Parent root = loader.load();

        // Obtener el controlador de la vista cargada
        MenuDocenteController controller = loader.getController();
        // Pasar los datos del docente al controlador en texto plano y los IDs
        controller.setDatosDocente(
            docente.getNombreCompleto(), 
            docente.getGrados().getNombreGrado(), 
            docente.getSecciones().getNombreSeccion(), 
            docente.getGrados().getGradoId(),  // Pasar el ID del grado
            docente.getSecciones().getSeccionId()  // Pasar el ID de la sección
        );

        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.show();

        // Cerrar la ventana de login
        Stage loginStage = (Stage) btnIngreso.getScene().getWindow();
        loginStage.close();

        stage.setOnCloseRequest(event -> {
            abrirLogin();  
        });
    } catch (IOException e) {
        e.printStackTrace();
    }
}


    private void cargarFXML(String fxml) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();

            Stage loginStage = (Stage) btnIngreso.getScene().getWindow(); 
            loginStage.close();

            stage.setOnCloseRequest(event -> {
                abrirLogin();  
            });

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void abrirLogin() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Vista/login.fxml"));
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
        errorUsPas.setVisible(false); 
    }

    private boolean animacionEjecutada = false;
    private boolean animacionEjecutada2 = false;

    @FXML
    private void mover() {
        if (!animacionEjecutada) {
            TranslateTransition move = new TranslateTransition();
            move.setNode(lblUser);
            move.setDuration(Duration.millis(1700));
            move.setCycleCount(1);
            move.setByY(-30);
            move.play();

            animacionEjecutada = true;
        }
    }

    @FXML
    private void mover2() {
        if (!animacionEjecutada2) {
            TranslateTransition move = new TranslateTransition();
            move.setNode(lblContra);
            move.setDuration(Duration.millis(1700));
            move.setCycleCount(1);
            move.setByY(-30);
            move.play();

            animacionEjecutada2 = true;
        }
    }


@FXML
private void recuperar(ActionEvent event) {
    try {
        // Cargar la vista de recuperación de contraseña
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Vista/RecuperarPorCorreo.fxml"));
        Parent root = loader.load();

        Stage stage = new Stage();
        stage.setTitle("Recuperar Contraseña");
        stage.setScene(new Scene(root));

        stage.show();

        Stage loginStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        loginStage.close();

    } catch (IOException e) {
        e.printStackTrace();
    }
}


}
