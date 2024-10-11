/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import POJOs.Grados;

/**
 *
 * @author wissegt
 */
public class MenuCursos {

    private Integer cursoId;
    private Grados grados;
    private String nombreCurso;

    public MenuCursos() {
    }

    public MenuCursos(Integer cursoId, Grados grados, String nombreCurso) {
        this.cursoId = cursoId;
        this.grados = grados;
        this.nombreCurso = nombreCurso;
    }

    public Integer getCursoId() {
        return cursoId;
    }

    public void setCursoId(Integer cursoId) {
        this.cursoId = cursoId;
    }

    public Grados getGrados() {
        return grados;
    }

    public void setGrados(Grados grados) {
        this.grados = grados;
    }

    public String getNombreCurso() {
        return nombreCurso;
    }

    public void setNombreCurso(String nombreCurso) {
        this.nombreCurso = nombreCurso;
    }
    

}
