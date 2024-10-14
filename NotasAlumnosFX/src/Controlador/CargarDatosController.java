package Controlador;

import Modelo.Alumno;
import Modelo.CargarDatos;
import javafx.fxml.FXML;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.collections.FXCollections;

import java.io.File;
import java.util.List;

public class CargarDatosController {

    @FXML
    private Button cCargar;
    @FXML
    private TableView<Alumno> tablaAlumnos; 
    @FXML
    private TableColumn<Alumno, String> cCui;
    @FXML
    private TableColumn<Alumno, String> cCOdigo;
    @FXML
    private TableColumn<Alumno, String> cNombre;
    @FXML
    private TableColumn<Alumno, String> cApelldio;

    private CargarDatos cargarDatos = new CargarDatos(); 

    public void initialize() {
        // Configuración de las columnas de la tabla
        cCui.setCellValueFactory(new PropertyValueFactory<>("cui"));
        cCOdigo.setCellValueFactory(new PropertyValueFactory<>("codigoPersonal"));
        cNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        cApelldio.setCellValueFactory(new PropertyValueFactory<>("apellido"));

        // Verifica que todos los componentes no sean nulos
        if (tablaAlumnos == null) {
            System.out.println("tablaAlumnos es nulo");
        }
        if (cCui == null) {
            System.out.println("cCui es nulo");
        }
        if (cCOdigo == null) {
            System.out.println("cCOdigo es nulo");
        }
        if (cNombre == null) {
            System.out.println("cNombre es nulo");
        }
        if (cApelldio == null) {
            System.out.println("cApelldio es nulo");
        }

        // Asignar evento al botón para cargar el archivo PDF
        cCargar.setOnAction(event -> cargarArchivoPDF());
    }

    private void cargarArchivoPDF() {
        // Abrir un FileChooser para que el usuario seleccione el archivo PDF
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Archivos PDF", "*.pdf"));
        File archivoPDF = fileChooser.showOpenDialog(new Stage());

        if (archivoPDF != null) {
            System.out.println("Archivo seleccionado: " + archivoPDF.getAbsolutePath());

            // Llamar a la clase CargarDatos para extraer los datos del archivo PDF
            List<Alumno> alumnos = cargarDatos.extraerDatosPDF(archivoPDF);

            // Verificar si los datos son nulos antes de añadirlos a la tabla
            if (alumnos != null && !alumnos.isEmpty()) {
                tablaAlumnos.setItems(FXCollections.observableArrayList(alumnos));
            } else {
                System.out.println("No se encontraron datos.");
            }
        } else {
            System.out.println("No se seleccionó ningún archivo.");
        }
    }
}
