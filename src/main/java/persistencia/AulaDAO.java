/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package persistencia;

import entidades.Aula;
import java.util.List;
import org.hibernate.criterion.Restrictions;
import java.io.Serializable;
/**
 *
 * @author 7
 */
public class AulaDAO extends GenericDAO<Aula> implements Serializable{
    public Aula obtenerAula(int nivel,int grado,int seccion) {
        Aula returnList = null;
        try {
            returnList = (Aula)getHibernateTemplate().createCriteria(Aula.class)
                    .add(Restrictions.and(
                            Restrictions.eq("seccion",seccion),
                            Restrictions.eq("numGrad",grado),
                            Restrictions.eq("nivel",nivel)
                            )).uniqueResult();
            //session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return returnList;
    }
    
    public boolean existeAula(Aula aula) {
        Aula a = null;
        try {
            a = (Aula) getHibernateTemplate().createCriteria(Aula.class)
                    .add(Restrictions.eq("nivel", aula.getNivel()))
                    .add(Restrictions.eq("numGrad", aula.getNumGrad()))
                    .add(Restrictions.eq("seccion", aula.getSeccion()))
                    .uniqueResult();
            //session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return a != null;
    }
    public List<Aula> Niveles() {
        List<Aula> returnList = null;
        try {
            returnList = getHibernateTemplate()
                    .createCriteria(Aula.class, "aula").list();
            //session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return returnList;
    }
    
    public List<Aula> obtenerGradosPorNivel(int nivel) {
        List<Aula> returnList = null;
        try {
            returnList = getHibernateTemplate()
                    .createCriteria(Aula.class, "aula")
                    .add(Restrictions.eq("aula.nivel",nivel)).list();
            //session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return returnList;
    }
    
    public List<Aula> obtenerSeccionesPorGrado(int nivel, int grado) {
        List<Aula> returnList = null;
        try {
            returnList = getHibernateTemplate()
                    .createCriteria(Aula.class, "aula")
                    .add(Restrictions.eq("aula.nivel",nivel))
                    .add(Restrictions.eq("aula.numGrad",grado)).list();
            //session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return returnList;
    }
}
