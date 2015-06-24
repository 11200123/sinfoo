package controladores.mantenimiento;

import entidades.Alumno;
import entidades.Usuario;
import java.io.Serializable;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import org.primefaces.context.RequestContext;
import persistencia.AlumnoDAO;
import persistencia.PadreDeFamiliaDAO;
import persistencia.UsuarioDAO;

/**
 *
 * @author TOSHIBA
 */
@ManagedBean(name = "modificarpadre")
@SessionScoped
public class Control_Modificar_Padredefamilia implements Serializable {

    private final PadreDeFamiliaDAO padre_dao;
    private boolean[] mostrado;
    private int panelActivo;
    private boolean volverDesactivado;
    private String botonContinuar;
    private boolean continuarDesactivado;
    List<Alumno> listaAlumnos;
    
    private final AlumnoDAO alu_dao;
    private final UsuarioDAO usu_dao;
    Usuario usuario;
    String usuar;
    String nombreUser;
    String rolsito;
    boolean seguir=true;
    
    private String nombreBuscado;
    private List<Usuario> listaPadres;

    private Usuario padreSeleccionado;
    
    public String getNombreBuscado() {
        return nombreBuscado;
    }

    public void setNombreBuscado(String nombreBuscado) {
        this.nombreBuscado = nombreBuscado;
    }

    public List<Usuario> getListaPadres() {
        return listaPadres;
    }

    public void setListaPadres(List<Usuario> listaPadres) {
        this.listaPadres = listaPadres;
    }


    public Usuario getPadreSeleccionado() {
        return padreSeleccionado;
    }

    public void setPadreSeleccionado(Usuario padreSeleccionado) {
        this.padreSeleccionado = padreSeleccionado;
    }
    
    public String buscarPadres() {
        if (!nombreBuscado.equals("")) {
            listaPadres=padre_dao.obtenerPadresPorNombre(nombreBuscado);
        } else {
            RequestContext.getCurrentInstance().execute("PF('noingresado').show();");
        }
        return null;
    }
    
    public Control_Modificar_Padredefamilia() {
        
        padre_dao = new PadreDeFamiliaDAO();
        padreSeleccionado = new Usuario();
        
        usu_dao=new UsuarioDAO();
        usuario=new Usuario();

        panelActivo = 0;

        botonContinuar="Continuar";
        volverDesactivado = true;
        continuarDesactivado = false;
        usuar = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("usuario");
        nombreUser = usu_dao.obtenerUsuariosPorID(usuar).getIdUsuario();
        rolsito = usu_dao.obtenerUsuariosPorID(usuar).getRol();
        
        mostrado = new boolean[2];
        mostrado[0] = true;
        mostrado[1] = false;
        alu_dao=new AlumnoDAO();
    }

    public int getPanelActivo() {
        return panelActivo;
    }

    public String volverMenu(){
        return "IU_GestionarPadredeFamilia?usuario=" + usuario + "faces-redirect=true";
        
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
       
    public List<Alumno> getListaAlumnos() {
        return listaAlumnos;
    }

    public void setListaAlumnos(List<Alumno> listaAlumnos) {
        this.listaAlumnos = listaAlumnos;
    }
    
    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
    
    public Usuario getUsuario() {
        return usuario;
    }
    
    public String continuar() {
        System.out.println("entrec");
        switch (panelActivo) {
            case 0:
                validarPadre();
                break;
            case 1:
                validarHijos();
                validarInformacion();
                modificarInformacion();
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
    
    public void validarPadre(){
        if(padreSeleccionado==null){
            RequestContext.getCurrentInstance().execute("PF('no_padre').show();");
            
            panelActivo--;
        }
    }
    
    public void validarHijos(){
        listaAlumnos=alu_dao.obtenerAlumnosPorPadre(padreSeleccionado.getIdUsuario());
        if(listaAlumnos.size()!=0){
            System.out.println("No apto para eliminar");
            System.out.println("tamañitoooooooo:   "+listaAlumnos.size());
            
            if(padreSeleccionado.getPadreDeFamilia().getEstadoActivo()==1){
                seguir=true;
            }
            else{
                RequestContext.getCurrentInstance().execute("PF('tiene_hijos').show();");
                seguir=false;
            }
        }
        else{
            seguir=true;
        }
    }
    
    public boolean esNumero(String cadena){
        boolean bandera=true;
        try{
            Integer.parseInt(cadena);
        }catch (Exception e){
            bandera=false;
        }
        return bandera;  
    }
    
    private void validarInformacion(){
        if(seguir){
        if((padreSeleccionado.getPadreDeFamilia().getApePat().equals(""))||(padreSeleccionado.getPadreDeFamilia().getApeMat().equals(""))
          ||(padreSeleccionado.getPadreDeFamilia().getNombre1().equals(""))
          ||(padreSeleccionado.getPadreDeFamilia().getNumDoc().equals(""))){
            System.out.println("Debe completar los campos");
            RequestContext.getCurrentInstance().execute("PF('camposvacios').show();");
            seguir=false;
            panelActivo--;
        }
        else{
            
            System.out.println("No nulo");
            seguir=true;
        }
      }
    }
    
    private void modificarInformacion(){
        if(seguir){
            padre_dao.modificarObjeto(padreSeleccionado.getPadreDeFamilia());
            usu_dao.modificarObjeto(padreSeleccionado);
            RequestContext.getCurrentInstance().execute("PF('guardado').show();");
        }
        padreSeleccionado = new Usuario();
        nombreBuscado = "";
        listaPadres = null;
    }
    
    public String retroceder() {
        System.out.println("entrea");
        if (panelActivo > 0) {
            panelActivo--;
        } else {
            panelActivo = 0;
            return "IU_GestionarPadredeFamilia?usuario=" + usuario + "&faces-redirect=true";
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
                botonContinuar = "Continuar";
                break;
            case 1:
                volverDesactivado = false;
                botonContinuar = "Modificar";
                break;
        }
    }


    public String salir(){
        HttpSession s = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        s.invalidate();
        return "IU_IngresarSistema?faces-redirect=true";
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

    public String getRolsito() {
        return rolsito;
    }

    public void setRolsito(String rolsito) {
        this.rolsito = rolsito;
    }
    

    
 }