package POJOs;
// Generated 10/10/2024 10:33:56 AM by Hibernate Tools 4.3.1


import java.util.HashSet;
import java.util.Set;

/**
 * Grados generated by hbm2java
 */
public class Grados  implements java.io.Serializable {


     private Integer gradoId;
     private String nombreGrado;
     private Set<Evaluaciones> evaluacioneses = new HashSet<Evaluaciones>(0);
     private Set<Docentes> docenteses = new HashSet<Docentes>(0);
     private Set<Cursos> cursoses = new HashSet<Cursos>(0);
     private Set<Estudiantes> estudianteses = new HashSet<Estudiantes>(0);

    public Grados() {
    }

	
    public Grados(String nombreGrado) {
        this.nombreGrado = nombreGrado;
    }
    public Grados(String nombreGrado, Set<Evaluaciones> evaluacioneses, Set<Docentes> docenteses, Set<Cursos> cursoses, Set<Estudiantes> estudianteses) {
       this.nombreGrado = nombreGrado;
       this.evaluacioneses = evaluacioneses;
       this.docenteses = docenteses;
       this.cursoses = cursoses;
       this.estudianteses = estudianteses;
    }
   
    public Integer getGradoId() {
        return this.gradoId;
    }
    
    public void setGradoId(Integer gradoId) {
        this.gradoId = gradoId;
    }
    public String getNombreGrado() {
        return this.nombreGrado;
    }
    
    public void setNombreGrado(String nombreGrado) {
        this.nombreGrado = nombreGrado;
    }
    public Set<Evaluaciones> getEvaluacioneses() {
        return this.evaluacioneses;
    }
    
    public void setEvaluacioneses(Set<Evaluaciones> evaluacioneses) {
        this.evaluacioneses = evaluacioneses;
    }
    public Set<Docentes> getDocenteses() {
        return this.docenteses;
    }
    
    public void setDocenteses(Set<Docentes> docenteses) {
        this.docenteses = docenteses;
    }
    public Set<Cursos> getCursoses() {
        return this.cursoses;
    }
    
    public void setCursoses(Set<Cursos> cursoses) {
        this.cursoses = cursoses;
    }
    public Set<Estudiantes> getEstudianteses() {
        return this.estudianteses;
    }
    
    public void setEstudianteses(Set<Estudiantes> estudianteses) {
        this.estudianteses = estudianteses;
    }




}


