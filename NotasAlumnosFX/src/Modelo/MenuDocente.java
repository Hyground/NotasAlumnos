/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import POJOs.Grados;
import POJOs.Secciones;

/**
 *
 * @author wissegt
 */
public class MenuDocente {
        private Integer usuarioId;
     private Grados grados;
     private Secciones secciones;
     private String nombreCompleto;
     private String cui;
     private String nombreUsuario;
     private String contrasenia;
     private String rol;

    public MenuDocente() {
    }

    public MenuDocente(Integer usuarioId, Grados grados, Secciones secciones, String nombreCompleto, String cui, String nombreUsuario, String contrasenia, String rol) {
        this.usuarioId = usuarioId;
        this.grados = grados;
        this.secciones = secciones;
        this.nombreCompleto = nombreCompleto;
        this.cui = cui;
        this.nombreUsuario = nombreUsuario;
        this.contrasenia = contrasenia;
        this.rol = rol;
    }

    public Integer getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Integer usuarioId) {
        this.usuarioId = usuarioId;
    }

    public Grados getGrados() {
        return grados;
    }

    public void setGrados(Grados grados) {
        this.grados = grados;
    }

    public Secciones getSecciones() {
        return secciones;
    }

    public void setSecciones(Secciones secciones) {
        this.secciones = secciones;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public String getCui() {
        return cui;
    }

    public void setCui(String cui) {
        this.cui = cui;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getContrasenia() {
        return contrasenia;
    }

    public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }
     
    
}
