package Controlador;

import CRUDs.CBimestres;
import CRUDs.CCurso;
import CRUDs.CEvaluaciones;
import POJOs.Bimestres;
import POJOs.Cursos;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import java.math.BigDecimal;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;

public class MenuEvaluacionController implements Initializable {

    @FXML
    private TextField txtNombreActividad;
    @FXML
    private TextField txtPonderacion;
    @FXML
    private ComboBox<Cursos> txtCurso;
    @FXML
    private ComboBox<Bimestres> txtBimestre;
    @FXML
    private ComboBox<String> txtTipo;

    private ObservableList<Cursos> listaCursos;
    private ObservableList<Bimestres> listaBimestres;
    private ObservableList<String> listaTipos;
    @FXML
    private Button btnGuardar;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cargarCursos();
        cargarBimestres();
        cargarTiposEvaluacion();
    }

    private void cargarCursos() {
        // Utilizamos el método listarCursos de CCurso para cargar los cursos
        List<Cursos> cursos = CCurso.listarCursos();
        listaCursos = FXCollections.observableArrayList(cursos);
        txtCurso.setItems(listaCursos);
    }

    private void cargarBimestres() {
        // Utilizamos el método listarBimestres de CBimestres para cargar los bimestres
        List<Bimestres> bimestres = CBimestres.listarBimestres();
        listaBimestres = FXCollections.observableArrayList(bimestres);
        txtBimestre.setItems(listaBimestres);
    }

    private void cargarTiposEvaluacion() {
        // Lista de tipos predefinidos
        listaTipos = FXCollections.observableArrayList("Evaluación", "Actividad");
        txtTipo.setItems(listaTipos);
    }
    
    /*
    // Método para guardar la evaluación
    @FXML
    private void guardarEvaluacion() {
        String nombreEvaluacion = txtNombreActividad.getText();
        BigDecimal ponderacion = new BigDecimal(txtPonderacion.getText());
        Cursos cursoSeleccionado = txtCurso.getSelectionModel().getSelectedItem();
        Bimestres bimestreSeleccionado = txtBimestre.getSelectionModel().getSelectedItem();
        String tipoSeleccionado = txtTipo.getSelectionModel().getSelectedItem();

        // Aquí llamamos al método para crear una nueva evaluación
        boolean resultado = CEvaluaciones.crearEvaluacion(
            bimestreSeleccionado.getBimestreId(),
            cursoSeleccionado.getCursoId(),
            cursoSeleccionado.getGrado().getGradoId(),
            cursoSeleccionado.getSeccion().getSeccionId(),
            nombreEvaluacion,
            tipoSeleccionado,
            ponderacion
        );

        if (resultado) {
            // Mostramos un mensaje de éxito
            System.out.println("Evaluación creada exitosamente.");
        } else {
            // Mostramos un mensaje de error
            System.out.println("Error al crear la evaluación.");
        }
    }
    */

    @FXML
    private void Crear(ActionEvent event) {
    }
}
