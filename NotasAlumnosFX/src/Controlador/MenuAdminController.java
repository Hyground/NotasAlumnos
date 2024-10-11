/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import CRUDs.CDocentes;
import CRUDs.CGrados;
import CRUDs.CSecciones;
import Modelo.TablaUsuarios;
import POJOs.Grados;
import POJOs.Secciones;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

/**
 * FXML Controller class
 *
 * @author IngeMayk
 */
public class MenuAdminController implements Initializable {

    @FXML
    private Label MENUADMIN;
    @FXML
    private TextField txtCui;
    @FXML
    private TextField txtUsuario;
    @FXML
    private TextField txtContra;
    private TextField txtGrado;
    private TextField txtSeccion;
    
    private ObservableList<TablaUsuarios> listaUsuario;
    private Integer UsuarioIDV;
    @FXML
    private TableColumn<?, ?> UsuarioID;
    @FXML
    private TableColumn<?, ?> CUI;
    @FXML
    private TableColumn<?, ?> nombreUsuario;
    @FXML
    private TableColumn<?, ?> Contrasenia;
    @FXML
    private TableColumn<?, ?> Rol;
    @FXML
    private TableColumn<?, ?> GradoID;
    @FXML
    private TableColumn<?, ?> SeccionID;
    @FXML
    private Button btnCrear;
    @FXML
    private Button btnBorrar;
    @FXML
    private Button btnModificar;
    @FXML
    private Button BtnLimpiar;
    private TextField txtRol;
    @FXML
    private TextField txtNombre;
    @FXML
    private ComboBox<String> conGrado;
    @FXML
    private ComboBox<String> conSeccion;
    @FXML
    private ComboBox<String> conROl;
    @FXML
    private TableView<?> tablaView;
    /**
     * Initializes the controller class.
     * @param url
     */
    @Override
public void initialize(URL url, ResourceBundle rb) {
    cargarGrados();
    cargarSecciones();
    cargarRoles();
}

private void cargarGrados() {
    List<Grados> listaGrados = CGrados.listarGrados();
    ObservableList<String> grados = FXCollections.observableArrayList();
    for (Grados grado : listaGrados) {
        grados.add(grado.getNombreGrado());
    }
    conGrado.setItems(grados); // conGrado es tu ChoiceBox
}

private void cargarSecciones() {
    List<Secciones> listaSecciones = CSecciones.listarSecciones();
    ObservableList<String> secciones = FXCollections.observableArrayList();
    for (Secciones seccion : listaSecciones) {
        secciones.add(seccion.getNombreSeccion());
    }
    conSeccion.setItems(secciones); // conSeccion es tu ChoiceBox
}
private void cargarRoles() {
    ObservableList<String> roles = FXCollections.observableArrayList("Administrador", "Docente");
    conROl.setItems(roles);  // Solo permitir Administrador o Docente
}


    @FXML
    private void seleccionar(MouseEvent event) {

    }
@FXML
private void crear(ActionEvent event) {
    String nombreCompleto = txtNombre.getText();
    String cui = txtCui.getText();
    String nombreUsuario = txtUsuario.getText();
    String contrasenia = txtContra.getText();
    String rol = conROl.getValue();  // Obtienes el rol seleccionado
    
    // Obtener el nombre seleccionado de los ComboBox
    String nombreGrado = conGrado.getValue();
    String nombreSeccion = conSeccion.getValue();
    
    // Obtener el objeto Grado a partir del nombre seleccionado
    Grados gradoSeleccionado = CGrados.obtenerGradoPorNombre(nombreGrado);
    Secciones seccionSeleccionada = CSecciones.obtenerSeccionPorNombre(nombreSeccion);

    if (gradoSeleccionado != null && seccionSeleccionada != null) {
        // Extraer los IDs del grado y sección
        int gradoId = gradoSeleccionado.getGradoId();
        int seccionId = seccionSeleccionada.getSeccionId();

        // Llamar al método para crear un nuevo docente
        boolean creado = CDocentes.crearDocente(
            nombreCompleto, 
            cui, 
            nombreUsuario, 
            contrasenia, 
            rol, 
            gradoId, 
            seccionId
        );

        if (creado) {
            System.out.println("Usuario creado exitosamente.");
            limpiarCampos();  // Opcional: limpiar los campos después de crear el usuario
        } else {
            System.out.println("Error al crear el usuario.");
        }
    } else {
        System.out.println("Error: grado o sección no válidos.");
    }
}


    @FXML
    private void eliminar(ActionEvent event) {
      
    }

    @FXML
    private void modificar(ActionEvent event) {
        
    }



    /**
     * @return the UsuarioIDV
     */
    public Integer getUsuarioIDV() {
        return null;

    
}
    private void limpiarCampos() {
    txtNombre.clear();
    txtCui.clear();
    txtUsuario.clear();
    txtContra.clear();
    conGrado.getSelectionModel().clearSelection();
    conSeccion.getSelectionModel().clearSelection();
    conROl.getSelectionModel().clearSelection();
}

    @FXML
    private void limpiar(ActionEvent event) {
    }
}
