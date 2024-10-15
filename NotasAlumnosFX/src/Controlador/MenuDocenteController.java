package Controlador;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class MenuDocenteController implements Initializable {

    @FXML
    private Label labelNomDocente;
    @FXML
    private Label labelGrado;
    @FXML
    private Label labelSeccion;

    private Integer gradoId;   // Variable para almacenar el ID del grado
    private Integer seccionId; // Variable para almacenar el ID de la sección

    private Stage docenteStage; // Referencia de la ventana del MenuDocente
    @FXML
    private Button btnCambiarContrasenia;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Inicialización
    }

    // Método para establecer los datos del docente, incluyendo los IDs
    public void setDatosDocente(String nombreDocente, String grado, String seccion, Integer gradoId, Integer seccionId) {
        labelNomDocente.setText(nombreDocente);
        labelGrado.setText(grado);
        labelSeccion.setText(seccion);
        this.gradoId = gradoId;   // Guardar el ID del grado
        this.seccionId = seccionId; // Guardar el ID de la sección
    }

    @FXML
    private void btnInscripcion(ActionEvent event) {
        try {
            // Guardar referencia de la ventana actual
            docenteStage = (Stage) labelNomDocente.getScene().getWindow();
            
            // Ocultar la ventana de MenuDocente
            docenteStage.hide();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Vista/MenuEstudiante.fxml"));
            Parent root = loader.load();

            // Obtener el controlador de la vista cargada
            MenuEstudianteController controller = loader.getController();
            // Pasar los datos de grado, sección y sus IDs al controlador de estudiantes y la referencia de la ventana
            controller.setDatosGradoSeccion(labelGrado.getText(), labelSeccion.getText(), gradoId, seccionId, docenteStage);

            Stage stage = new Stage();
            stage.setTitle("Inscripción de Estudiantes");
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(MenuDocenteController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void btnRegistoActividades(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Vista/MenuEvaluacion.fxml"));
            Parent root = loader.load();
            // Obtener el controlador de la vista cargada
            MenuEvaluacionController controller = loader.getController();
            // Pasar los datos de grado, sección y sus IDs al controlador de estudiantes
            controller.setDatosGradoSeccion(labelGrado.getText(), labelSeccion.getText(), gradoId, seccionId);
            Stage stage = new Stage();
            stage.setTitle("Evaluación");
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(MenuEvaluacionController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void btnRegistrarNotas(ActionEvent event) {
         try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Vista/MenuNotas.fxml"));
            Parent root = loader.load();
            // Obtener el controlador de la vista cargada
            MenuNotasController controller = loader.getController();
            // Pasar los datos de grado, sección y sus IDs al controlador de estudiantes
            controller.setDatosGradoSeccion(labelGrado.getText(), labelSeccion.getText(), gradoId, seccionId);
            Stage stage = new Stage();
            stage.setTitle("Registro de Notas");
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(MenuNotasController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void btnCerrarSesion(ActionEvent event) {
        try {
            // Cargar la vista de Login
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Vista/login.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
            
            // Cerrar la ventana actual de MenuDocente
            Stage currentStage = (Stage) labelNomDocente.getScene().getWindow(); 
            currentStage.close();
            
        } catch (IOException ex) {
            Logger.getLogger(MenuDocenteController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
public void btnCambiarContrasenia(ActionEvent event) {
        try {
            // Cargar la vista de cambio de contraseña
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Vista/MenuContrasenia.fxml"));
            Parent root = loader.load();

            // Crear una nueva ventana (Stage) para cambiar contraseña
            Stage stage = new Stage();
            stage.setTitle("Cambiar Contraseña");
            stage.setScene(new Scene(root));

            // Mostrar la ventana
            stage.show();
        } catch (IOException e) {
            // Imprime errores si no se encuentra el FXML o hay problemas de carga
            
        }
    }
}