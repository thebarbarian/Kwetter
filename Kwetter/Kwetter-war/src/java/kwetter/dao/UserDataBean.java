/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package kwetter.dao;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import kwetter.domain.TweetUser;

/**
 *
 * @author David
 */
@Stateless
public class UserDataBean{
    
    
    @PersistenceContext(name = "Kwetter-warPU")
    private EntityManager em;

    public int count() {
        Query q = em.createQuery("SELECT COUNT(u) from TweetUser u");
        return (int) q.getSingleResult();        
    }

    public void create(TweetUser user) {
        if(user == null){
            throw new NullPointerException("foo");
        }
        if(em == null){
            throw new NullPointerException("em");
        }
        em.persist(user);
    }

    
    public void edit(TweetUser user) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public List<TweetUser> findAll() {
        return em.createQuery("select u from TweetUser u", TweetUser.class).getResultList();
    }

    public TweetUser find(Long id) {
        return em.find(TweetUser.class, id);
    }

    public TweetUser find(String username) {
        Query q = em.createNamedQuery("select u from TweetUser u where u.username :username");
        q.setParameter("username", username);
        List<TweetUser> res = q.getResultList();
        
        if(res.size() == 1){
            return res.get(0);
        }else if(res.size() > 1){
            throw new IllegalStateException("duplicate username");
        }else{
            return null;
        }        
    }

    public void remove(TweetUser user) {
        em.remove(user);
    }
    
}
