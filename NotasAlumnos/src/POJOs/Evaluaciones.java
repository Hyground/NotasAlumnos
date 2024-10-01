package POJOs;
// Generated 30-sep-2024 21:55:42 by Hibernate Tools 4.3.1


import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

/**
 * Evaluaciones generated by hbm2java
 */
public class Evaluaciones  implements java.io.Serializable {


     private Integer evaluacionId;
     private Bimestres bimestres;
     private Cursos cursos;
     private Grados grados;
     private String nombreEvaluacion;
     private String tipo;
     private BigDecimal ponderacion;
     private Set<Notas> notases = new HashSet<Notas>(0);

    public Evaluaciones() {
    }

	
    public Evaluaciones(Bimestres bimestres, Cursos cursos, Grados grados, String nombreEvaluacion, String tipo, BigDecimal ponderacion) {
        this.bimestres = bimestres;
        this.cursos = cursos;
        this.grados = grados;
        this.nombreEvaluacion = nombreEvaluacion;
        this.tipo = tipo;
        this.ponderacion = ponderacion;
    }
    public Evaluaciones(Bimestres bimestres, Cursos cursos, Grados grados, String nombreEvaluacion, String tipo, BigDecimal ponderacion, Set<Notas> notases) {
       this.bimestres = bimestres;
       this.cursos = cursos;
       this.grados = grados;
       this.nombreEvaluacion = nombreEvaluacion;
       this.tipo = tipo;
       this.ponderacion = ponderacion;
       this.notases = notases;
    }
   
    public Integer getEvaluacionId() {
        return this.evaluacionId;
    }
    
    public void setEvaluacionId(Integer evaluacionId) {
        this.evaluacionId = evaluacionId;
    }
    public Bimestres getBimestres() {
        return this.bimestres;
    }
    
    public void setBimestres(Bimestres bimestres) {
        this.bimestres = bimestres;
    }
    public Cursos getCursos() {
        return this.cursos;
    }
    
    public void setCursos(Cursos cursos) {
        this.cursos = cursos;
    }
    public Grados getGrados() {
        return this.grados;
    }
    
    public void setGrados(Grados grados) {
        this.grados = grados;
    }
    public String getNombreEvaluacion() {
        return this.nombreEvaluacion;
    }
    
    public void setNombreEvaluacion(String nombreEvaluacion) {
        this.nombreEvaluacion = nombreEvaluacion;
    }
    public String getTipo() {
        return this.tipo;
    }
    
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
    public BigDecimal getPonderacion() {
        return this.ponderacion;
    }
    
    public void setPonderacion(BigDecimal ponderacion) {
        this.ponderacion = ponderacion;
    }
    public Set<Notas> getNotases() {
        return this.notases;
    }
    
    public void setNotases(Set<Notas> notases) {
        this.notases = notases;
    }




}


