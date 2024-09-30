/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

/**
 *
 * @author vicen
 */
public class TablaPersona {
    private Integer idPersona;
    private String nombrePersona;
    private Integer cedulaPersona;
    private Integer edadPersona;

    public TablaPersona(Integer idPersona, String nombrePersona, Integer cedulaPersona, Integer edadPersona) {
        this.idPersona = idPersona;
        this.nombrePersona = nombrePersona;
        this.cedulaPersona = cedulaPersona;
        this.edadPersona = edadPersona;
    }

    /**
     * @return the idPersona
     */
    public Integer getIdPersona() {
        return idPersona;
    }

    /**
     * @param idPersona the idPersona to set
     */
    public void setIdPersona(Integer idPersona) {
        this.idPersona = idPersona;
    }

    /**
     * @return the nombrePersona
     */
    public String getNombrePersona() {
        return nombrePersona;
    }

    /**
     * @param nombrePersona the nombrePersona to set
     */
    public void setNombrePersona(String nombrePersona) {
        this.nombrePersona = nombrePersona;
    }

    /**
     * @return the cedulaPersona
     */
    public Integer getCedulaPersona() {
        return cedulaPersona;
    }

    /**
     * @param cedulaPersona the cedulaPersona to set
     */
    public void setCedulaPersona(Integer cedulaPersona) {
        this.cedulaPersona = cedulaPersona;
    }

    /**
     * @return the edadPersona
     */
    public Integer getEdadPersona() {
        return edadPersona;
    }

    /**
     * @param edadPersona the edadPersona to set
     */
    public void setEdadPersona(Integer edadPersona) {
        this.edadPersona = edadPersona;
    }
    
}
