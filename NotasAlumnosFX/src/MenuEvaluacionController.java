import POJOs.Bimestres;
import CRUDs.CBimestres;
import CRUDs.CCurso;
import CRUDs.CEvaluaciones;
import POJOs.Cursos;
import POJOs.Evaluaciones;
import java.math.BigDecimal;
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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

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
    private TableView<Evaluaciones> tblPersona;  // Cambiar el tipo de TableView a Evaluaciones
    @FXML
    private TableColumn<Evaluaciones, Integer> tblEvaluacionId;  // Cambiar el tipo
    @FXML
    private TableColumn<Evaluaciones, String> tblCurso;          // Cambiar el tipo
    @FXML
    private TableColumn<Evaluaciones, String> tblNombreActividad; // Cambiar el tipo
    @FXML
    private TableColumn<Evaluaciones, BigDecimal> tblPonderacionActividad; // Cambiar el tipo
    @FXML
    private TableColumn<Evaluaciones, String> tblTipoActividad;   // Cambiar el tipo
    
    private Integer gradoId;
    private Integer seccionId;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cargarBimestre();
        cargarTipo();
        cargaCurso();
        listarEvaluaciones(); // Llamar a listar las evaluaciones al inicializar
    }

    public void setDatosGradoSeccion(String grado, String seccion, Integer gradoId, Integer seccionId) {
        this.gradoId = gradoId;
        this.seccionId = seccionId;
        rGrado.setText(grado);
        rSeccion.setText(seccion);
        cargaCurso();
    }

    public void cargarTipo() {
        ObservableList<String> tipo = FXCollections.observableArrayList("Evaluacion", "Actividad");
        conTipo.setItems(tipo);
    }

    public void cargarBimestre() {
        List<Bimestres> listtaBimestres = CBimestres.listarBimestres();
        ObservableList<String> bimestres = FXCollections.observableArrayList();
        listtaBimestres.forEach(bimestre -> bimestres.add(bimestre.getNombreBimestre()));
        conBimestre.setItems(bimestres);
    }

    public void cargaCurso() {
        if (gradoId != null) {
            List<Cursos> listtaCursos = CCurso.listarCursosPorGrado(gradoId);
            ObservableList<String> cursos = FXCollections.observableArrayList();
            listtaCursos.forEach(curso -> cursos.add(curso.getNombreCurso()));
            conCurso.setItems(cursos);
        }
    }

    // Método para llenar el TableView con evaluaciones
    public void listarEvaluaciones() {
        List<Evaluaciones> evaluacionesList = CEvaluaciones.universo();  // Llamamos al método para listar las evaluaciones

        ObservableList<Evaluaciones> evaluacionesObservableList = FXCollections.observableArrayList(evaluacionesList);

        // Configuramos las columnas del TableView con los atributos de Evaluaciones
        tblEvaluacionId.setCellValueFactory(new PropertyValueFactory<>("evaluacionId"));
        tblCurso.setCellValueFactory(new PropertyValueFactory<>("cursos"));
        tblNombreActividad.setCellValueFactory(new PropertyValueFactory<>("nombreEvaluacion"));
        tblPonderacionActividad.setCellValueFactory(new PropertyValueFactory<>("ponderacion"));
        tblTipoActividad.setCellValueFactory(new PropertyValueFactory<>("tipo"));

        // Llenamos el TableView con los datos
        tblPersona.setItems(evaluacionesObservableList);
    }

    @FXML
    private void btnGuardar(ActionEvent event) {
        try {
            BigDecimal ponderacion = new BigDecimal(txtPonderacion.getText());
            Cursos cursoSeleccionado = CCurso.obtenerCursoPorNombreYGrado(conCurso.getValue(), gradoId);
            Bimestres bimestreSeleccionado = CBimestres.obtenerBimestrePorNombre(conBimestre.getValue());

            boolean resultado = CEvaluaciones.crearEvaluacion(
                    bimestreSeleccionado.getBimestreId(),
                    cursoSeleccionado.getCursoId(),
                    gradoId,
                    seccionId,
                    txtNombre.getText(),
                    conTipo.getValue(),
                    ponderacion
            );

            System.out.println(resultado ? "Evaluación creada correctamente." : "Error al crear la evaluación.");
            listarEvaluaciones();  // Refrescar la lista de evaluaciones
        } catch (NumberFormatException e) {
            System.out.println("Ponderación inválida: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    private void btnActualizar(ActionEvent event) {
        // Lógica para actualizar una evaluación
        // Obtener la evaluación seleccionada del TableView y actualizar sus valores
    }

    @FXML
    private void btnListar(MouseEvent event) {
        listarEvaluaciones();  // Actualizar la lista de evaluaciones al hacer clic
    }

    @FXML
    private void seleccionaModificar(MouseEvent event) {
        // Obtener la evaluación seleccionada del TableView
        Evaluaciones evaluacionSeleccionada = tblPersona.getSelectionModel().getSelectedItem();
        if (evaluacionSeleccionada != null) {
            txtNombre.setText(evaluacionSeleccionada.getNombreEvaluacion());
            conTipo.setValue(evaluacionSeleccionada.getTipo());
            txtPonderacion.setText(evaluacionSeleccionada.getPonderacion().toString());
            // Otros campos si es necesario
        }
    }
}
