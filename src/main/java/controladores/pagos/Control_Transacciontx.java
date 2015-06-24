/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package controladores.pagos;

import entidades.Usuario;
import java.io.Serializable;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ViewScoped;

import javax.inject.Named;

import persistencia.UsuarioDAO;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
 
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

/**
 *
 * @author RONAL
 */
@ViewScoped
@Named("tesoreroTrasac")
public class Control_Transacciontx implements Serializable{
private boolean[] mostrado;
 private String botonArchivo;
    private boolean continuarDesactivado;
    private final UsuarioDAO usu_dao;
    String nombreUser ;
      Usuario usuario;

  
     
    private int panelActivo;
    private boolean volverDesactivado;
    private String botonContinuar;

    
    String nombreBuscado;
    private List<Usuario> listaUsuarios;
    private Usuario usuarioSeleccionado;
      
      
    private UploadedFile file;

    /**
     * Creates a new instance of Control_Transacciontx
     */
    public String getNombreBuscado() {
        return nombreBuscado;
    }

    public void setNombreBuscado(String nombreBuscado) {
        this.nombreBuscado = nombreBuscado;
    }
    public String getNombreUser() {
        return nombreUser;
    }

    public void setNombreUser(String nombreUser) {
        this.nombreUser = nombreUser;
    }
    public List<Usuario> getListaUsuarios() {
        return listaUsuarios;
    }

    public void setListaUsuarios(List<Usuario> listaUsuarios) {
        this.listaUsuarios = listaUsuarios;
    }

    public Usuario getUsuarioSeleccionado() {
        return usuarioSeleccionado;
    }

    public void setUsuarioSeleccionado(Usuario usuarioSeleccionado) {
        this.usuarioSeleccionado = usuarioSeleccionado;
    }

    public UploadedFile getFile() {
        return file;
    }

    public void setFile(UploadedFile file) {
        this.file = file;
    }

    
    public boolean[] getMostrado() {
        return mostrado;
    }

    public void setMostrado(boolean[] mostrado) {
        this.mostrado = mostrado;
    }

    public String getBotonArchivo() {
        return botonArchivo;
    }

    public void setBotonArchivo(String botonArchivo) {
        this.botonArchivo = botonArchivo;
    }

    public boolean isContinuarDesactivado() {
        return continuarDesactivado;
    }

    public void setContinuarDesactivado(boolean continuarDesactivado) {
        this.continuarDesactivado = continuarDesactivado;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public int getPanelActivo() {
        return panelActivo;
    }

    public void setPanelActivo(int panelActivo) {
        this.panelActivo = panelActivo;
    }

    public boolean isVolverDesactivado() {
        return volverDesactivado;
    }

    public void setVolverDesactivado(boolean volverDesactivado) {
        this.volverDesactivado = volverDesactivado;
    }

    public String getBotonContinuar() {
        return botonContinuar;
    }

    public void setBotonContinuar(String botonContinuar) {
        this.botonContinuar = botonContinuar;
    }

    
    
    
    public Control_Transacciontx() {
        usu_dao=new UsuarioDAO();
        
        usuario=new Usuario();
        mostrado = new boolean[0];
    }
    
    
    public void upload() {
        if(file != null) {
            FacesMessage message = new FacesMessage("Succesful", file.getFileName() + " is uploaded.");
            FacesContext.getCurrentInstance().addMessage(null, message);
        }
    
    }
    
    public String volverMenu(){
        System.out.println("esto es menu");
        return "IU_Tesorero?usuario=" + nombreUser + "&faces-redirect=true";
    }
    
    public String salir(){
        HttpSession s = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        s.invalidate();
        return "IU_IngresarSistema?faces-redirect=true";
    }
}
