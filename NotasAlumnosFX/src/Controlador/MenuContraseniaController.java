package Controlador;

import CRUDs.CDocentes;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class MenuContraseniaController {

    @FXML
    private PasswordField txtPassword;
    @FXML
    private TextField txtContr; 
    @FXML
    private TextField txtNuevaContra; 
    @FXML
    private TextField txtConfirmarContra; 
    @FXML
    private TextField txtCui; 

    @FXML
    private void actualizar(ActionEvent event) {
        String cui = txtCui.getText();
        String contraseniaActual = txtContr.getText();
        String nuevaContrasenia = txtNuevaContra.getText();
        String confirmarContrasenia = txtConfirmarContra.getText();

        if (cui.isEmpty() || contraseniaActual.isEmpty() || nuevaContrasenia.isEmpty() || confirmarContrasenia.isEmpty()) {
            mostrarAlerta("Error", "Por favor, complete todos los campos.");
            return;
        }

        if (!nuevaContrasenia.equals(confirmarContrasenia)) {
            mostrarAlerta("Error", "La nueva contraseña y la confirmación no coinciden.");
            return;
        }

        boolean actualizado = CDocentes.actualizarContrasenia(cui, contraseniaActual, nuevaContrasenia);
        if (actualizado) {
            mostrarAlerta("Éxito", "Contraseña actualizada correctamente.");
            limpiarCampos();
        } else {
            mostrarAlerta("Error", "No se pudo actualizar la contraseña. Verifique los datos.");
        }
    }

  
    private void limpiarCampos() {
        txtCui.setText("");
        txtContr.setText("");
        txtNuevaContra.setText("");
        txtConfirmarContra.setText("");
    }

    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alerta = new Alert(AlertType.INFORMATION);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }
}
