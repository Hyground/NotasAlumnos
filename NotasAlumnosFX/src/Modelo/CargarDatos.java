package Modelo;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CargarDatos {

    public List<Alumno> extraerDatosPDF(File archivoPDF) {
        List<Alumno> listaAlumnos = new ArrayList<>();

        try (PDDocument document = PDDocument.load(archivoPDF)) {
            PDFTextStripper pdfStripper = new PDFTextStripper();
            String textoPDF = pdfStripper.getText(document);
            String[] lineas = textoPDF.split("\\r?\\n");

            for (String linea : lineas) {
                if (linea.matches(".*\\d{13}.*")) {
                    String[] datos = linea.split("\\s+");
                    if (datos.length >= 9) {
                        String codigoPersonal = datos[1]; 
                        String apellidos = datos[2] + " " + datos[3]; 
                        String nombres = datos[4] + " " + datos[5];

                        String cui = "";
                        for (String dato : datos) {
                            if (dato.matches("\\d{13}")) {
                                cui = dato;
                                break;
                            }
                        }

                        Alumno alumno = new Alumno(cui, codigoPersonal, nombres, apellidos);
                        listaAlumnos.add(alumno);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (listaAlumnos.isEmpty()) {
            System.out.println("No se encontraron datos en el PDF.");
        }

        return listaAlumnos;
    }
}
