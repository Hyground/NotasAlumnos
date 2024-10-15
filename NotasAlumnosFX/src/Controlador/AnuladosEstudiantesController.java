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
import java.util.stream.Collectors;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * FXML Controller class
 *
 * @author Alonzo Morales
 */
public class AnuladosEstudiantesController implements Initializable {

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

    private MenuEstudianteController menuEstudianteController; // Referencia al controlador de MenuEstudiante

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        mostrar();
        seleccionarEstudiante();
    }

    public void setMenuEstudianteController(MenuEstudianteController controller) {
        this.menuEstudianteController = controller;
    }

    private void mostrar() {
        // Configurar las columnas de la tabla
        cui.setCellValueFactory(new PropertyValueFactory<>("cui"));
        codigopersonal.setCellValueFactory(new PropertyValueFactory<>("codigoPersonal"));
        nombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        apellido.setCellValueFactory(new PropertyValueFactory<>("apellido"));
        grado.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getGrados().getNombreGrado()));
        seccion.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getSecciones().getNombreSeccion()));

        // Cargar los estudiantes anulados
        cargarEstudiantesAnulados();
    }

    private void cargarEstudiantesAnulados() {
        List<Estudiantes> estudiantes = CEstudiantes.ListarEstudiante();
        // Filtrar estudiantes que están anulados
        List<Estudiantes> estudiantesAnulados = estudiantes.stream()
                .filter(est -> est.isBorradoLogico()) // Filtra los estudiantes cuyo borradoLogico es verdadero
                .collect(Collectors.toList());

        listaEstudiantes = FXCollections.observableArrayList(estudiantesAnulados);
        tblEstudiante.setItems(listaEstudiantes);
    }

    private void seleccionarEstudiante() {
        // Escuchar cambios de selección en la tabla
        tblEstudiante.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null && menuEstudianteController != null) {
                // Pasar el estudiante seleccionado al MenuEstudianteController
                menuEstudianteController.setEstudianteSeleccionado(newValue); // Llamar al método que configura el estudiante
            }
        });
    }
}


