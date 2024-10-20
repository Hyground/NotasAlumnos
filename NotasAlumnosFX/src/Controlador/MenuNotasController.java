package Controlador;

import CRUDs.CBimestres;
import CRUDs.CCurso;
import CRUDs.CEvaluaciones;
import POJOs.Cursos;
import POJOs.Evaluaciones;
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
import Modelo.TablaNotas;
import POJOs.Bimestres;

/**
 * FXML Controller class
 *
 */
public class MenuNotasController implements Initializable {

    @FXML
    private TableView<TablaNotas> tblNotas;
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
    @FXML
    private Label rGrado;
    @FXML
    private Label rSeccion;
    @FXML
    private TableColumn<TablaNotas, String> tbNombre;
    @FXML
    private TableColumn<TablaNotas, String> tbActividad;
    @FXML
    private TableColumn<TablaNotas, String> tbPunto;
    @FXML
    private TableColumn<TablaNotas, String> tbApellido;
    @FXML
    private ChoiceBox<String> conBimestre;

    // Almacenamos el gradoId y seccionId que vienen del MenuEvaluacionController
    private Integer gradoId;
    private Integer seccionId;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Inicializamos los datos del ChoiceBox con base en las evaluaciones
        cargarCurso();
        cargarActividades();
        cargarBimestres();
    }

    // Método para recibir los datos del grado y la sección desde el controlador anterior
    public void setDatosGradoSeccion(String grado, String seccion, Integer gradoId, Integer seccionId) {
        this.gradoId = gradoId;
        this.seccionId = seccionId;
        rGrado.setText(grado);  // Mostramos el nombre del grado
        rSeccion.setText(seccion);  // Mostramos el nombre de la sección
        cargarCurso();  // Recargamos los cursos filtrados por grado
        cargarActividades();  // Recargamos las actividades (evaluaciones) filtradas
        cargarBimestres();  // Recargamos los bimestres
    }

    // Cargamos los cursos filtrados por grado
    private void cargarCurso() {
        if (gradoId != null) {
            List<Cursos> listaCursos = CCurso.listarCursosPorGrado(gradoId);
            ObservableList<String> cursos = FXCollections.observableArrayList();
            listaCursos.forEach(curso -> cursos.add(curso.getNombreCurso()));
            chCurso.setItems(cursos);
            chCurso.setValue("Selecciona un curso");
        }
    }

    // Cargamos las actividades (evaluaciones) filtradas por grado y sección
    private void cargarActividades() {
        if (gradoId != null && seccionId != null) {
            List<Evaluaciones> listaEvaluaciones = CEvaluaciones.universo(gradoId, seccionId);
            ObservableList<String> actividades = FXCollections.observableArrayList();
            listaEvaluaciones.forEach(evaluacion -> actividades.add(evaluacion.getNombreEvaluacion()));
            chActividad.setItems(actividades);
            chActividad.setValue("Selecciona una actividad");
        }
    }

    // Cargamos los bimestres disponibles
    private void cargarBimestres() {
        List<Bimestres> listaBimestres = CBimestres.listarBimestres();
        ObservableList<String> bimestres = FXCollections.observableArrayList();
        listaBimestres.forEach(bimestre -> bimestres.add(bimestre.getNombreBimestre()));
        conBimestre.setItems(bimestres);
        conBimestre.setValue("Selecciona un bimestre");
    }

    // Aquí puedes añadir los métodos para modificar, eliminar y guardar las notas si lo deseas.
    @FXML
    private void modificar(ActionEvent event) {
        // Código para modificar
    }

    @FXML
    private void eliminar(ActionEvent event) {
        // Código para eliminar
    }

    @FXML
    private void guardar(ActionEvent event) {
        // Código para guardar
    }
}
