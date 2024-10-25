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
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

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
    private Integer gradoId;
    private Integer seccionId;

    private Stage menuEstudianteStage;
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

    public void setMenuEstudianteStage(Stage stage) {
        this.menuEstudianteStage = stage;  // Guardar la referencia de la ventana de MenuEstudiante
    }
    public void setDatosGradoSeccion(Integer gradoId, Integer seccionId) {
    this.gradoId = gradoId;
    this.seccionId = seccionId;

    cargarEstudiantesAnulados();
}


    public static List<Estudiantes> ListarEstudianteAnulados() {
        Session session = HibernateUtil.HibernateUtil.getSessionFactory().openSession();
        List<Estudiantes> lista = null;
        try {
            session.beginTransaction();
            Criteria criteria = session.createCriteria(Estudiantes.class);
            criteria.createAlias("grados", "g"); // Relacionar con Grados
            criteria.createAlias("secciones", "s"); // Relacionar con Secciones
            criteria.add(Restrictions.eq("borradoLogico", false));  // Listar solo los estudiantes anulados
            criteria.addOrder(Order.asc("nombre"));  // Ordenar por nombre
            lista = criteria.list(); // Retornar la lista completa de Estudiantes
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.getTransaction().commit();
            session.close();
        }
        return lista;
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
    List<Estudiantes> estudiantes = ListarEstudianteAnulados();
    
    List<Estudiantes> estudiantesAnulados = estudiantes.stream()
            .filter(est -> !est.isBorradoLogico())
            .filter(est -> est.getGrados().getGradoId().equals(gradoId) && est.getSecciones().getSeccionId().equals(seccionId))
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

    @FXML
    private void btnAtras(ActionEvent event) {
        // Mostrar la ventana anterior (menuEstudianteStage)
        if (menuEstudianteStage != null) {
            menuEstudianteStage.show();
        }

        // Cerrar la ventana actual (AnuladosEstudiantes)
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.close();
    }

}
