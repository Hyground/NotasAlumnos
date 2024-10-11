package Controlador;

import CRUDs.CEstudiantes;
import POJOs.Estudiantes;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

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
        // Configurar las columnas de la tabla
        cui.setCellValueFactory(new PropertyValueFactory<>("cui"));
        codigopersonal.setCellValueFactory(new PropertyValueFactory<>("codigoPersonal"));
        nombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        apellido.setCellValueFactory(new PropertyValueFactory<>("apellido"));
        grado.setCellValueFactory(new PropertyValueFactory<>("nombreGrado"));
        seccion.setCellValueFactory(new PropertyValueFactory<>("nombreSeccion"));
        
        // Cargar los estudiantes en la tabla
        cargarEstudiantes();
    }

    private void cargarEstudiantes() {
        List<Estudiantes> estudiantes = CEstudiantes.ListarEstudiante(); 
        listaEstudiantes = FXCollections.observableArrayList(estudiantes);
        tblEstudiante.setItems(listaEstudiantes);
    }

    // Método para recibir los datos de Grado y Sección y sus IDs
    public void setDatosGradoSeccion(String grado, String seccion, Integer gradoId, Integer seccionId) {
        this.gradoId = gradoId;       // Almacenar el ID del grado
        this.seccionId = seccionId;   // Almacenar el ID de la sección
        txtGrado.setText(grado);      // Mostrar el nombre del grado
        txtSeccion.setText(seccion);  // Mostrar el nombre de la sección
    }

    @FXML
    private void btnAgregar(ActionEvent event) {
        String cui = txtCui.getText();
        String codigoPersonal = txtCodigoPersonal.getText();
        String nombre = txtNombre.getText();
        String apellido = txtApellido.getText();

        // Utilizar el gradoId y seccionId almacenados
        if (CEstudiantes.crearEstudiante(cui, codigoPersonal, nombre, apellido, gradoId, seccionId)) {
            cargarEstudiantes();
            limpiarCampos();
        }
    }

    @FXML
    private void btnAnular(ActionEvent event) {
        String cui = txtCui.getText();
        if (CEstudiantes.eliminarEstudiante(cui)) {
            cargarEstudiantes();
            limpiarCampos();
        }
    }

    @FXML
    private void btnActualizar(ActionEvent event) {
        String cui = txtCui.getText();
        String codigoPersonal = txtCodigoPersonal.getText();
        String nombre = txtNombre.getText();
        String apellido = txtApellido.getText();

        // aqui tratamos de validar y usar lo informacion 
        if (CEstudiantes.actualizarEstudiante(cui, codigoPersonal, nombre, apellido, gradoId, seccionId)) {
            cargarEstudiantes();
            limpiarCampos();
        }
    }

    @FXML
    private void btnReactivar(ActionEvent event) {
        String cui = txtCui.getText();
        if (CEstudiantes.reactivarEstudiante(cui)) {
            cargarEstudiantes();
            limpiarCampos();
        }
    }

    private void limpiarCampos() {
        txtCui.clear();
        txtCodigoPersonal.clear();
        txtNombre.clear();
        txtApellido.clear();
        // No limpiar txtGrado y txtSeccion, ya que son los datos del docente que cargamos antes, por eso lo elimine alonzo
    }
}
