/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package kwetter.beans;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import kwetter.dao.Probeersel;

/**
 *
 * @author grave
 */
@Stateless
public class ProbeerselFacade extends AbstractFacade<Probeersel> {
    @PersistenceContext(unitName = "Kwetter-warPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ProbeerselFacade() {
        super(Probeersel.class);
    }
    
}
