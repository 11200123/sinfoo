/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package persistencia;

import entidades.Transaccion;
import org.hibernate.criterion.Restrictions;
import java.io.Serializable;
/**
 *
 * @author 7
 */
public class TransaccionDAO extends GenericDAO<Transaccion> implements Serializable{
    public Transaccion obtenerObjetoPorNroTransaccion(Integer cod) {
        Transaccion returnValue = null;
        try {
            returnValue = (Transaccion) getHibernateTemplate().createCriteria(Transaccion.class)
                    .add(Restrictions.eq("nroTransaccion", cod)).uniqueResult();
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return returnValue;
    }
    
    //Pedro abajo
    
    public Transaccion verificarIdentificacion(int u) {
        Transaccion usuario = null;
        try {
            usuario = (Transaccion) getHibernateTemplate().createCriteria(Transaccion.class)
                    .add(Restrictions.and(
                            Restrictions.eq("nroTransaccion", u))).uniqueResult();
            //session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return usuario;
    }
}
