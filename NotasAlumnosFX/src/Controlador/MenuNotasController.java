package Controlador;

import CRUDs.CEstudiantes;
import CRUDs.CBimestres;
import CRUDs.CCurso;
import CRUDs.CEvaluaciones;
import CRUDs.CNotas;
import POJOs.Cursos;
import POJOs.Evaluaciones;
import POJOs.Estudiantes;
import POJOs.Bimestres;
import java.math.BigDecimal;
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
import javafx.scene.control.Button;
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
    private Label txtNombre;
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
    @FXML
    private TableColumn<?, ?> tbActividad;
    @FXML
    private TableColumn<?, ?> tbPunto;
    @FXML
    private Button btnModificar;
    @FXML
    private Button btnGuardar;
    @FXML
    private Label txtApelldio;
    @FXML
    private TableColumn<Estudiantes, String> tblCui;
    @FXML
    private TableColumn<Estudiantes, String> tblCodPer;
    @FXML
    private Label txtCui;

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
    tblCui.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCui()));
    tblCodPer.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCodigoPersonal()));
    
    tblNotas.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
        if (newValue != null) {
            txtNombre.setText(newValue.getNombre());
            txtApelldio.setText(newValue.getApellido());
            txtCui.setText(newValue.getCui());
        }
    });
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

        // Almacenar tanto el nombre de la actividad como el ID de la evaluación
        ObservableList<String> actividades = FXCollections.observableArrayList();
        actividadesFiltradas.forEach(actividad -> {
            actividades.add(actividad.getNombreEvaluacion());
        });
        
        chActividad.setItems(actividades);
        chActividad.setValue("Selecciona una actividad");

        // Guardar el ID de la evaluación seleccionada en un campo aparte
        chActividad.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null && newValue.intValue() >= 0) {
                Integer evaluacionId = actividadesFiltradas.get(newValue.intValue()).getEvaluacionId();
                // Guardar el evaluacionId para usarlo luego al guardar la nota
                txtNota.setUserData(evaluacionId);  // Usamos txtNota para almacenar el ID
            }
        });
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
private void guardar(ActionEvent event) {
    try {
        String cui = txtCui.getText();  // Obtener el CUI del estudiante seleccionado
        BigDecimal nota = new BigDecimal(txtNota.getText());  // Obtener la nota ingresada
        Integer evaluacionId = (Integer) txtNota.getUserData();  // Obtener el ID de la evaluación almacenado

        if (cui != null && evaluacionId != null && nota != null) {
            boolean resultado = CNotas.crearNota(cui, evaluacionId, nota);
            if (resultado) {
                System.out.println("Nota guardada correctamente.");
            } else {
                System.out.println("Error al guardar la nota. Verifique si ya existe una nota para esta evaluación.");
            }
        } else {
            System.out.println("Datos incompletos: CUI, Evaluación o Nota faltante.");
        }
    } catch (Exception e) {
        System.out.println("Error al guardar la nota: " + e.getMessage());
        e.printStackTrace();
    }
}

}
