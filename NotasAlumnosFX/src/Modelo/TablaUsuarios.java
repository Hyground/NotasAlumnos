/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

/**
 *
 * @author hygro
 */
public class TablaUsuarios {
    private Integer UsuarioID;
    private String NombreCompleto;
    private String CUI;
    private String NombreUsuario;
    private String Contrasenia;
    private String Rol;
    private Integer GradoID;
    private Integer SeccionID;

    public TablaUsuarios(Integer UsuarioID, String NombreCompleto, String CUI, String NombreUsuario, String Contrasenia, String Rol, Integer GradoID, Integer SeccionID) {
        this.UsuarioID = UsuarioID;
        this.NombreCompleto = NombreCompleto;
        this.CUI = CUI;
        this.NombreUsuario = NombreUsuario;
        this.Contrasenia = Contrasenia;
        this.Rol = Rol;
        this.GradoID = GradoID;
        this.SeccionID = SeccionID;
    }

    
    
    /**
     * @return the UsuarioID
     */
    public Integer getUsuarioID() {
        return UsuarioID;
    }

    /**
     * @param UsuarioID the UsuarioID to set
     */
    public void setUsuarioID(Integer UsuarioID) {
        this.UsuarioID = UsuarioID;
    }

    /**
     * @return the NombreCompleto
     */
    public String getNombreCompleto() {
        return NombreCompleto;
    }

    /**
     * @param NombreCompleto the NombreCompleto to set
     */
    public void setNombreCompleto(String NombreCompleto) {
        this.NombreCompleto = NombreCompleto;
    }

    /**
     * @return the CUI
     */
    public String getCUI() {
        return CUI;
    }

    /**
     * @param CUI the CUI to set
     */
    public void setCUI(String CUI) {
        this.CUI = CUI;
    }

    /**
     * @return the NombreUsuario
     */
    public String getNombreUsuario() {
        return NombreUsuario;
    }

    /**
     * @param NombreUsuario the NombreUsuario to set
     */
    public void setNombreUsuario(String NombreUsuario) {
        this.NombreUsuario = NombreUsuario;
    }

    /**
     * @return the Contrasenia
     */
    public String getContrasenia() {
        return Contrasenia;
    }

    /**
     * @param Contrasenia the Contrasenia to set
     */
    public void setContrasenia(String Contrasenia) {
        this.Contrasenia = Contrasenia;
    }

    /**
     * @return the Rol
     */
    public String getRol() {
        return Rol;
    }

    /**
     * @param Rol the Rol to set
     */
    public void setRol(String Rol) {
        this.Rol = Rol;
    }

    /**
     * @return the GradoID
     */
    public Integer getGradoID() {
        return GradoID;
    }

    /**
     * @param GradoID the GradoID to set
     */
    public void setGradoID(Integer GradoID) {
        this.GradoID = GradoID;
    }

    /**
     * @return the SeccionID
     */
    public Integer getSeccionID() {
        return SeccionID;
    }

    /**
     * @param SeccionID the SeccionID to set
     */
    public void setSeccionID(Integer SeccionID) {
        this.SeccionID = SeccionID;
    }
    
    
    
}
