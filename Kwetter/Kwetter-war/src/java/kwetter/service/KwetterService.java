package kwetter.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import kwetter.dao.UserDAO;
import kwetter.dao.UserDAOCollectionImpl;
import kwetter.domain.Tweet;
import kwetter.domain.User;
//import javax.ejb.Stateless;

//@Stateless
/**
 *
 * @author grave
 */
public class KwetterService {

    private UserDAO userDAO = new UserDAOCollectionImpl();

    /**
     * initUsers maakt een paar users en dummy tweets aan.
     */
    public KwetterService() {
        initUsers();
    }   
    
    /**
     * create user, lijkt logisch
     * @param user
     */
    public void create(User user) {
        userDAO.create(user);
    }

    /**
     *
     * @param user
     */
    public void edit(User user) {
        throw new UnsupportedOperationException("Not supported yet."); 
    }

    /**
     *
     * @param user
     */
    public void remove(User user) {
        userDAO.remove(user);
    }

    /**
     *
     * @return lijst met alle usernames
     */
    public List<User> findAll() {
        return userDAO.findAll();
    }

    /**
     *
     * @param id
     * @return
     */
    public User find(Object id) {
        throw new UnsupportedOperationException("Not supported yet.");      
    }

    /**
     *
     * @return
     */
    public int count() {
        return userDAO.count();
    }
    
    /**
     * Deze nog even checken of dit niet aantal van followers en following moet zijn ipv alles.
     * @return het totale aantal tweets in de site
     */
    public int getAllTweetsCount(){
        int totalNrOfTweets=0;
        for(User u:this.findAll()){
            totalNrOfTweets = totalNrOfTweets+u.getTweets().size();
        }        
        return totalNrOfTweets;
    }
    
    /**
     *
     * @param u
     * @return aantal users dat User u zelf volgt
     */
    public int getNrOfFollowing(User u){
        return u.getFollowing().size();                
    }
    
    public ArrayList<Tweet> getTweetsFromFollowedBy(User gebruiker){
        ArrayList<User> u = this.getAllUsersFollowedBy(gebruiker);
        ArrayList<Tweet> t = new ArrayList<Tweet>();
        for(User k:u){
            for(Tweet p:k.getTweets()){
            t.add(p);
            }
        }  
        return t;
    }
    
    public ArrayList<Tweet> getTweetsFromFollowers(User jan){
        List<User> allUsers = this.findAll();
        ArrayList<Tweet> msgs = new ArrayList<Tweet>();
        for(User h:allUsers){
            if(h.getName() == null ? jan.getName() == null : h.getName().equals(jan.getName())){
                for(Tweet y:h.getTweets()){
                    msgs.add(y);
                }                    
            }
        }       
        return msgs;
    }
    
     /**
     *
     * @param u
     * @return aantal users dat User u door gevolg wordt.
     */
    public ArrayList<User> getAllUsersFollowedBy(User u){
        ArrayList<User> c = new ArrayList<User>();
        for (User p : this.findAll()) {
            if (p.getFollowing().contains(u)){
                c.add(p);
            }
        }
        return c;
    }
    
    
    /**
     *
     * @param u
     * @return aantal users dat User u door gevolg wordt.
     */
    public int getFollowedBy(User u){
        int nrOfFollowers = 0;
        for (User p : this.findAll()) {
            if (p.getFollowing().contains(u)) nrOfFollowers++;                    
        }
        return nrOfFollowers;
    }
    
    private void initUsers() {
        User u1 = new User("Hans", "http", "geboren 1");
        User u2 = new User("Frank", "httpF", "geboren 2");
        User u3 = new User("Tom", "httpT", "geboren 3");
        User u4 = new User("Sjaak", "httpS", "geboren 4");
        u1.addFollowing(u2);
        u1.addFollowing(u3);
        u1.addFollowing(u4);

        Tweet t1 = new Tweet("Hallo", new Date(), "PC");
        Tweet t2 = new Tweet("Hallo again", new Date(), "PC");
        Tweet t3 = new Tweet("Hallo where are you", new Date(), "PC");
        u1.addTweet(t1);
        u1.addTweet(t2);
        u1.addTweet(t3);

        userDAO.create(u1);
        userDAO.create(u2);
        userDAO.create(u3);
        userDAO.create(u4);
    }
}