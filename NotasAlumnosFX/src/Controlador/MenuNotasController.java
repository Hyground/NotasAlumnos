package Controlador;

import CRUDs.CEstudiantes;
import CRUDs.CBimestres;
import CRUDs.CCurso;
import CRUDs.CEvaluaciones;
import POJOs.Cursos;
import POJOs.Evaluaciones;
import POJOs.Estudiantes;
import POJOs.Bimestres;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import javafx.beans.property.SimpleStringProperty;
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

/**
 * FXML Controller class
 */
public class MenuNotasController implements Initializable {

    @FXML
    private TableView<Estudiantes> tblNotas;
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
    private TableColumn<Estudiantes, String> tbNombre;
    @FXML
    private TableColumn<Estudiantes, String> tbApellido;
    @FXML
    private ChoiceBox<String> conBimestre;

    // Almacenamos el gradoId y seccionId que vienen del MenuEvaluacionController
    private Integer gradoId;
    private Integer seccionId;
    private ObservableList<Estudiantes> listaEstudiantes;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cargarBimestres();  // Primero cargamos las opciones de unidad/bimestre
        configurarTabla();  // Configurar las columnas de la tabla
        configurarFiltros();  // Configuramos los listeners para aplicar los filtros
    }

    // Método para recibir los datos del grado y la sección desde el controlador anterior
    public void setDatosGradoSeccion(String grado, String seccion, Integer gradoId, Integer seccionId) {
        this.gradoId = gradoId;
        this.seccionId = seccionId;
        rGrado.setText(grado);
        rSeccion.setText(seccion);
        cargarEstudiantes();
    }

    // Cargar bimestres disponibles
    private void cargarBimestres() {
        List<Bimestres> listaBimestres = CBimestres.listarBimestres();
        ObservableList<String> bimestres = FXCollections.observableArrayList();
        listaBimestres.forEach(bimestre -> bimestres.add(bimestre.getNombreBimestre()));
        conBimestre.setItems(bimestres);
        conBimestre.setValue("Selecciona un bimestre");
    }

    // Configuración de las columnas de la tabla
    private void configurarTabla() {
        tbNombre.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNombre()));
        tbApellido.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getApellido()));
    }

    // Configuramos los filtros para que se apliquen encadenadamente
    private void configurarFiltros() {
        // Cuando cambie el bimestre/unidad seleccionado
        conBimestre.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null && !newValue.equals("Selecciona un bimestre")) {
                filtrarCursosPorUnidad(newValue);  // Filtramos cursos según la unidad seleccionada
            }
        });

        // Cuando cambie el curso seleccionado
        chCurso.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null && !newValue.equals("Selecciona un curso")) {
                filtrarActividadesPorCursoYUnidad(newValue, conBimestre.getValue());  // Filtramos actividades
            }
        });
    }

    // Filtrar cursos por unidad (bimestre)
private void filtrarCursosPorUnidad(String unidadSeleccionada) {
    if (gradoId != null && seccionId != null) {
        List<Evaluaciones> listaEvaluaciones = CEvaluaciones.universo(gradoId, seccionId);
        // Filtrar los cursos que tienen actividades en la unidad seleccionada
        List<Cursos> cursosFiltrados = listaEvaluaciones.stream()
                .filter(evaluacion -> evaluacion.getBimestres().getNombreBimestre().equals(unidadSeleccionada))
                .map(Evaluaciones::getCursos)  // Obtener los cursos
                .distinct()  // Remover duplicados
                .collect(Collectors.toList());

        // Actualizamos los cursos en el ChoiceBox
        ObservableList<String> cursos = FXCollections.observableArrayList();
        cursosFiltrados.forEach(curso -> cursos.add(curso.getNombreCurso()));
        chCurso.setItems(cursos);
        chCurso.setValue("Selecciona un curso");

        // Reiniciamos el ChoiceBox de actividades cuando cambie la unidad
        chActividad.setItems(FXCollections.observableArrayList());  // Limpiar actividades
        chActividad.setValue("Selecciona una actividad");  // Valor por defecto
    }
}

    // Filtrar actividades por curso y unidad (bimestre)
    private void filtrarActividadesPorCursoYUnidad(String cursoSeleccionado, String unidadSeleccionada) {
        if (gradoId != null && seccionId != null) {
            List<Evaluaciones> listaEvaluaciones = CEvaluaciones.universo(gradoId, seccionId);
            // Filtrar actividades por curso y unidad
            List<Evaluaciones> actividadesFiltradas = listaEvaluaciones.stream()
                    .filter(evaluacion -> evaluacion.getCursos().getNombreCurso().equals(cursoSeleccionado))
                    .filter(evaluacion -> evaluacion.getBimestres().getNombreBimestre().equals(unidadSeleccionada))
                    .collect(Collectors.toList());

            // Actualizamos las actividades en el ChoiceBox
            ObservableList<String> actividades = FXCollections.observableArrayList();
            actividadesFiltradas.forEach(actividad -> actividades.add(actividad.getNombreEvaluacion()));
            chActividad.setItems(actividades);
            chActividad.setValue("Selecciona una actividad");
        }
    }

    // Cargar estudiantes por grado y sección
    private void cargarEstudiantes() {
        if (gradoId != null && seccionId != null) {
            List<Estudiantes> estudiantes = CEstudiantes.ListarEstudiante();
            // Filtrar estudiantes según el grado y la sección seleccionados
            List<Estudiantes> estudiantesFiltrados = estudiantes.stream()
                    .filter(est -> est.getGrados().getGradoId().equals(gradoId) && est.getSecciones().getSeccionId().equals(seccionId))
                    .collect(Collectors.toList());

            listaEstudiantes = FXCollections.observableArrayList(estudiantesFiltrados);
            tblNotas.setItems(listaEstudiantes);
        }
    }

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
