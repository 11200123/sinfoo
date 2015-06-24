package entidades;
// Generated 24/06/2015 11:04:55 AM by Hibernate Tools 3.6.0


import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * Aula generated by hbm2java
 */
public class Aula  implements Serializable {


     private Integer codAula;
     private int seccion;
     private int numGrad;
     private int nivel;
     private int cantidadAlumnos;
     private int limiteAlumnos;
     private Set matriculas = new HashSet(0);

    public Aula() {
    }

	
    public Aula(int seccion, int numGrad, int nivel, int cantidadAlumnos, int limiteAlumnos) {
        this.seccion = seccion;
        this.numGrad = numGrad;
        this.nivel = nivel;
        this.cantidadAlumnos = cantidadAlumnos;
        this.limiteAlumnos = limiteAlumnos;
    }
    public Aula(int seccion, int numGrad, int nivel, int cantidadAlumnos, int limiteAlumnos, Set matriculas) {
       this.seccion = seccion;
       this.numGrad = numGrad;
       this.nivel = nivel;
       this.cantidadAlumnos = cantidadAlumnos;
       this.limiteAlumnos = limiteAlumnos;
       this.matriculas = matriculas;
    }
   
    public Integer getCodAula() {
        return this.codAula;
    }
    
    public void setCodAula(Integer codAula) {
        this.codAula = codAula;
    }
    public int getSeccion() {
        return this.seccion;
    }
    
    public void setSeccion(int seccion) {
        this.seccion = seccion;
    }
    public int getNumGrad() {
        return this.numGrad;
    }
    
    public void setNumGrad(int numGrad) {
        this.numGrad = numGrad;
    }
    public int getNivel() {
        return this.nivel;
    }
    
    public void setNivel(int nivel) {
        this.nivel = nivel;
    }
    public int getCantidadAlumnos() {
        return this.cantidadAlumnos;
    }
    
    public void setCantidadAlumnos(int cantidadAlumnos) {
        this.cantidadAlumnos = cantidadAlumnos;
    }
    public int getLimiteAlumnos() {
        return this.limiteAlumnos;
    }
    
    public void setLimiteAlumnos(int limiteAlumnos) {
        this.limiteAlumnos = limiteAlumnos;
    }
    public Set getMatriculas() {
        return this.matriculas;
    }
    
    public void setMatriculas(Set matriculas) {
        this.matriculas = matriculas;
    }




}


