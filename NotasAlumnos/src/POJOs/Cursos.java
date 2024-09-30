package POJOs;
// Generated 29/09/2024 11:15:42 PM by Hibernate Tools 4.3.1


import java.util.HashSet;
import java.util.Set;

/**
 * Cursos generated by hbm2java
 */
public class Cursos  implements java.io.Serializable {


     private Integer cursoId;
     private Grados grados;
     private String nombreCurso;
     private Set<Evaluaciones> evaluacioneses = new HashSet<Evaluaciones>(0);
     private Set<Actividades> actividadeses = new HashSet<Actividades>(0);
     private Set<Docentes> docenteses = new HashSet<Docentes>(0);

    public Cursos() {
    }

	
    public Cursos(Grados grados, String nombreCurso) {
        this.grados = grados;
        this.nombreCurso = nombreCurso;
    }
    public Cursos(Grados grados, String nombreCurso, Set<Evaluaciones> evaluacioneses, Set<Actividades> actividadeses, Set<Docentes> docenteses) {
       this.grados = grados;
       this.nombreCurso = nombreCurso;
       this.evaluacioneses = evaluacioneses;
       this.actividadeses = actividadeses;
       this.docenteses = docenteses;
    }
   
    public Integer getCursoId() {
        return this.cursoId;
    }
    
    public void setCursoId(Integer cursoId) {
        this.cursoId = cursoId;
    }
    public Grados getGrados() {
        return this.grados;
    }
    
    public void setGrados(Grados grados) {
        this.grados = grados;
    }
    public String getNombreCurso() {
        return this.nombreCurso;
    }
    
    public void setNombreCurso(String nombreCurso) {
        this.nombreCurso = nombreCurso;
    }
    public Set<Evaluaciones> getEvaluacioneses() {
        return this.evaluacioneses;
    }
    
    public void setEvaluacioneses(Set<Evaluaciones> evaluacioneses) {
        this.evaluacioneses = evaluacioneses;
    }
    public Set<Actividades> getActividadeses() {
        return this.actividadeses;
    }
    
    public void setActividadeses(Set<Actividades> actividadeses) {
        this.actividadeses = actividadeses;
    }
    public Set<Docentes> getDocenteses() {
        return this.docenteses;
    }
    
    public void setDocenteses(Set<Docentes> docenteses) {
        this.docenteses = docenteses;
    }




}


