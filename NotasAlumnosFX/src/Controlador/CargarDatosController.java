package Controlador;

import CRUDs.CEstudiantes;
import CRUDs.CGrados;
import CRUDs.CSecciones;
import Modelo.Alumno;
import Modelo.CargarDatos;
import POJOs.Grados;
import POJOs.Secciones;
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
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;

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
    @FXML
    private Label labGrado;
    @FXML
    private Label labSeccion;

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

    public void setGradoSeccion(String grado, String seccion) {
        labGrado.setText(grado);     // Asignar el texto al Label de Grado
        labSeccion.setText(seccion); // Asignar el texto al Label de Sección
    }

    @FXML
private void btnInscribir(ActionEvent event) {
    // Obtener los nombres del grado y la sección desde los Labels
    String nombreGrado = labGrado.getText();
    String nombreSeccion = labSeccion.getText();

    // Obtener los objetos de Grado y Sección por sus nombres
    Grados grado = CGrados.obtenerGradoPorNombre(nombreGrado);
    Secciones seccion = CSecciones.obtenerSeccionPorNombre(nombreSeccion);

    if (grado != null && seccion != null) {
        int gradoId = grado.getGradoId();
        int seccionId = seccion.getSeccionId();

        // Lista temporal para almacenar los alumnos que se inscribieron correctamente
        List<Alumno> inscritosExitosamente = FXCollections.observableArrayList();

        // Iterar sobre los alumnos en la tabla
        for (Alumno alumno : tablaAlumnos.getItems()) {
            String cui = alumno.getCui();
            String codigoPersonal = alumno.getCodigoPersonal();
            String nombre = alumno.getNombre();
            String apellido = alumno.getApellido();

            // Intentar inscribir el estudiante
            boolean inscrito = CEstudiantes.crearEstudiante(cui, codigoPersonal, nombre, apellido, gradoId, seccionId);
            if (inscrito) {
                // El estudiante se inscribió correctamente, lo agregamos a la lista de inscritos
                inscritosExitosamente.add(alumno);
            }
        }

        // Eliminar de la tabla solo los alumnos que se inscribieron exitosamente
        tablaAlumnos.getItems().removeAll(inscritosExitosamente);

        // Si quedan alumnos en la tabla, mostrar una alerta indicando que no se pudieron inscribir
        if (!tablaAlumnos.getItems().isEmpty()) {
            mostrarAlerta(Alert.AlertType.ERROR, "Inscripción incompleta", "Algunos estudiantes no se pudieron inscribir.");
        }
    } else {
        mostrarAlerta(Alert.AlertType.ERROR, "Error", "No se encontraron el grado o la sección.");
    }
}

// Método para mostrar alertas
private void mostrarAlerta(Alert.AlertType tipo, String titulo, String mensaje) {
    Alert alerta = new Alert(tipo);
    alerta.setTitle(titulo);
    alerta.setHeaderText(null);
    alerta.setContentText(mensaje);
    alerta.showAndWait();
}


}
