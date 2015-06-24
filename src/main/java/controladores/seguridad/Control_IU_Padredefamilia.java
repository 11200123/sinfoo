/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package controladores.seguridad;

import entidades.Cronograma;
import java.io.Serializable;
import java.util.Date;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import org.primefaces.context.RequestContext;
import persistencia.CronogramaDAO;
import persistencia.UsuarioDAO;
/**
 *
 * @author 7
 */
@ManagedBean(name = "padrebean")
@ViewScoped
public class Control_IU_Padredefamilia implements Serializable{
    String rol;
    String nombreUser;
    String mostrarRol;
    UsuarioDAO usu_dao;
    private final CronogramaDAO crono_dao;
    private Cronograma crono;
    Date fechaActual; 
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
    
    public Control_IU_Padredefamilia(){
        usuario = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("usuario");
        usu_dao = new UsuarioDAO();
        nombreUser = null;
        mostrarRol = null;
        rol = null;
        System.out.println(nombreUser+" "+mostrarRol+" "+rol);
        nombreUser = usu_dao.obtenerUsuariosPorID(usuario).getIdUsuario();
        mostrarRol = usu_dao.obtenerUsuariosPorID(usuario).getRol();
        mostrarRol = usu_dao.obtenerUsuariosPorID(usuario).getRol();
        if(mostrarRol.equals("padre_de_familia")){
            rol="Padre de familia";
        }
        else{
            rol=mostrarRol;
        }
        System.out.println(nombreUser);
        System.out.println(rol);
        crono_dao = new CronogramaDAO();
        crono = new Cronograma();
        fechaActual = new Date();
        //rol = Integer.toString(usu_dao.obtenerUsuariosPorUsername(usuario).getRol());
    }
    
    public String irRenovarMatricula(){
        crono = crono_dao.obtenerCronogramaPorTipo("matricula");
        if(crono!=null){
            if(fechaActual.before(crono.getFechaVenc())){
                return "IU_RenovarMatricula?usuario=" + usuario + "faces-redirect=true";
            }
            else{
                RequestContext.getCurrentInstance().execute("PF('fuera_fecha').show();");
                return null;
            }
        }
        else{
            RequestContext.getCurrentInstance().execute("PF('no_cronograma').show();");
            return null;
        }
        
    }
    
    public String irCancelarDeudas(){
        return "IU_CancelarDeudas?usuario=" + usuario + "faces-redirect=true";
    }
    
    public String irRegistrarPago(){
        return "IU_RegistrarPago?usuario=" + usuario + "faces-redirect=true";
    }
   
    
    public String salir(){
        HttpSession s = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        s.invalidate();
        return "IU_IngresarSistema?faces-redirect=true";
    }
    
   




}

