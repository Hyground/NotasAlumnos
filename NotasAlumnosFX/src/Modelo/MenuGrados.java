/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

/**
 *
 * @author wissegt
 */
public class MenuGrados {
     private Integer gradoId;
     private String nombreGrado;

    public MenuGrados(Integer gradoId, String nombreGrado) {
        this.gradoId = gradoId;
        this.nombreGrado = nombreGrado;
    }

    public Integer getGradoId() {
        return gradoId;
    }

    public void setGradoId(Integer gradoId) {
        this.gradoId = gradoId;
    }

    public String getNombreGrado() {
        return nombreGrado;
    }

    public void setNombreGrado(String nombreGrado) {
        this.nombreGrado = nombreGrado;
    }
     
    
}
