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
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

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
    private Integer evaluacionIdSeleccionada;
    @FXML
    private ChoiceBox<String> conPorUnidad;
    @FXML
    private ChoiceBox<String> conPorCurso;
    private Stage menuDocenteStage;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cargarBimestre();
        cargarTipo();
        cargaCurso();
        configurarTabla();
        listarEvaluacionesFiltradas(); // Cambié esto para que muestre las evaluaciones filtradas
        seleccionChoiceFiltrar();

    }

    // aqui cuando selecionamos alguna opcion entonces acutara el filtro, si no no 
    public void seleccionChoiceFiltrar() {
        conPorUnidad.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> listarEvaluacionesFiltradas());
        conPorCurso.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> listarEvaluacionesFiltradas());
    }

    public void setDatosGradoSeccion(String grado, String seccion, Integer gradoId, Integer seccionId) {
        this.gradoId = gradoId;
        this.seccionId = seccionId;
        rGrado.setText(grado);
        rSeccion.setText(seccion);
        cargaCurso();
        listarEvaluacionesFiltradas(); // Mostrar las evaluaciones filtradas cuando se establezcan los datos
    }

    public void cargarTipo() {
        ObservableList<String> tipo = FXCollections.observableArrayList("Tipo", "Evaluacion", "Actividad");
        conTipo.setItems(tipo);
        conTipo.setValue("Tipo");
    }

    public void cargarBimestre() {
        List<Bimestres> listaBimestres = CBimestres.listarBimestres();
        ObservableList<String> bimestres = FXCollections.observableArrayList("Bimestre");
        listaBimestres.forEach(bimestre -> bimestres.add(bimestre.getNombreBimestre()));
        conBimestre.setItems(bimestres);
        conBimestre.setValue("Bimestre");
        conPorUnidad.setItems(bimestres);
        conPorUnidad.setValue("Bimestre");
    }
// cargamos nuestros choisebox

    public void cargaCurso() {
        if (gradoId != null) {
            List<Cursos> listaCursos = CCurso.listarCursosPorGrado(gradoId);
            ObservableList<String> cursos = FXCollections.observableArrayList("Curso");
            listaCursos.forEach(curso -> cursos.add(curso.getNombreCurso()));
            conCurso.setItems(cursos);
            conCurso.setValue("Curso");
            conPorCurso.setItems(cursos);
            conPorCurso.setValue("Curso");
        }
    }

    private void configurarTabla() {
        tblEvaluacionId.setCellValueFactory(new PropertyValueFactory<>("evaluacionId"));
        tblNombreActividad.setCellValueFactory(new PropertyValueFactory<>("nombreEvaluacion"));
        tblPonderacionActividad.setCellValueFactory(new PropertyValueFactory<>("ponderacion"));
        tblTipoActividad.setCellValueFactory(new PropertyValueFactory<>("tipo"));

        tblCurso.setCellValueFactory(cellData -> {
            Cursos curso = cellData.getValue().getCursos();
            return curso != null ? new SimpleStringProperty(curso.getNombreCurso()) : new SimpleStringProperty("Sin curso");
        });
    }

    private void listarEvaluacionesFiltradas() {
        if (gradoId != null && seccionId != null) {
            List<Evaluaciones> evaluaciones = CEvaluaciones.universo(gradoId, seccionId);

            // Filtro adicional por Unidad (Bimestre)
            String unidadSeleccionada = conPorUnidad.getValue();
            if (unidadSeleccionada != null && !unidadSeleccionada.equals("Bimestre")) {
                evaluaciones.removeIf(evaluacion -> !evaluacion.getBimestres().getNombreBimestre().equals(unidadSeleccionada));
            }

            // Filtro adicional por Curso
            String cursoSeleccionado = conPorCurso.getValue();
            if (cursoSeleccionado != null && !cursoSeleccionado.equals("Curso")) {
                evaluaciones.removeIf(evaluacion -> !evaluacion.getCursos().getNombreCurso().equals(cursoSeleccionado));
            }

            // Ordenar y mostrar en la tabla
            evaluaciones.sort(Comparator.comparing(evaluacion -> {
                Cursos curso = evaluacion.getCursos();
                return (curso != null) ? curso.getNombreCurso() : "";
            }));
            ObservableList<Evaluaciones> listaEvaluaciones = FXCollections.observableArrayList(evaluaciones);
            tblPersona.setItems(listaEvaluaciones);
        } else {
            tblPersona.setItems(FXCollections.observableArrayList());  // Limpiar si no hay filtro
        }
    }

    // Método para restablecer los campos a su estado predeterminado
    private void resetearCampos() {
        txtNombre.clear();  // Limpiar el campo de nombre
        txtPonderacion.clear();  // Limpiar el campo de ponderación
        conCurso.setValue("Curso");  // Restablecer ChoiceBox curso
        conBimestre.setValue("Bimestre");  // Restablecer ChoiceBox bimestre
        conTipo.setValue("Tipo");  // Restablecer ChoiceBox tipo
        evaluacionIdSeleccionada = null;  // Limpiar la evaluación seleccionada
    }

    // Método que selecciona una evaluación y muestra los datos en los campos
    @FXML
    private void seleccionaModificar(MouseEvent event) {
        Evaluaciones evaluacionSeleccionada = tblPersona.getSelectionModel().getSelectedItem();
        if (evaluacionSeleccionada != null) {
            evaluacionIdSeleccionada = evaluacionSeleccionada.getEvaluacionId();
            txtNombre.setText(evaluacionSeleccionada.getNombreEvaluacion());
            txtPonderacion.setText(evaluacionSeleccionada.getPonderacion().toString());
            conTipo.setValue(evaluacionSeleccionada.getTipo());

            if (evaluacionSeleccionada.getCursos() != null) {
                conCurso.setValue(evaluacionSeleccionada.getCursos().getNombreCurso());
            }
            if (evaluacionSeleccionada.getBimestres() != null) {
                conBimestre.setValue(evaluacionSeleccionada.getBimestres().getNombreBimestre());
            }
        }
    }

    @FXML
    private void btnActualizar(ActionEvent event) {
        if (evaluacionIdSeleccionada != null) {
            try {
                BigDecimal ponderacion = new BigDecimal(txtPonderacion.getText());
                Cursos cursoSeleccionado = CCurso.obtenerCursoPorNombreYGrado(conCurso.getValue(), gradoId);
                Bimestres bimestreSeleccionado = CBimestres.obtenerBimestrePorNombre(conBimestre.getValue());

                boolean resultado = CEvaluaciones.actualizarEvaluacion(
                        evaluacionIdSeleccionada,
                        bimestreSeleccionado.getBimestreId(),
                        cursoSeleccionado.getCursoId(),
                        gradoId,
                        seccionId,
                        txtNombre.getText(),
                        conTipo.getValue(),
                        ponderacion
                );

                System.out.println(resultado ? "Evaluación actualizada correctamente." : "Error al actualizar la evaluación.");
                listarEvaluacionesFiltradas();
                resetearCampos(); // Restablecer campos después de actualizar
            } catch (NumberFormatException e) {
                System.out.println("Ponderación inválida: " + e.getMessage());
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
                e.printStackTrace();
            }
        } else {
            System.out.println("No se ha seleccionado ninguna evaluación para actualizar.");
        }
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
            listarEvaluacionesFiltradas();
            resetearCampos(); // Restablecer campos después de guardar
        } catch (NumberFormatException e) {
            System.out.println("Ponderación inválida: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    private void btnAtras(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Vista/MenuDocente.fxml"));
        menuDocenteStage.show();

        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.close();
    }

}
