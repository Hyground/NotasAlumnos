/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

/**
 *
 * @author USER
 */
import POJOs.Estudiantes;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.stage.Stage;

public class MenuAsistenciaController {

    @FXML private TableView<Estudiantes> tablaAsistencia;
    @FXML private TableColumn<Estudiantes, String> colNombre;
    @FXML private TableColumn<Estudiantes, Boolean> colPresente;

    private ObservableList<Estudiantes> listaEstudiantes;

    @FXML
    public void initialize() {
        colNombre.setCellValueFactory(est -> new SimpleStringProperty(est.getValue().getNombre() + " " + est.getValue().getApellido()));
        colPresente.setCellValueFactory(est -> new SimpleBooleanProperty(true));  // Por defecto: todos presentes
        colPresente.setCellFactory(tc -> new CheckBoxTableCell<>());

        listaEstudiantes = FXCollections.observableArrayList(CRUDs.CEstudiantes.ListarEstudiante());
        tablaAsistencia.setItems(listaEstudiantes);
    }

    @FXML
    public void guardarAsistencia() {
        Alert alerta = new Alert(Alert.AlertType.INFORMATION, "Asistencia registrada (simulada).");
        alerta.showAndWait();
    }

    @FXML
    public void cerrarVentana() {
        Stage stage = (Stage) tablaAsistencia.getScene().getWindow();
        stage.close();
    }
}