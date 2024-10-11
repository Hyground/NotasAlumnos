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
public class tablaEstuidiante {

    private String cui;
    private Grados grados;
    private Secciones secciones;
    private String codigoPersonal;
    private String nombre;
    private String apellido;
    private boolean borradoLogico;

    public tablaEstuidiante() {
    }

    public tablaEstuidiante(String cui, Grados grados, Secciones secciones, String codigoPersonal, String nombre, String apellido, boolean borradoLogico) {
        this.cui = cui;
        this.grados = grados;
        this.secciones = secciones;
        this.codigoPersonal = codigoPersonal;
        this.nombre = nombre;
        this.apellido = apellido;
        this.borradoLogico = borradoLogico;
    }

    public String getCui() {
        return cui;
    }

    public void setCui(String cui) {
        this.cui = cui;
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

    public String getCodigoPersonal() {
        return codigoPersonal;
    }

    public void setCodigoPersonal(String codigoPersonal) {
        this.codigoPersonal = codigoPersonal;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public boolean isBorradoLogico() {
        return borradoLogico;
    }

    public void setBorradoLogico(boolean borradoLogico) {
        this.borradoLogico = borradoLogico;
    }
    
    
}
