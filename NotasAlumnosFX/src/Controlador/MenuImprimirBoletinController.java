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

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

/**
 * FXML Controller class
 */
public class MenuImprimirBoletinController implements Initializable {

    @FXML
    private TextField txtCui;
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
    
    private Integer gradoId;    // Variable para almacenar el ID del grado
    private Integer seccionId;  // Variable para almacenar el ID de la sección
    @FXML
    private TableView<Boletin> tblBoletin;  // Tipo cambiado a Boletin

    private ObservableList<Boletin> boletinList = FXCollections.observableArrayList();  // Lista observable para la tabla

    /**
     * Initializes the controller class.
     */
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
    @FXML
    private void btnBuscarBoletin(ActionEvent event) {
    System.out.println("Buscando boletín...");
    // Obtener el CUI del campo de texto
    String cui = txtCui.getText();
    
    // Limpiar la lista actual de la tabla
    boletinList.clear();

    // Llamar al método obtenerBoletinEstudiante para obtener los datos
    List<Object[]> resultados = Boletines.obtenerBoletinEstudiante(cui);

    if (resultados != null && !resultados.isEmpty()) {
        System.out.println("Resultados encontrados: " + resultados.size());
        for (Object[] fila : resultados) {
            // Procesar cada fila
            String nombre = (String) fila[1];
            String apellido = (String) fila[2];
            String grado = (String) fila[3];
            String seccion = (String) fila[4];
            String curso = (String) fila[5];
            Double unidad1 = obtenerNotaPorUnidad(fila, 1);
            Double unidad2 = obtenerNotaPorUnidad(fila, 2);
            Double unidad3 = obtenerNotaPorUnidad(fila, 3);
            Double unidad4 = obtenerNotaPorUnidad(fila, 4);
            Double promedio = calcularPromedio(unidad1, unidad2, unidad3, unidad4);
            String aprobado = promedio >= 60 ? "Aprobado" : "No Aprobado";

            // Llenar las etiquetas con los datos del estudiante
            lbNombre.setText(nombre);
            lbApelldio.setText(apellido);
            lbGrado.setText(grado);
            lbSeccion.setText(seccion);

            // Agregar los datos a la lista observable
            boletinList.add(new Boletin(curso, unidad1, unidad2, unidad3, unidad4, promedio, aprobado));
        }
    } else {
        System.out.println("No se encontraron resultados.");
    }
}


    private Double obtenerNotaPorUnidad(Object[] fila, int unidad) {
        // Aquí obtienes las notas por unidad según sea necesario, ajusta según la estructura de datos
        // Suponiendo que la unidad empieza desde el índice 6 en adelante:
        try {
            return (Double) fila[unidad + 5];  // Ajusta el índice según los datos
        } catch (Exception e) {
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
