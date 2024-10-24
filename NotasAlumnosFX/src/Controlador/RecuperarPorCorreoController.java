package Controlador;

import CRUDs.RecupararContraPorCorreo;
import java.io.IOException;
import util.EmailService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;


public class RecuperarPorCorreoController {

    @FXML
    private TextField txtCui;
    @FXML
    private TextField txtNombre;
    @FXML
    private TextField txtCorreo; 
    @FXML
    private TextField txtUsuario; 

    private Stage loginStage;
    @FXML
    private void btnRecuperar(ActionEvent event) {
        String cui = txtCui.getText();
        String nombreCompleto = txtNombre.getText(); 
        String nombreUsuario = txtUsuario.getText();
        String correo = txtCorreo.getText(); 

        if (cui.isEmpty() || nombreCompleto.isEmpty() || nombreUsuario.isEmpty() || correo.isEmpty()) {
            mostrarAlerta("Error", "Por favor, complete todos los campos.");
            return;
        }

        try {
            String nuevaContrasenia = RecupararContraPorCorreo.recuperarContrasenia(cui, nombreUsuario, nombreCompleto);

          
            if (nuevaContrasenia != null) {
                EmailService.enviarCorreo(correo, nuevaContrasenia, nombreCompleto, cui, nombreUsuario);
                mostrarAlerta("Éxito", "Se ha generado una nueva contraseña y se ha enviado a su correo.");
            } else {
                mostrarAlerta("Error", "No se pudo generar la nueva contraseña.");
            }
        } catch (Exception e) {
            mostrarAlerta("Error", "No se pudo recuperar la contraseña: " + e.getMessage());
        }
    }

    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alerta = new Alert(AlertType.INFORMATION);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }

@FXML
private void btnAtras(ActionEvent event) {
    Stage stageActual = (Stage) ((Node) event.getSource()).getScene().getWindow();
    stageActual.close();

    abrirLogin();
}

private void abrirLogin() {
    try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Vista/login.fxml"));
        Parent root = loader.load();

        Stage stageNueva = new Stage();
        stageNueva.setScene(new Scene(root));
        stageNueva.show();
    } catch (IOException e) {
        e.printStackTrace();
    }
}


}
