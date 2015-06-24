/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package controladores.matricula;
import entidades.Alumno;
import entidades.Aula;
import entidades.Cronograma;
import entidades.Detallepago;
import entidades.Domicilio;
import entidades.Estandarespago;
import entidades.Matricula;
import entidades.PadreDeFamilia;
import entidades.Transaccion;
import entidades.Ubicaciondocumentos;
import entidades.Usuario;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.servlet.http.HttpSession;
import org.primefaces.context.RequestContext;
import persistencia.AlumnoDAO;
import persistencia.AulaDAO;
import persistencia.CronogramaDAO;
import persistencia.DetallepagoDAO;
import persistencia.DomicilioDAO;
import persistencia.EstandarespagoDAO;
import persistencia.MatriculaDAO;
import persistencia.PadreDeFamiliaDAO;
import persistencia.TransaccionDAO;
import persistencia.UbicaciondocumentosDAO;
import persistencia.UsuarioDAO;
/**
 *
 * @author 7
 */
@ManagedBean(name = "renovarmatricula")

@ViewScoped
public final class Control_Renovar_Matricula implements Serializable{
    //@ManagedProperty("#{param.usuario}")
    private String usuario;
    private boolean[] mostrado;
    private int panelActivo;
    private String padrebuscado;
    private boolean volverDesactivado;
    private boolean continuarDesactivado;
    private String botonContinuar;
    private Integer numVoucher;
    private String alumno;
    Matricula m;
    Matricula ma;
    
    String conceptoPago;
    
    Date fechaActual ;
    
    List<Alumno> listaAlumnos;
    Alumno alumnoSeleccionado;
    Aula aula;
    Aula aulaCambia;
    Aula aulaNueva;
    Aula aulaEstandar;
    String auxUsuario;
    int count;
    PadreDeFamilia padre;
    
    private final MatriculaDAO mat_dao;
    private final AlumnoDAO alu_dao;
    private final TransaccionDAO tran_dao;
    private final AulaDAO aula_dao;
    private final DetallepagoDAO pago_dao;
    private final UsuarioDAO usu_dao;
    private final EstandarespagoDAO estandares_dao;
    private final CronogramaDAO cronograma_dao;
    private final PadreDeFamiliaDAO padre_dao;
    Estandarespago estandarM;
    Estandarespago estandarP;
    
    Calendar fecha;
    String dia;
    String hora;
 
    Set<Detallepago> pagos; 
    Detallepago pagoMatricula;
    Detallepago pagoPension; 
    Cronograma cronogramaM;
    Cronograma cronogramaP;
    Detallepago pag;
    Transaccion tran;
    String rol;
    String mostrarRol;
    String nombreUser;
    List<Matricula> matriculasAnteriores;
    String retorna;

    public Control_Renovar_Matricula() {
        retorna=null;
        count=0;
        fechaActual=new Date();
        panelActivo = 0;
        mostrado = new boolean[4];
        mostrado[0] = true;
        botonContinuar = "Continuar";
        volverDesactivado = true;
        continuarDesactivado = false;
        for (int i = 1; i < mostrado.length; i++) {
            mostrado[i] = false;
        }
        mat_dao = new MatriculaDAO();
        alu_dao = new AlumnoDAO();
        tran_dao = new TransaccionDAO();
        aula_dao = new AulaDAO();
        pago_dao = new DetallepagoDAO();
        usu_dao = new UsuarioDAO();
        estandares_dao = new EstandarespagoDAO();
        cronograma_dao = new CronogramaDAO();
        padre_dao = new PadreDeFamiliaDAO();
        
        estandarM = new Estandarespago();
        estandarP = new Estandarespago();
        pagos = new HashSet<Detallepago>();
        
        tran=null;
        
        usuario = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("usuario");
        auxUsuario = usuario;
        nombreUser = usu_dao.obtenerUsuariosPorID(usuario).getIdUsuario();
        mostrarRol = usu_dao.obtenerUsuariosPorID(usuario).getRol();
        if(mostrarRol.equals("padre_de_familia")){
            rol="Padre de familia";
        }
        else{
            rol=mostrarRol;
        }
        padre = padre_dao.obtenerPadreporCodigo(nombreUser);
        
        String p;
        p = buscarAlumnos();
        m=new Matricula();
        ma=new Matricula();
        pag = new Detallepago();
        pagoMatricula = new Detallepago();
        pagoPension = new Detallepago();
        cronogramaM = new Cronograma();
        cronogramaP = new Cronograma();
        fecha = new GregorianCalendar();
        aulaEstandar = new Aula();

        
    }
    
    public String continua() {
        
        switch (panelActivo) {
            case 0:
                validarBusqueda();
                validarEstandar();
                identificarDeudas(); 
                break;
            case 1:
                verificarNroTransaccion();
                break;
            case 2:
                comprobarCantidad();
                //aulaNueva=aulaCambia; cuando se borre renovarmatricula();
                mostrarhora();
                mostrardia();
                break;
            case 3:
                limpiar();
                volver();
                break;
        }
        
        if(retorna==null){
            System.out.println(panelActivo);
            if (panelActivo < mostrado.length-1) {
                panelActivo++;
            } else {
                panelActivo = 0;
            }
            System.out.println(panelActivo);
            actualizaEstado();
            System.out.println(mostrado.length);
            retorna=null;
        }
        return retorna;
        
    }

    public String retrocede() {
        if (panelActivo > 0) {
            panelActivo--;
        } else {
            panelActivo = 4;
        }
        actualizaEstado();
        System.out.println(panelActivo);
        return null;
    }

    private void actualizaEstado() {
        for (int i = 0; i < mostrado.length; i++) {
            mostrado[i] = panelActivo == i;
        }

        switch (panelActivo) {
            case 0:
                volverDesactivado = true;
                continuarDesactivado = false;
                botonContinuar = "Continuar";
                break;
            case 1:
                botonContinuar = "Continuar";
                volverDesactivado = false;
                continuarDesactivado = false;
                break;
            case 2:
                volverDesactivado = false;
                continuarDesactivado = false;
                botonContinuar = "Finalizar Matricula";
                break;
             case 3:
                volverDesactivado = true;
                continuarDesactivado = false;
                botonContinuar = "Salir";
                break;   
             
            
        }
    }

    private void validarBusqueda() {
        if (alumnoSeleccionado == null) {
            RequestContext.getCurrentInstance().execute("PF('noseleccionado').show();");
            panelActivo = 3;
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
    
    private void verificarNroTransaccion() {

        if (numVoucher != null) {
            tran = tran_dao.obtenerObjetoPorNroTransaccion(numVoucher);
            if (tran == null) {
                
                RequestContext.getCurrentInstance().execute("PF('noencontrado').show();");
                panelActivo--;
            }else if(tran.getEstadoTransaccion()==1){
                
                RequestContext.getCurrentInstance().execute("PF('usado').show();");
                panelActivo--;
                
            } else if(!tran.getConceptoTransacion().equals(conceptoPago)) {
                
                RequestContext.getCurrentInstance().execute("PF('otroconcepto').show();");
                panelActivo--;
               
            }else{
                renovarAulas();
            }
        } else {
             
             RequestContext.getCurrentInstance().execute("PF('nada').show();");
             panelActivo--;
        }

    }
    private void comprobarCantidad(){
       if(aulaCambia.getCantidadAlumnos() == aulaCambia.getLimiteAlumnos()){
           //voucherNoEncontrado = true;
                RequestContext.getCurrentInstance().execute("PF('seccionllena').show();");
                panelActivo--;
       }else{
           renovarMatricula(); //ACTUALIZA BD LUEGO DESOCULTAR
       } 
    }
    
    private void renovarAulas(){
        ma=buscarMatricula(alumnoSeleccionado);
        System.out.println(ma.getAula().getNivel()+"-"+ma.getAula().getNumGrad()+"-"+ma.getAula().getSeccion());
        Aula aulaAnt=ma.getAula();
        System.out.println(aulaAnt.getNivel()+"-"+aulaAnt.getNumGrad()+"-"+aulaAnt.getSeccion());
        aulaNueva=crearAula(aulaAnt);
        aulaCambia=aulaNueva;
    }
    private void renovarMatricula() {
        
        //USAR DAOS DE ESTOS
        
        aulaNueva=aulaCambia;
        aulaNueva=aula_dao.obtenerAula(aulaNueva.getNivel(), aulaNueva.getNumGrad(), aulaNueva.getSeccion());
        aulaNueva.setCantidadAlumnos(aulaNueva.getCantidadAlumnos()+1);
        aula_dao.modificarObjeto(aulaNueva);
        m.setAula(aulaNueva);
        m.setAlumno(alumnoSeleccionado);
        m.setEstadoMatricula(1);
        m.setAnoMatricula(fecha.get(Calendar.YEAR));//CAMBIAR
        m.setMotivoCancelacion("");
        //m.setCodMatricula(2);//CAMBIAR
        m.setDeudaFinAno(0);
        m.setFechaMatricula(fechaActual);
        
        mat_dao.agregarObjeto(m);
        
        
        //Transaccion Actualizar
        actualizarEstadoTransaccion();
        
        
        //DETALLEPAGO - PAGO DE MATRICULA
        cronogramaM=cronograma_dao.obtenerCronogramaPorTipo("matricula");
        estandarM = estandares_dao.obtenerConceptoPorTipo(1);
        //pagoMatricula.setCodigoPago(1);//CAMBIAR
        pagoMatricula.setEstadoPago(1);
        pagoMatricula.setMatricula(m);
        pagoMatricula.setMesPago("marzo"); //Usando la fecha actual
        pagoMatricula.setUsoPago(cronogramaM.getTipoCronograma());
        pagoMatricula.setTipoPago(estandarM.getTipoPago());//Pago matricula
        pagoMatricula.setTransaccion(tran);
        pagoMatricula.setCronograma(cronogramaM); //EN BASE A MESPAGO Y USO PAGO
        pagoMatricula.setEstandarespago(estandarM);//EN BASE A TIPO PAGO
        
        pago_dao.agregarObjeto(pagoMatricula);
        //AGREGAR DETALLE A BD <---- AQUI DAO´S

        //DETALLEPAGO - PAGOS DE PENSION
        
        int tipoPagoPension=0;
        if(aulaNueva.getNivel()==1){
            tipoPagoPension=2;
        }else if(aulaNueva.getNivel()==2){
            tipoPagoPension=3;
        }else{
            //Algun error o algo
        }
        //cronogramaP = cronograma_dao.obtenerCronogramaMesPension("marzo");//VARIABLE
        
        String meses[]={"marzo","abril","mayo","junio","julio","agosto","septiembre","octubre","noviembre","diciembre"};
        for(int i=0;i<meses.length;i++){
            
            crearDetallePagoPension(meses[i],tipoPagoPension);
        }
        
        
    }
    private void crearDetallePagoPension(String mes,int tipo){
        cronogramaP = cronograma_dao.obtenerCronogramaPension(mes);//VARIABLE
        System.out.println(cronogramaP.getMesPago());
        estandarP = estandares_dao.obtenerConceptoPorTipo(tipo);//VARIABLE
        //pagoPension.setCodigoPago(codigo);//CAMBIAR, ADEMAS VARIA A CADA INGRESO
        pagoPension.setEstadoPago(0);
        pagoPension.setMatricula(m);
        pagoPension.setMesPago(cronogramaP.getMesPago()); //MEJOR USARLO EN NUMERO DE MES
        pagoPension.setUsoPago(cronogramaP.getTipoCronograma());
        pagoPension.setTipoPago(estandarP.getTipoPago());//Pago primaria
        pagoPension.setTransaccion(null);
        
        pagoPension.setCronograma(cronogramaP); //EN BASE A MESPAGO Y USO PAGO
        pagoPension.setEstandarespago(estandarP); //EN BASE A TIPO PAGO
        
        pago_dao.agregarObjeto(pagoPension); //BD DAO
        pagoPension = new Detallepago();
        cronogramaP = new Cronograma();
    }
    private Matricula buscarMatricula(Alumno alu){
        Matricula eme = new Matricula();
        matriculasAnteriores=mat_dao.obtenerMatricula(alu.getCodigoAlumno());
        int nMatriculas=matriculasAnteriores.size();
        int año = fecha.get(Calendar.YEAR);
        for(int i=0;i<nMatriculas;i++){
            System.out.println("entrare?");
            if(matriculasAnteriores.get(i).getAnoMatricula()==(año-1)){//CAMBIAR
                eme=matriculasAnteriores.get(i);
            }
        }
        
        return eme;
    }
    private Aula actualizarAula(Aula a){
        if((a.getNivel() == 1) &&(a.getNumGrad()<6)){
            a.setNumGrad(a.getNumGrad()+1);
        }else if(a.getNivel() == 2 && a.getNumGrad()<5){
            a.setNumGrad(a.getNumGrad()+1);
        }else if(a.getNivel() == 1 && a.getNumGrad()==6){
            a.setNivel(2);
            a.setNumGrad(1);
            
        }else{
            //
        }
        return a;

    }
    private Aula crearAula(Aula aulaAnt){
        aula = actualizarAula(aulaAnt);
        aulaNueva=aula_dao.obtenerAula(aula.getNivel(),aula.getNumGrad(),aula.getSeccion());
        return aulaNueva;
    }
    public void actualizarCantidades(){
        aulaCambia=aula_dao.obtenerAula(aulaCambia.getNivel(), aulaCambia.getNumGrad(), aulaCambia.getSeccion());    
    }
    
    public void limpiar() {
        tran=null;
        System.out.println("Limpie");
        m=new Matricula();
        pag = new Detallepago();
        alumnoSeleccionado=null;
        usuario=auxUsuario;
        aula = null;
        aulaNueva = null;
        numVoucher = null;
        aulaCambia= null;

        
    }
    private void actualizarEstadoTransaccion(){
        tran.setEstadoTransaccion(1);
        tran_dao.modificarObjeto(tran);
    }
    private void validarEstandar(){
        Estandarespago ep = estandares_dao.obtenerConceptoPorTipo(1);
        conceptoPago = ep.getConceptoPago();
        System.out.println(conceptoPago);
        
        
    }
    private void identificarDeudas(){
        ma=buscarMatricula(alumnoSeleccionado);
        int tienedeudas=ma.getDeudaFinAno();
        if(tienedeudas == 0){
            alumno=Integer.toString(alumnoSeleccionado.getCodigoAlumno());
            retorna="IU_CancelarDeudas?usuario="+usuario+"&alumno="+alumno+"&faces-redirect=true";
        }else{
            retorna=null;
            //CONTINUAR EN EL MISMO 
        }
        //cambioCU=true;
    }
    
    public String iraregistrarmatricula(){
        return "IU_RegistrarPago?usuario=" + usuario + "&faces-redirect=true";
    }
    public void volver(){
        retorna = "IU_Padredefamilia?usuario=" + usuario + "&faces-redirect=true";
    }
    public String salir(){
        HttpSession s = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        s.invalidate();
        return "IU_IngresarSistema?faces-redirect=true";
    }
    public String volverMenu(){
        
        return "IU_Padredefamilia?usuario=" + usuario + "faces-redirect=true";

        
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
    
    public Matricula getM() {
        return m;
    }

    public void setM(Matricula m) {
        this.m = m;
    }
    
    public Aula getAulaNueva() {
        return aulaNueva;
    }

    public void setAulaNueva(Aula aulaNueva) {
        this.aulaNueva = aulaNueva;
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

    public String getAlumno() {
        return alumno;
    }

    public void setAlumno(String alumno) {
        this.alumno = alumno;
    }

    public Aula getAulaCambia() {
        return aulaCambia;
    }

    public void setAulaCambia(Aula aulaCambia) {
        this.aulaCambia = aulaCambia;
    }
    public void countryChangeListener(ValueChangeEvent event){
        FacesContext.getCurrentInstance().renderResponse();
    }
    public void increment() {
	count++;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public PadreDeFamilia getPadre() {
        return padre;
    }

    public void setPadre(PadreDeFamilia padre) {
        this.padre = padre;
    }
    public String getDia() {
        return dia;
    }

    public void setDia(String dia) {
        this.dia = dia;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }
    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }
    
    public void mostrarhora(){
        hora=fecha.get(Calendar.HOUR_OF_DAY)+":"+fecha.get(Calendar.MINUTE)+":"+fecha.get(Calendar.SECOND);
        System.out.println(hora);
    }
    public void mostrardia(){
        dia=fecha.get(Calendar.YEAR)+"/"+fecha.get(Calendar.MONTH)+"/"+fecha.get(Calendar.DAY_OF_MONTH);
        System.out.println(dia);
    }
}
