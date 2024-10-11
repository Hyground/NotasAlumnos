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
public class MenuSeccion {
     private Integer seccionId;
     private String nombreSeccion;

    public MenuSeccion() {
    }

    public MenuSeccion(Integer seccionId, String nombreSeccion) {
        this.seccionId = seccionId;
        this.nombreSeccion = nombreSeccion;
    }

    public Integer getSeccionId() {
        return seccionId;
    }

    public void setSeccionId(Integer seccionId) {
        this.seccionId = seccionId;
    }

    public String getNombreSeccion() {
        return nombreSeccion;
    }

    public void setNombreSeccion(String nombreSeccion) {
        this.nombreSeccion = nombreSeccion;
    }
    
}
