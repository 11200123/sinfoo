package persistencia;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Example;

public abstract class GenericDAO<T> implements Serializable {

    public Class<T> domainClass = getDomainClass();
    protected Session session;

    protected Class getDomainClass() {
        if (domainClass == null) {
            ParameterizedType thisType = (ParameterizedType) getClass()
                    .getGenericSuperclass();
            domainClass = (Class) thisType.getActualTypeArguments()[0];
        }
        return domainClass;
    }

    @SuppressWarnings("unchecked")
    public T obtenerObjeto(int id) {
        T returnValue = null;
        try {
            returnValue = (T) getHibernateTemplate().load(domainClass, id);
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return returnValue;
    }

    public boolean modificarObjeto(T t) {
        try {
            getHibernateTemplate().update(session.merge(t));
            session.getTransaction().commit();
            return true;
        } catch (HibernateException e) {
            handleException(e);
            return false;
        }
    }

    public boolean agregarObjeto(T t) {
        try {
            getHibernateTemplate().save(t);
            session.getTransaction().commit();
            return true;
        } catch (HibernateException e) {
            session.getTransaction().rollback();
            return false;
        }
    }

    public boolean eliminarObjeto(T t) {
        try {
            getHibernateTemplate().delete(session.merge(t));
            session.getTransaction().commit();
            return true;
        } catch (HibernateException e) {
            return false;
        }
    }

    @SuppressWarnings("unchecked")
    public List<T> obtenerListaObjetos() {
        List<T> returnList = null;
        try {
            returnList = getHibernateTemplate().createQuery(
                    "from " + domainClass.getName()+ " x").list();
            //session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return returnList;
    }

    public void eliminarObjetoPorId(int id) {
        Object obj = this.obtenerObjeto(id);
        getHibernateTemplate().delete(obj);
    }

    public int eliminarTodo(boolean isSure) {
        int countDeleted = getHibernateTemplate().createQuery(
                "delete " + domainClass.getName()).executeUpdate();
        if (isSure) {
            session.getTransaction().commit();
        } else {
            session.getTransaction().rollback();
        }
        return countDeleted;
    }

    public Long tamañoLista() {
        Long count = (Long) getHibernateTemplate().createQuery(
                "select count(*) from " + domainClass.getName() + " x")
                .uniqueResult();
        session.getTransaction().commit();
        return count.longValue();
    }

    public List<T> findByExample(T exampleObject) {
        Example example = Example.create(exampleObject).excludeZeroes()
                .enableLike().ignoreCase();
        List<T> list = getHibernateTemplate().createCriteria(domainClass).add(
                example).list();
        session.getTransaction().commit();
        return list;
    }

    protected Session getHibernateTemplate() {
        session = HibernateUtil.getSessionFactory()
                .openSession();
        session.beginTransaction();
        return session;
    }

    private void handleException(HibernateException he) throws HibernateException {
        session.getTransaction().rollback();
        he.printStackTrace();
    }
}