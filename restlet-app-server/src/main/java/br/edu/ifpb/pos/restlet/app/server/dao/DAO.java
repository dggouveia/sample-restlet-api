package br.edu.ifpb.pos.restlet.app.server.dao;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

/**
 *
 * @author douglasgabriel
 * @version 0.1
 */
public class DAO<T> {

    private EntityManager entityManager;

    public DAO() {
        this.entityManager = Persistence.createEntityManagerFactory("DBAPI").createEntityManager();
    }

    public boolean save(T obj) throws Exception{
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            entityManager.persist(obj);
            transaction.commit();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            transaction.rollback();
            throw e;
        }
    }
    
    public boolean update(T obj) throws Exception{
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();            
            entityManager.merge(obj);
            transaction.commit();
            return true;
        } catch (Exception e) {
            transaction.rollback();
            throw e;
        }
    }
    
    public boolean delete(T obj) throws Exception{
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            entityManager.remove(obj);
            transaction.commit();
            return true;
        } catch (Exception e) {
            transaction.rollback();
            throw e;
        }
    }
    
    public T find (Object key, Class<T> entity){
        return entityManager.find(entity, key);
    }

    public EntityManager getEntityManager() {
        return entityManager;
    }
    
}
