package Modelo;

public class Alumno {

    private String cui;
    private String codigoPersonal;
    private String nombre;
    private String apellido;

    public Alumno(String cui, String codigoPersonal, String nombre, String apellido) {
        this.cui = cui;
        this.codigoPersonal = codigoPersonal;
        this.nombre = nombre;
        this.apellido = apellido;
    }

    // Getters y setters
    public String getCui() {
        return cui;
    }

    public void setCui(String cui) {
        this.cui = cui;
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
}
