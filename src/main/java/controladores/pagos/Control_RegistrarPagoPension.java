/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package controladores.pagos;

import entidades.Estandarespago;
import entidades.Usuario;
import java.io.Serializable;

//import javax.enterprise.context.Dependent;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import org.primefaces.context.RequestContext;
import persistencia.EstandarespagoDAO;



/**
 *
 * @author 7
 */
@ManagedBean(name = "tesoreroGestPagoPen")
@SessionScoped
public class Control_RegistrarPagoPension implements Serializable{
    private final EstandarespagoDAO est_dao;
    
    
    private boolean[] mostrado;
    private int panelActivo;
    private boolean volverDesactivado;
    private String botonContinuar;
    private boolean continuarDesactivado;
    
    
    boolean yaRegistrado;
    boolean noRegistrado;

    
    
    Usuario usuario;
    
    
    String conceptoPrimaria;
    int montoPrimaria;
    String conceptoSecundaria;
    int montoSecundaria;

    public boolean isYaRegistrado() {
        return yaRegistrado;
    }

    public void setYaRegistrado(boolean yaRegistrado) {
        this.yaRegistrado = yaRegistrado;
    }
    
    
    
    

    public String getConceptoPrimaria() {
        return conceptoPrimaria;
    }

    public void setConceptoPrimaria(String conceptoPrimaria) {
        this.conceptoPrimaria = conceptoPrimaria;
    }

    public int getMontoPrimaria() {
        return montoPrimaria;
    }

    public void setMontoPrimaria(int montoPrimaria) {
        this.montoPrimaria = montoPrimaria;
    }

    public String getConceptoSecundaria() {
        return conceptoSecundaria;
    }

    public void setConceptoSecundaria(String conceptoSecundaria) {
        this.conceptoSecundaria = conceptoSecundaria;
    }

    public int getMontoSecundaria() {
        return montoSecundaria;
    }

    public void setMontoSecundaria(int montoSecundaria) {
        this.montoSecundaria = montoSecundaria;
    }

    public Estandarespago getEstaPrimaria() {
        return estaPrimaria;
    }

    public void setEstaPrimaria(Estandarespago estaPrimaria) {
        this.estaPrimaria = estaPrimaria;
    }

    public Estandarespago getEstaSecundaria() {
        return estaSecundaria;
    }

    public void setEstaSecundaria(Estandarespago estaSecundaria) {
        this.estaSecundaria = estaSecundaria;
    }

    public boolean isNoRegistrado() {
        return noRegistrado;
    }

    public void setNoRegistrado(boolean noRegistrado) {
        this.noRegistrado = noRegistrado;
    }
    

    
    
   
    Estandarespago estaPrimaria;
    Estandarespago estaSecundaria;
    
    public Control_RegistrarPagoPension(){
        est_dao=new EstandarespagoDAO();
        
        estaPrimaria = new Estandarespago();
        estaSecundaria = new Estandarespago();
        
        panelActivo = 0;
        botonContinuar="Continuar";
        volverDesactivado = true;
        continuarDesactivado = false;

        usuario=new Usuario();
        
        
        yaRegistrado=false;
        noRegistrado=true;
                
        mostrado = new boolean[3];
        mostrado[0] = true;
        mostrado[1] = false;
        mostrado[2] = false;
        
        conceptoPrimaria="";
        montoPrimaria=0;
        conceptoSecundaria="";
        montoSecundaria=0;
        
        
        verificarYaRegistrado();
        
    }
    
    void verificarYaRegistrado(){
        Estandarespago estp= new Estandarespago();
        estp=est_dao.obtenerEstandarPorTipo(2);
        if(estp!=null){
            yaRegistrado=true;
            noRegistrado=false;
            
            continuarDesactivado=true;
        }
        else{
            yaRegistrado=false;
            noRegistrado=true;
            continuarDesactivado=false;
        }
    }
    

    public int getPanelActivo() {
        
        return panelActivo;
    }

    public void limpiar(){
        
        estaPrimaria = new Estandarespago();
        estaSecundaria = new Estandarespago();
        conceptoPrimaria= "";
        conceptoSecundaria="";
        montoPrimaria=0;
        montoSecundaria=0;
        
    }
    public String volverMenu(){
        mostrado[0] = true;
        mostrado[1] = false;
        mostrado[2] = false;
        continuarDesactivado=false;
        volverDesactivado=true;
        
        limpiar();
        return "IU_Tesorero?usuario=" + usuario + "faces-redirect=true";
    }
    
    
    public void setPanelActivo(int panelActivo) {
        this.panelActivo = panelActivo;
    }
    
    public boolean[] getMostrado() {
        return mostrado;
    }

    public void setMostrado(boolean[] mostrado) {
        this.mostrado = mostrado;
    }

    public boolean isContinuarDesactivado() {
        return continuarDesactivado;
    }

    public void setContinuarDesactivado(boolean continuarDesactivado) {
        this.continuarDesactivado = continuarDesactivado;
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
    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
    
    public Usuario getUsuario() {
        return usuario;
    }
    
    public void registrarMontoPagoPension()
    {
        estaPrimaria.setConceptoPago(conceptoPrimaria);
        estaPrimaria.setMontoPago(montoPrimaria);
        estaPrimaria.setTipoPago(2);
        
        estaSecundaria.setConceptoPago(conceptoSecundaria);
        estaSecundaria.setMontoPago(montoSecundaria);
        estaSecundaria.setTipoPago(3);
        
        est_dao.agregarObjeto(estaPrimaria);
        est_dao.agregarObjeto(estaSecundaria);
        yaRegistrado=true;
        noRegistrado=false;
        
    }
    
    
   
    
    public String continuar() {
        System.out.println("entrec");
        switch (panelActivo) {
            case 0:
                verificarCampos();
                break;
            case 1:
                registrarMontoPagoPension();
               
                break;   
            case 2:
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
    
    private void verificarCampos(){
        boolean bandera=true;
        if(conceptoPrimaria==""||conceptoSecundaria==""||montoPrimaria<=0||montoSecundaria<=0){
            RequestContext.getCurrentInstance().execute("PF('noingresado').show();");
            panelActivo--;
        }
        else{
          if(!conceptoPrimaria.equals(conceptoSecundaria)){  
            Estandarespago esta= new Estandarespago();
            esta=est_dao.obtenerEstandarPorTipo(1);
            if(esta!=null){
                if(esta.getConceptoPago().equals(conceptoPrimaria)){
                    RequestContext.getCurrentInstance().execute("PF('relac_primaria').show();");
                    bandera=false;
                }
                if(esta.getConceptoPago().equals(conceptoSecundaria)){
                    RequestContext.getCurrentInstance().execute("PF('relac_secundaria').show();");
                    bandera=false;
                }
            }
            if(bandera==false){
                panelActivo--;
            }
          }
          else{
              RequestContext.getCurrentInstance().execute("PF('montos_iguales').show();");
              panelActivo--;
          }

        }
    }
    
    
    /*
    public String continuar2() {
        System.out.println("entrec");
        switch (panelActivo) {
            case 0:
                //registrarUsuario();
                System.out.println("Entre aqui1");
                
                break;
            case 1:
                actualizarMontoPagoPension();
                System.out.println("Entre aqui2");
                
                break;   
            case 2:
                System.out.println("Entre aqui3");
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
    */
        public String retroceder() {
            System.out.println("entrea");
            if (panelActivo > 0) {
                panelActivo--;
            } else {
                panelActivo = 0;
                //return "IU_Tesorero_GestPagoMatricula?usuario=" + usuario + "&faces-redirect=true";
            }
            actualizaEstado();
            return null;
        }

        private void actualizaEstado() {
            for (int i = 0; i < mostrado.length; i++) {
                mostrado[i] = panelActivo == i;
            }

            switch (panelActivo) {
                case 0:
                    volverDesactivado = true;
                    botonContinuar = "Guardar";
                    continuarDesactivado = false;
                    break;
                case 1:
                    volverDesactivado = false;
                    continuarDesactivado = true;
                    botonContinuar = "Aceptar";
                    break;
                case 2:
                    volverDesactivado = true;
                    continuarDesactivado = true;
                    botonContinuar = "Aceptar";
                    break;    
            }
        }
        public String salir(){
        HttpSession s = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        s.invalidate();
        limpiar();
        return "IU_IngresarSistema?faces-redirect=true";
        }

        
}

