/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores.matricula;

import entidades.Alumno;
import entidades.Aula;
import entidades.Matricula;
import entidades.Usuario;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.servlet.http.HttpSession;
import org.primefaces.context.RequestContext;
import persistencia.AlumnoDAO;
import persistencia.AulaDAO;
import persistencia.DomicilioDAO;
import persistencia.MatriculaDAO;
import persistencia.PadreDeFamiliaDAO;
import persistencia.UbicaciondocumentosDAO;

/**
 *
 * @author TOSHIBA
 */
@ManagedBean(name = "modificarmatricula")
@SessionScoped
public class Control_Modificar_Matricula implements Serializable {

    private boolean[] mostrado;
    private int panelActivo;

    private boolean volverDesactivado;
    private boolean grabarDesactivado;
    private String botonContinuar;

    private String nombreBuscado;
    private List<Matricula> listaMatriculados;
    private final MatriculaDAO mat_dao;
    private final AlumnoDAO alu_dao;
    private final DomicilioDAO dom_dao;
    private final UbicaciondocumentosDAO ubi_dao;
    private Matricula matriculaSeleccionada;
    private Alumno alu;
    private String numVoucher;
    public Control_Modificar_Matricula() {
        panelActivo = 0;
        mostrado = new boolean[3];
        mostrado[0] = true;
        botonContinuar = "Continuar";
        volverDesactivado = true;
        grabarDesactivado = false;
        for (int i = 1; i < mostrado.length; i++) {
            mostrado[i] = false;
        }
        mat_dao = new MatriculaDAO();
        alu_dao = new AlumnoDAO();
        dom_dao = new DomicilioDAO();
        ubi_dao = new UbicaciondocumentosDAO();
        nombreBuscado = "";
        aula = new Aula();
        alu = new Alumno();
        aula_dao = new AulaDAO();
        padre_dao = new PadreDeFamiliaDAO();
        padreSeleccionado = new Usuario();
        
    }

    public List<Usuario> getListaPadres() {
        return listaPadres;
    }

    public void setListaPadres(List<Usuario> listaPadres) {
        this.listaPadres = listaPadres;
    }

    public List<Aula> getListaGrados() {
        return listaGrados;
    }

    public void setListaGrados(List<Aula> listaGrados) {
        this.listaGrados = listaGrados;
    }

    public List<Aula> getListaAulas() {
        return listaAulas;
    }

    public void setListaAulas(List<Aula> listaAulas) {
        this.listaAulas = listaAulas;
    }

    public List<Aula> getListaSecciones() {
        return listaSecciones;
    }

    public void setListaSecciones(List<Aula> listaSecciones) {
        this.listaSecciones = listaSecciones;
    }

    public Aula getAula() {
        return aula;
    }

    public void setAula(Aula aula) {
        this.aula = aula;
    }
    
    public String continua() {
        switch (panelActivo) {
            case 0:
                validarBusqueda();
                break;
            case 1:
                validar();
                break;
            case 2:
                registrarMatriculaModificada();
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
    public String continua2() {
        switch (panelActivo) {
            case 0:
                validarBusqueda();
                break;
            case 1:
                break;
            case 2:
                //registrarMatriculaModificada();
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

    public MatriculaDAO getMat_dao() {
        return mat_dao;
    }

    public AlumnoDAO getAlu_dao() {
        return alu_dao;
    }

    public DomicilioDAO getDom_dao() {
        return dom_dao;
    }

    public AulaDAO getAula_dao() {
        return aula_dao;
    }

    public PadreDeFamiliaDAO getPadre_dao() {
        return padre_dao;
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

    private List<Aula> listaGrados;
    private List<Aula> listaAulas;
    private List<Aula> listaSecciones;
    private final AulaDAO aula_dao;
    
    private Aula aula;
    private String nombreBuscado2;

    public String getNombreBuscado2() {
        return nombreBuscado2;
    }

    public void setNombreBuscado2(String nombreBuscado2) {
        this.nombreBuscado2 = nombreBuscado2;
    }
    
    Date fechaActual = new Date();
    
    public void validar(){
        boolean bandera=true;
        
        if((esNumero(matriculaSeleccionada.getAlumno().getNumDoc()))&&
                (esNumero(matriculaSeleccionada.getAlumno().getTelfFijo()))&&
                        (esNumero(matriculaSeleccionada.getAlumno().getTelfEmerg()))){
            
            if(matriculaSeleccionada.getAlumno().getFechaNac().before(fechaActual)){
                if(!matriculaSeleccionada.getAlumno().getDomicilio().getDireccion().equals("")&&
                    !matriculaSeleccionada.getAlumno().getApePat().equals("")&&
                    !matriculaSeleccionada.getAlumno().getApeMat().equals("")&&
                    !matriculaSeleccionada.getAlumno().getPrimerNom().equals("")&&
                    !matriculaSeleccionada.getAlumno().getNumDoc().equals("")&&    
                    !matriculaSeleccionada.getAlumno().getUbicaciondocumentos().getNumFolioCertComport().equals("")&&    
                    !matriculaSeleccionada.getAlumno().getUbicaciondocumentos().getNumFolioCertNotas().equals("")&&    
                    !matriculaSeleccionada.getAlumno().getUbicaciondocumentos().getNumFolioPartNac().equals("")){
                    if((padreSeleccionado!=null)){
                            if(padreSeleccionado.getPadreDeFamilia().getEstadoActivo()==0){
                                RequestContext.getCurrentInstance().execute("PF('padre_inactivo').show();");
                                bandera=false;
                                }
                            }
                        if(bandera==false){
                            panelActivo--;
                        }
                }
                else{
                    RequestContext.getCurrentInstance().execute("PF('campos_vacios').show();");
                    panelActivo--;
                }
            }
            else{
                RequestContext.getCurrentInstance().execute("PF('fecha_razonable').show();");
                panelActivo--;
            }
        }
        else{
            RequestContext.getCurrentInstance().execute("PF('no_numero').show();");
            panelActivo--;
        }
        
        
        
        
        
        
        
    }
    
    
    private List<Usuario> listaPadres;
    private final PadreDeFamiliaDAO padre_dao;
    
    private Usuario padreSeleccionado;

    public Usuario getPadreSeleccionado() {
        return padreSeleccionado;
    }

    public void setPadreSeleccionado(Usuario padreSeleccionado) {
        this.padreSeleccionado = padreSeleccionado;
    }
    
    public String buscarPadres() {
        if (!nombreBuscado2.equals("")) {
            listaPadres=padre_dao.obtenerPadresPorNombre(nombreBuscado2);
            System.out.println("tamañito v: "+listaPadres.size());
        } else {
            RequestContext.getCurrentInstance().execute("PF('noingresado').show();");
        }
        return null;
    }
    
    public void countryChangeListener(ValueChangeEvent event){
        FacesContext.getCurrentInstance().renderResponse();
    }
    
    public void listarNiveles(){
        listaAulas=aula_dao.Niveles();
        aula.setNumGrad(0);
        listaGrados=null;
        actualizarGrados();
    }
    public void actualizarGrados(){
         aula.setNumGrad(0);
         listaGrados=null;
         if (aula.getNivel()!=0){
            listaGrados=aula_dao.obtenerGradosPorNivel(aula.getNivel());
            aula.setSeccion(0);
            listaSecciones=null;
         }
         else{
             listaGrados=null;
             aula.setNumGrad(0);
             aula.setSeccion(0);
             aula.setLimiteAlumnos(0);
             aula.setCantidadAlumnos(0);
         }
    }

    public void actualizarSecciones(){
         aula.setSeccion(0);
         listaSecciones=null;
         if ((aula.getNivel()!=0)&&(aula.getNumGrad()!=0)){
            listaSecciones=aula_dao.obtenerSeccionesPorGrado(aula.getNivel(), aula.getNumGrad());
         }
         else{
             listaSecciones=null;
             aula.setSeccion(0);
             aula.setLimiteAlumnos(0);
             aula.setCantidadAlumnos(0);
         }
    }
    
    public void actualizarCantidades(){
         if ((aula.getNivel()!=0)&&(aula.getNumGrad()!=0)&&(aula.getSeccion()!=0)){
            aula=aula_dao.obtenerAula(aula.getNivel(), aula.getNumGrad(), aula.getSeccion());
         }
         else{
             aula.setLimiteAlumnos(0);
             aula.setCantidadAlumnos(0);
         }
    }

    public boolean isGrabarDesactivado() {
        return grabarDesactivado;
    }

    public void setGrabarDesactivado(boolean grabarDesactivado) {
        this.grabarDesactivado = grabarDesactivado;
    }
    
    private void actualizaEstado() {
        for (int i = 0; i < mostrado.length; i++) {
            mostrado[i] = panelActivo == i;
        }

        switch (panelActivo) {
            case 0:
                volverDesactivado = true;
                grabarDesactivado = false;
                botonContinuar = "Continuar";
                break;
            case 1:
                listarNiveles();
                grabarDesactivado = false;
                volverDesactivado = false;
                botonContinuar = "Continuar";
                break;
            case 2:
                listarNiveles();
                volverDesactivado = false;
                botonContinuar = "Continuar";
                grabarDesactivado = true;
                break;    
        }
    }

    private void validarBusqueda() {
        if (matriculaSeleccionada == null) {
            RequestContext.getCurrentInstance().execute("PF('noseleccionado').show();");
            panelActivo = 2;
        }
        else{
            if(matriculaSeleccionada.getEstadoMatricula()==0){
                RequestContext.getCurrentInstance().execute("PF('ya_eliminado').show();");
                panelActivo = 2;
            }
            
        }
    }

    private void registrarMatriculaModificada() {
        dom_dao.modificarObjeto(matriculaSeleccionada.getAlumno().getDomicilio());
        ubi_dao.modificarObjeto(matriculaSeleccionada.getAlumno().getUbicaciondocumentos());
        
        if((padreSeleccionado!=null)){
            System.out.println("HAY PADRE!"+padreSeleccionado.getPadreDeFamilia().getApeMat());
            matriculaSeleccionada.getAlumno().setPadreDeFamilia(padreSeleccionado.getPadreDeFamilia());
        }
        
        System.out.println("nivel: "+aula.getNivel()+"/grado: "+aula.getNumGrad()+"/Seccion: "+aula.getSeccion());
        if((aula.getNivel()!=0)&&(aula.getNumGrad()!=0)&&(aula.getSeccion()!=0)){
            aula=aula_dao.obtenerAula(aula.getNivel(), aula.getNumGrad(), aula.getSeccion());
            if(aula.getCantidadAlumnos()<aula.getLimiteAlumnos()){
                matriculaSeleccionada.getAula().setCantidadAlumnos(matriculaSeleccionada.getAula().getCantidadAlumnos()-1);
                aula_dao.modificarObjeto(matriculaSeleccionada.getAula());
                aula.setCantidadAlumnos(aula.getCantidadAlumnos()+1);
                aula_dao.modificarObjeto(aula);
                matriculaSeleccionada.setAula(aula);
            }
            else{
                RequestContext.getCurrentInstance().execute("PF('aula_llena').show();");
            }
        }
        
        mat_dao.modificarObjeto(matriculaSeleccionada);
        alu_dao.modificarObjeto(matriculaSeleccionada.getAlumno());
        
        
        
        
        
        matriculaSeleccionada=null;
        listaMatriculados=null;
        nombreBuscado = "";
        nombreBuscado2 = "";
        padreSeleccionado=null;
        aula = new Aula();
 //numVoucher= "";
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

    public String getNombreBuscado() {
        return nombreBuscado;
    }

    public void setNombreBuscado(String nombreBuscado) {
        this.nombreBuscado = nombreBuscado;
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

    public int getPanelActivo() {
        return panelActivo;
    }

    public void setPanelActivo(int panelActivo) {
        this.panelActivo = panelActivo;
    }

    public String getNumVoucher() {
        return numVoucher;
    }

    public Alumno getAlu() {
        return alu;
    }

    public void setAlu(Alumno alu) {
        this.alu = alu;
    }

    public void setNumVoucher(String numVoucher) {
        this.numVoucher = numVoucher;
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
    
    
}
