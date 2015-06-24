/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package controladores.pagos;
import entidades.Alumno;
import entidades.Aula;
import entidades.Cronograma;
import entidades.Detallepago;
import entidades.Domicilio;
import entidades.Estandarespago;
import entidades.Matricula;
import entidades.Transaccion;
import entidades.Ubicaciondocumentos;
import entidades.Usuario;
import java.io.Serializable;
import java.text.SimpleDateFormat;
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
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import org.primefaces.context.RequestContext;
import persistencia.AlumnoDAO;
import persistencia.AulaDAO;
import persistencia.CronogramaDAO;
import persistencia.DetallepagoDAO;
import persistencia.DomicilioDAO;
import persistencia.EstandarespagoDAO;
import persistencia.MatriculaDAO;
import persistencia.TransaccionDAO;
import persistencia.UbicaciondocumentosDAO;
import persistencia.UsuarioDAO;

/**
 *
 * @author TOSHIBA
 */
@ManagedBean(name = "registrarpago")
@SessionScoped
public class Control_Registrar_Pago implements Serializable {
    Date ahora = new Date();
    
    private String usuario;
    private boolean[] mostrado;
    private int panelActivo;
    private String padrebuscado;
    private String ola;
    private boolean volverDesactivado;
    private String botonContinuar;
    private boolean continuarDesactivado;
    List<Alumno> listaAlumnos;
    Alumno alumnoSeleccionado;
    Alumno alumnoDeudas;
    Matricula matriculaActual;
    String concepto;
    private List<Detallepago> listaDetallesPago;
    private List<Detallepago> listaComparar;
    
    private Set<Matricula> listaMatriculas;
    
    private Integer numVoucher;
    private String numMatricula;
    private String mesPago;
    private final MatriculaDAO mat_dao;
    private final AlumnoDAO alu_dao;
    private final DomicilioDAO dom_dao;
    private final TransaccionDAO tran_dao;
    private final DetallepagoDAO pago_dao;
    private final EstandarespagoDAO estandar_dao;
    private final UsuarioDAO usu_dao;
    private final CronogramaDAO crono_dao;
    Cronograma crono;
    Transaccion tran;
    Matricula mat;
    Matricula iteraM;
    String rol;
    String mostrarRol;
    String nombreUser;
    Calendar fecha;
    List<String> listaMeses;
    List<String> listaMesesFechaActual;
    String mes;
    int montoPension;
    Date fechaActual;
    Detallepago pago;
    String retorna;
    public Control_Registrar_Pago() {
        fecha = new GregorianCalendar();
        mat_dao = new MatriculaDAO();
        alu_dao = new AlumnoDAO();
        dom_dao = new DomicilioDAO();
        tran_dao = new TransaccionDAO();
        usu_dao = new UsuarioDAO();
        pago_dao=new DetallepagoDAO();
        crono_dao = new CronogramaDAO();
        estandar_dao = new EstandarespagoDAO();
        crono = new Cronograma();
        tran = null;
        mat=null;
        mes=null;
        montoPension=0;
        concepto=null;
        listaMatriculas = new HashSet<Matricula>();
        listaComparar = new ArrayList<Detallepago>();
        listaMeses = new ArrayList<String>();
        listaMesesFechaActual = new ArrayList<String>();
        listaDetallesPago = new ArrayList<Detallepago>();
        matriculaActual = new Matricula();
        iteraM = new Matricula();
        alumnoDeudas = new Alumno();
        alumnoSeleccionado = new Alumno();
        pago = new Detallepago();
        botonContinuar="continuar";
        volverDesactivado = true;
        continuarDesactivado = false;
        usuario = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("usuario");
        nombreUser = usu_dao.obtenerUsuariosPorID(usuario).getIdUsuario();
        mostrarRol = usu_dao.obtenerUsuariosPorID(usuario).getRol();
        if(mostrarRol.equals("padre_de_familia")){
            rol="Padre de familia";
        }
        else{
            rol=mostrarRol;
        }
        retorna=null;
        String p;
        p = buscarAlumnos();
        
        mostrado = new boolean[4];
        mostrado[0] = true;
        for (int i = 1; i < mostrado.length; i++) {
            mostrado[i] = false;
        }
        fechaActual = new Date();
    }
    
    public String continua() {

        switch (panelActivo) {
            case 0:
                validarBusqueda();
                retorna=null;
                break;
            case 1:
                traerConcepto();
                validarNroTransaccion();
                retorna=null;
                break;
            case 2:
                validarMesPago();
                
                retorna=null;
                break;
            case 3:
                limpiar();
                retorna = "IU_Padredefamilia?usuario=" + usuario + "&faces-redirect=true";
                break;
        }
        if (panelActivo < mostrado.length - 1) {
            panelActivo++;
        } else {
            panelActivo = 0;
        }
        actualizaEstado();
        System.out.println(panelActivo);
        return retorna;
    }
    
    public String retrocede() {
        if (panelActivo > 0) {
            panelActivo--;
        } else {
            panelActivo = 4;
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
                volverDesactivado = false;
                botonContinuar = "Continuar";
                break;
            case 2:
                volverDesactivado = true;
                //RequestContext.getCurrentInstance().execute("PF('encontrado').show();");
                botonContinuar = "Registrar";
                break;
            case 3:
                //RequestContext.getCurrentInstance().execute("PF('confirmaroperacion').show();");
                volverDesactivado = true;
                botonContinuar = "Salir";
                break;
            default:
                botonContinuar = "Continuar";
                volverDesactivado = false;
                continuarDesactivado = false;
                break;
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
    private void validarNroTransaccion() {

        if (numVoucher != null) {
            
            tran = tran_dao.obtenerObjetoPorNroTransaccion(numVoucher);
            if (tran == null) {
                
                RequestContext.getCurrentInstance().execute("PF('noencontrado').show();");
                panelActivo--;
            }else if(tran.getEstadoTransaccion()==1){
                
                RequestContext.getCurrentInstance().execute("PF('usado').show();");
                panelActivo--;
            }else if(!tran.getConceptoTransacion().equals(concepto)){
                
                RequestContext.getCurrentInstance().execute("PF('otroconcepto').show();");
                panelActivo--;
            }
            else{
                manejarDetalles();
            }
        } else {
            RequestContext.getCurrentInstance().execute("PF('nada').show();");
            panelActivo--;
        }
    }
    
    private void validarBusqueda() {
        if (alumnoSeleccionado == null) {
            RequestContext.getCurrentInstance().execute("PF('noseleccionado').show();");
            panelActivo = 3;
        }
    }
    private void traerConcepto() {
        traerMatricula();
        int tip = matriculaActual.getAula().getNivel()+1;
        concepto = estandar_dao.obtenerConceptoPorTipo(tip).getConceptoPago() ;
    }
    private void validarMesPago(){
        System.out.println(mes);
        if(!mes.equals("0")){
            Iterator<Detallepago> it = listaDetallesPago.iterator();
            Detallepago mespago = new Detallepago();
            while(it.hasNext()){
                Detallepago dp = it.next();
                if(dp.getMesPago().equals(mes)){
                    mespago = dp;
                }
            }
            boolean masAntiguo=true;
            Iterator<Detallepago> it2 = listaDetallesPago.iterator();
            while(it2.hasNext()){
                Detallepago dp1 = it2.next();
                if(dp1.getCronograma().getFechaIni().before(mespago.getCronograma().getFechaIni())){
                    masAntiguo = false;
                    break;
                }
            }
            if(!masAntiguo){
                
                RequestContext.getCurrentInstance().execute("PF('mesnoantiguo').show();");
                panelActivo--;
            }else{
                registrarPagoAlumno();
            }
        }else{
            
            RequestContext.getCurrentInstance().execute("PF('noseleccionames').show();");
            panelActivo--;
        }
        
    }
    public void registrarPagoAlumno(){
        Iterator<Detallepago> it = listaDetallesPago.iterator();
        while(it.hasNext()){
            Detallepago dp = it.next();
            if(dp.getMesPago().equals(mes)){
                pago = dp;
            }
        }
        actualizarEstadoTransaccion(); //Guardar BD LUEGO DEJAR DE OCULTAR
        actualizarDetallePago();
        verificarDeudaAño();
       
    }
    private void actualizarEstadoTransaccion(){
        tran.setEstadoTransaccion(1);
        tran_dao.modificarObjeto(tran);
    }
    private void actualizarDetallePago(){
        pago.setEstadoPago(1);
        pago.setTransaccion(tran);
        Detallepago dp=pago;
        pago_dao.modificarObjeto(dp);
    }
    private void verificarDeudaAño(){
        if(mes.equals("diciembre")){
            matriculaActual.setDeudaFinAno(1);
            mat_dao.modificarObjeto(matriculaActual);
        }
    }
    private void verificarNroMatricula() {          //////////////////// ESTA ES LA FUNCION DEBIDAMENTE

    mat = mat_dao.obtenerMatricula(alumnoSeleccionado.getCodigoAlumno()).get(0);
    if (mat == null) {
                //voucherNoEncontrado = true;
        System.out.println("asdasd-->> "+alumnoSeleccionado.getCodigoAlumno());
        RequestContext.getCurrentInstance().execute("PF('CodMatriculaNoEncontrado').show();");
        //panelActivo--;
    }
    }
    private void manejarDetalles(){
        
        traerDetallesPago();
        traerMonto();
        listarMeses();
        listarMesesFechaActual();
    }
    private void traerMatricula(){
        alumnoDeudas = alu_dao.obtenerObjetoPorCodigo(alumnoSeleccionado.getCodigoAlumno());
        //alumnoSeleccionado.getMatriculas();
        listaMatriculas = alumnoDeudas.getMatriculas();
        Iterator<Matricula> it = listaMatriculas.iterator();
        System.out.println(fecha.get(Calendar.YEAR));
        int año = fecha.get(Calendar.YEAR);
        while(it.hasNext()){
            iteraM = it.next();
            int añoMatricula = iteraM.getAnoMatricula();
            if((año) == añoMatricula){
                matriculaActual = mat_dao.obtenerObjeto((int)iteraM.getCodMatricula());  
                System.out.println("Ano: "+matriculaActual.getCodMatricula());
            }
        }
    }
    private void traerDetallesPago(){
        listaComparar = pago_dao.obtenerDetallesPagoPorMatricula(matriculaActual.getCodMatricula());
        Iterator<Detallepago> it = listaComparar.iterator();
        System.out.println(listaComparar.size());
        while(it.hasNext()){
            Detallepago dp = it.next();
            if(dp.getEstadoPago()==0)
            {
                listaDetallesPago.add(dp);
                
            }
            
        }
        System.out.println(listaDetallesPago.size());
        
    }
    private void traerMonto(){
        montoPension = tran.getMontoTransaccion();
    }
    private void listarMeses(){
        Iterator<Detallepago> it = listaDetallesPago.iterator();
        while(it.hasNext()){
            Detallepago dp = it.next();
            listaMeses.add(dp.getMesPago());
        }
    }
    private void listarMesesFechaActual(){
        Iterator<String> it = listaMeses.iterator();
        while(it.hasNext()){
            String mesa = it.next();
            crono = crono_dao.obtenerCronogramaPension(mesa);
            if(crono!=null){
                if(fechaActual.after(crono.getFechaIni())){
                    listaMesesFechaActual.add(mesa);
                }
            }
        }
    }
    public void limpiar(){
        crono = new Cronograma();
        tran = null;
        mat=null;
        mes=null;
        montoPension=0;
        listaMatriculas = new HashSet<Matricula>();
        listaMeses = new ArrayList<String>();
        listaComparar = new ArrayList<Detallepago>();
        listaMesesFechaActual = new ArrayList<String>();
        listaDetallesPago = new ArrayList<Detallepago>();
        matriculaActual = new Matricula();
        iteraM = new Matricula();
        alumnoDeudas = new Alumno();
        alumnoSeleccionado = new Alumno();
        pago = new Detallepago();
        
        botonContinuar="continuar";
        volverDesactivado = true;
        continuarDesactivado = false;
        numVoucher=null;
        

    }
    
    public String irarenovarmatricula(){
        return "IU_RenovarMatricula?usuario=" + usuario + "&faces-redirect=true";
    }
    
    public String volverMenu(){
        
        return "IU_Padredefamilia?usuario=" + usuario + "faces-redirect=true";

        
    }
    
    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
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
       
    public List<Alumno> getListaAlumnos() {
        return listaAlumnos;
    }

    public void setListaAlumnos(List<Alumno> listaAlumnos) {
        this.listaAlumnos = listaAlumnos;
    }
    
    
    public void setAlumnoSeleccionado(Alumno alumnoSeleccionado) {
        this.alumnoSeleccionado = alumnoSeleccionado;
    }
    
    public Alumno getAlumnoSeleccionado() {
        return alumnoSeleccionado;
    }
   public void setTran(Transaccion tran) {
        this.tran = tran;
    }
    
    public Transaccion getTran() {
        return tran;
    }

    public String getPadrebuscado() {
        return padrebuscado;
    }

    public void setPadrebuscado(String padrebuscado) {
        this.padrebuscado = padrebuscado;
    }
      
    public Integer getNumVoucher() {
        return numVoucher;
    }

    public void setNumVoucher(Integer numVoucher) {
        this.numVoucher = numVoucher;
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
    public String salir(){
        HttpSession s = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        s.invalidate();
        return "IU_IngresarSistema?faces-redirect=true";
    }

    public List<String> getListaMeses() {
        return listaMeses;
    }

    public void setListaMeses(List<String> listaMeses) {
        this.listaMeses = listaMeses;
    }

    public List<String> getListaMesesFechaActual() {
        return listaMesesFechaActual;
    }

    public void setListaMesesFechaActual(List<String> listaMesesFechaActual) {
        this.listaMesesFechaActual = listaMesesFechaActual;
    }
    
    public String getMes() {
        return mes;
    }

    public void setMes(String mes) {
        this.mes = mes;
    }

    public int getMontoPension() {
        return montoPension;
    }

    public void setMontoPension(int montoPension) {
        this.montoPension = montoPension;
    }

    public Matricula getMatriculaActual() {
        return matriculaActual;
    }

    public void setMatriculaActual(Matricula matriculaActual) {
        this.matriculaActual = matriculaActual;
    }
    
 }
