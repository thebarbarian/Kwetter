/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package kwetter.beans;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import kwetter.domain.Tweet;
import kwetter.domain.User;
import kwetter.service.KwetterService;

/**
 *
 * @author grave
 */
@Named
@RequestScoped
public class KwetterController {

    
    private KwetterService kws;
    
    /**
     * Creates a new instance of KwetterController
     */
    public KwetterController() {
        kws = new KwetterService();
    }
    
    public List<Tweet> getTweetsFromUser(User user){
        List<Tweet> l = new ArrayList<Tweet>();
        for (Tweet t : user.getTweets()) {
            l.add(t);
        }
        return l;
    }        
        
    public String create(){
        return "user aangemaakt";        
    }
    
      public void edit(User user) {
        throw new UnsupportedOperationException("Not supported yet."); 
    }
    
    public User find(Long id){        
	List<User> allUsers = kws.findAll();
	for(User user : allUsers){
	    if(user.getId() == id){
		return user;
	    }
	}
	//TODO return not found oid.
	return null;
    }
    
    public void remove(User user) {
        kws.remove(user);
    }

    public List<User> findAll() {
        return kws.findAll();
    }

    public User find(Object id) {
        throw new UnsupportedOperationException("Not supported yet.");      
    }

    public int count() {
        return kws.count();
    }
    
    
}
