/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import java.awt.event.ActionEvent;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

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

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    @FXML
    private void cambio(javafx.event.ActionEvent event) {
        if(docenteOn.isSelected()){
            label1.setText("BIENVENIDO QUERIDO USUARIO");
            label2.setText("POR FAVOR INICIA SESION CON LOS DATOS QUE SE TE PROPORCIONARON");
        }else if (adminOn.isSelected()){
            label1.setText("BIENVENIDO PA");
            label2.setText("INGRESA TU USUARIO ADMIN");
        }
    }
}
