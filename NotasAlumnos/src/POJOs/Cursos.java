package POJOs;
// Generated 02-oct-2024 21:21:47 by Hibernate Tools 4.3.1


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

    public Cursos() {
    }

	
    public Cursos(Grados grados, String nombreCurso) {
        this.grados = grados;
        this.nombreCurso = nombreCurso;
    }
    public Cursos(Grados grados, String nombreCurso, Set<Evaluaciones> evaluacioneses) {
       this.grados = grados;
       this.nombreCurso = nombreCurso;
       this.evaluacioneses = evaluacioneses;
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

   @Override
    public String toString() {
        return this.nombreCurso;  // Muestra el nombre del curso en el ComboBox
    }


}

