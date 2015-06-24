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
import persistencia.UsuarioDAO;



/**
 *
 * @author 7
 */
@ManagedBean(name = "tesoreroGestPagoMat")
@SessionScoped
public class Control_RegistrarPagoMatricula implements Serializable{
    private final EstandarespagoDAO est_dao;
    Estandarespago estandarespago;
    Estandarespago estandares;
    private boolean[] mostrado;
    private int panelActivo;
    private boolean volverDesactivado;
    private String botonContinuar;
    private boolean continuarDesactivado;
    
    Estandarespago objEstandar; //para modificar el objeto

    public Estandarespago getObjEstandar() {
        return objEstandar;
    }

    public void setObjEstandar(Estandarespago objEstandar) {
        this.objEstandar = objEstandar;
    }
    
    Usuario usuario;
    
    
    
    public Estandarespago getEstandarespago() {
        return estandarespago;
    }

    public void setEstandarespago(Estandarespago estandarespago) {
        this.estandarespago = estandarespago;
    }
    String usuar;
    String nombreUser;
    String rol;
    
    private final UsuarioDAO usu_dao;
    
    public Control_RegistrarPagoMatricula(){
        est_dao=new EstandarespagoDAO();
        
        estandarespago = new Estandarespago();
        //this.estandarespago.setTipoPago(1);
        estandares = new Estandarespago();
        usu_dao=new UsuarioDAO();
        objEstandar=new Estandarespago();
        objEstandar=est_dao.obtenerEstandarPorTipo(1);
        
        panelActivo = 0;
        botonContinuar="Guardar";
        volverDesactivado = true;
        continuarDesactivado = false;

        usuario=new Usuario();

        yaRegistrado=verificarRegistrado();  
        //yaRegistrado=false;
        mostrado = new boolean[3];
        mostrado[0] = true;
        mostrado[1] = false;
        mostrado[2] = false;
        usuar = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("usuario");
        nombreUser = usu_dao.obtenerUsuariosPorID(usuar).getIdUsuario();
        rol = usu_dao.obtenerUsuariosPorID(usuar).getRol();
        estandarespago.setTipoPago(1);
        monto=0;
        concepto="";
        
    }

    
    private boolean verificarRegistrado(){
        panelActivo=0;
        estandares=est_dao.obtenerEstandarPorTipo(1);
        if(estandares==null){
            recienRegistrar=true;
            
            return false;
            
        }
        else{
            continuarDesactivado=true;      //      AKIIIIIIIIIIIIIIIIIIIIIIIIIIIIII
            recienRegistrar=false;
            
            return true;
            
            
        }
        
    }
            
    public String getUsuar() {
        return usuar;
    }

    public boolean isYaRegistrado() {
        return yaRegistrado;
    }

    public void setYaRegistrado(boolean yaRegistrado) {
        this.yaRegistrado = yaRegistrado;
    }

    public void setUsuar(String usuar) {
        this.usuar = usuar;
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

    public boolean isRecienRegistrar() {
        return recienRegistrar;
    }

    public void setRecienRegistrar(boolean recienRegistrar) {
        this.recienRegistrar = recienRegistrar;
    }
    
    
    public boolean yaRegistrado;
    public boolean recienRegistrar;

    public int getPanelActivo() {
        
        return panelActivo;
    }

    public void limpiar(){
        estandarespago=new Estandarespago();
        estandarespago.setTipoPago(1);
        mostrado[0] = true;
        mostrado[1] = false;
        mostrado[2] = false;
        panelActivo=0;
        usuario=new Usuario();
        
    }
    public String volverMenu(){
        limpiar();
        
        return "IU_Tesorero?usuario=" + usuar + "&faces-redirect=true";
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
    
    public void registrarMontoPagoMatricula()
    {
        estandarespago.setConceptoPago(concepto);
        estandarespago.setMontoPago(monto);
        System.out.println("aquiii  "+estandarespago.getMontoPago());
        System.out.println("aquiii  "+estandarespago.getTipoPago());
        System.out.println("aquiii  "+estandarespago.getConceptoPago());
        est_dao.agregarObjeto(estandarespago);
        concepto="";
        monto=0;
    }
    String concepto="";
    int monto;

    public Estandarespago getEstandares() {
        return estandares;
    }

    public void setEstandares(Estandarespago estandares) {
        this.estandares = estandares;
    }

    public String getConcepto() {
        return concepto;
    }

    public void setConcepto(String concepto) {
        this.concepto = concepto;
    }

    public int getMonto() {
        return monto;
    }

    public void setMonto(int monto) {
        this.monto = monto;
    }
   
    
    public void verificarInformacion(){
        Estandarespago est = new Estandarespago();
        
        if(concepto=="" || monto==0){
            RequestContext.getCurrentInstance().execute("PF('noingresado').show();");
            panelActivo--;
        }
        else{
            est= est_dao.obtenerEstandarPorConcepto(concepto);
            if(est!=null){
                RequestContext.getCurrentInstance().execute("PF('ya_existe').show();");
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
    public String continuar2() {
        System.out.println("entrec");
        switch (panelActivo) {
            case 0:
                break;
            case 1:
                registrarMontoPagoMatricula();
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
        public String retrocede() {
        if (panelActivo > 0) {
            panelActivo--;
            } else {
                panelActivo = 0;
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
                    yaRegistrado=verificarRegistrado();
                    //yaRegistrado=false;
                    volverDesactivado = true;
                    botonContinuar = "Guardar";
                    break;
                case 1:
                    volverDesactivado = false;
                    botonContinuar = "Aceptar";
                    continuarDesactivado=true;
                    break;
                case 2:
                    volverDesactivado = true;
                    botonContinuar = "Aceptar";
                    continuarDesactivado=true;
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

