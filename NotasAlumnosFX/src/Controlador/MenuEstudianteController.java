package Controlador;

import CRUDs.CEstudiantes;
import POJOs.Estudiantes;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Alonzo Morales
 */
public class MenuEstudianteController implements Initializable {

    @FXML
    private TextField txtCui;
    @FXML
    private TextField txtCodigoPersonal;
    @FXML
    private TextField txtNombre;
    @FXML
    private TextField txtApellido;
    @FXML
    private Label txtGrado;
    @FXML
    private Label txtSeccion;
    @FXML
    private TableView<Estudiantes> tblEstudiante;
    @FXML
    private TableColumn<Estudiantes, String> cui;
    @FXML
    private TableColumn<Estudiantes, String> codigopersonal;
    @FXML
    private TableColumn<Estudiantes, String> nombre;
    @FXML
    private TableColumn<Estudiantes, String> apellido;
    @FXML
    private TableColumn<Estudiantes, String> grado;
    @FXML
    private TableColumn<Estudiantes, String> seccion;

    private ObservableList<Estudiantes> listaEstudiantes;

    private Integer gradoId;    // Variable para almacenar el ID del grado
    private Integer seccionId;  // Variable para almacenar el ID de la sección
    private Stage menuDocenteStage; // Referencia de la ventana del MenuDocente


    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //Configurar las columnas de la tabla
        mostrar();
        // Configurar la selección de estudiantes
        seleccionarEstudiante();
    }

    private void mostrar() {
        // Configurar las columnas de la tabla con los datos de los estudiantes
        cui.setCellValueFactory(new PropertyValueFactory<>("cui"));
        codigopersonal.setCellValueFactory(new PropertyValueFactory<>("codigoPersonal"));
        nombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        apellido.setCellValueFactory(new PropertyValueFactory<>("apellido"));

        // Acceder a los nombres de grado y sección
        grado.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getGrados().getNombreGrado()));
        seccion.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getSecciones().getNombreSeccion()));

        // Cargar los estudiantes filtrados por grado y sección
        cargarEstudiantes();
    }

    private void cargarEstudiantes() {
        List<Estudiantes> estudiantes = CEstudiantes.ListarEstudiante();
        // Filtrar estudiantes según el grado y la sección seleccionados
        List<Estudiantes> estudiantesFiltrados = estudiantes.stream()
                .filter(est -> est.getGrados().getGradoId().equals(gradoId) && est.getSecciones().getSeccionId().equals(seccionId))
                .collect(Collectors.toList());

        listaEstudiantes = FXCollections.observableArrayList(estudiantesFiltrados);
        tblEstudiante.setItems(listaEstudiantes);
    }

    // Método para recibir los datos de Grado, Sección y la referencia de la ventana de MenuDocente
    public void setDatosGradoSeccion(String grado, String seccion, Integer gradoId, Integer seccionId, Stage docenteStage) {
        this.gradoId = gradoId;
        this.seccionId = seccionId;
        this.menuDocenteStage = docenteStage; // Guardar la referencia del MenuDocente
        txtGrado.setText(grado);
        txtSeccion.setText(seccion);

        // Cargar los estudiantes correspondientes
        cargarEstudiantes();
    }

    public void setEstudianteSeleccionado(Estudiantes estudiante) {
        if (estudiante != null) {
            System.out.println("Estudiante seleccionado: " + estudiante.getNombre()); // Depuración
            txtCui.setText(estudiante.getCui());
            txtCodigoPersonal.setText(estudiante.getCodigoPersonal());
            txtNombre.setText(estudiante.getNombre());
            txtApellido.setText(estudiante.getApellido());
            txtGrado.setText(estudiante.getGrados().getNombreGrado());
            txtSeccion.setText(estudiante.getSecciones().getNombreSeccion());
        }
    }

    ////////////////////////////////////// Métodos importantes, no se tocan ///////////////////////////
    @FXML
    private void btnAgregar(ActionEvent event) {
        String cui = txtCui.getText();
        String codigoPersonal = txtCodigoPersonal.getText();
        String nombre = txtNombre.getText();
        String apellido = txtApellido.getText();

        // Validar que todos los campos estén llenos
        if (cui.isEmpty() || codigoPersonal.isEmpty() || nombre.isEmpty() || apellido.isEmpty()) {
            // Mostrar una alerta si hay campos vacíos
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Campos vacíos");
            alert.setHeaderText(null);
            alert.setContentText("Por favor, llene todos los campos antes de agregar el estudiante.");
            alert.showAndWait();
            return;  // Salir del método si hay campos vacíos
        }

        // Utilizar el gradoId y seccionId almacenados
        if (CEstudiantes.crearEstudiante(cui, codigoPersonal, nombre, apellido, gradoId, seccionId)) {
            cargarEstudiantes();  // Recargar la tabla después de agregar un nuevo estudiante
            limpiarCampos();  // Limpiar los campos después de agregar el estudiante
        }
    }

    @FXML
    private void btnAnular(ActionEvent event) {
        String cui = txtCui.getText();
        if (CEstudiantes.eliminarEstudiante(cui)) {
            cargarEstudiantes();  // Recargar la tabla después de anular un estudiante
            limpiarCampos();
        }
    }

    @FXML
    private void btnActualizar(ActionEvent event) {
        String cui = txtCui.getText();
        String codigoPersonal = txtCodigoPersonal.getText();
        String nombre = txtNombre.getText();
        String apellido = txtApellido.getText();

        if (CEstudiantes.actualizarEstudiante(cui, codigoPersonal, nombre, apellido, gradoId, seccionId)) {
            cargarEstudiantes();  // Recargar la tabla después de actualizar un estudiante
            limpiarCampos();
        }
    }

    private void btnReactivar(ActionEvent event) {
        String cui = txtCui.getText();
        if (CEstudiantes.reactivarEstudiante(cui)) {
            cargarEstudiantes();  // Recargar la tabla después de reactivar un estudiante
            limpiarCampos();
        }
    }

    private void limpiarCampos() {
        txtCui.clear();
        txtCodigoPersonal.clear();
        txtNombre.clear();
        txtApellido.clear();
    }

    private void seleccionarEstudiante() {
        // Escuchar cambios de selección en la tabla
        tblEstudiante.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                txtCui.setText(newValue.getCui());
                txtCodigoPersonal.setText(newValue.getCodigoPersonal());
                txtNombre.setText(newValue.getNombre());
                txtApellido.setText(newValue.getApellido());
            }
        });
    }

    @FXML
    private void btnLimpiar(ActionEvent event) {
        txtApellido.clear();
        txtCodigoPersonal.clear();
        txtCui.clear();
        txtNombre.clear();
    }

    @FXML
    private void btnAtras(ActionEvent event) {
        menuDocenteStage.show();

        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.close();
    }

    @FXML
    private void btnPdf(ActionEvent event) {
        try {
            // Guardar la ventana actual
            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            currentStage.hide();  // Ocultar la ventana actual

            // Cargar el archivo FXML de la nueva ventana
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Vista/CargarDatos.fxml"));
            Parent root = loader.load();

            // Obtener el controlador del nuevo FXML
            CargarDatosController cargarDatosController = loader.getController();

            // Pasar los valores de txtGrado y txtSeccion y la referencia de la ventana actual
            cargarDatosController.setGradoSeccion(txtGrado.getText(), txtSeccion.getText(), currentStage);

            // Crear una nueva ventana
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

@FXML
private void btnMostrarAnulados(ActionEvent event) {
    try {
        // Guardar la ventana actual
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.hide();  // Ocultar la ventana actual

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Vista/AnuladosEstudiantes.fxml"));
        Parent root = loader.load();

        // Obtener el controlador de AnuladosEstudiantes
        AnuladosEstudiantesController anuladosController = loader.getController();

        anuladosController.setMenuEstudianteController(this);
        anuladosController.setMenuEstudianteStage(currentStage);
        anuladosController.setDatosGradoSeccion(gradoId, seccionId); 

        // Crear y mostrar la nueva ventana
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.show();
    } catch (IOException e) {
        e.printStackTrace();
    }
}

    

}
