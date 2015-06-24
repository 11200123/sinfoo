package controladores.mantenimiento;
import entidades.Usuario;
import java.io.Serializable;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import org.primefaces.context.RequestContext;
import persistencia.UsuarioDAO;

/**
 *
 * @author ronal
 */
@ManagedBean(name = "eliminarusuario")
@SessionScoped
public class Control_Eliminar_Usuario implements Serializable {
    private boolean[] mostrado;
    private int panelActivo;
    private boolean volverDesactivado;
    private boolean contDesactivado;
    private String botonContinuar;
    private boolean continuarDesactivado;
    private final UsuarioDAO usu_dao;
    Usuario usuario;
    String nombreBuscado;
    private List<Usuario> listaUsuarios;
    private Usuario usuarioSeleccionado;
    
    
    public Control_Eliminar_Usuario() {
        
        usu_dao=new UsuarioDAO();
        //usuario=null;
        usuario=new Usuario();

        panelActivo = 0;
        botonContinuar="Siguiente";
        volverDesactivado = true;
        contDesactivado = false;
        continuarDesactivado = false;
        mostrado = new boolean[3];
        mostrado[0] = true;
        
        for (int i = 1; i < mostrado.length; i++) {
            mostrado[i] = false;
        }
        nombreBuscado = "";
    }
    
    public String buscarUsuario() {
        if (!nombreBuscado.equals("")) {
            
            listaUsuarios = usu_dao.obtenerUsuariosPorNombre(nombreBuscado);
            System.out.println(listaUsuarios.size());
        } else {
            RequestContext.getCurrentInstance().execute("PF('noingresado').show();");
        }
        return null;
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

    public boolean isContDesactivado() {
        return contDesactivado;
    }

    public void setContDesactivado(boolean contDesactivado) {
        this.contDesactivado = contDesactivado;
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
                volverDesactivado = true;
                botonContinuar = "Continuar";
                continuarDesactivado=false;
                break;
            case 1:
                volverDesactivado = false;
                continuarDesactivado=false;
                botonContinuar = "Eliminar registro";
                break;
            case 2:
                volverDesactivado = false;
                continuarDesactivado=true;
                botonContinuar = "Eliminar registro";
                break;
        }
    }
    
    public void eliminarUsuario() {
        //System.out.println(usuarioSeleccionado.  .getAlumno().getSegundoNom());
        usu_dao.eliminarObjeto(usuarioSeleccionado);
        RequestContext.getCurrentInstance().execute("PF('eliminado').show();");
        continua();
        usuarioSeleccionado=new Usuario();
        listaUsuarios=null;
        nombreBuscado="";
        
    }
    
    
    
    public String continua() {

        switch (panelActivo) {
            case 0:
                verificarUsuario();
                break;
            case 1:
                
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
        if(usuarioSeleccionado==null){
            RequestContext.getCurrentInstance().execute("PF('no_seleccionado').show();");
            panelActivo--;
        }
        else{
            System.out.println("el seleccionado es: "+usuarioSeleccionado.getRol());
            if(usuarioSeleccionado.getRol().equals("padre_de_familia")){
                RequestContext.getCurrentInstance().execute("PF('padre').show();");
                panelActivo--;
            }
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

