package Controlador;

import Modelo.MenuDocente;
import CRUDs.CDocentes;
import CRUDs.CGrados;
import CRUDs.CSecciones;
import POJOs.Docentes;
import POJOs.Grados;
import POJOs.Secciones;
import java.net.URL;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

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
    
    private ObservableList<MenuDocente> listaUsuario;
    private Integer UsuarioIDV;
    
    @FXML
    private TableColumn<MenuDocente, Integer> UsuarioID;
    @FXML
    private TableColumn<MenuDocente, String> CUI;
    @FXML
    private TableColumn<MenuDocente, String> nombreUsuario;
    @FXML
    private TableColumn<MenuDocente, String> Contrasenia;
    @FXML
    private TableColumn<MenuDocente, String> Rol;
    @FXML
    private TableColumn<MenuDocente, String> GradoID;
    @FXML
    private TableColumn<MenuDocente, String> SeccionID;
    
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
    private TableView<MenuDocente> tablaView;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cargarGrados();
        cargarSecciones();
        cargarRoles();
        mostrar();  // Mostrar los datos en la tabla al inicializar la vista
    }

    private void cargarGrados() {
        List<Grados> listaGrados = CGrados.listarGrados();
        ObservableList<String> grados = FXCollections.observableArrayList();
        for (Grados grado : listaGrados) {
            grados.add(grado.getNombreGrado());
        }
        conGrado.setItems(grados); // conGrado es tu ComboBox
    }

    private void cargarSecciones() {
        List<Secciones> listaSecciones = CSecciones.listarSecciones();
        ObservableList<String> secciones = FXCollections.observableArrayList();
        for (Secciones seccion : listaSecciones) {
            secciones.add(seccion.getNombreSeccion());
        }
        conSeccion.setItems(secciones); // conSeccion es tu ComboBox
    }

    private void cargarRoles() {
        ObservableList<String> roles = FXCollections.observableArrayList("Administrador", "Docente");
        conROl.setItems(roles);  // Solo permitir Administrador o Docente
    }

    @FXML
    private void crear(ActionEvent event) {
        // Crear nuevo docente
        String nombreCompleto = txtNombre.getText();
        String cui = txtCui.getText();
        String nombreUsuario = txtUsuario.getText();
        String contrasenia = txtContra.getText();
        String rol = conROl.getValue();
        String nombreGrado = conGrado.getValue();
        String nombreSeccion = conSeccion.getValue();
        
        Grados gradoSeleccionado = CGrados.obtenerGradoPorNombre(nombreGrado);
        Secciones seccionSeleccionada = CSecciones.obtenerSeccionPorNombre(nombreSeccion);

        if (gradoSeleccionado != null && seccionSeleccionada != null) {
            int gradoId = gradoSeleccionado.getGradoId();
            int seccionId = seccionSeleccionada.getSeccionId();

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
                mostrar();
                limpiarCampos();  // Limpiar campos después de crear
                mostrarAlerta("Operación exitosa", "El usuario ha sido creado exitosamente.");
            } else {
                mostrarAlerta("Error", "No se pudo crear el usuario.");
            }
        } else {
            mostrarAlerta("Error", "Grado o sección no válidos.");
        }
    }

    @FXML
    public void mostrar() {
        // Mostrar docentes en la tabla
        try {
            listaUsuario = FXCollections.observableArrayList();

            for (Iterator it = CDocentes.listarDocentes().iterator(); it.hasNext();) {
                Docentes docente = (Docentes) it.next();
                listaUsuario.add(new MenuDocente(
                    docente.getUsuarioId(),
                    docente.getGrados(),
                    docente.getSecciones(),
                    docente.getNombreCompleto(),
                    docente.getCui(),
                    docente.getNombreUsuario(),
                    docente.getContrasenia(),
                    docente.getRol()
                ));
            }

            UsuarioID.setCellValueFactory(new PropertyValueFactory<>("usuarioId"));
            CUI.setCellValueFactory(new PropertyValueFactory<>("cui"));
            nombreUsuario.setCellValueFactory(new PropertyValueFactory<>("nombreUsuario"));
            Contrasenia.setCellValueFactory(new PropertyValueFactory<>("contrasenia"));
            Rol.setCellValueFactory(new PropertyValueFactory<>("rol"));

            GradoID.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getGrados().getNombreGrado()));
            SeccionID.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getSecciones().getNombreSeccion()));

            tablaView.setItems(listaUsuario);

        } catch (Exception e) {
            e.printStackTrace();  // Manejo de errores
        }
    }

    @FXML
    private void seleccionar(MouseEvent event) {
        // Seleccionar un docente de la tabla y mostrarlo en los campos de entrada
        MenuDocente seleccionado = tablaView.getSelectionModel().getSelectedItem();
        if (seleccionado != null) {
            txtNombre.setText(seleccionado.getNombreCompleto());
            txtCui.setText(seleccionado.getCui());
            txtUsuario.setText(seleccionado.getNombreUsuario());
            txtContra.setText(seleccionado.getContrasenia());
            conGrado.setValue(seleccionado.getGrados().getNombreGrado());
            conSeccion.setValue(seleccionado.getSecciones().getNombreSeccion());
            conROl.setValue(seleccionado.getRol());

            UsuarioIDV = seleccionado.getUsuarioId();
        

        }
    }

    @FXML
    private void limpiar(ActionEvent event) {
        limpiarCampos();
    }

    @FXML
    private void eliminar(ActionEvent event) {
        // Eliminar docente seleccionado
        if (UsuarioIDV != null) {
            boolean eliminado = CDocentes.eliminarDocente(UsuarioIDV);
            if (eliminado) {
                mostrar();
                limpiarCampos();
                mostrarAlerta("Operación exitosa", "El docente ha sido eliminado.");
            } else {
                mostrarAlerta("Error", "No se pudo eliminar el docente.");
            }
        }
    }

    @FXML
private void modificar(ActionEvent event) {
    // Modificar docente seleccionado
    String nuevoCui = txtCui.getText();  // Añadir actualización del CUI
    String nuevoNombreUsuario = txtUsuario.getText();
    String nuevaContrasenia = txtContra.getText();
    String nuevoRol = conROl.getValue();
    String nombreGrado = conGrado.getValue();
    String nombreSeccion = conSeccion.getValue();

    Grados nuevoGrado = CGrados.obtenerGradoPorNombre(nombreGrado);
    Secciones nuevaSeccion = CSecciones.obtenerSeccionPorNombre(nombreSeccion);

    if (nuevoGrado != null && nuevaSeccion != null) {
        boolean actualizado = CDocentes.actualizarDocente(UsuarioIDV, nuevoCui, nuevoNombreUsuario, nuevaContrasenia, nuevoRol, nuevoGrado.getGradoId(), nuevaSeccion.getSeccionId());
        if (actualizado) {
            mostrar();
            limpiarCampos();
            mostrarAlerta("Operación exitosa", "El docente ha sido actualizado.");
        } else {
            mostrarAlerta("Error", "No se pudo actualizar el docente.");
        }
    } else {
        mostrarAlerta("Error", "Grado o sección no válidos.");
    }
}

    private void limpiarCampos() {
        // Limpiar todos los campos de texto y ComboBox
        txtNombre.clear();
        txtCui.clear();
        txtUsuario.clear();
        txtContra.clear();
        conGrado.getSelectionModel().clearSelection();
        conSeccion.getSelectionModel().clearSelection();
        conROl.getSelectionModel().clearSelection();

    }

    private void mostrarAlerta(String titulo, String mensaje) {
        // Mostrar una alerta en pantalla
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }
}
