/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import POJOs.Bimestres;
import CRUDs.CBimestres;
import CRUDs.CCurso;
import POJOs.Cursos;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
    private ChoiceBox<String> conCurso;
    @FXML
    private ChoiceBox<String> conBimestre;
    @FXML
    private Label rGrado;
    @FXML
    private Label rSeccion;
    @FXML
    private ChoiceBox<String> conTipo;
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
    private Integer gradoId;
    private Integer seccionId;
            

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        cargarBimestre();
        cargarTipo();
        cargaCurso();
    }
    // este codigo tambien, tiene una parte que va en nuestro menu docente, si no
    //no lo trae
    public  void setDatosGradoSeccion (String grado, String seccion, Integer gradoId, Integer seccionId){
           this.gradoId = gradoId;       // Almacenar el ID del grado
        this.seccionId = seccionId;   // Almacenar el ID de la sección
        rGrado.setText(grado);      // Mostrar el nombre del grado
        rSeccion.setText(seccion);  // Mostrar el nombre de la sección
        
    }
    // tipo de evaluacion conbobox
    public  void cargarTipo() {
    ObservableList<String> tipo = FXCollections.observableArrayList("Evaluacion","Actividad");
    conTipo.setItems(tipo);
    }
    //aqui cargamos bimestres faker
    public void cargarBimestre(){
        List<Bimestres> listtaBimestres = CBimestres.listarBimestres();
        ObservableList<String> bimestres = FXCollections.observableArrayList();
        for(Bimestres bimestre:listtaBimestres){
        bimestres.add(bimestre.getNombreBimestre());
        }
        conBimestre.setItems(bimestres);
    }
    
    public void cargaCurso(){
        List<Cursos> listtaCursos = CCurso.listarCursos(); 
        ObservableList<String> cursos = FXCollections.observableArrayList();
        for(Cursos curso:listtaCursos){
        cursos.add(curso.getNombreCurso());
        }
        conCurso.setItems(cursos);
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