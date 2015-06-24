package controladores.mantenimiento;

import entidades.Usuario;
import java.io.Serializable;
import java.util.Date;

import javax.faces.bean.ManagedBean;

import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import org.primefaces.context.RequestContext;
import persistencia.UsuarioDAO;

import org.apache.commons.codec.digest.DigestUtils;
/**
 *
 * @author ronal
 */
@ManagedBean(name = "registrarusuario")
@ViewScoped
public class Control_Registrar_Usuario implements Serializable {
    Date ahora = new Date();
    
    
    private boolean[] mostrado;
    private int panelActivo;
    private String padrebuscado;
    private boolean volverDesactivado;
    private String botonContinuar;
    private boolean continuarDesactivado;

   private final UsuarioDAO usu_dao;
   
    public Usuario usuario;
    String usuar;
    String nombreUser;
    String rol;
    
    public Control_Registrar_Usuario() {
        usu_dao=new UsuarioDAO();
        usuario=new Usuario();
        panelActivo = 0;
        botonContinuar="Guardar registro";
        volverDesactivado = true;
        continuarDesactivado = false;
        usuar = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("usuario");
        nombreUser = usu_dao.obtenerUsuariosPorID(usuar).getIdUsuario();
        rol = usu_dao.obtenerUsuariosPorID(usuar).getRol();
        mostrado = new boolean[1];
        mostrado[0] = true;
        
    }

    
    public int getPanelActivo() {
        return panelActivo;
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

    public String getComprobarPass() {
        return comprobarPass;
    }

    public void setComprobarPass(String comprobarPass) {
        this.comprobarPass = comprobarPass;
    }
    
    public String comprobarPass;
  
    public String getPadrebuscado() {
        return padrebuscado;
    }

    public void setPadrebuscado(String padrebuscado) {
        this.padrebuscado = padrebuscado;
    }

    public String continua() {

        switch (panelActivo) {
            case 0:
                registrarUsuario();
                break;
            
        }
        return null;
    }

    String cadenaID="";

    public String getCadenaID() {
        return cadenaID;
    }

    public void setCadenaID(String cadenaID) {
        this.cadenaID = cadenaID;
    }
    
    String pass1="";
    String email="";

    public String getPass1() {
        return pass1;
    }

    public void setPass1(String pass1) {
        this.pass1 = pass1;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    
    String role;

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
    
    
    public void registrarUsuario(){
        String cadenaEncriptada="";
        System.out.println("registrao "+cadenaID);
        if((cadenaID.equals(""))||(pass1.equals(""))||(comprobarPass.equals(""))){
            RequestContext.getCurrentInstance().execute("PF('complete_campos').show();");
        }
        else{
            Usuario uPrueba = usu_dao.obtenerUsuariosPorID(cadenaID);
            if(pass1.equals(comprobarPass)){
                if (uPrueba==null) {
                    usuario.setIdUsuario(cadenaID);
                    
                    cadenaEncriptada=DigestUtils.sha1Hex(pass1);
                    System.out.println("original: "+pass1+"   la clave es: "+cadenaEncriptada);
                    
                    
                    usuario.setPassUsuario(cadenaEncriptada);
                    usuario.setEmail(email);
                    usuario.setRol(role);
                    usu_dao.agregarObjeto(usuario);
                    RequestContext.getCurrentInstance().execute("PF('guardado').show();");
                    usuario=new Usuario();
                    cadenaID="";
                    pass1="";
                    email="";
                    role="";
                    comprobarPass="";
                }else{
                    RequestContext.getCurrentInstance().execute("PF('ya_existe').show();");
                }
            }
            else{
                RequestContext.getCurrentInstance().execute("PF('no_iguales').show();");
                }
        }
    
    }        

    public String salir(){
        HttpSession s = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        s.invalidate();
        return "IU_IngresarSistema?faces-redirect=true";
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public String getUsuar() {
        return usuar;
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



    
 }