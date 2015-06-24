/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package controladores.mantenimiento;

import entidades.PadreDeFamilia;
//import entidades.Pago;
import entidades.Usuario;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.servlet.http.HttpSession;
import org.apache.commons.codec.digest.DigestUtils;
import org.primefaces.context.RequestContext;
import persistencia.PadreDeFamiliaDAO;

import persistencia.UsuarioDAO;

/**
 *
 * @author Christian
 */
@ManagedBean(name = "registrarpadre")
@SessionScoped
public class Control_Registrar_Padredefamilia implements Serializable {

    private boolean[] mostrado;
    private int panelActivo;
    
    private boolean volverDesactivado;
    private boolean continuarDesactivado;
    private String botonContinuar;

    private final UsuarioDAO usu_dao;
    private final PadreDeFamiliaDAO padre_dao;
    PadreDeFamilia padre;

    Usuario usuarioPadre;
    Usuario nuevoPadre;

    
    String usuario; 
    String nombreUser; 
    String rol; 
    
    public Control_Registrar_Padredefamilia() {
        panelActivo = 0;
        mostrado = new boolean[2];
        
        for (int i = 0; i < mostrado.length; i++) {
            mostrado[i] = false;
            System.out.println(">> "+i);
        }
        mostrado[0] = true;
        
        botonContinuar = "Continuar";
        volverDesactivado = true;
        continuarDesactivado = false;

        usuario = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("usuario");
        
        usu_dao = new UsuarioDAO();
        padre_dao = new PadreDeFamiliaDAO();
        padre = new PadreDeFamilia();
        usuarioPadre = new Usuario();
        nombreUser = usu_dao.obtenerUsuariosPorID(usuario).getIdUsuario();
        rol = usu_dao.obtenerUsuariosPorID(usuario).getRol();
        nuevoPadre = new Usuario();
    }
    
    public Usuario getNuevoPadre() {
        return nuevoPadre;
    }

    public void setNuevoPadre(Usuario nuevoPadre) {
        this.nuevoPadre = nuevoPadre;
    }
    
    
    public int getPanelActivo() {
        return panelActivo;
    }

    public void setPanelActivo(int panelActivo) {
        this.panelActivo = panelActivo;
    }

    public Usuario getUsuarioPadre() {
        return usuarioPadre;
    }

    public void setUsuarioPadre(Usuario usuarioPadre) {
        this.usuarioPadre = usuarioPadre;
    }

    public void countryChangeListener(ValueChangeEvent event){
        FacesContext.getCurrentInstance().renderResponse();
    }
    
    public PadreDeFamilia getPadre() {
        return padre;
    }

    public void setPadre(PadreDeFamilia padre) {
        this.padre = padre;
    }

    public String volverMenu(){
        
        return "IU_GestionarPadredeFamilia?usuario=" + usuario + "faces-redirect=true";
    }

    public UsuarioDAO getUsu_dao() {
        return usu_dao;
    }

    public PadreDeFamiliaDAO getPadre_dao() {
        return padre_dao;
    }
    String cadenaEmail;

    public String getCadenaEmail() {
        return cadenaEmail;
    }

    public void setCadenaEmail(String cadenaEmail) {
        this.cadenaEmail = cadenaEmail;
    }
    
    public void generarUsuario(){
        String cadenaEncriptada="";
        String cadena=padre.getNumDoc();
        nuevoPadre = new Usuario();
        nuevoPadre.setIdUsuario(cadena);
        cadenaEncriptada=DigestUtils.sha1Hex(cadena);
        System.out.println("original: "+cadena+"   la clave es: "+cadenaEncriptada);
        nuevoPadre.setPassUsuario(cadenaEncriptada);
        nuevoPadre.setRol("padre_de_familia");
        nuevoPadre.setEmail(cadenaEmail);
    }
    
   public String continuar() {
        System.out.println("entrec");
        switch (panelActivo) {
            case 0:
                validarInformacion();
                generarUsuario();
                break;
            case 1:
                limpiar();
                break;   
        }
        if (panelActivo < mostrado.length - 1) {
            panelActivo++;
        } else {
            panelActivo = 0;
        }
        actualizaEstado();
        return null;
    }
    
   public void limpiar(){
        if(botonContinuar=="Salir"){
            nuevoPadre=new Usuario();
            cadenaEmail = "";
            padre = new PadreDeFamilia();
       }
   }
   
    public String retroceder() {
        System.out.println("entrea");
        if (panelActivo > 0) {
            panelActivo--;
        } else {
            panelActivo = 0;
            return "IU_GestionarPadredeFamilia?usuario=" + usuario + "&faces-redirect=true";
        }
        actualizaEstado();
        return null;
    }
    
    public boolean esNumero(String cadena){
        boolean bandera=true;
        try{
            Integer.parseInt(cadena);
        }catch (Exception e){
            bandera=false;
        }
        return bandera;  
    }
    
    
    public void validarInformacion(){
        if(!padre.getApePat().equals("") && !padre.getApeMat().equals("") 
                && !padre.getNombre1().equals("") && !padre.getNumDoc().equals("")){
            System.out.println("Todos los campos completos");
            
            if(esNumero(padre.getNumDoc()) && esNumero(""+padre.getTelefono())){
                usuarioPadre=padre_dao.obtenerPadreporDocumento(padre.getNumDoc());
                if(usuarioPadre!=null){
                   RequestContext.getCurrentInstance().execute("PF('existe').show();");
                   panelActivo--;
                }
                else{
                    System.out.println("no existeeeeeeeee v:");
                }
            }
            else{
                RequestContext.getCurrentInstance().execute("PF('no_numero').show();");
                panelActivo--;
            }
            
            
                
                
                
                
                
                
        }
        else{
            RequestContext.getCurrentInstance().execute("PF('campos_vacios').show();");
            panelActivo = 0;
        }
    }
    
    private void actualizaEstado() {
        for (int i = 0; i < mostrado.length; i++) {
            mostrado[i] = panelActivo == i;
        }

        switch (panelActivo) {
            case 0:
                volverDesactivado = true;
                botonContinuar = "Continuar";
                break;
            case 1:
                volverDesactivado = false;
                botonContinuar = "Salir";
                break;
        }
    }

    public boolean isVolverDesactivado() {
        return volverDesactivado;
    }

    public void setVolverDesactivado(boolean volverDesactivado) {
        this.volverDesactivado = volverDesactivado;
    }

    public boolean isContinuarDesactivado() {
        return continuarDesactivado;
    }

    public void setContinuarDesactivado(boolean continuarDesactivado) {
        this.continuarDesactivado = continuarDesactivado;
    }

    public String getBotonContinuar() {
        return botonContinuar;
    }

    public void setBotonContinuar(String botonContinuar) {
        this.botonContinuar = botonContinuar;
    }

    public boolean[] getMostrado() {
        return mostrado;
    }

    public void setMostrado(boolean[] mostrado) {
        this.mostrado = mostrado;
    }
    
    public void registrarUsuarioPadre(){
        usu_dao.agregarObjeto(nuevoPadre);
        padre.setEstadoActivo(1);
        padre.setUsuario(nuevoPadre);
        padre_dao.agregarObjeto(padre);
        nuevoPadre=new Usuario();
        cadenaEmail = "";
        padre = new PadreDeFamilia();
        continuar();
    }

    public List<String> completeText(String query) {
        List<String> results = new ArrayList();
        for (int i = 0; i < 10; i++) {
            results.add(query + i);
        }
        return results;
    }

    public String salir(){
        HttpSession s = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        s.invalidate();
        return "IU_IngresarSistema?faces-redirect=true";
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getNombreUser() {
        return nombreUser;
    }

    public void setNombreUser(String nombreUser) {
        this.nombreUser = nombreUser;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }
}