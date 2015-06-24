/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores.matricula;

import entidades.Matricula;
import persistencia.AlumnoDAO;
import persistencia.AulaDAO;
import persistencia.DomicilioDAO;
import persistencia.MatriculaDAO;
import persistencia.UbicaciondocumentosDAO;
import persistencia.UsuarioDAO;
import java.io.Serializable;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import org.primefaces.context.RequestContext;

/**
 *
 * @author eduardo
 */
@ManagedBean(name = "eliminarmatricula")
@SessionScoped
public class Control_Eliminar_Matricula implements Serializable {

    private boolean[] mostrado;
    private int panelActivo;

    private boolean volverDesactivado;
    private String botonContinuar;
    private boolean grabarDesactivado;

    public boolean isGrabarDesactivado() {
        return grabarDesactivado;
    }

    public void setGrabarDesactivado(boolean grabarDesactivado) {
        this.grabarDesactivado = grabarDesactivado;
    }

    private String nombreBuscado;
    private List<Matricula> listaMatriculados;

    private Matricula matriculaSeleccionada;

    private final UbicaciondocumentosDAO ubi_dao;
    private final AulaDAO aula_dao;
    private final UsuarioDAO usu_dao;
    private final AlumnoDAO alu_dao;
    private final DomicilioDAO dom_dao;
    private final MatriculaDAO mat_dao;
    
    String usuario; 
    String nombreUser; 
    String rol; 
    
    public Control_Eliminar_Matricula() {
        panelActivo = 0;
        mostrado = new boolean[3];
        mostrado[0] = true;
        botonContinuar = "Continuar";
        volverDesactivado = true;
        for (int i = 1; i < mostrado.length; i++) {
            mostrado[i] = false;
        }
        nombreBuscado = "";
        mat_dao = new MatriculaDAO();
        alu_dao = new AlumnoDAO();
        dom_dao = new DomicilioDAO();
        ubi_dao = new UbicaciondocumentosDAO();
        aula_dao = new AulaDAO();
        usu_dao = new UsuarioDAO();
        
        usuario = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("usuario");
        nombreUser = usu_dao.obtenerUsuariosPorID(usuario).getIdUsuario();
        rol = usu_dao.obtenerUsuariosPorID(usuario).getRol();
    }

    public String salir(){
        HttpSession s = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        s.invalidate();
        return "IU_IngresarSistema?faces-redirect=true";
    }
    
    public String continua() {
        switch (panelActivo) {
            case 0:
                validarBusqueda();
                break;
            case 1:
                validarMotivoCancelacion();
                break;
            case 2:
                limpiar();
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

    
    public void continua2(){
        eliminarMatriculaAlumno();
        continua();
    }
    
    public void limpiar(){
        nombreBuscado="";
        matriculaSeleccionada=new Matricula();
        listaMatriculados = null;
    }
    
    public String retrocede() {
        if (panelActivo > 0) {
            panelActivo--;
        } else {
            panelActivo = 5;
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
                grabarDesactivado = false;
                break;
            case 1:
                volverDesactivado = false;
                grabarDesactivado = false;
                botonContinuar = "Cancelar Matrícula";
                break;
            case 2:
                volverDesactivado = false;
                grabarDesactivado = true;
                botonContinuar = "Continuar";
                break;
        }
    }

    private void validarBusqueda() {
        if (matriculaSeleccionada == null) {
            RequestContext.getCurrentInstance().execute("PF('noseleccionado').show();");
            panelActivo = 2;
        }
        else {
            if(matriculaSeleccionada.getEstadoMatricula()==0){
                RequestContext.getCurrentInstance().execute("PF('ya_eliminado').show();");
                panelActivo = 2;
            }
            
        }
            
    }
    
    void validarMotivoCancelacion(){
        if(matriculaSeleccionada.getMotivoCancelacion().equals("")){
            RequestContext.getCurrentInstance().execute("PF('ingrese_motivo').show();");
            panelActivo--;
        }
        
    }
    

    public String volverMenu(){
        return "IU_Secretaria?usuario=" + usuario + "faces-redirect=true";
    }
    
    private void eliminarMatriculaAlumno() {
        matriculaSeleccionada.setEstadoMatricula(0);
        mat_dao.modificarObjeto(matriculaSeleccionada);
        RequestContext.getCurrentInstance().execute("PF('eliminado_exito').show();");
    }

    public String buscarMatriculas() {
        if (!nombreBuscado.equals("")) {
            listaMatriculados = mat_dao.obtenerMatriculasPorNombreAlumno(nombreBuscado);
            System.out.println(listaMatriculados.size());
        } else {
            RequestContext.getCurrentInstance().execute("PF('noingresado').show();");
        }
        return null;
    }

    public boolean[] getMostrado() {
        return mostrado;
    }

    public void setMostrado(boolean[] mostrado) {
        this.mostrado = mostrado;
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

    public List<Matricula> getListaMatriculados() {
        return listaMatriculados;
    }

    public void setListaMatriculados(List<Matricula> listaMatriculados) {
        this.listaMatriculados = listaMatriculados;
    }

    public Matricula getMatriculaSeleccionada() {
        return matriculaSeleccionada;
    }

    public void setMatriculaSeleccionada(Matricula matriculaSeleccionada) {
        this.matriculaSeleccionada = matriculaSeleccionada;
    }

    public String getNombreBuscado() {
        return nombreBuscado;
    }

    public void setNombreBuscado(String nombreBuscado) {
        this.nombreBuscado = nombreBuscado;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
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
}
