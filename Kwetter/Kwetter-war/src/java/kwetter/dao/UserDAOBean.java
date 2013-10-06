/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package kwetter.dao;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import kwetter.domain.User;

/**
 *
 * @author David
 */
public class UserDAOBean implements UserDAO{
    
    
    @PersistenceContext
    private EntityManager em;

    @Override
    public int count() {
        Query q = em.createQuery("SELECT COUNT(u) from user u");
        return (int) q.getSingleResult();        
    }

    @Override
    public void create(User user) {
        em.persist(user);
    }

    @Override
    public void edit(User user) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<User> findAll() {
        return em.createQuery("select u from user u", User.class).getResultList();
    }

    @Override
    public User find(Long id) {
        return em.find(User.class, id);
    }

    @Override
    public User find(String username) {
        Query q = em.createNamedQuery("select u from user u where u.username :username");
        List<User> res = q.getResultList();
        
        if(res.size() == 1){
            return res.get(0);
        }else if(res.size() > 1){
            throw new IllegalStateException("duplicate username");
        }else{
            return null;
        }        
    }

    @Override
    public void remove(User user) {
        em.remove(user);
    }
    
}
