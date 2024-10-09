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
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

/**
 * FXML Controller class
 *
 * @author wissegt
 */
public class MenuEvaluacionController implements Initializable {

    @FXML
    private ChoiceBox<?> conCurso;
    @FXML
    private ChoiceBox<?> conBimestre;
    @FXML
    private Label rGrado;
    @FXML
    private Label rSeccion;
    @FXML
    private ChoiceBox<?> conTipo;
    @FXML
    private TextField txtNombre;
    @FXML
    private TextField txtPonderacion;
    @FXML
    private TableView<?> tblPersona;
    @FXML
    private TableColumn<?, ?> EvaluacionId;
    @FXML
    private TableColumn<?, ?> nombreActividad;
    @FXML
    private TableColumn<?, ?> tipoActividad;
    @FXML
    private TableColumn<?, ?> ponderacionActividad;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void btnGuardar(ActionEvent event) {
    }

    @FXML
    private void btnActualizar(ActionEvent event) {
    }

    @FXML
    private void btnListar(MouseEvent event) {
    }

    @FXML
    private void seleccionaModificar(MouseEvent event) {
    }
    
}
