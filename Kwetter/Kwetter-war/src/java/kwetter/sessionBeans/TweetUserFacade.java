/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package kwetter.sessionBeans;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import kwetter.domain.TweetUser;

/**
 *
 * @author Administrator
 */
@Stateless
public class TweetUserFacade extends AbstractFacade<TweetUser> {
    @PersistenceContext(unitName = "Kwetter-warPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public TweetUserFacade() {
        super(TweetUser.class);
    }
    
}
