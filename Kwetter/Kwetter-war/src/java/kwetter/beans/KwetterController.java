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
    private User selectedUser;
    /**
     * Creates a new instance of KwetterController
     */
    public KwetterController() {
        kws = new KwetterService();
        selectedUser=kws.find(1L); // zet een default user voor als de pagina voor het eerst gelaaien wordt.       
    }
    
    /**
     *
     * @param user
     * @return lijst van alle tweets van User user
     */
    public ArrayList<Tweet> getTweetsFromUser(User user){
        ArrayList<Tweet> l = new ArrayList<Tweet>();
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
        return kws.find(id);
    }
    
    /**
     *
     * @param user
     */
    public void remove(User user) {
        kws.remove(user);
    }

     public ArrayList<User> getAllUsersFollowedBy(User u){
         return kws.getAllUsersFollowedBy(u);
     }
    
    /**
     *
     * @return
     */
    public ArrayList<User> findAll() {
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

    /**
     * @return the selectedUser
     */
    public User getSelectedUser() {
        return selectedUser;
    }

    /**
     * @param selectedUser the selectedUser to set
     */
    public void setSelectedUser(User selectedUser) {
        this.selectedUser = selectedUser;
    }
}
