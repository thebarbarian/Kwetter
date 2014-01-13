/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package kwetter.beans;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.enterprise.context.ApplicationScoped;
import kwetter.dao.KwetterService;
import kwetter.domain.Tweet;
import kwetter.domain.TweetRole;
import kwetter.domain.TweetUser;
import kwetter.sessionBeans.TweetFacade;
import kwetter.sessionBeans.TweetRoleFacade;
import kwetter.sessionBeans.TweetUserFacade;

/**
 *
 * @author David Vollmar <david@vollmar.nl>
 */
@ApplicationScoped
@Startup
@Singleton
public class DummyInjectorBean {
    @EJB
    private TweetUserFacade tweetUserFacade;
    @EJB
    private TweetFacade tweetFacade;
    @EJB
    private TweetRoleFacade tweetRoleFacade;
    @EJB
    private KwetterService kwetterService;

    
    @PreDestroy
    public void PreDestroy(){
        emptyDatabase();        
    }
    
    @PostConstruct
    public void postConstruct() {
        
	TweetUser u0 = new TweetUser("david", "david", "Web", "Bio");
        TweetUser u1 = new TweetUser("Hans","Hans123", "http", "geboren 1");
        TweetUser u2 = new TweetUser("Frank","Fank123", "httpF", "geboren 2");
        TweetUser u3 = new TweetUser("Tom", "Tom123","httpT", "geboren 3");
        TweetUser u4 = new TweetUser("Sjaak","Sjaak123", "httpS", "geboren 4");
        
        TweetRole role = new TweetRole("superdude");
        TweetRole role2 = new TweetRole("loggeduser");
        TweetRole[] roles = new TweetRole[]{role,role2};
        
        u0.setRoleList(Arrays.asList(roles));
        
        
        u1.addFollowing(u2);
        u1.addFollowing(u3);
        u1.addFollowing(u4);

        Tweet t1 = new Tweet("Hallo", new Date(), "PC", u0);        
        Tweet t2 = new Tweet("Hallo again", new Date(), "PC",u0);
        Tweet t3 = new Tweet("Hallo where are you", new Date(), "PC",u0);
        Tweet t4 = new Tweet("Time to rock!", new Date(), "PC",u0);
        Tweet t5 = new Tweet("time to sleep -_-", new Date(), "PC",u0);
        Tweet t6 = new Tweet("How about jij gaat aan het werk of zo", new Date(), "PC",u0);
        Tweet t7 = new Tweet("Niks beters te doen dan ?", new Date(), "PC",u0);
        Tweet t8 = new Tweet("Prinsjesdag maakt koekjes goedkoper!", new Date(), "PC",u0);
        Tweet t9 = new Tweet("imma let you finish.", new Date(), "PC",u0);
        Tweet t10 = new Tweet("OMG NOOOOooooooo", new Date(), "PC",u0);
        
        
        if(kwetterService.findAllTweetUsers().contains(u0)){            
            emptyDatabase();
        }
                
        tweetRoleFacade.create(role);
        tweetRoleFacade.create(role2);
        
        for(TweetUser tu : new TweetUser[]{u0,u1,u2,u3,u4}){
            kwetterService.createUser(tu);
        }
        
        for(Tweet t : new Tweet[]{t1,t2,t3,t4,t5,t6,t7,t8,t8,t9,t10}){
            kwetterService.createTweet(u0, t);
        }  
    }

    private void emptyDatabase() {
        System.err.print("Emptying database...");
        for(TweetRole tr : tweetRoleFacade.findAll()){
            tweetRoleFacade.remove(tr);
        }
        for(Tweet t: tweetFacade.findAll()){
            tweetFacade.remove(t);
        }
        for(TweetUser tu : tweetUserFacade.findAll()){
            tweetUserFacade.remove(tu);
        }
        System.err.println(" done!");
    }
}
