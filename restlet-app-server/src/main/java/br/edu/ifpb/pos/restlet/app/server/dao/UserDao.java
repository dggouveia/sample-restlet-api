package br.edu.ifpb.pos.restlet.app.server.dao;

import br.edu.ifpb.pos.restlet.app.server.entities.Person;
import br.edu.ifpb.pos.restlet.app.server.entities.User;
import java.util.List;
import javax.persistence.EntityTransaction;

/**
 *
 * @author douglasgabriel
 * @version 0.1
 */
public class UserDao extends DAO<User>{

    public List<User> findAll (){
        return getEntityManager().createQuery("SELECT u FROM User u").getResultList();
    }
    
    public List<User> find (String person_code, String name, String password){
        StringBuilder query = new StringBuilder("SELECT u FROM User u");
        if (person_code != null && !person_code.trim().isEmpty())
            query.append(" WHERE u.person_code like '").append(person_code).append("'");
        if (name != null && !name.trim().isEmpty())
            query.append(" WHERE u.name like '").append(name).append("'");
        if (password != null && !password.trim().isEmpty())
            query.append(" WHERE u.password like '").append(password).append("'");
        return getEntityManager().createQuery(query.toString()).getResultList();
    }
    
    public boolean update(String userCode, User user) throws Exception{
        EntityTransaction transaction = getEntityManager().getTransaction();
        try {
            transaction.begin();     
            User entity = getEntityManager().find(User.class, userCode);
            if (entity == null){
                throw new Exception("Entity not found");
            }
            entity.setName(user.getName());
            entity.setPassword(user.getPassword());
            getEntityManager().merge(entity);
            transaction.commit();
            return true;
        } catch (Exception e) {
            transaction.rollback();
            throw e;
        }
    }
    
}
