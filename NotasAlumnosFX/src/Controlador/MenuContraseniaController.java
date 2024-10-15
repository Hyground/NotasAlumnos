/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;

/**
 *
 * @author USER
 */
public class MenuContraseniaController {

    @FXML
    private PasswordField txtPassword;

    @FXML
    private void handleCambiarPassword() {
        String nuevaContrasenia = txtPassword.getText();
        // Lógica para cambiar la contraseña
        System.out.println("Nueva contraseña: " + nuevaContrasenia);
    }
}