/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;
import CRUDs.CBimestres;
import CRUDs.CCurso;
import CRUDs.CEvaluaciones;
import POJOs.Cursos;
import POJOs.Evaluaciones;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import Modelo.TablaNotas;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * FXML Controller class
 *
 * @author wissegt
 */
public class MenuNotasController implements Initializable {

    @FXML
    private TableView<TablaNotas> tblNotas;
    @FXML
    private Button btnModificar;
    @FXML
    private Button btnEliminar;
    @FXML
    private Button btnGuardar;
    @FXML
    private TextField txtNombre;
    @FXML
    private TextField txtApellido;
    @FXML
    private ChoiceBox<String> chCurso;
    @FXML
    private ChoiceBox<String> chActividad;
    @FXML
    private TextField txtNota;
    ////////////////////////////////////////////////////
    /////// ya no me lo vuelvan a eliminar me sirve////
   /*/////////////*/ private Integer gradoId;/////////
   /*/////////////*/ private Integer seccionId;//////
    ////////////////////////////////////////////////
    @FXML
    private Label rGrado;
    @FXML
    private Label rSeccion;
    @FXML
    private TableColumn<TablaNotas, ?> tbNombre;
    @FXML
    private TableColumn<TablaNotas, ?> tbActividad;
    @FXML
    private TableColumn<TablaNotas, ?> tbPunto;
    @FXML
    private TableColumn<TablaNotas, ?> tbApellido;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cargarCurso();
      mostrar();
        // TODO
    }  
    private void mostrar() {
        
        
        
    }
    
    
    
        public  void setDatosGradoSeccion (String grado, String seccion, Integer gradoId, Integer seccionId){
           this.gradoId = gradoId;       // Almacenar el ID del grado
        this.seccionId = seccionId;   // Almacenar el ID de la sección
        rGrado.setText(grado);      // Mostrar el nombre del grado
        rSeccion.setText(seccion);  // Mostrar el nombre de la sección
        
    }
        public void cargarCurso(){
            List<Cursos> listtaCurso = CCurso.listarCursos();
            ObservableList<String> curso = FXCollections.observableArrayList();
            for (Cursos cursos: listtaCurso) {
                curso.add(cursos.getNombreCurso());
            }
            chCurso.setItems(curso);
}
                public void cargarActividades(){
            List<Evaluaciones> listtaEvaluaciones = CEvaluaciones.universo();
            ObservableList<String> evaluacion = FXCollections.observableArrayList();
            for (Evaluaciones Evaluaciones: listtaEvaluaciones) {
                evaluacion.add(Evaluaciones.getNombreEvaluacion());
            }
            chActividad.setItems(evaluacion);
}

    @FXML
    private void modificar(ActionEvent event) {
    }

    @FXML
    private void eliminar(ActionEvent event) {
    }

    @FXML
    private void guardar(ActionEvent event) {
    }
    
}
