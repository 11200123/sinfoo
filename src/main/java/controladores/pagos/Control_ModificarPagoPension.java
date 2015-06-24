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
@ManagedBean(name = "modificarpension")
@SessionScoped
public class Control_ModificarPagoPension implements Serializable{
    private final EstandarespagoDAO est_dao;
    Estandarespago estandarespagoPrimaria;
    Estandarespago estandarespagoSecundaria;

    
    private boolean[] mostrado;
    private int panelActivo;
    private boolean volverDesactivado;
    private String botonContinuar;
    private boolean continuarDesactivado;
    
    Estandarespago objEstandarP; //para modificar el objeto Primaria
    Estandarespago objEstandarS; //para modificar el objeto Primaria

    public Estandarespago getObjEstandarP() {
        return objEstandarP;
    }

    public void setObjEstandarP(Estandarespago objEstandarP) {
        this.objEstandarP = objEstandarP;
    }

    public Estandarespago getObjEstandarS() {
        return objEstandarS;
    }

    public void setObjEstandarS(Estandarespago objEstandarS) {
        this.objEstandarS = objEstandarS;
    }

    
    
    Usuario usuario;
    
    public Estandarespago getEstandarespagoPrimaria() {
        return estandarespagoPrimaria;
    }

    public void setEstandarespagoPrimaria(Estandarespago estandarespagoPrimaria) {
        this.estandarespagoPrimaria = estandarespagoPrimaria;
    }

    public Estandarespago getEstandarespagoSecundaria() {
        return estandarespagoSecundaria;
    }

    public void setEstandarespagoSecundaria(Estandarespago estandarespagoSecundaria) {
        this.estandarespagoSecundaria = estandarespagoSecundaria;
    }
    
    boolean existe=false;
   
    
    public Control_ModificarPagoPension(){
        est_dao=new EstandarespagoDAO();
        this.estandarespagoPrimaria=new Estandarespago();
        this.estandarespagoSecundaria=new Estandarespago();
        
        
        objEstandarP=new Estandarespago();
        objEstandarS=new Estandarespago();
        
        objEstandarP=est_dao.obtenerEstandarPorTipo(2);
        objEstandarS=est_dao.obtenerEstandarPorTipo(3);
        
        montoPrimaria=0;
        montoSecundaria=0;
        conceptoPrimaria="";
        conceptoSecundaria="";
        
        llenar();
        
        
        
        panelActivo = 0;
        botonContinuar="Modificar";
        volverDesactivado = true;
        continuarDesactivado = false;

        usuario=new Usuario();
        
        
                
        mostrado = new boolean[3];
        mostrado[0] = true;
        mostrado[1] = false;
        mostrado[2] = false;

    }
    void llenar(){
        if(objEstandarP!=null){
            montoPrimaria=objEstandarP.getMontoPago();
            conceptoPrimaria=objEstandarP.getConceptoPago();
            existe=true;
        }
        if(objEstandarS!=null){
            montoSecundaria=objEstandarS.getMontoPago();
            conceptoSecundaria=objEstandarS.getConceptoPago();
            existe=true;
        }
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
    
    String conceptoPrimaria;
    int montoPrimaria;
    String conceptoSecundaria;
    int montoSecundaria;

    public int getPanelActivo() {
        
        return panelActivo;
    }

    public void limpiar(){
        estandarespagoPrimaria=new Estandarespago();
        estandarespagoSecundaria=new Estandarespago();
        
        estandarespagoPrimaria=est_dao.obtenerEstandarPorTipo(2);
        estandarespagoSecundaria=est_dao.obtenerEstandarPorTipo(3);
        
        montoPrimaria=estandarespagoPrimaria.getMontoPago();
        montoSecundaria=estandarespagoSecundaria.getMontoPago();
        conceptoPrimaria=estandarespagoPrimaria.getConceptoPago();
        conceptoSecundaria=estandarespagoSecundaria.getConceptoPago();
        mostrado[0] = true;
        mostrado[1] = false;
        mostrado[2] = false;
        panelActivo=0;
        usuario=new Usuario();
        
    }
    public String volverMenu(){
        limpiar();
        //return "IU_Tesorero?usuario=" + nombreUser + "&faces-redirect=true";
        continuarDesactivado=false;
        return "IU_Tesorero?faces-redirect=true";
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
    
    
    
    public void actualizarMontoPagoPension()
    {
        
        objEstandarP.setConceptoPago(conceptoPrimaria);
        objEstandarP.setMontoPago(montoPrimaria);
        objEstandarS.setConceptoPago(conceptoSecundaria);
        objEstandarS.setMontoPago(montoSecundaria);
        est_dao.modificarObjeto(objEstandarP);
        est_dao.modificarObjeto(objEstandarS);
        
        
    }
   
    
    void verificarInformacion(){
        if(montoPrimaria<=0 || montoSecundaria<=0 
                || conceptoPrimaria==""||conceptoSecundaria==""){
            RequestContext.getCurrentInstance().execute("PF('noingresado').show();");
            panelActivo--;
        }
        else{
            if(existe){
                if(conceptoPrimaria.equals(conceptoSecundaria)){
                    RequestContext.getCurrentInstance().execute("PF('montos_iguales').show();");
                    panelActivo--;
                }
                else{
                    Estandarespago estpa = new Estandarespago();
                    estpa=est_dao.obtenerEstandarPorTipo(1);
                    if(estpa!=null){
                        if(conceptoSecundaria.equals(estpa.getConceptoPago()) 
                                || conceptoPrimaria.equals(estpa.getConceptoPago())){
                            RequestContext.getCurrentInstance().execute("PF('de_otro').show();");
                            panelActivo--;
                        }
                        
                    }
                   
                }
            }
            else{
                RequestContext.getCurrentInstance().execute("PF('no_existe').show();");
                panelActivo--;
            }
                
            
            
        }
    }
    
    
    public String continuar() {
        System.out.println("entrec");
        switch (panelActivo) {
            case 0:
                verificarInformacion();
                
                
                break;
            case 1:
                actualizarMontoPagoPension();
                
                System.out.println("Entre aqui2");
                
                break;   
            case 2:
               
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
    
        public String retroceder() {
            System.out.println("entrea");
            if (panelActivo > 0) {
                panelActivo--;
            } else {
                panelActivo = 0;
                return "IU_Tesorero_GestPagoMatricula?usuario=" + usuario + "&faces-redirect=true";
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
                    botonContinuar = "Grabar";
                    continuarDesactivado = false;
                    break;
                case 1:
                    volverDesactivado = false;
                    botonContinuar = "Aceptar";
                    continuarDesactivado= true;
                    break;
                case 2:
                    volverDesactivado = true;
                    botonContinuar = "Aceptar";
                    continuarDesactivado= true;
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

