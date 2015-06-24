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
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import org.primefaces.context.RequestContext;
import persistencia.CronogramaDAO;
import persistencia.UsuarioDAO;
/**
 *
 * @author 7
 */
@ManagedBean(name = "secretariabean")
@SessionScoped
public class Control_IU_Secretaria implements Serializable{
    String rol;
    String nombreUser;
    UsuarioDAO usu_dao;
    String usuario;
    private final CronogramaDAO crono_dao;
    private Cronograma crono;
    Date fechaActual = new Date();
    
    public Control_IU_Secretaria(){
        usuario = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("usuario");
        usu_dao = new UsuarioDAO();
        nombreUser = usu_dao.obtenerUsuariosPorID(usuario).getIdUsuario() ;
        rol = usu_dao.obtenerUsuariosPorID(usuario).getRol();
        crono_dao = new CronogramaDAO();
        crono = new Cronograma();
    }
    
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
    
    public String irGestionarMatricula(){
        crono = crono_dao.obtenerCronogramaPorTipo("matricula");
        if(crono!=null){
            if(fechaActual.before(crono.getFechaVenc())){
                return "IU_GestionarMatricula?usuario=" + usuario + "faces-redirect=true";
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
    
    public String irCancelarMatricula(){
        return "IU_CancelarMatricula?usuario=" + usuario + "faces-redirect=true";
    }
    
    public String irGestionarRetiro(){
        return "IU_GestionarRetiro?usuario=" + usuario + "faces-redirect=true";
    }
    
    public String irGestionarPadre(){
        return "IU_GestionarPadredeFamilia?usuario=" + usuario + "faces-redirect=true";
    }
    
    public String irGenerarRepMatricula(){
        return "IU_GenerarReporteMatricula?usuario=" + usuario + "faces-redirect=true";
    }
    
    public String salir(){
        HttpSession s = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        s.invalidate();
        return "IU_IngresarSistema?faces-redirect=true";
    }
}

