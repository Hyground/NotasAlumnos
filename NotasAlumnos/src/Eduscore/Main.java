/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Eduscore;

/**
 *
 * @author IngeMayk
 */
public class Main {
    public static void main(String[] args) {
        
       // este comentario se ejecuta una vez es un metodo para iniciar todo 
       // CRUDs.InicializadorDeDatos.IniciarTodo();
        
        /// esto se ejecuta una vez, 
        
        //System.out.println("Crear Docente" + CRUDs.CDocentes.crearDocente("Admin", "admin", "Docente", 1, 1));
        System.out.println(""+ CRUDs.CLogin.CLogin("Admin", "admin"));
    }
    
}