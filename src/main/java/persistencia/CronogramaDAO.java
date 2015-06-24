/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package persistencia;

import entidades.Cronograma;
import org.hibernate.criterion.Restrictions;
import java.io.Serializable;
/**
 *
 * @author 7
 */
public class CronogramaDAO extends GenericDAO<Cronograma> implements Serializable{
    public Cronograma obtenerCronogramaPorTipo(String tipo) {
        Cronograma returnValue = null;
        try {
            returnValue = (Cronograma) getHibernateTemplate().createCriteria(Cronograma.class)
                    .add(Restrictions.eq("tipoCronograma", tipo)).uniqueResult();
            session.getTransaction().commit();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return returnValue;
    }
    
    
    
    //Pedro abajo
    
    public Cronograma obtenerCronogramaPension(String mes) {
        Cronograma returnList = null;
        String tipo = "pension";
        try {
            returnList = (Cronograma)getHibernateTemplate().createCriteria(Cronograma.class)
                    .add(Restrictions.and(
                            Restrictions.eq("tipoCronograma",tipo),
                            Restrictions.eq("mesPago",mes)
                            )).uniqueResult();
            //session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return returnList;
    }
}
