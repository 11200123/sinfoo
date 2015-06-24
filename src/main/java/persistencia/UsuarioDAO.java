/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package persistencia;

import java.io.Serializable;
import entidades.Usuario;
import java.util.List;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author 7
 */
public class UsuarioDAO extends GenericDAO<Usuario> implements Serializable{
     public Usuario verificarIdentificacion(String u, String p) {
        Usuario usuario = null;
        try {
            usuario = (Usuario) getHibernateTemplate().createCriteria(Usuario.class)
                    .add(Restrictions.and(
                            Restrictions.eq("idUsuario", u),
                            Restrictions.eq("passUsuario", p))).uniqueResult();
            //session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return usuario;
    }
    
    
    public List<Usuario> obtenerUsuariosPorNombre(String nombreBuscado) {
        List<Usuario> returnList = null;
        try {
            returnList = getHibernateTemplate()
                    .createCriteria(Usuario.class)
                    .add(Restrictions.or(
                                    Restrictions.eq("idUsuario", nombreBuscado),
                                    Restrictions.eq("rol", nombreBuscado),
                                   // Restrictions.eq("email", nombreBuscado),
                                    Restrictions.eq("email", nombreBuscado))).list();
            //session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return returnList;
    }
   
    
    //Pedro abajo
    public List<Usuario> obtenerUsuariosPorRol(String rol) {
        List<Usuario> returnList = null;
        try {
            returnList = getHibernateTemplate()
                    .createCriteria(Usuario.class)
                    .add(Restrictions.or(
                                    Restrictions.eq("idUsuario", rol))).list();
            //session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return returnList;
    }
    
    public Usuario obtenerUsuariosPorID(String user) {
        Usuario returnList = null;
        try {
            returnList = (Usuario)getHibernateTemplate()
                    .createCriteria(Usuario.class)
                    .add(Restrictions.eq("idUsuario",user)).uniqueResult();
            //session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return returnList;
    }
    
     
}
