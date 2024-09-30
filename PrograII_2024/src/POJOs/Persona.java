package POJOs;
// Generated 06-sep-2024 21:19:58 by Hibernate Tools 4.3.1


import java.util.HashSet;
import java.util.Set;

/**
 * Persona generated by hbm2java
 */
public class Persona  implements java.io.Serializable {


     private Integer idPersona;
     private String nombre;
     private Integer cedula;
     private Integer edad;
     private Boolean estado;
     private Set<Empleado> empleados = new HashSet<Empleado>(0);
     private Set<Cliente> clientes = new HashSet<Cliente>(0);

    public Persona() {
    }

    public Persona(String nombre, Integer cedula, Integer edad, Boolean estado, Set<Empleado> empleados, Set<Cliente> clientes) {
       this.nombre = nombre;
       this.cedula = cedula;
       this.edad = edad;
       this.estado = estado;
       this.empleados = empleados;
       this.clientes = clientes;
    }
   
    public Integer getIdPersona() {
        return this.idPersona;
    }
    
    public void setIdPersona(Integer idPersona) {
        this.idPersona = idPersona;
    }
    public String getNombre() {
        return this.nombre;
    }
    
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public Integer getCedula() {
        return this.cedula;
    }
    
    public void setCedula(Integer cedula) {
        this.cedula = cedula;
    }
    public Integer getEdad() {
        return this.edad;
    }
    
    public void setEdad(Integer edad) {
        this.edad = edad;
    }
    public Boolean getEstado() {
        return this.estado;
    }
    
    public void setEstado(Boolean estado) {
        this.estado = estado;
    }
    public Set<Empleado> getEmpleados() {
        return this.empleados;
    }
    
    public void setEmpleados(Set<Empleado> empleados) {
        this.empleados = empleados;
    }
    public Set<Cliente> getClientes() {
        return this.clientes;
    }
    
    public void setClientes(Set<Cliente> clientes) {
        this.clientes = clientes;
    }




}


