/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import CRUDs.CEstudiantes;
import POJOs.Estudiantes;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * FXML Controller class
 *
 * @author Alonzo Morales
 */
public class MenuInscripsionController implements Initializable {

    @FXML
    private TextField txtCui;
    @FXML
    private TextField txtCodigoPersonal;
    @FXML
    private TextField txtNombre;
    @FXML
    private TextField txtApellido;
    @FXML
    private TextField txtGrado;
    @FXML
    private TextField txtSeccion;
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
    private Integer idEstudiante;
    @FXML
    private Button btnAgregar;
    @FXML
    private Button btnModificar;
    @FXML
    private Button btnAnular;
    @FXML
    private Button btnReactivar;
 
    /**
     * Initializes the controller class.
     */
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Platform.runLater(() -> txtCui.requestFocus());
        configurarColumnas();
        cargarEstudiantes();
    }

    private void configurarColumnas() {
        cui.setCellValueFactory(new PropertyValueFactory<>("cui"));
        codigopersonal.setCellValueFactory(new PropertyValueFactory<>("codigoPersonal"));
        nombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        apellido.setCellValueFactory(new PropertyValueFactory<>("apellido"));
        grado.setCellValueFactory(new PropertyValueFactory<>("nombreGrado"));
        seccion.setCellValueFactory(new PropertyValueFactory<>("nombreSeccion"));
    }

    private void cargarEstudiantes() {
        List<Estudiantes> estudiantes = CEstudiantes.ListarEstudiante(); // Cargar los estudiantes desde el controlador
        listaEstudiantes = FXCollections.observableArrayList(estudiantes);
        tblEstudiante.setItems(listaEstudiantes);
    }

    @FXML
    private void btnAgregar(ActionEvent event) {
    try {
            String cui = txtCui.getText();
            String codigoPersonal = txtCodigoPersonal.getText();
            String nombre = txtNombre.getText();
            String apellido = txtApellido.getText();
            Integer gradoId = Integer.parseInt(txtGrado.getText());
            Integer seccionId = Integer.parseInt(txtSeccion.getText());

            if (CEstudiantes.crearEstudiante(cui, codigoPersonal, nombre, apellido, gradoId, seccionId)) {
                cargarEstudiantes();
                mostrarAlerta("El estudiante ha sido registrado exitosamente.");
                limpiarCampos();
            } else {
                mostrarAlerta("No se pudo registrar el estudiante.");
            }
        } catch (Exception e) {
            mostrarAlerta("Ocurrió un error: " + e.getMessage());
        }
    }
    
    @FXML
    private void btnModificar(ActionEvent event) {
     try {
            String cui = txtCui.getText();
            String codigoPersonal = txtCodigoPersonal.getText();
            String nombre = txtNombre.getText();
            String apellido = txtApellido.getText();
            Integer gradoId = Integer.parseInt(txtGrado.getText());
            Integer seccionId = Integer.parseInt(txtSeccion.getText());

            if (CEstudiantes.actualizarEstudiante(cui, codigoPersonal, nombre, apellido, gradoId, seccionId)) {
                cargarEstudiantes();
                mostrarAlerta("El estudiante ha sido modificado exitosamente.");
                limpiarCampos();
            } else {
                mostrarAlerta("No se pudo modificar el estudiante.");
            }
        } catch (Exception e) {
            mostrarAlerta("Ocurrió un error: " + e.getMessage());
        }
    }

    @FXML
    private void btnAnular(ActionEvent event) {
      try {
            String cui = txtCui.getText();
            if (CEstudiantes.eliminarEstudiante(cui)) {
                cargarEstudiantes();
                mostrarAlerta("El estudiante ha sido anulado exitosamente.");
                limpiarCampos();
            } else {
                mostrarAlerta("No se pudo anular el estudiante.");
            }
        } catch (Exception e) {
            mostrarAlerta("Ocurrió un error: " + e.getMessage());
        }
    }

    @FXML
    private void btnReactivar(ActionEvent event) {
       try {
            String cui = txtCui.getText();
            if (CEstudiantes.reactivarEstudiante(cui)) {
                cargarEstudiantes();
                mostrarAlerta("El estudiante ha sido reactivado exitosamente.");
                limpiarCampos();
            } else {
                mostrarAlerta("No se pudo reactivar el estudiante.");
            }
        } catch (Exception e) {
            mostrarAlerta("Ocurrió un error: " + e.getMessage());
        }
    }

    private void limpiarCampos() {
        txtCui.clear();
        txtCodigoPersonal.clear();
        txtNombre.clear();
        txtApellido.clear();
        txtGrado.clear();
        txtSeccion.clear();
    }

private void mostrarAlerta(String mensaje) {
    Alert alerta = new Alert(Alert.AlertType.INFORMATION);
    alerta.setTitle("Información");
    alerta.setHeaderText(null);
    alerta.setContentText(mensaje);
    alerta.showAndWait();
}
}


//  FALTA QUE LA TABLA MUESTRE LOS DATOS 
