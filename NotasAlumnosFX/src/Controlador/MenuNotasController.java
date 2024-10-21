package Controlador;

import CRUDs.CEstudiantes;
import CRUDs.CBimestres;
import CRUDs.CEvaluaciones;
import CRUDs.CNotas;
import POJOs.Bimestres;
import POJOs.Cursos;
import POJOs.Evaluaciones;
import POJOs.Estudiantes;
import POJOs.Notas;
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
    private Integer gradoId;
    private Integer seccionId;
    private ObservableList<Estudiantes> listaEstudiantes;
    private List<Notas> listaNotas;
    private List<Evaluaciones> listaEvaluaciones;

    @FXML
    private TableColumn<Estudiantes, String> tbActividad;
    @FXML
    private TableColumn<Estudiantes, String> tbPunto;
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
        listaEstudiantes = FXCollections.observableArrayList(CEstudiantes.ListarEstudiante());
        listaNotas = CNotas.listarNotas();  // Cargar todas las notas de una vez
        listaEvaluaciones = CEvaluaciones.universo(gradoId, seccionId);  // Cargar todas las evaluaciones de una vez

        cargarBimestres();  // Primero cargamos las opciones de unidad/bimestre
        configurarTabla();  // Configurar las columnas de la tabla
        configurarFiltros();  // Configuramos los listeners para aplicar los filtros
    }

    public void setDatosGradoSeccion(String grado, String seccion, Integer gradoId, Integer seccionId) {
        this.gradoId = gradoId;
        this.seccionId = seccionId;
        rGrado.setText(grado);
        rSeccion.setText(seccion);
        cargarEstudiantes();
    }

    private void cargarBimestres() {
        List<Bimestres> listaBimestres = CBimestres.listarBimestres();
        ObservableList<String> bimestres = FXCollections.observableArrayList();
        listaBimestres.forEach(bimestre -> bimestres.add(bimestre.getNombreBimestre()));
        conBimestre.setItems(bimestres);
        conBimestre.setValue("Selecciona un bimestre");
    }

    private void configurarTabla() {
        tbNombre.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNombre()));
        tbApellido.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getApellido()));
        tblCui.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCui()));
        tblCodPer.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCodigoPersonal()));

        tbActividad.setCellValueFactory(cellData -> new SimpleStringProperty(obtenerNombreActividad(cellData.getValue())));
        tbPunto.setCellValueFactory(cellData -> new SimpleStringProperty(obtenerNotaEstudiante(cellData.getValue())));

        tblNotas.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                cargarDatosEstudiante(newValue);
            }
        });
    }

    private void cargarDatosEstudiante(Estudiantes estudiante) {
        txtNombre.setText(estudiante.getNombre());
        txtApelldio.setText(estudiante.getApellido());
        txtCui.setText(estudiante.getCui());

        Integer evaluacionId = (Integer) txtNota.getUserData();  // ID de la actividad
        if (evaluacionId != null) {
            Notas nota = listaNotas.stream()
                    .filter(n -> n.getEstudiantes().getCui().equals(estudiante.getCui())
                    && n.getEvaluaciones().getEvaluacionId().equals(evaluacionId))
                    .findFirst().orElse(null);
            txtNota.setText(nota != null ? nota.getNota().toString() : "0.00");
        } else {
            txtNota.setText("0.00");  // Si no hay actividad seleccionada
        }
    }

    private String obtenerNombreActividad(Estudiantes estudiante) {
        Integer evaluacionId = (Integer) txtNota.getUserData();
        return (evaluacionId != null) ? listaEvaluaciones.stream()
                .filter(ev -> ev.getEvaluacionId().equals(evaluacionId))
                .map(Evaluaciones::getNombreEvaluacion)
                .findFirst().orElse("Sin actividad") : "Sin actividad";
    }

    private String obtenerNotaEstudiante(Estudiantes estudiante) {
        Integer evaluacionId = (Integer) txtNota.getUserData();
        return (evaluacionId != null) ? listaNotas.stream()
                .filter(n -> n.getEstudiantes().getCui().equals(estudiante.getCui())
                && n.getEvaluaciones().getEvaluacionId().equals(evaluacionId))
                .map(n -> n.getNota().toString())
                .findFirst().orElse("0.00") : "0.00";
    }

    private void configurarFiltros() {
        conBimestre.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null && !newValue.equals("Selecciona un bimestre")) {
                filtrarCursosPorUnidad(newValue);
            }
        });

        chCurso.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null && !newValue.equals("Selecciona un curso")) {
                filtrarActividadesPorCursoYUnidad(newValue, conBimestre.getValue());
            }
        });
    }

    private void filtrarCursosPorUnidad(String unidadSeleccionada) {
        if (gradoId != null && seccionId != null) {
            List<Cursos> cursosFiltrados = listaEvaluaciones.stream()
                    .filter(evaluacion -> evaluacion.getBimestres().getNombreBimestre().equals(unidadSeleccionada))
                    .map(Evaluaciones::getCursos)
                    .distinct()
                    .collect(Collectors.toList());

            ObservableList<String> cursos = FXCollections.observableArrayList();
            cursosFiltrados.forEach(curso -> cursos.add(curso.getNombreCurso()));
            chCurso.setItems(cursos);
            chCurso.setValue("Selecciona un curso");

            chActividad.setItems(FXCollections.observableArrayList());
            chActividad.setValue("Selecciona una actividad");
        }
    }

    private void filtrarActividadesPorCursoYUnidad(String cursoSeleccionado, String unidadSeleccionada) {
        if (gradoId != null && seccionId != null) {
            List<Evaluaciones> actividadesFiltradas = listaEvaluaciones.stream()
                    .filter(evaluacion -> evaluacion.getCursos().getNombreCurso().equals(cursoSeleccionado))
                    .filter(evaluacion -> evaluacion.getBimestres().getNombreBimestre().equals(unidadSeleccionada))
                    .collect(Collectors.toList());

            ObservableList<String> actividades = FXCollections.observableArrayList();
            actividadesFiltradas.forEach(actividad -> actividades.add(actividad.getNombreEvaluacion()));
            chActividad.setItems(actividades);
            chActividad.setValue("Selecciona una actividad");

            chActividad.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue != null && newValue.intValue() >= 0) {
                    Integer evaluacionId = actividadesFiltradas.get(newValue.intValue()).getEvaluacionId();
                    txtNota.setUserData(evaluacionId);
                    cargarNotasPorActividad(evaluacionId);
                }
            });
        }
    }

    private void cargarNotasPorActividad(Integer evaluacionId) {
        tblNotas.getItems().clear();
        cargarEstudiantes();
        tblNotas.refresh();
    }

    private void cargarEstudiantes() {
        if (gradoId != null && seccionId != null) {
            List<Estudiantes> estudiantesFiltrados = listaEstudiantes.stream()
                    .filter(est -> est.getGrados().getGradoId().equals(gradoId) && est.getSecciones().getSeccionId().equals(seccionId))
                    .collect(Collectors.toList());

            ObservableList<Estudiantes> estudiantesObservable = FXCollections.observableArrayList(estudiantesFiltrados);
            tblNotas.setItems(estudiantesObservable);
        }
    }

    @FXML
    private void modificar(ActionEvent event) {
        gestionarNota(true);
    }

    @FXML
    private void guardar(ActionEvent event) {
        gestionarNota(false);
    }

    private void gestionarNota(boolean esModificacion) {
        try {
            String cui = txtCui.getText();
            BigDecimal nuevaNota = new BigDecimal(txtNota.getText());
            Integer evaluacionId = (Integer) txtNota.getUserData();

            if (cui != null && evaluacionId != null && nuevaNota != null) {
                Notas nota = listaNotas.stream()
                        .filter(n -> n.getEstudiantes().getCui().equals(cui)
                        && n.getEvaluaciones().getEvaluacionId().equals(evaluacionId))
                        .findFirst().orElse(null);

                boolean resultado = (esModificacion && nota != null)
                        ? CNotas.actualizarNota(nota.getNotaId(), nuevaNota) : CNotas.crearNota(cui, evaluacionId, nuevaNota);

                if (resultado) {
                    System.out.println(esModificacion ? "Nota actualizada correctamente." : "Nota guardada correctamente.");
                    listaNotas = CNotas.listarNotas();
                    cargarNotasPorActividad(evaluacionId);
                    limpiarCampos();
                } else {
                    System.out.println("Error al " + (esModificacion ? "modificar" : "guardar") + " la nota.");
                }
            } else {
                System.out.println("Datos incompletos: CUI, Evaluación o Nota faltante.");
            }
        } catch (Exception e) {
            System.out.println("Error al " + (esModificacion ? "modificar" : "guardar") + " la nota: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void limpiarCampos() {
        txtCui.setText("");
        txtNombre.setText("");
        txtApelldio.setText("");
        txtNota.setText("");
    }
}
