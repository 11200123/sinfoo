package entidades;
// Generated 24/06/2015 11:04:55 AM by Hibernate Tools 3.6.0


import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * Ubicaciondocumentos generated by hbm2java
 */
public class Ubicaciondocumentos  implements Serializable {


     private Integer codDocument;
     private String numFolioCertNotas;
     private String numFolioCertComport;
     private String numFolioPartNac;
     private Set alumnos = new HashSet(0);

    public Ubicaciondocumentos() {
    }

	
    public Ubicaciondocumentos(String numFolioCertNotas, String numFolioCertComport, String numFolioPartNac) {
        this.numFolioCertNotas = numFolioCertNotas;
        this.numFolioCertComport = numFolioCertComport;
        this.numFolioPartNac = numFolioPartNac;
    }
    public Ubicaciondocumentos(String numFolioCertNotas, String numFolioCertComport, String numFolioPartNac, Set alumnos) {
       this.numFolioCertNotas = numFolioCertNotas;
       this.numFolioCertComport = numFolioCertComport;
       this.numFolioPartNac = numFolioPartNac;
       this.alumnos = alumnos;
    }
   
    public Integer getCodDocument() {
        return this.codDocument;
    }
    
    public void setCodDocument(Integer codDocument) {
        this.codDocument = codDocument;
    }
    public String getNumFolioCertNotas() {
        return this.numFolioCertNotas;
    }
    
    public void setNumFolioCertNotas(String numFolioCertNotas) {
        this.numFolioCertNotas = numFolioCertNotas;
    }
    public String getNumFolioCertComport() {
        return this.numFolioCertComport;
    }
    
    public void setNumFolioCertComport(String numFolioCertComport) {
        this.numFolioCertComport = numFolioCertComport;
    }
    public String getNumFolioPartNac() {
        return this.numFolioPartNac;
    }
    
    public void setNumFolioPartNac(String numFolioPartNac) {
        this.numFolioPartNac = numFolioPartNac;
    }
    public Set getAlumnos() {
        return this.alumnos;
    }
    
    public void setAlumnos(Set alumnos) {
        this.alumnos = alumnos;
    }




}


