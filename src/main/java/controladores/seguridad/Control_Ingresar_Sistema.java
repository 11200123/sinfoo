/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores.seguridad;

import entidades.Usuario;
import java.io.Serializable;
import java.util.Map;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import org.apache.commons.codec.digest.DigestUtils;
import org.primefaces.context.RequestContext;
import persistencia.UsuarioDAO;

/**
 *
 * @author eduardo
 */
@ManagedBean(name = "login")
@ViewScoped

public class Control_Ingresar_Sistema implements Serializable {

    private String usuario;
    private String password;
    int contadorIntentos=0;
    private final UsuarioDAO usu_dao;
    private String user;
    public Control_Ingresar_Sistema() {
        usu_dao = new UsuarioDAO();
    }
    public boolean activo=true;
    
    long TInicio, TFin, tiempo; //Variables para determinar el tiempo de ejecución
  
    
    
    public String esperar(){
        if(activo==true){
            //iniciar contador
            System.out.println("esperando pe ktmadre: ");
            activo=false;
            TInicio = System.currentTimeMillis();
            return null;
        }
        else{
            TFin = System.currentTimeMillis();
            tiempo = TFin - TInicio;
            System.out.println("Tiempo de ejecución en milisegundos: " + tiempo);
            if(tiempo>300*1000){
                activo=true;
                return "IU_IngresarSistema?faces-redirect=true";
            }
            else{
                RequestContext.getCurrentInstance().execute("PF('esperando').show();");
                return null;
            }
        }
        
        
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }
    
    /**
     *
     * @return
     */
    public String validaIngreso() {
        String cadenaEncriptada="";
        cadenaEncriptada=DigestUtils.sha1Hex(password);
        System.out.println("original: "+password+"   la clave es: "+cadenaEncriptada);
                    
        
        Usuario u = usu_dao.verificarIdentificacion(usuario, cadenaEncriptada);
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        
        if ((u != null)) {
            
            Map<String, Object> sessionMap = externalContext.getSessionMap();
            sessionMap.put("usuarioLogeado", u.getIdUsuario());
            
            
            if (u.getRol().equals("director")) {
                user = u.getIdUsuario();
                return "IU_Director_GestCronograma?usuario=" + user + "&faces-redirect=true";
            }else if(u.getRol().equals("padre_de_familia")){
                user = u.getIdUsuario();
                return "IU_Padredefamilia?usuario=" + user + "faces-redirect=true";
            }else if(u.getRol().equals("administrador")){
                user = u.getIdUsuario();
                return "IU_GestionarUsuarios?usuario=" + user + "&faces-redirect=true";
            }else if(u.getRol().equals("secretaria")){
                user = u.getIdUsuario();
                return "IU_Secretaria?usuario=" + user + "&faces-redirect=true";
            }else if(u.getRol().equals("tesorera")){
                user = u.getIdUsuario();
                return "IU_Tesorero?usuario=" + user + "&faces-redirect=true";
            }else{
                return null;
            }
            
        } else {
            contadorIntentos++;
            System.out.println("XD>>>>>>>>: "+contadorIntentos);
            if(contadorIntentos>=3){
                RequestContext.getCurrentInstance().execute("PF('excedido').show();");
                
            }
            else{
                RequestContext.getCurrentInstance().execute("PF('noingresado').show();");
            }
            return null;
        }
        
        
          /////////////////////////////////// SE DEBE BORRARRRRRRRRRRRRRRRRRRRRRRRRRRRRRRR
    }

    
    
    
    
    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
