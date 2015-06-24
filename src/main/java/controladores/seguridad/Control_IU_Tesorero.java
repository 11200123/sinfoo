/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package controladores.seguridad;

import java.io.Serializable;

//import javax.enterprise.context.Dependent;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import javax.servlet.http.HttpSession;
import persistencia.UsuarioDAO;
/**
 *
 * @author 7
 */
@ManagedBean(name = "tesorerobean")
@SessionScoped
public class Control_IU_Tesorero implements Serializable{
   
    String rol;
    public String nombreUser;
    String mostrarRol;
    UsuarioDAO usu_dao;
    

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public String getNombreUser() {
        return nombreUser;
    }

    public void setNombreUser(String nombreUser) {
        this.nombreUser = nombreUser;
    }
    String usuario;
    
    public Control_IU_Tesorero(){
        System.out.println("aca");
        usuario = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("usuario");
        System.out.println("aca2");
        usu_dao = new UsuarioDAO();
        if(usu_dao==null){
            System.out.println("estoy aki oyeee");
        }else{
            System.out.println("a no serrr");
        }
        nombreUser = usu_dao.obtenerUsuariosPorID(usuario).getIdUsuario();
        
        if(nombreUser==null){
            System.out.println("holi");
        }
        else{
            System.out.println("estoy aki cositi");
        }
        mostrarRol = usu_dao.obtenerUsuariosPorID(usuario).getRol();
        if(mostrarRol.equals("tesorero")){
            rol="Tesorero";
        }
        else{
            rol=mostrarRol;
        }
        //rol = Integer.toString(usu_dao.obtenerUsuariosPorUsername(usuario).getRol());
    }

    public String volverMenu(){
        
        return "IU_Tesorero?usuario=" + nombreUser + "&faces-redirect=true";
    }
    
    public String irGestionarMonto(){
        
        return "PruebaRegUsu?usuario=" + nombreUser + "&faces-redirect=true";
    }
    
    public String irGestionarPagoMatricula(){
        return "IU_Tesorero_GestPagoMatricula?usuario=" + nombreUser + "&faces-redirect=true";
        
    }
    
    public String irCargarTransacciones(){
        
        return "IU_Tesorero_CargarTx?usuario=" + nombreUser + "&faces-redirect=true";
    }
    
    public String irMostrarCronograma(){
        
        return "IU_Tesorero_MostrarCronograma?usuario=" + nombreUser + "&faces-redirect=true";
    }
    
    public String irGestPagoPension(){
        return "IU_Tesorero_GestPagoPension?usuario=" + nombreUser + "&faces-redirect=true";
    }
    
    public String irGenerarReportePagos(){
        return "IU_Tesorero_GenerarRepPagos?usuario=" + nombreUser + "&faces-redirect=true";
    }
    
    public String irGestMontosPensiones(){
        return "IU_Tesorero_GestMontoPension?usuario=" + nombreUser + "&faces-redirect=true";
    }
    
    public String salir(){
        System.out.println("estoeslogout");
        HttpSession s = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        s.invalidate();
        return "IU_IngresarSistema?faces-redirect=true";
    }




}

