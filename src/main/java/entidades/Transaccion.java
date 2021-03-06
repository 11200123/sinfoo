package entidades;
// Generated 24/06/2015 11:04:55 AM by Hibernate Tools 3.6.0


import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Transaccion generated by hbm2java
 */
public class Transaccion  implements Serializable {


     private Integer nroTransaccion;
     private Date fechaTransaccion;
     private int montoTransaccion;
     private int estadoTransaccion;
     private String conceptoTransacion;
     private Set detallepagos = new HashSet(0);

    public Transaccion() {
    }

	
    public Transaccion(Date fechaTransaccion, int montoTransaccion, int estadoTransaccion, String conceptoTransacion) {
        this.fechaTransaccion = fechaTransaccion;
        this.montoTransaccion = montoTransaccion;
        this.estadoTransaccion = estadoTransaccion;
        this.conceptoTransacion = conceptoTransacion;
    }
    public Transaccion(Date fechaTransaccion, int montoTransaccion, int estadoTransaccion, String conceptoTransacion, Set detallepagos) {
       this.fechaTransaccion = fechaTransaccion;
       this.montoTransaccion = montoTransaccion;
       this.estadoTransaccion = estadoTransaccion;
       this.conceptoTransacion = conceptoTransacion;
       this.detallepagos = detallepagos;
    }
   
    public Integer getNroTransaccion() {
        return this.nroTransaccion;
    }
    
    public void setNroTransaccion(Integer nroTransaccion) {
        this.nroTransaccion = nroTransaccion;
    }
    public Date getFechaTransaccion() {
        return this.fechaTransaccion;
    }
    
    public void setFechaTransaccion(Date fechaTransaccion) {
        this.fechaTransaccion = fechaTransaccion;
    }
    public int getMontoTransaccion() {
        return this.montoTransaccion;
    }
    
    public void setMontoTransaccion(int montoTransaccion) {
        this.montoTransaccion = montoTransaccion;
    }
    public int getEstadoTransaccion() {
        return this.estadoTransaccion;
    }
    
    public void setEstadoTransaccion(int estadoTransaccion) {
        this.estadoTransaccion = estadoTransaccion;
    }
    public String getConceptoTransacion() {
        return this.conceptoTransacion;
    }
    
    public void setConceptoTransacion(String conceptoTransacion) {
        this.conceptoTransacion = conceptoTransacion;
    }
    public Set getDetallepagos() {
        return this.detallepagos;
    }
    
    public void setDetallepagos(Set detallepagos) {
        this.detallepagos = detallepagos;
    }




}


