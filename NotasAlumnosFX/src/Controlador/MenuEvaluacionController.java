package Controlador;

import POJOs.Bimestres;
import CRUDs.CBimestres;
import CRUDs.CCurso;
import CRUDs.CEvaluaciones;
import POJOs.Cursos;
import POJOs.Evaluaciones;
import java.math.BigDecimal;
import java.net.URL;
import java.util.Comparator;
import java.util.List;
import java.util.ResourceBundle;
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
    private TableView<Evaluaciones> tblPersona;
    @FXML
    private TableColumn<Evaluaciones, Integer> tblEvaluacionId;
    @FXML
    private TableColumn<Evaluaciones, String> tblCurso;
    @FXML
    private TableColumn<Evaluaciones, String> tblNombreActividad;
    @FXML
    private TableColumn<Evaluaciones, BigDecimal> tblPonderacionActividad;
    @FXML
    private TableColumn<Evaluaciones, String> tblTipoActividad;

    private Integer gradoId;
    private Integer seccionId;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cargarBimestre();
        cargarTipo();
        cargaCurso();
        configurarTabla();
        listarTodasLasEvaluaciones(); 
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

    private void configurarTabla() {
        // Configurando las columnas para que muestren los datos correctos
        tblEvaluacionId.setCellValueFactory(new PropertyValueFactory<>("evaluacionId"));
        tblNombreActividad.setCellValueFactory(new PropertyValueFactory<>("nombreEvaluacion"));
        tblPonderacionActividad.setCellValueFactory(new PropertyValueFactory<>("ponderacion"));
        tblTipoActividad.setCellValueFactory(new PropertyValueFactory<>("tipo"));

        // Para la columna de Cursos, usamos un SimpleStringProperty para obtener el nombre del curso
        tblCurso.setCellValueFactory(cellData -> {
            Cursos curso = cellData.getValue().getCursos();
            if (curso != null) {
                return new SimpleStringProperty(curso.getNombreCurso());
            } else {
                return new SimpleStringProperty("Sin curso");
            }
        });
    }

    private void listarTodasLasEvaluaciones() {
        // Listar todas las evaluaciones
        List<Evaluaciones> evaluaciones = CEvaluaciones.universo();
        
        // Ordenar las evaluaciones por el nombre del curso
        evaluaciones.sort(Comparator.comparing(evaluacion -> {
            Cursos curso = evaluacion.getCursos();
            return (curso != null) ? curso.getNombreCurso() : "";
        }));

        // Convertir la lista ordenada en un ObservableList y asignarla a la tabla
        ObservableList<Evaluaciones> listaEvaluaciones = FXCollections.observableArrayList(evaluaciones);
        tblPersona.setItems(listaEvaluaciones);
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
            listarTodasLasEvaluaciones(); 
        } catch (NumberFormatException e) {
            System.out.println("Ponderación inválida: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
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
