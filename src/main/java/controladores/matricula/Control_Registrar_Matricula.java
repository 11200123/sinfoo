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
import entidades.Transaccion;
import entidades.Ubicaciondocumentos;
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
 * @author TOSHIBA
 */
@ManagedBean(name = "registrarmatricula")
@SessionScoped
public class Control_Registrar_Matricula implements Serializable {
    
    private final AlumnoDAO alu_dao;
    private final MatriculaDAO mat_dao;
    private final UsuarioDAO usu_dao;
    private final DomicilioDAO dom_dao;
    private final AulaDAO aula_dao;
    private final UbicaciondocumentosDAO ubi_dao;
    private final TransaccionDAO tran_dao;
    private final PadreDeFamiliaDAO padre_dao;
    private final EstandarespagoDAO estandares_dao;

    private Alumno alu;
    private Matricula mat;
    private Domicilio dom;
    private Aula aula;
    private Ubicaciondocumentos ubi;
    private Transaccion tran;
    private Estandarespago estandares;
    private Usuario padreSeleccionado;
  
    private List<Aula> listaGrados;
    private List<Aula> listaAulas;
    private List<Aula> listaSecciones;
    private List<Usuario> listaPadres;
    private List<Matricula> listaMatriculas;
    
    private boolean[] mostrado;
    private boolean voucherNoEncontrado;
    private boolean volverDesactivado;
    private boolean continuarDesactivado;
    
    private String botonContinuar;
    private String numVoucher;
    private String nombreBuscado;

    private int panelActivo;
    private int auxiliar;
    Date fechaActual = new Date();
    String usuario; 
    String nombreUser; 
    String rol;
    
    private boolean seguir=true;
    
    public Date getFechaActual() {
        return fechaActual;
    }

    public void setFechaActual(Date fechaActual) {
        this.fechaActual = fechaActual;
    }
    
    public Control_Registrar_Matricula() {
        panelActivo = 0;
        botonContinuar = "Continuar";
        volverDesactivado = true;
        continuarDesactivado = false;

        mostrado = new boolean[6];
        mostrado[0] = true;
        for (int i = 1; i < mostrado.length; i++) {
            mostrado[i] = false;
        }

        voucherNoEncontrado = false;
        usuario = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("usuario");

        alu_dao = new AlumnoDAO();
        mat_dao = new MatriculaDAO();
        usu_dao = new UsuarioDAO();
        dom_dao = new DomicilioDAO();
        aula_dao = new AulaDAO();
        ubi_dao = new UbicaciondocumentosDAO();
        tran_dao = new TransaccionDAO();
        padre_dao = new PadreDeFamiliaDAO();
        estandares_dao = new EstandarespagoDAO();
        deta_dao = new DetallepagoDAO();
        crono_dao = new CronogramaDAO();
        alu = new Alumno();
        mat = new Matricula();
        dom = new Domicilio();
        aula = new Aula();
        ubi = new Ubicaciondocumentos();
        estandares = new Estandarespago();
        detalles = new Detallepago();
        tran = new Transaccion();
        crono = new Cronograma();
        nombreUser = usu_dao.obtenerUsuariosPorID(usuario).getIdUsuario();
        rol = usu_dao.obtenerUsuariosPorID(usuario).getRol();
        
    }
    
    public String getNombreBuscado() {
        return nombreBuscado;
    }

    public void setNombreBuscado(String nombreBuscado) {
        this.nombreBuscado = nombreBuscado;
    }
    
    public List<Aula> getListaGrados() {
        return listaGrados;
    }

    public void setListaGrados(List<Aula> listaGrados) {
        this.listaGrados = listaGrados;
    }

    public List<Aula> getListaSecciones() {
        return listaSecciones;
    }

    public void setListaSecciones(List<Aula> listaSecciones) {
        this.listaSecciones = listaSecciones;
    }

    public List<Aula> getListaAulas() {
        return listaAulas;
    }

    public void setListaAulas(List<Aula> listaAulas) {
        this.listaAulas = listaAulas;
    }
    
    public Transaccion getTran() {
        return tran;
    }

    public void setTran(Transaccion tran) {
        this.tran = tran;
    }
    
    public void countryChangeListener(ValueChangeEvent event){
        FacesContext.getCurrentInstance().renderResponse();
    }
    

    public Alumno getAlu() {
        return alu;
    }

    public void setAlu(Alumno alu) {
        this.alu = alu;
    }

    public Matricula getMat() {
        return mat;
    }

    public void setMat(Matricula mat) {
        this.mat = mat;
    }

    public Domicilio getDom() {
        return dom;
    }

    public void setDom(Domicilio dom) {
        this.dom = dom;
    }

    public Aula getAula() {
        return aula;
    }

    public int getPanelActivo() {
        return panelActivo;
    }

    public void setPanelActivo(int panelActivo) {
        this.panelActivo = panelActivo;
    }

    public boolean isSeguir() {
        return seguir;
    }

    public void setSeguir(boolean seguir) {
        this.seguir = seguir;
    }

    public int getAuxiliar() {
        return auxiliar;
    }

    public void setAuxiliar(int auxiliar) {
        this.auxiliar = auxiliar;
    }

    public void setAula(Aula aula) {
        this.aula = aula;
    }

    public Ubicaciondocumentos getUbi() {
        return ubi;
    }

    public void setUbi(Ubicaciondocumentos ubi) {
        this.ubi = ubi;
    }

    public boolean isVolverDesactivado() {
        return volverDesactivado;
    }

    public void setVolverDesactivado(boolean volverDesactivado) {
        this.volverDesactivado = volverDesactivado;
    }

    public boolean isContinuarDesactivado() {
        return continuarDesactivado;
    }

    public void setContinuarDesactivado(boolean continuarDesactivado) {
        this.continuarDesactivado = continuarDesactivado;
    }

    public String getBotonContinuar() {
        return botonContinuar;
    }

    public void setBotonContinuar(String botonContinuar) {
        this.botonContinuar = botonContinuar;
    }

    public boolean[] getMostrado() {
        return mostrado;
    }

    public void setMostrado(boolean[] mostrado) {
        this.mostrado = mostrado;
    }

    public boolean isVoucherNoEncontrado() {
        return voucherNoEncontrado;
    }

    public void setVoucherNoEncontrado(boolean voucherNoEncontrado) {
        this.voucherNoEncontrado = voucherNoEncontrado;
    }

    public String getNumVoucher() {
        return numVoucher;
    }

    public void setNumVoucher(String numVoucher) {
        this.numVoucher = numVoucher;
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
    
    public String volverMenu(){
        
        return "IU_Secretaria?usuario=" + usuario + "faces-redirect=true";

        
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

    public boolean esNumero(String cadena){
        boolean bandera=true;
        try{
            Integer.parseInt(cadena);
        }catch (Exception e){
            bandera=false;
        }
        return bandera;  
    }
    
    public String buscarPadres() {
        if (!nombreBuscado.equals("")) {
            listaPadres=padre_dao.obtenerPadresPorNombre(nombreBuscado);
        } else {
            RequestContext.getCurrentInstance().execute("PF('noingresado').show();");
        }
        return null;
    }
    

    public Estandarespago getEstandares() {
        return estandares;
    }

    public void setEstandares(Estandarespago estandares) {
        this.estandares = estandares;
    }

    public Detallepago getDetalles() {
        return detalles;
    }

    public void setDetalles(Detallepago detalles) {
        this.detalles = detalles;
    }
    
    private void verificarNroTransaccion() {
        boolean bandera=false;
        if (numVoucher != null) {
            bandera=esNumero(numVoucher);
         if(bandera){
            auxiliar= Integer.parseInt(numVoucher);
            tran = tran_dao.obtenerObjetoPorNroTransaccion(auxiliar);
            if (tran == null) {
                voucherNoEncontrado = true;
                RequestContext.getCurrentInstance().execute("PF('noencontrado').show();");
                panelActivo = 6;
            }
            else {
                if (tran.getEstadoTransaccion()==1) {
                    RequestContext.getCurrentInstance().execute("PF('ya_usado').show();");
                    panelActivo = 6;
                }
                else {
                    estandares = estandares_dao.obtenerConceptoPorTipo(1);
                    if (!tran.getConceptoTransacion().equals(estandares.getConceptoPago())) {
                        RequestContext.getCurrentInstance().execute("PF('no_matricula').show();");
                        panelActivo = 6;
                    }
                    else{
                        if (tran.getMontoTransaccion()!=estandares.getMontoPago()){
                            RequestContext.getCurrentInstance().execute("PF('no_monto').show();");
                            panelActivo = 6;
                        }
                    }
                }
            }
         }
         else{
             RequestContext.getCurrentInstance().execute("PF('no_numero').show();");
             panelActivo = 6;
         }
      }
    }
    
    private void pasarinfopersonal() {
        if (alu.getApePat() == null || alu.getApeMat() == null || alu.getPrimerNom() == null
         || alu.getFechaNac() == null || alu.getNacionalidad() == null
         || alu.getNumDoc() == null || alu.getSexo() == null
         || alu.getTelfFijo() == null || alu.getTelfEmerg() == null) {
             RequestContext.getCurrentInstance().execute("PF('camposvacios').show();");
             seguir=false;
        }
        else{
          if(alu.getFechaNac().before(fechaActual)){
              
              if(esNumero(alu.getNumDoc())){
                if((esNumero(alu.getTelfFijo()))||(esNumero(alu.getTelfEmerg()))){
                    seguir=true;
                }
                else{
                    RequestContext.getCurrentInstance().execute("PF('camposNumericos_telefono').show();");
                    seguir=false;
                    alu.setTelfFijo("");
                    alu.setTelfEmerg("");
                }
            }
            else{
                RequestContext.getCurrentInstance().execute("PF('camposNumericos_dni').show();");
                seguir=false;
                alu.setNumDoc("");
            }
          }
          else{
              RequestContext.getCurrentInstance().execute("PF('fecha_razonable').show();");
              seguir=false;
              alu.setFechaNac(null);
          }
        }
    }

    public String getCadenaDireccion() {
        return cadenaDireccion;
    }

    public void setCadenaDireccion(String cadenaDireccion) {
        this.cadenaDireccion = cadenaDireccion;
    }
    

    String cadenaDireccion="";
    
    
    private void pasardomicilio() {
        if (cadenaDireccion.equals("")) {
             RequestContext.getCurrentInstance().execute("PF('camposvacios').show();");
             System.out.println("no paso :c");
             seguir=false;
             
        }
        else{
            dom.setDireccion(cadenaDireccion);
            System.out.println("si paso xd");
            seguir=true;
        }
    }

    private final DetallepagoDAO deta_dao;
    private Detallepago detalles;
    private final CronogramaDAO crono_dao;
    private Cronograma crono;

    public Cronograma getCrono() {
        return crono;
    }

    public void setCrono(Cronograma crono) {
        this.crono = crono;
    }
    
    public void almacenar(){
        
        if(seguir){
        crono = crono_dao.obtenerCronogramaPorTipo("matricula");
        
        tran.setEstadoTransaccion(1);
        tran_dao.modificarObjeto(tran);
        
        alu.setMotivoRetiro("");
        alu.setEstadoActivo(1);
        alu.setDomicilio(dom);                                          
        alu.setPadreDeFamilia(padreSeleccionado.getPadreDeFamilia());   /////////////////////////
        alu.setUbicaciondocumentos(ubi);                                /////////////////////////
        
        dom_dao.agregarObjeto(dom);
        
        mat.setEstadoMatricula(1);
        mat.setMotivoCancelacion("");
        mat.setAnoMatricula(2015);
        mat.setDeudaFinAno(0);
        
        mat.setFechaMatricula(fechaActual);
        aula.setCantidadAlumnos(aula.getCantidadAlumnos()+1);
        aula_dao.modificarObjeto(aula);
        mat.setAula(aula);
        mat.setAlumno(alu);

        ubi_dao.agregarObjeto(ubi);
        alu_dao.agregarObjeto(alu);
        mat_dao.agregarObjeto(mat);
        
        //Creando tus benditos detalles
        
        listaMatriculas = mat_dao.ListarMatriculas();
        mat=mat_dao.obtenerMatriculaPorCodigo(listaMatriculas.size());
        detalles.setMatricula(mat);
        detalles.setTransaccion(tran);
        detalles.setEstandarespago(estandares);
        detalles.setCronograma(crono);
        detalles.setUsoPago(crono.getTipoCronograma());
        detalles.setEstadoPago(1);
        detalles.setTipoPago(1);
        detalles.setMesPago(crono.getMesPago());
        deta_dao.agregarObjeto(detalles);
        
        //detalles de pensiones
        detalles = new Detallepago();
        estandares = new Estandarespago();
        
        
        if(aula.getNivel()==1){
            estandares = estandares_dao.obtenerConceptoPorTipo(2);
            detalles.setTipoPago(2);
        }
        if(aula.getNivel()==2){
            estandares = estandares_dao.obtenerConceptoPorTipo(3);
            detalles.setTipoPago(3);
        }
        
        
        detalles.setEstadoPago(0);
        detalles.setEstandarespago(estandares);
        detalles.setMatricula(mat);
        detalles.setTransaccion(null);
        
        String meses[]={"febrero","marzo","abril","mayo","junio","julio","agosto","septiembre","octubre","noviembre","diciembre"};
        
        for(int i=0;i<meses.length;i++){
            crearDetalle(meses[i]);
        }
    }
    }
    
    private void crearDetalle(String mes){
        crono = new Cronograma();
        crono = crono_dao.obtenerCronogramaPension(mes);
        if(crono!=null){
            detalles.setMesPago(crono.getMesPago());
            detalles.setUsoPago(crono.getTipoCronograma());
            detalles.setCronograma(crono);
            deta_dao.agregarObjeto(detalles);
        }
    }
    
    
    private void pasarpadre() {
        if(padreSeleccionado==null){
            RequestContext.getCurrentInstance().execute("PF('nopadre').show();");
            seguir=false;
        }
        else{
            if(padreSeleccionado.getPadreDeFamilia().getEstadoActivo()==1){
                seguir=true;
            }
            else{
                RequestContext.getCurrentInstance().execute("PF('padre_inactivo').show();");
                seguir=false;
            }
        }
    }
            
    private void pasarubicacion() {
        if (ubi.getNumFolioCertComport().equals("") || ubi.getNumFolioCertNotas().equals("") || ubi.getNumFolioPartNac().equals("")
           || aula.getNivel() == 0 || aula.getNumGrad() == 0 || aula.getSeccion() == 0) {
             RequestContext.getCurrentInstance().execute("PF('camposvacios').show();");
             seguir=false;
             System.out.println("no paso pe ktmadre");
             
        }
        else{
            System.out.println("xddddddddddddddddddddddd");
            if(aula.getCantidadAlumnos()>=aula.getLimiteAlumnos()){
                RequestContext.getCurrentInstance().execute("PF('aula_llena').show();");
                seguir=false;
                
            }
            else{
                seguir=true;
            }
        }
    }
    
    public String continua() {
        switch (panelActivo) {
            case 0:
                verificarNroTransaccion();
                break;
            case 1:
                pasarinfopersonal();
                break;
            case 2:
                pasardomicilio();
                break;
            case 3:
                pasarpadre();
                break;
            case 4:
                pasarubicacion();
                
                almacenar();
                break;
            case 5:
                limpiar();
                break;
        }
        if (panelActivo < mostrado.length - 1) {
            if(seguir==true){
                panelActivo++;
            }
        } else {
            panelActivo = 0;
        }
        actualizaEstado();
        return null;
    }

    public String retrocede() {
        if (panelActivo > 0) {
            panelActivo--;
        } else {
            panelActivo = 5;
        }
        actualizaEstado();
        seguir=false;
        return null;
    }

    private void actualizarEstadoTransaccion(){
        tran.setEstadoTransaccion(1);
        tran_dao.modificarObjeto(tran);
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
                volverDesactivado = true;
                botonContinuar = "Continuar";
                break;
            case 4:
                listarNiveles();
                actualizarGrados();
                botonContinuar = "Finalizar";
                break;
            case 5:
                volverDesactivado = true;
                botonContinuar = "Realizar otra matrícula";
                break;
            default:
                botonContinuar = "Continuar";
                volverDesactivado = false;
                continuarDesactivado = false;
                break;
        }
    }

    public String salir(){
        HttpSession s = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        s.invalidate();
        return "IU_IngresarSistema?faces-redirect=true";
    }
    
    public void limpiar(){
        voucherNoEncontrado = false;
        
        dom = new Domicilio();
        padreSeleccionado = new Usuario();
        ubi = new Ubicaciondocumentos();
        alu = new Alumno();
        mat = new Matricula();        
        aula = new Aula();
        tran = null;
        seguir=true;
        numVoucher = "";
    }
}
