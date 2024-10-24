package Controlador;

import CRUDs.Boletines;
import CRUDs.CEstudiantes;  // Para obtener la lista de estudiantes
import Modelo.Boletin;
import POJOs.Estudiantes;
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
import javafx.scene.control.cell.PropertyValueFactory;

import java.math.BigDecimal;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class MenuImprimirBoletinController implements Initializable {

    @FXML
    private TextField txtCui;  // Para mostrar el CUI del estudiante seleccionado
    @FXML
    private TableColumn<Boletin, String> tabCurso;
    @FXML
    private TableColumn<Boletin, Double> tbUniI;
    @FXML
    private TableColumn<Boletin, Double> tbUniII;
    @FXML
    private TableColumn<Boletin, Double> tbUniIII;
    @FXML
    private TableColumn<Boletin, Double> tbUniIV;
    @FXML
    private TableColumn<Boletin, Double> tbProm;
    @FXML
    private TableColumn<Boletin, String> tbAprob;
    @FXML
    private Button btnRegresar;
    @FXML
    private Label lbApelldio;
    @FXML
    private Label lbNombre;
    @FXML
    private Label lbSeccion;
    @FXML
    private Label lbGrado;
    @FXML
    private TableView<Boletin> tblBoletin;
    @FXML
    private ChoiceBox<String> chAlumnos;  // ChoiceBox para mostrar los estudiantes

    private ObservableList<Boletin> boletinList = FXCollections.observableArrayList();
    private List<Estudiantes> listaEstudiantes;  // Lista de estudiantes filtrada por grado y sección
    private Integer gradoId;
    private Integer seccionId;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Configurar las columnas de la tabla
        tabCurso.setCellValueFactory(new PropertyValueFactory<>("curso"));
        tbUniI.setCellValueFactory(new PropertyValueFactory<>("unidad1"));
        tbUniII.setCellValueFactory(new PropertyValueFactory<>("unidad2"));
        tbUniIII.setCellValueFactory(new PropertyValueFactory<>("unidad3"));
        tbUniIV.setCellValueFactory(new PropertyValueFactory<>("unidad4"));
        tbProm.setCellValueFactory(new PropertyValueFactory<>("promedio"));
        tbAprob.setCellValueFactory(new PropertyValueFactory<>("aprobado"));

        tblBoletin.setItems(boletinList);

        chAlumnos.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                String cui = newValue.split(" - ")[0];  
                txtCui.setText(cui);  
                btnBuscarBoletin(null); 
            }
        });
    }

    public void setDatosGradoSeccion(String grado, String seccion, Integer gradoId, Integer seccionId) {
        lbGrado.setText(grado);
        lbSeccion.setText(seccion);
        this.gradoId = gradoId;
        this.seccionId = seccionId;

        cargarEstudiantes();
    }

    private void cargarEstudiantes() {
        listaEstudiantes = CEstudiantes.ListarEstudiante();

        List<Estudiantes> estudiantesFiltrados = listaEstudiantes.stream()
            .filter(est -> est.getGrados().getGradoId().equals(gradoId) && est.getSecciones().getSeccionId().equals(seccionId))
            .collect(Collectors.toList());

        ObservableList<String> estudiantesList = FXCollections.observableArrayList(
            estudiantesFiltrados.stream()
                .map(est -> est.getCui() + " - " + est.getNombre() + " " + est.getApellido()) 
                .collect(Collectors.toList())
        );

        chAlumnos.setItems(estudiantesList);
        chAlumnos.setValue("Selecciona un estudiante"); 
    }

    @FXML
    private void btnBuscarBoletin(ActionEvent event) {
        String cui = txtCui.getText();  
        boletinList.clear();

        List<Object[]> resultados = Boletines.obtenerBoletinEstudiante(cui);

        if (resultados != null && !resultados.isEmpty()) {
            for (Object[] fila : resultados) {
                String nombre = (String) fila[1];
                String apellido = (String) fila[2];
                String curso = (String) fila[5];
                Double unidad1 = convertirBigDecimalADouble(fila[6]);
                Double unidad2 = convertirBigDecimalADouble(fila[7]);
                Double unidad3 = convertirBigDecimalADouble(fila[8]);
                Double unidad4 = convertirBigDecimalADouble(fila[9]);
                Double promedio = calcularPromedio(unidad1, unidad2, unidad3, unidad4);
                String aprobado = promedio >= 60 ? "Aprobado" : "No Aprobado";

                lbNombre.setText(nombre);
                lbApelldio.setText(apellido);

                boletinList.add(new Boletin(curso, unidad1, unidad2, unidad3, unidad4, promedio, aprobado));
            }
        } else {
            System.out.println("No se encontraron resultados.");
        }
    }

    private Double convertirBigDecimalADouble(Object valor) {
        if (valor instanceof BigDecimal) {
            return ((BigDecimal) valor).doubleValue();
        }
        return 0.0;
    }

    private Double calcularPromedio(Double... notas) {
        double suma = 0;
        int contador = 0;
        for (Double nota : notas) {
            if (nota != null && nota > 0) {
                suma += nota;
                contador++;
            }
        }
        return contador > 0 ? suma / contador : 0.0;
    }

    @FXML
    private void btnImprimir(ActionEvent event) {
    }

    @FXML
    private void btnRegresarAlMenuDocente(ActionEvent event) {
    }
}
