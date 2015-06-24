/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package persistencia;

import entidades.Alumno;
import entidades.Transaccion;
import java.util.List;
import org.hibernate.criterion.Restrictions;
import java.io.Serializable;
/**
 *
 * @author 7
 */
public class AlumnoDAO extends GenericDAO<Alumno> implements Serializable{
    public List<Alumno> obtenerAlumnoPorNombre(String nombreBuscado) {
        List<Alumno> returnList = null;
        try {
            returnList = getHibernateTemplate().createCriteria(Alumno.class)
                    .add(Restrictions.or(
                                    Restrictions.eq("apePat", nombreBuscado),
                                    Restrictions.eq("apeMat", nombreBuscado),
                                    Restrictions.eq("primerNom", nombreBuscado),
                                    Restrictions.eq("segundoNom", nombreBuscado))).list();
            //session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return returnList;
    }
    public Alumno obtenerObjetoPorCodigo(Integer cod) {
        Alumno returnValue = null;
        try {
            returnValue = (Alumno) getHibernateTemplate().createCriteria(Alumno.class)
                    .add(Restrictions.eq("codigoAlumno", cod)).uniqueResult();
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return returnValue;
    }
    
    
    public List<Alumno> obtenerListaAlumnosPorPadre(String padreId) {
        System.out.println("llegue aca al menos");
        List<Alumno> returnList = null;
        try {
            returnList = getHibernateTemplate().createCriteria(Alumno.class, "alumno")
                    .createAlias("alumno.padreDeFamilia", "padreDeFamilia")
                    .add(Restrictions.eq("padreDeFamilia.usuario.idUsuario", padreId)).list();
            //session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return returnList;
    }
    
    //Pedro ABAJO
    
    public Alumno verificarAlumnoPorDNI(String dni) {
        Alumno usuario = null;
        try {
            usuario = (Alumno) getHibernateTemplate().createCriteria(Alumno.class)
                    .add(Restrictions.and(
                            Restrictions.eq("numDoc", dni))).uniqueResult();
            //session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return usuario;
    }
    
    
    public List<Alumno> obtenerAlumnosPorPadre (String nombreBuscado){
        List<Alumno> returnList = null;
        try {
            returnList = getHibernateTemplate()
                    .createCriteria(Alumno.class, "alumno")
                    .createAlias("alumno.padreDeFamilia", "usu")
                    .add(Restrictions.or(
                                    Restrictions.eq("usu.usuarioIdUsuario", nombreBuscado))).list();
            //session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return returnList;
    }
    
    
    public Alumno obtenerAlumnoActivoPorPadre(String nombreBuscado, int estado) {

        Alumno usuario = null;
        try {
            usuario = (Alumno) getHibernateTemplate().createCriteria(Alumno.class)
                    .add(Restrictions.and(
                            Restrictions.eq("padreDeFamilia.usuarioIdUsuario", nombreBuscado),
                            Restrictions.eq("estadoActivo", estado))).uniqueResult();
            //session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return usuario;
    }
}
