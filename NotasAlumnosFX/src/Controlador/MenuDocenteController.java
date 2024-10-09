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
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class MenuDocenteController implements Initializable {

    @FXML
    private Label labelNomDocente;
    @FXML
    private Label labelGrado;
    @FXML
    private Label labelSeccion;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void btnInscripcion(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Vista/MenuInscripsion.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setTitle("Inscripsion");
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(MenuInscripsionController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void btnRegistoActividades(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Vista/MenuEvaluacion.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setTitle("Inscripsion");
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
    private void btnCambiarContrasenia(ActionEvent event) {
    }
    
}
