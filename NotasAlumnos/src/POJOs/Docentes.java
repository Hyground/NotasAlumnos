package POJOs;
// Generated 30-sep-2024 21:55:42 by Hibernate Tools 4.3.1



/**
 * Docentes generated by hbm2java
 */
public class Docentes  implements java.io.Serializable {


     private Integer usuarioId;
     private Grados grados;
     private Secciones secciones;
     private String nombreUsuario;
     private String contrasenia;
     private String rol;
     private boolean borradoLogico;

    public Docentes() {
    }

    public Docentes(Grados grados, Secciones secciones, String nombreUsuario, String contrasenia, String rol, boolean borradoLogico) {
       this.grados = grados;
       this.secciones = secciones;
       this.nombreUsuario = nombreUsuario;
       this.contrasenia = contrasenia;
       this.rol = rol;
       this.borradoLogico = borradoLogico;
    }
   
    public Integer getUsuarioId() {
        return this.usuarioId;
    }
    
    public void setUsuarioId(Integer usuarioId) {
        this.usuarioId = usuarioId;
    }
    public Grados getGrados() {
        return this.grados;
    }
    
    public void setGrados(Grados grados) {
        this.grados = grados;
    }
    public Secciones getSecciones() {
        return this.secciones;
    }
    
    public void setSecciones(Secciones secciones) {
        this.secciones = secciones;
    }
    public String getNombreUsuario() {
        return this.nombreUsuario;
    }
    
    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }
    public String getContrasenia() {
        return this.contrasenia;
    }
    
    public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
    }
    public String getRol() {
        return this.rol;
    }
    
    public void setRol(String rol) {
        this.rol = rol;
    }
    public boolean isBorradoLogico() {
        return this.borradoLogico;
    }
    
    public void setBorradoLogico(boolean borradoLogico) {
        this.borradoLogico = borradoLogico;
    }




}


