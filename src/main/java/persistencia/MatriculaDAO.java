/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package persistencia;

import entidades.Matricula;
import java.util.List;
import org.hibernate.criterion.Restrictions;
import java.io.Serializable;
/**
 *
 * @author 7
 */
public class MatriculaDAO extends GenericDAO<Matricula> implements Serializable{
     public List<Matricula> obtenerMatriculasPorNombre(String nombreBuscado) {
        List<Matricula> returnList = null;
        try {
            returnList = getHibernateTemplate()
                    .createCriteria(Matricula.class, "matricula")
                    .createAlias("matricula.alumno", "alumno")
                    .add(Restrictions.or(
                                    Restrictions.eq("alumno.apePat", nombreBuscado),
                                    Restrictions.eq("alumno.apeMat", nombreBuscado),
                                    Restrictions.eq("alumno.primerNom", nombreBuscado),
                                    Restrictions.eq("alumno.segundoNom", nombreBuscado))).list();
            //session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return returnList;
    }
    public List<Matricula> obtenerMatricula(int id) {
        List<Matricula> returnList = null;
        try {
            returnList = getHibernateTemplate()
                    .createCriteria(Matricula.class, "matricula")
                    .createAlias("matricula.alumno", "alumno")
                    .add(Restrictions.eq("alumno.id",id)).list();
            //session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return returnList;
    }
   
    
    public Matricula obtenerObjetoMatriculaPorIdAlumno(int cod) {
        Matricula returnValue = null;
        try {
        returnValue = (Matricula) getHibernateTemplate()
                    .createCriteria(Matricula.class, "matricula")
                    .createAlias("matricula.alumno", "alumno")
                    .add(Restrictions.eq("alumno.id",cod)).uniqueResult();
        
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return returnValue;
    }
    
    //PEDRO LAS DE ABAJO
    
    public List<Matricula> ListarMatriculas() {
        List<Matricula> returnList = null;
        try {
            returnList = getHibernateTemplate()
                    .createCriteria(Matricula.class, "matricula").list();
            //session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return returnList;
    }
    
    public Matricula obtenerMatriculaPorCodigo(int cod) {
        Matricula returnList = null;
        try {
            returnList = (Matricula)getHibernateTemplate().createCriteria(Matricula.class)
                    .add(Restrictions.and(Restrictions.eq("codMatricula",cod))).uniqueResult();
            //session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return returnList;
    }
    
    public List<Matricula> obtenerMatriculasPorNombreAlumno (String nombreBuscado){
        List<Matricula> returnList = null;
        try {
            returnList = getHibernateTemplate()
                    .createCriteria(Matricula.class, "mat")
                    .createAlias("mat.alumno", "usu")
                    .add(Restrictions.or(
                                    Restrictions.eq("usu.apePat", nombreBuscado),
                                    Restrictions.eq("usu.apeMat", nombreBuscado),
                                    Restrictions.eq("usu.primerNom", nombreBuscado),
                                    Restrictions.eq("usu.segundoNom", nombreBuscado))).list();
            //session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return returnList;
    }
}
