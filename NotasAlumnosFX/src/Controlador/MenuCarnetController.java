/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import CRUDs.CEstudiantes;
import POJOs.Estudiantes;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author USUARIO
 */
public class MenuCarnetController implements Initializable {

    @FXML
    private ChoiceBox<String> chAlumnos;
    @FXML
    private Label lbGrado;
    @FXML
    private Label lbSeccion;
    @FXML
    private Label txt4;
    @FXML
    private Label txt3;
    @FXML
    private Label lbNombre;
    @FXML
    private Label lbApelldio;
    @FXML
    private Label txt2;
    @FXML
    private Label txt;
    @FXML
    private Button btnRegresar;
    @FXML
    private TextField txtCui;
    @FXML
    private Label lbCUI;
    @FXML
    private Label lbInstitucion;
    @FXML
    private ImageView SelecImagen;
    @FXML
    private Label lbFechaMensaje;
    @FXML
    private Label lbFechaVenci;
    
    private List<Estudiantes> listaEstudiantes;
    private Stage menuDocenteStage; // Referencia de la ventana del MenuDocente
    private Integer gradoId;
    private Integer seccionId;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        SelecImagen.setOnMouseClicked(event -> subirImagen());
        
        chAlumnos.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                String cui = newValue.split(" - ")[0];
                txtCui.setText(cui);
                btnBuscarEstudiante(null);
            }
        });         
        // TODO
    }
    
    private void subirImagen() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Archivos de Imagen", "*.png", "*.jpg", "*.jpeg"));
        
        // Mostrar el cuadro de diálogo para seleccionar una imagen
        Stage stage = (Stage) SelecImagen.getScene().getWindow();
        java.io.File selectedFile = fileChooser.showOpenDialog(stage);  
        
        if (selectedFile != null) {
            // Cargar la imagen seleccionada en el ImageView
            Image image = new Image(selectedFile.toURI().toString());
            SelecImagen.setImage(image);
        }
     
    }  
    
    private void cargarEstudiantes() {
        listaEstudiantes = CEstudiantes.ListarEstudiante();
        
        List<Estudiantes> estudiantesFiltrados = listaEstudiantes.stream()
                .filter(est -> est.getGrados().getGradoId().equals(gradoId) && est.getSecciones().getSeccionId().equals(seccionId))
                .collect(Collectors.toList());
        
        ObservableList<String> estudiantesList = FXCollections.observableArrayList(
                estudiantesFiltrados.stream()
                        .map(est -> est.getCui() + " - " + est.getNombre() + " " + est.getApellido())
                        .collect(Collectors.toList())
        );
        chAlumnos.setItems(estudiantesList);
        chAlumnos.setValue("Selecciona un estudiante");
    }
    
    public void setDatosGradoSeccion(String grado, String seccion, Integer gradoId, Integer seccionId, Stage menuDocenteStage) {
        this.gradoId = gradoId;
        this.seccionId = seccionId;
        this.menuDocenteStage = menuDocenteStage; // Establecer la referencia de la ventana
        lbGrado.setText(grado);
        lbSeccion.setText(seccion);
        lbInstitucion.setText("Instituto Nacional de Educación Básica la estrella");
        lbFechaMensaje.setText("La vigencia de este carnet será de 3 años");
        calcularFechaVencimiento();
        cargarEstudiantes();
    }
    
    private void calcularFechaVencimiento() {
        // Obtener la fecha actual
        LocalDate fechaActual = LocalDate.now();

        // Sumar un año a la fecha actual para calcular la fecha de vencimiento
        LocalDate fechaVencimiento = fechaActual.plusYears(3);

        // Formatear la fecha de vencimiento
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String fechaVencimientoStr = fechaVencimiento.format(formatter);

        // Mostrar la fecha de vencimiento en el Label
        lbFechaVenci.setText(fechaVencimientoStr);
    }
      
    @FXML
    private void btnBuscarEstudiante(ActionEvent event) {
        String cui = txtCui.getText().trim();  // Obtener el CUI escrito en el campo
        if (!cui.isEmpty()) {
            Estudiantes estudiante = CEstudiantes.obtenerEstudiantePorCui(cui);
            
            if (estudiante != null && estudiante.isBorradoLogico()) {
                lbNombre.setText(estudiante.getNombre());
                lbApelldio.setText(estudiante.getApellido());
                lbCUI.setText(estudiante.getCui());       
            
            } else {
                lbNombre.setText("No encontrado");
                lbApelldio.setText("");
                lbCUI.setText("");
            }
        }  
    }

    @FXML
    private void btnImprimirCarnet(ActionEvent event) {
        
    }
    

    @FXML
    private void btnRegresar(ActionEvent event) {
        menuDocenteStage.show(); 
        
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.close();
    }  
}
