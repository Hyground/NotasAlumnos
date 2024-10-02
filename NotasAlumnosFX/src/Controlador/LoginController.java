/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.util.Duration;

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
    private Label label21;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
    }    
    
    
    
    
    
    
    
    
    
    
    
    //TODO PARA EL CSS NO TOCAR XD
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

    @FXML
    private void mover(javafx.scene.input.MouseEvent event) {
        TranslateTransition move = new TranslateTransition();
        move.setNode(lblUser);
        move.setDuration(Duration.millis(1000));
        move.setCycleCount(1);
        move.setByY(-30);
        move.play();
    }

    @FXML
    private void mover2(javafx.scene.input.MouseEvent event) {
        TranslateTransition move2 = new TranslateTransition();
        move2.setNode(lblContra);
        move2.setDuration(Duration.millis(1000));
        move2.setCycleCount(1);
        move2.setByY(-30);
        move2.play();
    }
}
