/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package persistencia;

import entidades.Estandarespago;
import org.hibernate.criterion.Restrictions;
import java.io.Serializable;
/**
 *
 * @author 7
 */
public class EstandarespagoDAO extends GenericDAO<Estandarespago> implements Serializable{
    
    public Estandarespago obtenerConceptoPorTipo(int tipo) {
        Estandarespago returnValue = null;
        try {
           
            returnValue = (Estandarespago) getHibernateTemplate().createCriteria(Estandarespago.class)
                    .add(Restrictions.eq("tipoPago", tipo)).uniqueResult();
            session.getTransaction().commit();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return returnValue;
    }
    public Estandarespago obtenerEstandarPorTipo(int tipo) {
        Estandarespago returnValue = null;
        try {
           
            returnValue = (Estandarespago) getHibernateTemplate().createCriteria(Estandarespago.class)
                    .add(Restrictions.eq("tipoPago", tipo)).uniqueResult();
            session.getTransaction().commit();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return returnValue;
    }
    public Estandarespago obtenerEstandarPorConcepto(String concepto) {
        Estandarespago returnValue = null;
        try {
           
            returnValue = (Estandarespago) getHibernateTemplate().createCriteria(Estandarespago.class)
                    .add(Restrictions.eq("conceptoPago", concepto)).uniqueResult();
            session.getTransaction().commit();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return returnValue;
    }
}