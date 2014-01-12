/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package kwetter.sessionBeans;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import kwetter.domain.TweetRole;

/**
 *
 * @author Administrator
 */
@Stateless
public class TweetRoleFacade extends AbstractFacade<TweetRole> {
    @PersistenceContext(unitName = "Kwetter-warPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public TweetRoleFacade() {
        super(TweetRole.class);
    }
    
}
