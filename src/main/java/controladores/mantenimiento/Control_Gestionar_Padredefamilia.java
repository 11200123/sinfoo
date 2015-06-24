/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package controladores.mantenimiento;

import entidades.Alumno;
import entidades.Aula;
import entidades.Domicilio;
import entidades.Matricula;
//import entidades.Pago;
import entidades.Transaccion;
import entidades.Ubicaciondocumentos;
import entidades.Usuario;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import org.primefaces.context.RequestContext;
import persistencia.AlumnoDAO;
import persistencia.AulaDAO;
import persistencia.DomicilioDAO;
import persistencia.MatriculaDAO;
//import persistencia.PagoDAO;
import persistencia.TransaccionDAO;
import persistencia.UbicaciondocumentosDAO;
import persistencia.UsuarioDAO;

/**
 *
 * @author Christian
 */
@ManagedBean(name = "gestionarpadre")
@SessionScoped
public class Control_Gestionar_Padredefamilia implements Serializable {

    private boolean[] mostrado;
    private int panelActivo;

    private boolean voucherNoEncontrado;

    private boolean volverDesactivado;
    private boolean continuarDesactivado;
    private String botonContinuar;
    private String matriculaBuscada;
    private String numVoucher;

    private final AlumnoDAO alu_dao;
    private final MatriculaDAO mat_dao;
    private final UsuarioDAO usu_dao;
    private final DomicilioDAO dom_dao;
    private final AulaDAO aula_dao;
    private final UbicaciondocumentosDAO ubi_dao;
    private final TransaccionDAO tran_dao;
//    private final PagoDAO pago_dao;

    private Alumno alu;
    private Matricula mat;
    private Usuario usu;
    private Domicilio dom;
    private Aula aula;
    private Ubicaciondocumentos ubi;
    private Transaccion tran;
    //private Pago pag;
    private Aula aulaBD;
    
    String usuario; 
    String auxUsuario; 
    String nombreUser; 
    String rol; 

    public Control_Gestionar_Padredefamilia() {
        /*UsuarioDAO dao = new UsuarioDAO();
         Usuario u = dao.obtenerObjeto(222254646);
         System.out.println(u.getUsername());*/

        panelActivo = 0;
        botonContinuar = "Continuar";
        volverDesactivado = true;
        continuarDesactivado = false;
        matriculaBuscada = "";

        mostrado = new boolean[6];
        mostrado[0] = true;
        for (int i = 1; i < mostrado.length; i++) {
            mostrado[i] = false;
        }

        voucherNoEncontrado = false;
        usuario = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("usuario");
        auxUsuario = usuario;
        
        alu_dao = new AlumnoDAO();
        mat_dao = new MatriculaDAO();
        usu_dao = new UsuarioDAO();
        dom_dao = new DomicilioDAO();
        aula_dao = new AulaDAO();
        ubi_dao = new UbicaciondocumentosDAO();
        tran_dao = new TransaccionDAO();
        //pago_dao = new PagoDAO();

        alu = new Alumno();
        mat = new Matricula();
        usu = new Usuario();
        dom = new Domicilio();
        aula = new Aula();
        ubi = new Ubicaciondocumentos();
        ///pag= new Pago();
        tran = null;
        
        nombreUser = usu_dao.obtenerUsuariosPorID(usuario).getIdUsuario();
        rol = usu_dao.obtenerUsuariosPorID(usuario).getRol();
        
    }
    

    public String volverMenu(){
        return "IU_Secretaria?usuario=" + usuario + "faces-redirect=true";
    }
    
    public String getMatriculaBuscada() {
        return matriculaBuscada;
    }

    public void setMatriculaBuscada(String matriculaBuscada) {
        this.matriculaBuscada = matriculaBuscada;
    }

    public Usuario getUsu() {
        return usu;
    }

    public void setUsu(Usuario usu) {
        this.usu = usu;
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
    
    

    private void mostrarFormPadredeFamilia() {

    }

    private void mostrarFormDocumentos() {

/*        usu.setArea("");
        usu.setDiscriminator("padre_de_familia");
        usu.setRol("");

        Random r = new Random(System.currentTimeMillis());
        usu.setUsername(usu.getApePat() + usu.getApeMat() + String.valueOf(r.nextInt(100)));
        usu.setPass(String.valueOf(r.nextInt(10000)));
        */
    }

    private void imprime() {
        if (usu.getRol() == null) {
            System.out.println("NULO");
        } else {
            System.out.println(usu.getRol());
        }

    }

    public List<String> completeText(String query) {
        List<String> results = new ArrayList();
        for (int i = 0; i < 10; i++) {
            results.add(query + i);
        }

        return results;
    }

    private void registrarAlumno() {
        /*
        aulaBD=aula_dao.obtenerAula(aula.getSeccion(),aula.getNumGrad(),aula.getNivel());

        ubi.setAlumno(alu);

        mat.setAlumno(alu);
        mat.setFechaMatricula(new Date());
        mat.setEstadoMatricula("matriculado");
        mat.setCantidadPagoMes(300);
        mat.setAula(aulaBD);

        
        
        pag.setConceptoPago("Matricula");
        pag.setMatricula(mat);
        pag.setFechaPago(new Date());
        pag.setFechaTransaccion(tran.getFechaTransaccion());
        pag.setNroTransaccion(tran.getNroTransaccion());

        usu_dao.agregarObjeto(usu);
        dom_dao.agregarObjeto(dom);
        alu_dao.agregarObjeto(alu);
 
        mat_dao.agregarObjeto(mat);
        ubi_dao.agregarObjeto(ubi);
        pago_dao.agregarObjeto(pag);
        
        */

    }

   

   

    public void crearUsername() {
        //
    }

    public void crearPassword() {
        //
    }
    

    public Aula getAulaBD() {
        return aulaBD;
    }

    public void setAulaBD(Aula aulaBD) {
        this.aulaBD = aulaBD;
    }
    public String salir(){
        HttpSession s = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        s.invalidate();
        return "IU_IngresarSistema?faces-redirect=true";
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
    
    public void limpiar(){
        
        matriculaBuscada = "";
        voucherNoEncontrado = false;
        
        alu = new Alumno();
        mat = new Matricula();
        usu = new Usuario();
        dom = new Domicilio();
        aula = new Aula();
        ubi = new Ubicaciondocumentos();
///        pag= new Pago();
        tran = null;
        
        numVoucher = "";

    }
    public String registrarPadre(){
        return "IU_Registrar_PadredeFamilia?usuario=" + usuario + "&faces-redirect=true";
    }
    public String modificarPadre(){
        return "IU_Modificar_PadredeFamilia?usuario=" + usuario + "&faces-redirect=true";
    }
    public String eliminarPadre(){
        return "IU_Eliminar_PadredeFamilia?usuario=" + usuario + "&faces-redirect=true";
    }
    
    
    
    

}
