/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package kwetter.beans;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;
import kwetter.dao.KwetterService;
import kwetter.domain.TweetUser;

/**
 *
 * @author David Vollmar <david@vollmar.nl>
 */
@Named
@ApplicationScoped
public class DummyInjectorBean {

    @EJB
    private KwetterService kws;

    @PostConstruct
    public void afterCreate() {
	kws.create(new TweetUser("Foo", "Password", "Web", "Bio"));
    }
}
