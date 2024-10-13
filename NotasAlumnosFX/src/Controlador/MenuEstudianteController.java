package Controlador;

import CRUDs.CEstudiantes;
import POJOs.Estudiantes;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
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

        // Utilizar SimpleStringProperty para acceder a los nombres de grado y sección
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

    // Método para recibir los datos de Grado y Sección y sus IDs
    public void setDatosGradoSeccion(String grado, String seccion, Integer gradoId, Integer seccionId) {
        this.gradoId = gradoId;       // Almacenar el ID del grado
        this.seccionId = seccionId;   // Almacenar el ID de la sección
        txtGrado.setText(grado);      // Mostrar el nombre del grado
        txtSeccion.setText(seccion);  // Mostrar el nombre de la sección
        
        // Después de establecer los datos, cargar los estudiantes correspondientes
        cargarEstudiantes();
    }

    //////////////////////////////////////estos son muy imporantes, no se tocan, /////////////////
    @FXML
    private void btnAgregar(ActionEvent event) {
        String cui = txtCui.getText();
        String codigoPersonal = txtCodigoPersonal.getText();
        String nombre = txtNombre.getText();
        String apellido = txtApellido.getText();

        // Utilizar el gradoId y seccionId almacenados
        if (CEstudiantes.crearEstudiante(cui, codigoPersonal, nombre, apellido, gradoId, seccionId)) {
            cargarEstudiantes();  // Recargar la tabla después de agregar un nuevo estudiante
            limpiarCampos();
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

        // aquí tratamos de validar y usar la información
        if (CEstudiantes.actualizarEstudiante(cui, codigoPersonal, nombre, apellido, gradoId, seccionId)) {
            cargarEstudiantes();  // Recargar la tabla después de actualizar un estudiante
            limpiarCampos();
        }
    }

    @FXML
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
        // No limpiar txtGrado y txtSeccion, ya que son los datos del docente que cargamos antes
    }

    private void seleccionarEstudiante() {
    // Escuchar cambios de selección en la tabla
    tblEstudiante.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
        // Verificar que haya un estudiante seleccionado
        if (newValue != null) {
            // Llenar los campos de texto con los datos del estudiante seleccionado
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
        try{
            Stage stage = new Stage();
            Parent root=FXMLLoader.load(getClass().getResource("/Vista/MenuDocente.fxml"));
            stage.setTitle("MENU DOCENTE");
            stage.setScene(new Scene(root));
            stage.show();
           
            // Cerrar la ventana actual
            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            currentStage.close();
        }catch (Exception e){
            System.out.println("error="+e);
        }
    }
}