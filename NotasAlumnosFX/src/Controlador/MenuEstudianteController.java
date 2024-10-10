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
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
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
    List<Estudiantes> estudiantes = CEstudiantes.ListarEstudiante(); // Cambiar Estudiantes a CEstudiantes
    listaEstudiantes = FXCollections.observableArrayList(estudiantes);
    tblEstudiante.setItems(listaEstudiantes);
}

    @FXML
    private void btnAgregar(ActionEvent event) {
    String cui = txtCui.getText();
        String codigoPersonal = txtCodigoPersonal.getText();
        String nombre = txtNombre.getText();
        String apellido = txtApellido.getText();
        Integer gradoId = Integer.parseInt(txtGrado.getText());
        Integer seccionId = Integer.parseInt(txtSeccion.getText());

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
        Integer gradoId = Integer.parseInt(txtGrado.getText());
        Integer seccionId = Integer.parseInt(txtSeccion.getText());

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
        txtGrado.clear();
        txtSeccion.clear();
    }
}
