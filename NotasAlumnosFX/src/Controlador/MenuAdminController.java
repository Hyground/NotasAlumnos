/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Modelo.TablaUsuarios;
import java.net.URL;
import java.util.Iterator;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
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
    @FXML
    private TextField txtGrado;
    @FXML
    private TextField txtSeccion;
    @FXML
    private TableView<TablaUsuarios> tablawe;
    
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
    @FXML
    private TextField txtRol;
    @FXML
    private TextField txtNombre;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        mostrar();
    }    

    
    public void mostrar(){
        listaUsuario = FXCollections.observableArrayList();
        for (Iterator it = CRUDs.CDocentes.listarDocentes().iterator(); it.hasNext();) {
            Object[] item = (Object[]) it.next();
            listaUsuario.add(new TablaUsuarios((Integer) item[0], (String) item[1], (String) item[2], (String) item[3],(String) item[4], (String) item[5], (Integer) item[6], (Integer) item[8]));

        }
        this.UsuarioID.setCellValueFactory(new PropertyValueFactory("UsuarioID"));
        this.CUI.setCellValueFactory(new PropertyValueFactory("CUI"));
        this.nombreUsuario.setCellValueFactory(new PropertyValueFactory("NombreUsuario"));
        this.Contrasenia.setCellValueFactory(new PropertyValueFactory("Contrasenia"));
        this.Rol.setCellValueFactory(new PropertyValueFactory("Rol"));
        this.GradoID.setCellValueFactory(new PropertyValueFactory("GradoID"));
        this.SeccionID.setCellValueFactory(new PropertyValueFactory("SeccionID"));
        tablawe.setItems(listaUsuario);
    }

    @FXML
    private void seleccionar(MouseEvent event) {
        TablaUsuarios p = this.tablawe.getSelectionModel().getSelectedItem();
        txtContra.setText(p.getContrasenia());
        txtCui.setText(p.getCUI()+ "");
        txtGrado.setText(p.getGradoID()+ "");
        txtSeccion.setText(p.getSeccionID()+ "");
        txtRol.setText(p.getRol()+"");
        UsuarioIDV = p.getUsuarioID();
    }

    @FXML
    private void crear(ActionEvent event) {
        try {
            String cui, Usuario, contra, rol,nombre;
            Integer grado, seccion;
            nombre = txtNombre.getText();
            Usuario = txtUsuario.getText();
            rol = txtRol.getText();
            contra = txtContra.getText();
            cui = txtCui.getText();
            grado = Integer.parseInt(txtGrado.getText());
            seccion = Integer.parseInt(txtSeccion.getText());
            if (CRUDs.CDocentes.crearDocente(nombre, cui, Usuario, contra, rol, grado, seccion)) {
                mostrar();
                Alert alerta = new Alert(Alert.AlertType.INFORMATION);
                alerta.setTitle("Informacion xd");
                alerta.setHeaderText(null);
                alerta.setContentText("Operacion Exitosa");
                alerta.showAndWait();
                limpiar();
            } else {
                Alert alerta = new Alert(Alert.AlertType.INFORMATION);
                alerta.setTitle("Informacion IMPORTANTE");
                alerta.setHeaderText(null);
                alerta.setContentText("No eres un papulince :'v");
                alerta.showAndWait();
            }
        } catch (Exception e) {
            Alert alerta = new Alert(Alert.AlertType.INFORMATION);
            alerta.setTitle("Informacion Importante");
            alerta.setHeaderText(null);
            alerta.setContentText("Error en: " + e);
            alerta.showAndWait();

        }
    }

    @FXML
    private void eliminar(ActionEvent event) {
        try {

            if (CRUDs.CDocentes.eliminarDocente(UsuarioIDV)) {
                mostrar();
                Alert alerta = new Alert(Alert.AlertType.INFORMATION);
                alerta.setTitle("Informacion xd");
                alerta.setHeaderText(null);
                alerta.setContentText("Registro Anulado");
                alerta.showAndWait();

                limpiar();
            } else {
                Alert alerta = new Alert(Alert.AlertType.INFORMATION);
                alerta.setTitle("Informacion IMPORTANTE MAMAHUEVO");
                alerta.setHeaderText(null);
                alerta.setContentText("Registro no anulado");
                alerta.showAndWait();
            }
        } catch (Exception e) {
            Alert alerta = new Alert(Alert.AlertType.INFORMATION);
            alerta.setTitle("Informacion IMPORTANTE MAMAHUEVO");
            alerta.setHeaderText(null);
            alerta.setContentText("Error en: " + e);
            alerta.showAndWait();

        }
    }

    @FXML
    private void modificar(ActionEvent event) {
        try {
            String Usuario, contra, rol;
            Integer cui, grado, seccion;
            Usuario = txtUsuario.getText();
            rol = txtRol.getText();
            contra = txtContra.getText();
            cui = Integer.parseInt(txtCui.getText());
            grado = Integer.parseInt(txtGrado.getText());
            seccion = Integer.parseInt(txtSeccion.getText());
            if (CRUDs.CDocentes.actualizarDocente(UsuarioIDV, Usuario, contra, rol, grado, seccion)) {
                mostrar();
                Alert alerta = new Alert(Alert.AlertType.INFORMATION);
                alerta.setTitle("Informacion xd");
                alerta.setHeaderText(null);
                alerta.setContentText("La grasa lo respalda ;v");
                alerta.showAndWait();
                // aqui se agrega el resto


                limpiar();
            } else {
                Alert alerta = new Alert(Alert.AlertType.INFORMATION);
                alerta.setTitle("Informacion IMPORTANTE MAMAHUEVO");
                alerta.setHeaderText(null);
                alerta.setContentText("No eres un papulince :'v");
                alerta.showAndWait();
            }
        } catch (Exception e) {
            Alert alerta = new Alert(Alert.AlertType.INFORMATION);
            alerta.setTitle("Informacion IMPORTANTE MAMAHUEVO");
            alerta.setHeaderText(null);
            alerta.setContentText("Error en: " + e);
            alerta.showAndWait();

        }
    }

    @FXML
    private void limpiar() {
        txtContra.setText("");
        txtCui.setText("");
        txtGrado.setText("");
        txtSeccion.setText("");
        txtUsuario.setText("");
    }

    /**
     * @return the UsuarioIDV
     */
    public Integer getUsuarioIDV() {
        return UsuarioIDV;
    }

    /**
     * @param UsuarioIDV the UsuarioIDV to set
     */
    public void setUsuarioIDV(Integer UsuarioIDV) {
        this.UsuarioIDV = UsuarioIDV;
    }
    
    
}
