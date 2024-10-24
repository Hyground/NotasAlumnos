package Modelo;

public class Boletin {

    private String curso;
    private Double unidad1;
    private Double unidad2;
    private Double unidad3;
    private Double unidad4;
    private Double promedio;
    private String aprobado;

    public Boletin(String curso, Double unidad1, Double unidad2, Double unidad3, Double unidad4, Double promedio, String aprobado) {
        this.curso = curso;
        this.unidad1 = unidad1;
        this.unidad2 = unidad2;
        this.unidad3 = unidad3;
        this.unidad4 = unidad4;
        this.promedio = promedio;
        this.aprobado = aprobado;
    }

    public String getCurso() {
        return curso;
    }

    public void setCurso(String curso) {
        this.curso = curso;
    }

    public Double getUnidad1() {
        return unidad1;
    }

    public void setUnidad1(Double unidad1) {
        this.unidad1 = unidad1;
    }

    public Double getUnidad2() {
        return unidad2;
    }

    public void setUnidad2(Double unidad2) {
        this.unidad2 = unidad2;
    }

    public Double getUnidad3() {
        return unidad3;
    }

    public void setUnidad3(Double unidad3) {
        this.unidad3 = unidad3;
    }

    public Double getUnidad4() {
        return unidad4;
    }

    public void setUnidad4(Double unidad4) {
        this.unidad4 = unidad4;
    }

    public Double getPromedio() {
        return promedio;
    }

    public void setPromedio(Double promedio) {
        this.promedio = promedio;
    }

    public String getAprobado() {
        return aprobado;
    }

    public void setAprobado(String aprobado) {
        this.aprobado = aprobado;
    }
}
