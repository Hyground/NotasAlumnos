/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author USER
 */
public class MenuImprimirBoletinController implements Initializable {

    @FXML
    private TextField txtCui;
    @FXML
    private TableColumn<?, ?> tabCurso;
    @FXML
    private TableColumn<?, ?> tbUniI;
    @FXML
    private TableColumn<?, ?> tbUniII;
    @FXML
    private TableColumn<?, ?> tbUniIII;
    @FXML
    private TableColumn<?, ?> tbUniIV;
    @FXML
    private TableColumn<?, ?> tbProm;
    @FXML
    private TableColumn<?, ?> tbAprob;
    @FXML
    private Button btnBuscar;
    @FXML
    private Button btnRegresar;
    @FXML
    private Label lbApelldio;
    @FXML
    private Label lbNombre;
    @FXML
    private Label lbSeccion;
    @FXML
    private Label lbGrado;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void btnImprimir(ActionEvent event) {
    }

    @FXML
    private void btnRegresarAlMenuDocente(ActionEvent event) {
    }
    
}
