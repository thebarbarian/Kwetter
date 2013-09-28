/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package kwetter.beans;

import java.util.ArrayList;
import java.util.Collection;
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
    
    /**
     *
     * @param user
     * @return lijst van alle tweets van User user
     */
    public List<Tweet> getTweetsFromUser(User user){
        List<Tweet> l = new ArrayList<Tweet>();
        for (Tweet t : user.getTweets()) {
            l.add(t);
        }
        return l;
    }        
        
    /**
     * aanmaken gebruiker
     * @return succes msg
     */
    public String create(User u){
        kws.create(u);
        return "user aangemaakt";        
    }
    
      /**
     *
     * @param user
     */
    public void edit(User user) {
        throw new UnsupportedOperationException("Not supported yet."); 
    }
    
    /*
     * Haal alle tweets op van volgers
     */
    public ArrayList<Tweet> getTweetsFromFollowers(User u){
        return kws.getTweetsFromFollowers(u);
    }
    
    /*
     * Haal alle tweets op van mensen die de user zelf volgt.
     */
    public ArrayList<Tweet> getTweetsFromFollowedBy(User u){
        return kws.getTweetsFromFollowedBy(u);
    }
    
    
    /**
     *
     * @param id
     * @return
     */
    public User find(Long id){
        User wantedUser=null;
        Iterator it = kws.findAll().iterator();
        while(it.hasNext())
        {
            wantedUser = (User) it.next();
            if (wantedUser.getId(wantedUser) == id){
                return wantedUser;
            }                     
        }
        return wantedUser; // kan zin dat ie nog niet geinitialiseerd is. boeie maar ff op letten bij nullpointers.
    }
    
    /**
     *
     * @param user
     */
    public void remove(User user) {
        kws.remove(user);
    }

     public Collection<User> getAllUsersFollowedBy(User u){
         return kws.getAllUsersFollowedBy(u);
     }
    
    /**
     *
     * @return
     */
    public List<User> findAll() {
        return kws.findAll();
    }

    /**
     * Deze lag dwars, ff gecomment
     * @param id
     * @return
     *
    public User find(Object id) {
        throw new UnsupportedOperationException("Not supported yet.");      
    }*/

    /**
     *
     * @return
     */
    public int count() {
        return kws.count();
    }
    
    /**
     *
     * @param u
     * @return
     */
    public int getNrOfFollowing(User u)
    {
        return kws.getNrOfFollowing(u);
    }
    
    /**
     *
     * @param u
     * @return
     */
    public int getNrOfFollowedBy(User u){
        return kws.getFollowedBy(u);
    }
    
    /**
     *
     * @return
     */
    public int getAllTweetsCount(){
        return kws.getAllTweetsCount();
    }
}
