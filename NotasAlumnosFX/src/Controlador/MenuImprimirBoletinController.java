package Controlador;

import CRUDs.Boletines;
import Modelo.Boletin;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.math.BigDecimal; // Importación para BigDecimal
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class MenuImprimirBoletinController implements Initializable {

    @FXML
    private TextField txtCui;  // Campo para ingresar el CUI del estudiante
    @FXML
    private TableColumn<Boletin, String> tabCurso;  // Columna para el curso
    @FXML
    private TableColumn<Boletin, Double> tbUniI;  // Columna para Unidad I
    @FXML
    private TableColumn<Boletin, Double> tbUniII;  // Columna para Unidad II
    @FXML
    private TableColumn<Boletin, Double> tbUniIII;  // Columna para Unidad III
    @FXML
    private TableColumn<Boletin, Double> tbUniIV;  // Columna para Unidad IV
    @FXML
    private TableColumn<Boletin, Double> tbProm;  // Columna para el promedio
    @FXML
    private TableColumn<Boletin, String> tbAprob;  // Columna para Aprobado/No Aprobado
    @FXML
    private Button btnRegresar;  // Botón para regresar al menú docente
    @FXML
    private Label lbApelldio;  // Label para mostrar el apellido del estudiante
    @FXML
    private Label lbNombre;  // Label para mostrar el nombre del estudiante
    @FXML
    private Label lbSeccion;  // Label para mostrar la sección (no cambiar)
    @FXML
    private Label lbGrado;  // Label para mostrar el grado (no cambiar)
    @FXML
    private TableView<Boletin> tblBoletin;  // Tabla para mostrar el boletín

    private ObservableList<Boletin> boletinList = FXCollections.observableArrayList();  // Lista observable para la tabla
    private Integer gradoId;  // Variable para almacenar el ID del grado
    private Integer seccionId;  // Variable para almacenar el ID de la sección

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

        // Asignar la lista observable a la tabla
        tblBoletin.setItems(boletinList);
    }

    // Método para recibir los datos de Grado, Sección y la referencia de la ventana de MenuDocente
    public void setDatosGradoSeccion(String grado, String seccion, Integer gradoId, Integer seccionId) {
        this.gradoId = gradoId;
        this.seccionId = seccionId;
        lbGrado.setText(grado);  // Mantener el grado asignado por el docente
        lbSeccion.setText(seccion);  // Mantener la sección asignada por el docente
    }

    @FXML
    private void btnBuscarBoletin(ActionEvent event) {
        // Obtener el CUI del campo de texto
        String cui = txtCui.getText();

        // Limpiar la lista actual de la tabla
        boletinList.clear();

        // Llamar al método obtenerBoletinEstudiante para obtener los datos
        List<Object[]> resultados = Boletines.obtenerBoletinEstudiante(cui);

        if (resultados != null && !resultados.isEmpty()) {
            for (Object[] fila : resultados) {
                String nombre = (String) fila[1];
                String apellido = (String) fila[2];
                String curso = (String) fila[5];

                // Realizamos la conversión de BigDecimal a Double
                Double unidad1 = convertirBigDecimalADouble(fila[6]);  // Suma de actividades de la Unidad I
                Double unidad2 = convertirBigDecimalADouble(fila[7]);  // Suma de actividades de la Unidad II
                Double unidad3 = convertirBigDecimalADouble(fila[8]);  // Suma de actividades de la Unidad III
                Double unidad4 = convertirBigDecimalADouble(fila[9]);  // Suma de actividades de la Unidad IV

                Double promedio = calcularPromedio(unidad1, unidad2, unidad3, unidad4);
                String aprobado = promedio >= 60 ? "Aprobado" : "No Aprobado";

                // Llenar las etiquetas con los datos del estudiante (excepto Grado y Sección)
                lbNombre.setText(nombre);
                lbApelldio.setText(apellido);

                // Agregar los datos a la lista observable
                boletinList.add(new Boletin(curso, unidad1, unidad2, unidad3, unidad4, promedio, aprobado));
            }
        } else {
            System.out.println("No se encontraron resultados.");
        }
    }

    // Método para convertir BigDecimal a Double
    private Double convertirBigDecimalADouble(Object valor) {
        if (valor != null && valor instanceof BigDecimal) {
            return ((BigDecimal) valor).doubleValue();
        } else {
            return 0.0;
        }
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
        // Aquí iría la funcionalidad para imprimir el boletín en PDF
    }

    @FXML
    private void btnRegresarAlMenuDocente(ActionEvent event) {
        // Implementa la lógica para regresar al menú docente
    }
}
