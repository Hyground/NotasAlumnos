/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import POJOs.Estudiantes;
import POJOs.Evaluaciones;
import java.math.BigDecimal;

/**
 *
 * @author IngeMayk
 */
public class TablaNotas {
         private Integer notaId;
     private Estudiantes estudiantes;
     private Evaluaciones evaluaciones;
     private BigDecimal nota;

    public TablaNotas() {
    }

    public TablaNotas(Integer notaId, Estudiantes estudiantes, Evaluaciones evaluaciones, BigDecimal nota) {
        this.notaId = notaId;
        this.estudiantes = estudiantes;
        this.evaluaciones = evaluaciones;
        this.nota = nota;
    }

    public Integer getNotaId() {
        return notaId;
    }

    public void setNotaId(Integer notaId) {
        this.notaId = notaId;
    }

    public Estudiantes getEstudiantes() {
        return estudiantes;
    }

    public void setEstudiantes(Estudiantes estudiantes) {
        this.estudiantes = estudiantes;
    }

    public Evaluaciones getEvaluaciones() {
        return evaluaciones;
    }

    public void setEvaluaciones(Evaluaciones evaluaciones) {
        this.evaluaciones = evaluaciones;
    }

    public BigDecimal getNota() {
        return nota;
    }

    public void setNota(BigDecimal nota) {
        this.nota = nota;
    }
     
     
    
}
