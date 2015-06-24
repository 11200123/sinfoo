
package controladores.mantenimiento;

import entidades.Usuario;
import java.io.Serializable;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import org.apache.commons.codec.digest.DigestUtils;
import org.primefaces.context.RequestContext;
import persistencia.UsuarioDAO;

/**
 *
 * @author ronal
 */
@ManagedBean(name = "modificarusuario")
@ViewScoped
public class Control_Modificar_Usuario implements Serializable {
    private boolean[] mostrado;
    private int panelActivo;
    private boolean volverDesactivado;
    private String botonContinuar;
    private boolean continuarDesactivado;
    private final UsuarioDAO usu_dao;
    Usuario usuario;
    String nombreBuscado;
    private List<Usuario> listaUsuarios;
    private Usuario usuarioSeleccionado;
    
    
    public Control_Modificar_Usuario() {
        
        usu_dao=new UsuarioDAO();
        //usuario=null;
        usuario=new Usuario();

        panelActivo = 0;
        botonContinuar="Continuar";
        volverDesactivado = true;
        continuarDesactivado = false;
        mostrado = new boolean[2];
        
        
        for (int i = 1; i < mostrado.length; i++) {
            mostrado[i] = false;
        }
        mostrado[0] = true;
        nombreBuscado = "";
        System.out.println("inicio");
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

    public String getNombreBuscado() {
        return nombreBuscado;
    }

    public void setNombreBuscado(String nombreBuscado) {
        this.nombreBuscado = nombreBuscado;
    }
    
    private void actualizaEstado() {
        for (int i = 0; i < mostrado.length; i++) {
            mostrado[i] = panelActivo == i;
        }

        switch (panelActivo) {
            case 0:
                volverDesactivado = false;
                botonContinuar = "Continuar";
                break;
            case 1:
                volverDesactivado = false;
                botonContinuar = "Grabar informacion";
                break;
        }
    }
    public String buscarUsuario() {
        System.out.println("llego");
        if (!nombreBuscado.equals("")) {
            
            listaUsuarios = usu_dao.obtenerUsuariosPorNombre(nombreBuscado);
            System.out.println(listaUsuarios.size());
        } else {
            RequestContext.getCurrentInstance().execute("PF('noingresado').show();");
        }
        return null;
    }
    
    String pass1;
    String pass2;

    public String getPass1() {
        return pass1;
    }

    public void setPass1(String pass1) {
        this.pass1 = pass1;
    }

    public String getPass2() {
        return pass2;
    }

    public void setPass2(String pass2) {
        this.pass2 = pass2;
    }
    
    
    
    private void registrarUsuarioModificado() {
        
        String cadenaEncriptada="";
        
        cadenaEncriptada=DigestUtils.sha1Hex(pass1);
        System.out.println("original: "+pass1+"   la clave es: "+cadenaEncriptada);
                    
        
        
        usuarioSeleccionado.setPassUsuario(cadenaEncriptada);
        usu_dao.modificarObjeto(usuarioSeleccionado);
        RequestContext.getCurrentInstance().execute("PF('modificado').show();");
        usuarioSeleccionado = new Usuario();
        nombreBuscado = "";
        listaUsuarios=null;
        pass1="";
        pass2="";
    }
    
    
    
    public String continua() {

        switch (panelActivo) {
            case 0:
                //registrarUsuario();
                verificarUsuario();
                break;
            case 1:
                registrarUsuarioModificado();
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
    
    public void verificarUsuario(){
        if (usuarioSeleccionado==null){
            RequestContext.getCurrentInstance().execute("PF('no_seleccionado').show();");
            panelActivo--;
        }
    
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
 }