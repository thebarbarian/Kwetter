/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package kwetter.beans;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import kwetter.domain.Tweet;
import kwetter.domain.TweetUser;
import kwetter.service.KwetterService;

/**
 *
 * @author grave
 */
@Named
@RequestScoped
public class KwetterController {
    
    private KwetterService kws;    
    private TweetUser selectedUser;
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
    public ArrayList<Tweet> getTweetsFromUser(TweetUser user){
        ArrayList<Tweet> l = new ArrayList<>();
        for (Tweet t : user.getTweets()) {
            l.add(t);
        }
        return l;
    }        
    
    public ArrayList<Tweet> getTimeLine(TweetUser u){
        if(u!=null){
            return kws.getTimeLine(u);
        }
        else throw new NullPointerException("User is null in getTimeLine");       
    }
    
     public ArrayList<Tweet> getMentions(TweetUser u){
         if(u!=null){
             return kws.getMentions(u);
         }
         else throw new NullPointerException("geen user ingevoerd bij GetMentions()");
     }
    
    
    public HashMap<String, Integer> getTrends(){
        if(kws.getTrends()!=null){
            return kws.getTrends();
        }
        else throw new NullPointerException("Geen trends aanwezig");        
    }
    
    public TweetUser login(String username, String password){
        TweetUser u = kws.findUser(username);
        if(u==null) throw new NullPointerException("user does not exist.");
        if(u.isPasswordCorrect(password)){
            return u;
        }
        else throw new NullPointerException("password fault.");
    }
    
    
    /**
     * aanmaken gebruiker
     * @return succes msg
     */
    public String create(TweetUser u){
        kws.create(u);
        return "user aangemaakt";        
    }
    
      /**
     *
     * @param user
     */
    public void edit(TweetUser user) {
        throw new UnsupportedOperationException("Not supported yet."); 
    }
    
    /*
     * Haal alle tweets op van volgers
     */
    public ArrayList<Tweet> getTweetsFromFollowers(TweetUser u){
        return kws.getTweetsFromFollowers(u);
    }
    
    /*
     * Haal alle tweets op van mensen die de user zelf volgt.
     */
    public ArrayList<Tweet> getTweetsFromFollowedBy(TweetUser u){
        return kws.getTweetsFromFollowedBy(u);
    }
    
    
    /**
     *
     * @param id
     * @return
     */
    public TweetUser find(Long id){        
        return kws.find(id);
    }
    
    /**
     *
     * @param user
     */
    public void remove(TweetUser user) {
        kws.remove(user);
    }

     public ArrayList<TweetUser> getAllUsersFollowedBy(TweetUser u){
         return kws.getAllUsersFollowedBy(u);
     }
    
    /**
     *
     * @return
     */
    public List<TweetUser> findAll() {
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
    public int getNrOfFollowing(TweetUser u)
    {
        return kws.getNrOfFollowing(u);
    }
    
    /**
     *
     * @param u
     * @return
     */
    public int getNrOfFollowedBy(TweetUser u){
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
    public TweetUser getSelectedUser() {
        return selectedUser;
    }

    /**
     * @param selectedUser the selectedUser to set
     */
    public void setSelectedUser(TweetUser selectedUser) {
        this.selectedUser = selectedUser;
    }
}
