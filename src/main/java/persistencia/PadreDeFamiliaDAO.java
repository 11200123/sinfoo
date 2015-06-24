/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package persistencia;

import entidades.PadreDeFamilia;
import entidades.Usuario;
import java.util.List;
import org.hibernate.criterion.Restrictions;
import java.io.Serializable;
/**
 *
 * @author 7
 */
public class PadreDeFamiliaDAO extends GenericDAO<PadreDeFamilia> implements Serializable{
    public PadreDeFamilia obtenerPadreporCodigo(String cod) {
        PadreDeFamilia returnList = null;
        try {
            returnList = (PadreDeFamilia) getHibernateTemplate()
                    .createCriteria(PadreDeFamilia.class, "padre_de_familia")
                    .createAlias("padre_de_familia.usuario", "usuario")
                    .add(Restrictions.eq("usuario.idUsuario", cod)).uniqueResult();
            //session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return returnList;
    }
    public List<Usuario> obtenerPadresPorNombre(String nombreBuscado) {
        List<Usuario> returnList = null;
        try {
            returnList = getHibernateTemplate()
                    .createCriteria(Usuario.class, "usuario")
                    .createAlias("usuario.padreDeFamilia", "usu")
                    .add(Restrictions.or(
                                    Restrictions.eq("usu.apePat", nombreBuscado),
                                    Restrictions.eq("usu.apeMat", nombreBuscado),
                                    Restrictions.eq("usu.nombre1", nombreBuscado),
                                    Restrictions.eq("usu.nombre2", nombreBuscado))).list();
            //session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return returnList;
    }
    //Pedro abajo
    
    public Usuario obtenerPadreporDocumento(String cod) {
        Usuario returnList = null;
        try {
            returnList = (Usuario) getHibernateTemplate()
                    .createCriteria(Usuario.class, "usu")
                    .createAlias("usu.padreDeFamilia", "usuario")
                    .add(Restrictions.eq("usuario.numDoc", cod)).uniqueResult();
            //session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return returnList;
    }
}
