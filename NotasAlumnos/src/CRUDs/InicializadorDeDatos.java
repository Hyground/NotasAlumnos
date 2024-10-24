/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CRUDs;
import CRUDs.CBimestres;
import CRUDs.CGrados;
import CRUDs.CCurso;
import CRUDs.CSecciones;

/**
 *
 * @author Miguel
 */

public class InicializadorDeDatos {
      public static void IniciarTodo() {
        // Crear bimestres automáticamente
        System.out.println("Creando Bimestres automáticamente...");
        boolean bimestresCreado = CBimestres.crearBimestres();
        if (bimestresCreado) {
            System.out.println("Los bimestres se han creado correctamente.");
        } else {
            System.out.println("Los bimestres ya existían, no fue necesario crearlos.");
        }

        // Crear grados automáticamente
        System.out.println("Creando Grados automáticamente...");
        boolean gradosCreado = CGrados.crearGrados(); // Llama al método que crea los grados
        if (gradosCreado) {
            System.out.println("Los grados se han creado correctamente.");
        } else {
            System.out.println("Los grados ya existían, no fue necesario crearlos.");
        }
        
        // Crear seccion automáticamente
            System.out.println("Creando Secciones automáticamente...");
        boolean seccion = CSecciones.crearSecciones(); // Llama al método que crea los cursos
        if (seccion) {
            System.out.println("las secciones se han creado correctamente.");
        } else {
            System.out.println("Las secciones ya existían, no fue necesario crearlos.");
        }

        // Crear cursos automáticamente
        System.out.println("Creando Cursos automáticamente...");
        boolean cursosCreado = CCurso.crearCursos(); // Llama al método que crea los cursos
        if (cursosCreado) {
            System.out.println("Los cursos se han creado correctamente.");
        } else {
            System.out.println("Los cursos ya existían, no fue necesario crearlos.");
        }
         
    }
    
    
}
