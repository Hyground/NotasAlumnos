/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import CRUDs.CEstudiantes;
import POJOs.Estudiantes;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
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
import javax.imageio.ImageIO;

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
    @FXML
    private Label txt6;
    @FXML
    private Label txt5;

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
        
        Stage stage = (Stage) SelecImagen.getScene().getWindow();
        java.io.File selectedFile = fileChooser.showOpenDialog(stage);  
        
        if (selectedFile != null) {
            
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
        this.menuDocenteStage = menuDocenteStage; 
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

        // Sumar años a la fecha actual 
        LocalDate fechaVencimiento = fechaActual.plusYears(3);
        
        // Formatear la fecha de vencimiento
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String fechaVencimientoStr = fechaVencimiento.format(formatter);

        lbFechaVenci.setText(fechaVencimientoStr);
    }
      
    @FXML
    private void btnBuscarEstudiante(ActionEvent event) {
        String cui = txtCui.getText().trim();  // Obtener el CUI
        if (!cui.isEmpty()) {
            Estudiantes estudiante = CEstudiantes.obtenerEstudiantePorCui(cui);
            
            if (estudiante != null && estudiante.isBorradoLogico()) {
                lbNombre.setText(estudiante.getNombre());
                lbApelldio.setText(estudiante.getApellido());
                lbCUI.setText(estudiante.getCui());       
            
            } else {
                lbNombre.setText("No encontrado");
                lbApelldio.setText("No encontrado");
                lbCUI.setText("No encontrado");
            }
        }  
    }

    @FXML
    private void btnCarnetHorizontal(ActionEvent event) {
    try {
        BufferedImage fondoCarnet = ImageIO.read(new File("src/Imagenes/CarnetH.png"));

        BufferedImage carnetFinal = new BufferedImage(
            fondoCarnet.getWidth(),
            fondoCarnet.getHeight(),
            BufferedImage.TYPE_INT_ARGB
        );

        Graphics2D g = carnetFinal.createGraphics();
        g.drawImage(fondoCarnet, 0, 0, null);

        // la fuente y color
        g.setFont(new Font("Arial", Font.BOLD, 14));
        g.setColor(Color.BLACK);

        // para ajustar el texto a la imagen
        g.drawString(lbInstitucion.getText(), 70, 40);
        g.drawString("CUI: " + lbCUI.getText(), 190, 90);
        g.drawString("Nombre: " + lbNombre.getText(), 190, 110);
        g.drawString("Apellido: " + lbApelldio.getText(), 190, 135);
        g.drawString("Grado: " + lbGrado.getText(), 190, 160);
        g.drawString("Sección: " + lbSeccion.getText(), 330, 160);
        g.drawString(lbFechaMensaje.getText(), 100, 225);
        g.drawString("Fecha de Vencimiento: " + lbFechaVenci.getText(), 120, 255);
        g.setFont(new Font("Arial", Font.PLAIN, 12));
       
        Image imagenFX = SelecImagen.getImage();
        if (imagenFX != null) {
            BufferedImage imagenEstudiante = SwingFXUtils.fromFXImage(imagenFX, null);
            BufferedImage redimensionada = resize(imagenEstudiante, 120, 120); //dimensiones
            g.drawImage(redimensionada, 35, 70, null); //posicion en la imagen
        }

        g.dispose(); 

        // crea carpeta 
        File directorio = new File("C:\\Users\\USUARIO\\Pictures\\Carnet Horizontal");
        if (!directorio.exists()) {
            directorio.mkdirs();
        }

        // guardar con nombre
       String nombreEstudiante = lbNombre.getText().trim().replaceAll("\\s+", " ");
       String apellidoEstudiante = lbApelldio.getText().trim().replaceAll("\\s+", " ");
       String nombreArchivo = (nombreEstudiante + " " + apellidoEstudiante).replaceAll("\\s+", " ") + ".png";

        // formato
        File output = new File(directorio, nombreArchivo);
        ImageIO.write(carnetFinal, "png", output);

        System.out.println("Carnet generado exitosamente");

    } catch (IOException e) {
        e.printStackTrace();
    }
}

    private BufferedImage resize(BufferedImage img, int width, int height) {
    java.awt.Image tmp = img.getScaledInstance(width, height, java.awt.Image.SCALE_SMOOTH);
    BufferedImage resized = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
    Graphics2D g2d = resized.createGraphics();
    g2d.drawImage(tmp, 0, 0, null);
    g2d.dispose();
    return resized;
}

    @FXML
    private void btnCarnetVertical(ActionEvent event) {
    }

    @FXML
    private void btnLimpiar(ActionEvent event) {
        txtCui.clear();
        lbCUI.setText("");
        lbNombre.setText("");
        lbApelldio.setText("");
        chAlumnos.setValue("");
        SelecImagen.setImage(null);
    }
    
    @FXML
    private void btnRegresar(ActionEvent event) {
        menuDocenteStage.show(); 
        
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.close();
    }    
}
