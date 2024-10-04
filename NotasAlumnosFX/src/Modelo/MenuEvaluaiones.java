/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import POJOs.Bimestres;
import POJOs.Cursos;
import POJOs.Grados;
import POJOs.Notas;
import POJOs.Secciones;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author IngeMayk
 */
public class MenuEvaluaiones {
     private Integer evaluacionId;
     private Bimestres bimestres;
     private Cursos cursos;
     private Grados grados;
     private Secciones secciones;
     private String nombreEvaluacion;
     private String tipo;
     private BigDecimal ponderacion;
     private Set<Notas> notases = new HashSet<Notas>(0);

    public MenuEvaluaiones(Integer evaluacionId, Bimestres bimestres, Cursos cursos, Grados grados, Secciones secciones, String nombreEvaluacion, String tipo, BigDecimal ponderacion) {
        this.evaluacionId = evaluacionId;
        this.bimestres = bimestres;
        this.cursos = cursos;
        this.grados = grados;
        this.secciones = secciones;
        this.nombreEvaluacion = nombreEvaluacion;
        this.tipo = tipo;
        this.ponderacion = ponderacion;
    }

    public Integer getEvaluacionId() {
        return evaluacionId;
    }

    public void setEvaluacionId(Integer evaluacionId) {
        this.evaluacionId = evaluacionId;
    }

    public Bimestres getBimestres() {
        return bimestres;
    }

    public void setBimestres(Bimestres bimestres) {
        this.bimestres = bimestres;
    }

    public Cursos getCursos() {
        return cursos;
    }

    public void setCursos(Cursos cursos) {
        this.cursos = cursos;
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

    public String getNombreEvaluacion() {
        return nombreEvaluacion;
    }

    public void setNombreEvaluacion(String nombreEvaluacion) {
        this.nombreEvaluacion = nombreEvaluacion;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public BigDecimal getPonderacion() {
        return ponderacion;
    }

    public void setPonderacion(BigDecimal ponderacion) {
        this.ponderacion = ponderacion;
    }

    public Set<Notas> getNotases() {
        return notases;
    }

    public void setNotases(Set<Notas> notases) {
        this.notases = notases;
    }
    
    
    
     
    
    
}
