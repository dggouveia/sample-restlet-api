package br.edu.ifpb.pos.restlet.app.server.dao;

import br.edu.ifpb.pos.restlet.app.server.entities.Person;
import java.util.List;
import javax.persistence.EntityTransaction;

/**
 *
 * @author douglasgabriel
 * @version 0.1
 */
public class PersonDao extends DAO<Person>{    
    
    public List<Person> findAll (){
        return getEntityManager().createQuery("SELECT p FROM Person p").getResultList();
    }
    
    public List<Person> find (String code, String name, String address){
        StringBuilder query = new StringBuilder("SELECT p FROM Person p");
        if (code != null && !code.trim().isEmpty())
            query.append(" WHERE p.code like '").append(code).append("'");
        if (name != null && !name.trim().isEmpty())
            query.append(" WHERE p.name like '").append(name).append("'");
        if (address != null && !address.trim().isEmpty())
            query.append(" WHERE p.address like '").append(address).append("'");
        return getEntityManager().createQuery(query.toString()).getResultList();
    }
        
    public boolean update(String personCode, Person obj) throws Exception{
        EntityTransaction transaction = getEntityManager().getTransaction();
        try {
            transaction.begin();     
            Person entity = getEntityManager().find(Person.class, personCode);
            entity.setName(obj.getName());
            entity.setAddress(obj.getAddress());
            getEntityManager().merge(entity);
            transaction.commit();
            return true;
        } catch (Exception e) {
            transaction.rollback();
            throw e;
        }
    }
    
}
