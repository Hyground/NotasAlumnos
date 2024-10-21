package Controlador;

import CRUDs.RecupararContraPorCorreo;
import util.EmailService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;

public class RecuperarPorCorreoController {

    @FXML
    private TextField txtCui;
    @FXML
    private TextField txtNombre;
    @FXML
    private TextField txtCorreo; 
    @FXML
    private TextField txtUsuario; 

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
}
