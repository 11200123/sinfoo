/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package controladores.pagos;

import entidades.Alumno;
import entidades.Aula;
import entidades.Detallepago;
import entidades.Domicilio;
import entidades.Matricula;
import entidades.Transaccion;
import entidades.Ubicaciondocumentos;
import entidades.Usuario;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Set;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import org.primefaces.context.RequestContext;
import persistencia.AlumnoDAO;
import persistencia.AulaDAO;
import persistencia.DetallepagoDAO;
import persistencia.DomicilioDAO;
import persistencia.MatriculaDAO;
import persistencia.TransaccionDAO;
import persistencia.UbicaciondocumentosDAO;
import persistencia.UsuarioDAO;
/**
 *
 * @author 7
 */
@ManagedBean(name = "cancelardeudas")

@ViewScoped
public final class Control_Cancelar_Deudas implements Serializable{
    //@ManagedProperty("#{param.usuario}")
    private String usuario;
    private boolean[] mostrado;
    private int panelActivo;
    private String padrebuscado;
    private boolean volverDesactivado;
    private boolean continuarDesactivado;
    private String botonContinuar;
    private String botonRetroceder;
    private Integer numVoucher;
    private String alumno;
    private Alumno alumnoDeudas;
    private Set<Matricula> listaMatriculas;
    private List<Detallepago> listaDetallesPago;
    private List<Detallepago> listaComparar;
    private Detallepago detallepagoSeleccionado;

    private Matricula m;
    private Matricula iteraM;
    private Matricula matriculaAnterior;
    private List<Alumno> listaAlumnos;
    private Alumno alumnoSeleccionado;
    private Aula aula;
    private Aula aulaNueva;
    private String auxUsuario;
    private Calendar fecha;
    
    
    private final MatriculaDAO mat_dao;
    private final AlumnoDAO alu_dao;
    private final TransaccionDAO tran_dao;
    private final DetallepagoDAO pago_dao;
    private final UsuarioDAO usu_dao;

    Transaccion tran;
    String rol;
    String mostrarRol;
    String nombreUser;
    String retorna;
    String retornar;
    private int montoTotal;
    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public Control_Cancelar_Deudas() {
        fecha = new GregorianCalendar();
        
        panelActivo = 0;
        mostrado = new boolean[3];
        mostrado[0] = true;
        botonContinuar = "Cancelar deuda";
        botonRetroceder = "Volver";
        volverDesactivado = false;
        continuarDesactivado = false;
        for (int i = 1; i < mostrado.length; i++) {
            mostrado[i] = false;
        }
        mat_dao = new MatriculaDAO();
        alu_dao = new AlumnoDAO();
        tran_dao = new TransaccionDAO();
        pago_dao = new DetallepagoDAO();
        usu_dao = new UsuarioDAO();
        listaMatriculas = new HashSet<Matricula>();
        iteraM = new Matricula();
        matriculaAnterior = new Matricula();
        listaDetallesPago = new ArrayList<Detallepago>();
        listaComparar = new ArrayList<Detallepago>();
        tran=null;
        retorna=null;
        retornar=null;
        //listaMatriculas = new HashSet<Matricula>(0);
        usuario = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("usuario");
        //auxUsuario = usuario;
        alumno = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("alumno");
        nombreUser = usu_dao.obtenerUsuariosPorID(usuario).getIdUsuario();
        mostrarRol = usu_dao.obtenerUsuariosPorID(usuario).getRol();
        if(mostrarRol.equals("padre_de_familia")){
            rol="Padre de familia";
        }
        else{
            rol=mostrarRol;
        }
        alumnoDeudas = alu_dao.obtenerObjetoPorCodigo(Integer.parseInt(alumno));
        System.out.println(alumnoDeudas.getPrimerNom());
        
        traerMatricula();

        traerDetallesPago();
        
        //rol = Integer.toString(usu_dao.obtenerUsuariosPorUsername(usuario).getRol());
        System.out.println(usuario);
        //System.out.println(padrebuscado);
        
        RequestContext.getCurrentInstance().execute("PF('avisodeudas').show();");
        
    }
    private void traerMatricula(){
        listaMatriculas = alumnoDeudas.getMatriculas();
        Iterator<Matricula> it = listaMatriculas.iterator();
        System.out.println(fecha.get(Calendar.YEAR));
        int año = fecha.get(Calendar.YEAR);
        //año = año+1;//Solo por que la matricula usada es del 2015
        while(it.hasNext()){
            iteraM = it.next();
            int añoMatricula = iteraM.getAnoMatricula();
            if((año-1) == añoMatricula){
                matriculaAnterior = mat_dao.obtenerObjeto((int)iteraM.getCodMatricula());  
                System.out.println("Ano: "+matriculaAnterior.getCodMatricula());
            }
        }
    }
    private void traerDetallesPago(){
        listaComparar = pago_dao.obtenerDetallesPagoPorMatricula(matriculaAnterior.getCodMatricula());
        Iterator<Detallepago> it = listaComparar.iterator();
        System.out.println(listaComparar.size());
        while(it.hasNext()){
            Detallepago dp = it.next();
            if(dp.getEstadoPago()==0)
            {
                listaDetallesPago.add(dp);
                montoTotal+=dp.getEstandarespago().getMontoPago();
            }
            
        }
        System.out.println(listaDetallesPago.size());
        
    }
    private void validarBusqueda() {
        if(detallepagoSeleccionado == null) {
            RequestContext.getCurrentInstance().execute("PF('noseleccionado').show();");
            System.out.println("Detalle NULL");
            panelActivo--;
        }else {
            System.out.println("Este detalle es del mes: "+detallepagoSeleccionado.getMesPago());
        }
    }
    public String continua() {
        switch (panelActivo) {
            case 0:
                validarBusqueda();
                break;
            case 1:
                verificarNroTransaccion();
                break;
            case 2:
                
                analizarCambio();
                limpiar();
                //generarreporte();
                //analizar si cambiar de CU o retornar al inicio
                //pruebita();//Funciona
                break;
        }
        System.out.println(panelActivo);
        if(retorna==null){
            if (panelActivo < mostrado.length-1) {
                panelActivo++;
            } else {
                panelActivo = 0;
            }
            System.out.println(panelActivo);
            actualizaEstado();
            System.out.println(mostrado.length);
        }
        System.out.println(retorna);
        return retorna;
    }
    private void pruebita(){
        retorna = "IU_RenovarMatricula?usuario=" + usuario + "faces-redirect=true";
    }
    public String retrocede() {
        if(panelActivo==0){
            retornar="IU_RenovarMatricula?usuario=" + usuario + "&faces-redirect=true";
        }
        if(retornar==null){
            if (panelActivo > 0) {
            panelActivo--;
            } else {
                panelActivo = 3;
            }
            actualizaEstado();
            retornar=null;
        }
        
        System.out.println(panelActivo);
        return retornar;
    }

    private void actualizaEstado() {
        for (int i = 0; i < mostrado.length; i++) {
            mostrado[i] = panelActivo == i;
        }

        switch (panelActivo) {
            case 0:
                volverDesactivado = false;
                continuarDesactivado = false;
                botonContinuar = "Cancelar Deudas";
                botonRetroceder = "Regresar en otro momento";
                
                break;
            case 1:
                volverDesactivado = false;
                continuarDesactivado = false;
                botonContinuar = "Confirmar";
                botonRetroceder = "Atras";
                break;
            
            case 2:
                botonContinuar = "Continuar";
                botonRetroceder = "Atras";
                volverDesactivado = true;
                continuarDesactivado = false;
                break;
        }
    }
    private void verificarNroTransaccion() {

        if (numVoucher != null) {
            tran = tran_dao.obtenerObjetoPorNroTransaccion(numVoucher);
            //Falta verificar concepto
            if (tran == null) {
                RequestContext.getCurrentInstance().execute("PF('noencontrado').show();");
                panelActivo--;
            }else if(tran.getEstadoTransaccion()==1){
                RequestContext.getCurrentInstance().execute("PF('usado').show();");
                panelActivo--;  
            }else if(!tran.getConceptoTransacion().equals(detallepagoSeleccionado.getEstandarespago().getConceptoPago())){
                RequestContext.getCurrentInstance().execute("PF('otroconcepto').show();");
                panelActivo--;    
            }else{
                
                cancelarDeuda();//Editar para no usar BD
            }
        } else {
            RequestContext.getCurrentInstance().execute("PF('nada').show();");
             panelActivo--;
        }

    }
    
    public String buscarAlumnos(){
        if(!usuario.equals("")){
            listaAlumnos = alu_dao.obtenerListaAlumnosPorPadre(usuario);
        }
        else{
            RequestContext.getCurrentInstance().execute("PF('noingresado').show();");
        }
        return null;
    }
    private void cancelarDeuda() {
        actualizarEstadoTransaccion();
        actualizarDetallePago();
        verificarDeudaAño();
    }
    private void actualizarEstadoTransaccion(){
        tran.setEstadoTransaccion(1);
        tran_dao.modificarObjeto(tran);
    }
    private void actualizarDetallePago(){
        detallepagoSeleccionado.setEstadoPago(1);
        detallepagoSeleccionado.setTransaccion(tran);
        Detallepago dp=detallepagoSeleccionado;
        pago_dao.modificarObjeto(dp);
    }
    private void verificarDeudaAño(){
        if(listaDetallesPago.size()==1){
            matriculaAnterior.setDeudaFinAno(1);
            mat_dao.modificarObjeto(matriculaAnterior);
        }
    }
    
    
    public void limpiar() {
        
        
        botonContinuar = "Cancelar deuda";
        botonRetroceder = "Regresar en otro momento";
        volverDesactivado = false;
        continuarDesactivado = false;
        tran=null;
        numVoucher = null;
        //retorna=null;
        listaMatriculas = new HashSet<Matricula>();
        iteraM = new Matricula();
        matriculaAnterior = new Matricula();
        listaDetallesPago = new ArrayList<Detallepago>();
        listaComparar = new ArrayList<Detallepago>();
        montoTotal=0;
        traerMatricula();

        traerDetallesPago();
        
    }
    public void analizarCambio(){
        if(listaDetallesPago.size()==1){
            retorna="IU_RenovarMatricula?usuario=" + usuario + "faces-redirect=true";
        }else{
            retorna=null;
        }
    }
    public String iraregistrarmatricula(){
        return "IU_RegistrarPago?usuario=" + usuario + "&faces-redirect=true";
    }
    public String iracancelardeudas(){
        return "IU_CancelarDeudas?usuario=" + usuario + "&faces-redirect=true";
    }
    public String irarenovar(){
        return "IU_RenovarMatricula?usuario=" + usuario + "&faces-redirect=true";
    }
    
    public String salir(){
        HttpSession s = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        s.invalidate();
        return "IU_IngresarSistema?faces-redirect=true";
    }
    
    public String volverMenu(){
        
        return "IU_Padredefamilia?usuario=" + usuario + "faces-redirect=true";

        
    }
    
    public boolean[] getMostrado() {
        return mostrado;
    }

    public void setMostrado(boolean[] mostrado) {
        this.mostrado = mostrado;
    }

    public int getPanelActivo() {
        return panelActivo;
    }

    public void setPanelActivo(int panelActivo) {
        this.panelActivo = panelActivo;
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

    public Alumno getAlumnoSeleccionado() {
        return alumnoSeleccionado;
    }

    public void setAlumnoSeleccionado(Alumno alumnoSeleccionado) {
        this.alumnoSeleccionado = alumnoSeleccionado;
    }
    

    public Integer getNumVoucher() {
        return numVoucher;
    }

    public void setNumVoucher(Integer numVoucher) {
        this.numVoucher = numVoucher;
    }

   
    public String getPadrebuscado() {
        return padrebuscado;
    }

    public void setPadrebuscado(String padrebuscado) {
        this.padrebuscado = padrebuscado;
    }
    
    private void finalizarSession() {
        HttpSession s = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        s.invalidate();
    }

    public boolean isContinuarDesactivado() {
        return continuarDesactivado;
    }

    public void setContinuarDesactivado(boolean continuarDesactivado) {
        this.continuarDesactivado = continuarDesactivado;
    }

    public Matricula getM() {
        return m;
    }

    public void setM(Matricula m) {
        this.m = m;
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

    public String getBotonRetroceder() {
        return botonRetroceder;
    }

    public void setBotonRetroceder(String botonRetroceder) {
        this.botonRetroceder = botonRetroceder;
    }

    public List<Detallepago> getListaDetallesPago() {
        return listaDetallesPago;
    }

    public void setListaDetallesPago(List<Detallepago> listaDetallesPago) {
        this.listaDetallesPago = listaDetallesPago;
    }

    public Detallepago getDetallepagoSeleccionado() {
        return detallepagoSeleccionado;
    }

    public void setDetallepagoSeleccionado(Detallepago detallepagoSeleccionado) {
        this.detallepagoSeleccionado = detallepagoSeleccionado;
    }

    public int getMontoTotal() {
        return montoTotal;
    }

    public void setMontoTotal(int montoTotal) {
        this.montoTotal = montoTotal;
    }

    public Alumno getAlumnoDeudas() {
        return alumnoDeudas;
    }

    public void setAlumnoDeudas(Alumno alumnoDeudas) {
        this.alumnoDeudas = alumnoDeudas;
    }

    public String getAlumno() {
        return alumno;
    }

    public void setAlumno(String alumno) {
        this.alumno = alumno;
    }

    public Set<Matricula> getListaMatriculas() {
        return listaMatriculas;
    }

    public void setListaMatriculas(Set<Matricula> listaMatriculas) {
        this.listaMatriculas = listaMatriculas;
    }

    public Matricula getMatriculaAnterior() {
        return matriculaAnterior;
    }

    public void setMatriculaAnterior(Matricula matriculaAnterior) {
        this.matriculaAnterior = matriculaAnterior;
    }

    public Aula getAulaNueva() {
        return aulaNueva;
    }

    public void setAulaNueva(Aula aulaNueva) {
        this.aulaNueva = aulaNueva;
    }

    public Transaccion getTran() {
        return tran;
    }

    public void setTran(Transaccion tran) {
        this.tran = tran;
    }
    
    
    
    
    
}