package Controlador;

import CRUDs.Boletines;
import CRUDs.CEstudiantes;
import Modelo.Boletin;
import POJOs.Estudiantes;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import org.apache.pdfbox.pdmodel.common.PDRectangle;

public class MenuImprimirBoletinController implements Initializable {

    @FXML
    private TextField txtCui;
    @FXML
    private TableColumn<Boletin, String> tabCurso;
    @FXML
    private TableColumn<Boletin, Double> tbUniI;
    @FXML
    private TableColumn<Boletin, Double> tbUniII;
    @FXML
    private TableColumn<Boletin, Double> tbUniIII;
    @FXML
    private TableColumn<Boletin, Double> tbUniIV;
    @FXML
    private TableColumn<Boletin, Double> tbProm;
    @FXML
    private TableColumn<Boletin, String> tbAprob;
    @FXML
    private Button btnRegresar;
    @FXML
    private Label lbApelldio;
    @FXML
    private Label lbNombre;
    @FXML
    private Label lbSeccion;
    @FXML
    private Label lbGrado;
    @FXML
    private TableView<Boletin> tblBoletin;
    @FXML
    private ChoiceBox<String> chAlumnos;

    private ObservableList<Boletin> boletinList = FXCollections.observableArrayList();
    private List<Estudiantes> listaEstudiantes;
    private Integer gradoId;
    private Integer seccionId;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Configurar las columnas de la tabla
        tabCurso.setCellValueFactory(new PropertyValueFactory<>("curso"));
        tbUniI.setCellValueFactory(new PropertyValueFactory<>("unidad1"));
        tbUniII.setCellValueFactory(new PropertyValueFactory<>("unidad2"));
        tbUniIII.setCellValueFactory(new PropertyValueFactory<>("unidad3"));
        tbUniIV.setCellValueFactory(new PropertyValueFactory<>("unidad4"));
        tbProm.setCellValueFactory(new PropertyValueFactory<>("promedio"));
        tbAprob.setCellValueFactory(new PropertyValueFactory<>("aprobado"));

        tblBoletin.setItems(boletinList);

        chAlumnos.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                String cui = newValue.split(" - ")[0];
                txtCui.setText(cui);
                btnBuscarBoletin(null);
            }
        });
    }

    public void setDatosGradoSeccion(String grado, String seccion, Integer gradoId, Integer seccionId) {
        lbGrado.setText(grado);
        lbSeccion.setText(seccion);
        this.gradoId = gradoId;
        this.seccionId = seccionId;

        cargarEstudiantes();
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

    @FXML
    private void btnBuscarBoletin(ActionEvent event) {
        String cui = txtCui.getText();
        boletinList.clear();

        List<Object[]> resultados = Boletines.obtenerBoletinEstudiante(cui);

        if (resultados != null && !resultados.isEmpty()) {
            for (Object[] fila : resultados) {
                String nombre = (String) fila[1];
                String apellido = (String) fila[2];
                String curso = (String) fila[5];
                Double unidad1 = convertirBigDecimalADouble(fila[6]);
                Double unidad2 = convertirBigDecimalADouble(fila[7]);
                Double unidad3 = convertirBigDecimalADouble(fila[8]);
                Double unidad4 = convertirBigDecimalADouble(fila[9]);
                Double promedio = calcularPromedio(unidad1, unidad2, unidad3, unidad4);
                String aprobado = promedio >= 60 ? "Aprobado" : "No Aprobado";

                lbNombre.setText(nombre);
                lbApelldio.setText(apellido);

                boletinList.add(new Boletin(curso, unidad1, unidad2, unidad3, unidad4, promedio, aprobado));
            }
        } else {
            System.out.println("No se encontraron resultados.");
        }
    }

    private Double convertirBigDecimalADouble(Object valor) {
        if (valor instanceof BigDecimal) {
            return ((BigDecimal) valor).doubleValue();
        }
        return 0.0;
    }

    private Double calcularPromedio(Double... notas) {
        double suma = 0;
        int contador = 0;
        for (Double nota : notas) {
            if (nota != null && nota > 0) {
                suma += nota;
                contador++;
            }
        }
        return contador > 0 ? suma / contador : 0.0;
    }

    @FXML
    private void btnImprimir(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Guardar Boletín como PDF");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PDF Files", "*.pdf"));
        File file = fileChooser.showSaveDialog(null);

        if (file != null) {
            try {
                PDDocument document = new PDDocument();
                PDPage page = new PDPage(PDRectangle.A4);  // Cambiar tamaño de la página
                document.addPage(page);

                PDPageContentStream contentStream = new PDPageContentStream(document, page);

                // Establecer fuentes y tamaños de texto
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 14);

                // Escribir encabezado
                contentStream.beginText();
                contentStream.newLineAtOffset(100, 750);  // Ajuste de posición
                contentStream.showText("Boletín de Notas");
                contentStream.endText();

                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);
                contentStream.beginText();
                contentStream.newLineAtOffset(100, 720);
                contentStream.showText("Nombre: " + lbNombre.getText() + " " + lbApelldio.getText());
                contentStream.endText();

                contentStream.beginText();
                contentStream.newLineAtOffset(100, 700);
                contentStream.showText("Grado: " + lbGrado.getText() + "  Sección: " + lbSeccion.getText());
                contentStream.endText();

                // Ajuste de posición y espaciado
                float margin = 50;
                float yStart = 650;
                float tableWidth = page.getMediaBox().getWidth() - 2 * margin;
                float yPosition = yStart;
                float rowHeight = 23f;  // Aumentar altura de las filas
                float cellMargin = 5f;

                // Crear tabla con bordes y estilo
                String[] headers = {"Curso", "Unidad I", "Unidad II", "Unidad III", "Unidad IV", "Promedio", "A/No A"};
                float[] columnWidths = {140, 52, 52, 52, 52, 52, 80};

                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                drawRow(contentStream, headers, columnWidths, yPosition, margin, rowHeight, cellMargin, true);
                yPosition -= rowHeight;

                contentStream.setFont(PDType1Font.HELVETICA, 10);
                for (Boletin boletin : boletinList) {
                    String[] data = {
                        boletin.getCurso(),
                        String.format("%.2f", boletin.getUnidad1()),
                        String.format("%.2f", boletin.getUnidad2()),
                        String.format("%.2f", boletin.getUnidad3()),
                        String.format("%.2f", boletin.getUnidad4()),
                        String.format("%.2f", boletin.getPromedio()),
                        boletin.getAprobado()
                    };
                    drawRow(contentStream, data, columnWidths, yPosition, margin, rowHeight, cellMargin, false);
                    yPosition -= rowHeight;
                }

                contentStream.close();

                // Guardar el documento en un archivo
                document.save(file);
                document.close();

                System.out.println("El boletín ha sido guardado como PDF en: " + file.getAbsolutePath());

            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Guardado cancelado.");
        }
    }


private void drawRow(PDPageContentStream contentStream, String[] data, float[] columnWidths, float y, float margin, float rowHeight, float cellMargin, boolean isHeader) throws IOException {
    float x = margin;

    for (int i = 0; i < data.length; i++) {
        // Dibujar bordes
        contentStream.addRect(x, y - rowHeight, columnWidths[i], rowHeight);
        contentStream.stroke();

        PDType1Font font = isHeader ? PDType1Font.HELVETICA_BOLD : PDType1Font.HELVETICA;
        contentStream.setFont(font, 10);

        // Medir la longitud del texto para centrarlo
        float textWidth = font.getStringWidth(data[i]) / 1000 * 10; // Tamaño de fuente 10
        float textXOffset = (columnWidths[i] - textWidth) / 2; // Centrado horizontal
        float textYOffset = (rowHeight / 2) - 3; // Centrado vertical ajustado

        // Escribir el texto con ajuste centrado
        contentStream.beginText();
        contentStream.newLineAtOffset(x + textXOffset, y - rowHeight + textYOffset + cellMargin);
        contentStream.showText(data[i]);
        contentStream.endText();

        x += columnWidths[i];
    }
}


    @FXML
    private void btnRegresarAlMenuDocente(ActionEvent event) {
        // Lógica para regresar al menú docente
    }
}
