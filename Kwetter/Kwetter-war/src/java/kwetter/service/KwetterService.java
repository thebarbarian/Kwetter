package kwetter.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import kwetter.dao.UserDAO;
import kwetter.dao.UserDAOCollectionImpl;
import kwetter.domain.Tweet;
import kwetter.domain.TweetUser;
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
    public void create(TweetUser user) {
        userDAO.create(user);
    }

    /**
     *
     * @param user
     */
    public void edit(TweetUser user) {
        throw new UnsupportedOperationException("Not supported yet."); 
    }
    
    /*
     * Tweet toevoegen van opdracht 3 :
     * 
     */
    public void createNewTweet(TweetUser user, String tekst){         
        Tweet t = new Tweet(tekst,new Date(),"internet" ,user);
        // wat betekent het vanaf-attribuut ?
        TweetUser u = userDAO.find(user.getId());
        u.addTweet(t);
    }
    
    public ArrayList<Tweet> getTimeLine(TweetUser u){
        ArrayList<Tweet> t = new ArrayList<>();
        for(TweetUser k:u.getFollowing()){
            for(Tweet p:k.getTweets()){
                t.add(p);
            }
        }
        for(Tweet y:u.getTweets()){
            t.add(y);
        }       
        orderTweetsByDate(t);
        return t;
    }
    
    public ArrayList<Tweet> getMentions(TweetUser u){
        return searchAllTweets("@"+u.getName());
    }
    
    public HashMap<String, Integer> getTrends(){
        // maak een lijst van alle hashtags in alle tweets :
        HashMap<String, Integer> hashList = new HashMap<>();
        // doorzoek alle tweets op hashtags '#+tekst' 
        // Geef alle tweets terug met een hashtag erin :
        ArrayList<Tweet> t = searchAllTweets("#");
        List<String> strlijst;
        strlijst = new ArrayList<>();
        // Haal de hashtags uit de tweets :
        for(Tweet e:t){           
            String str=e.getTweet();
            Pattern MY_PATTERN = Pattern.compile("#(\\w+|\\W+)");
            Matcher mat = MY_PATTERN.matcher(str);            
            while (mat.find()) {
              //System.out.println(mat.group(1));
              strlijst.add(mat.group(1));
            }}
            for(String w:strlijst){            
            if(hashList.containsKey(w)){
                hashList.put(w, (hashList.get(w)+1));
            }
            else
                hashList.put(w,1);
            }      
            return hashList;
    }                
    
    
    /*
     * Sorteer de tweets op volgorde van timestamp
     * 
     */
    public ArrayList<Tweet> orderTweetsByDate(ArrayList<Tweet> lijstje){  
        
        // als het goed is komt de laatste datum bovenaan.        
        Collections.sort(lijstje, new Comparator<Tweet>() {
        @Override
        public int compare(Tweet o1, Tweet o2) {
        return o2.getDatum().compareTo(o1.getDatum());
        }
        });
        return lijstje;        
    }
    
    /**
     *
     * @param user
     */
    public void remove(TweetUser user) {
        userDAO.remove(user);
    }

    public TweetUser find(Long id){        
        return userDAO.find(id);
    }
    
    /**
     *
     * @return lijst met alle usernames
     */
    public List<TweetUser> findAll() { 
        return userDAO.findAll();
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
        for(TweetUser u:this.findAll()){
            totalNrOfTweets = totalNrOfTweets+u.getTweets().size();
        }        
        return totalNrOfTweets;
    }
    
    /**
     *
     * @param u
     * @return aantal users dat User u zelf volgt
     */
    public int getNrOfFollowing(TweetUser u){
        return u.getFollowing().size();                
    }
    
    public ArrayList<Tweet> getTweetsFromFollowedBy(TweetUser gebruiker){
        ArrayList<TweetUser> u = this.getAllUsersFollowedBy(gebruiker);
        ArrayList<Tweet> t = new ArrayList<Tweet>();
        for(TweetUser k:u){
            for(Tweet p:k.getTweets()){
            t.add(p);
            }
        }  
        return t;
    }
    
    public TweetUser findUser(String username){
        if(username==null){
            throw new NullPointerException("param username cannot be null");
        }
        return userDAO.find(username);
    }
    
    public ArrayList<Tweet> getTweetsFromFollowers(TweetUser jan){
        List<TweetUser> allUsers = this.findAll();
        ArrayList<Tweet> msgs = new ArrayList<>();
        for(TweetUser h:allUsers){
            if(h.getName() == null ? jan.getName() == null : h.getName().equals(jan.getName())){
                for(Tweet y:h.getTweets()){
                    msgs.add(y);
                }                    
            }
        }       
        return msgs;
    }
    
    /*
     * alle tekst van tweets en alle usernames worden doorzocht.
     * Dit is de reden voor bidirectionele relatie tussen tweet en user.
     * 
     * @input : zoekterm search
     * @output : verzameling tweets waar zoekterm in voorkomt of tweets van bepaalde user
     */
    public ArrayList<Tweet> searchAllTweets(String search){
        ArrayList<Tweet> verzamelingTotaal = new ArrayList<>();
        ArrayList<Tweet> resultaat = new ArrayList<>();
        for(TweetUser u:userDAO.findAll()){
            for(Tweet k:u.getTweets()){
                verzamelingTotaal.add(k);
            }
        }
        for (Tweet r:verzamelingTotaal){
            if(r.getTweet().toLowerCase().contains(search.toLowerCase())){
                resultaat.add(r);
            if(r.getUser().getName().toLowerCase().contains(search.toLowerCase())){
                resultaat.add(r);
            }
            }   
        }
        return resultaat;
    }
    
     /**
     *
     * @param u
     * @return aantal users dat User u door gevolg wordt.
     */
    public ArrayList<TweetUser> getAllUsersFollowedBy(TweetUser u){
        ArrayList<TweetUser> c = new ArrayList<TweetUser>();
        for (TweetUser p : this.findAll()) {
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
    public int getFollowedBy(TweetUser u){
        int nrOfFollowers = 0;
        for (TweetUser p : this.findAll()) {
            if (p.getFollowing().contains(u)) nrOfFollowers++;                    
        }
        return nrOfFollowers;
    }
    
    private void initUsers() {
        TweetUser u1 = new TweetUser("Hans","Hans123", "http", "geboren 1");
        TweetUser u2 = new TweetUser("Frank","Fank123", "httpF", "geboren 2");
        TweetUser u3 = new TweetUser("Tom", "Tom123","httpT", "geboren 3");
        TweetUser u4 = new TweetUser("Sjaak","Sjaak123", "httpS", "geboren 4");
        
        u1.setId(0L);
        u2.setId(1L);
        u3.setId(2L);
        u4.setId(3L);
        
        u1.addFollowing(u2);
        u1.addFollowing(u3);
        u1.addFollowing(u4);

        Tweet t1 = new Tweet("Hallo", new Date(), "PC", u1);
        Tweet t2 = new Tweet("Hallo again", new Date(), "PC",u1);
        Tweet t3 = new Tweet("Hallo where are you", new Date(), "PC",u1);
        Tweet t4 = new Tweet("Time to rock!", new Date(), "PC",u2);
        Tweet t5 = new Tweet("time to sleep -_-", new Date(), "PC",u2);
        Tweet t6 = new Tweet("How about jij gaat aan het werk of zo", new Date(), "PC",u2);
        Tweet t7 = new Tweet("Niks beters te doen dan ?", new Date(), "PC",u3);
        Tweet t8 = new Tweet("Prinsjesdag maakt koekjes goedkoper!", new Date(), "PC",u2);
        Tweet t9 = new Tweet("imma let you finish.", new Date(), "PC",u2);
        Tweet t10 = new Tweet("OMG NOOOOooooooo", new Date(), "PC",u3);
        u1.addTweet(t1);
        u1.addTweet(t2);
        u1.addTweet(t3);
        
        u2.addTweet(t4);
        u2.addTweet(t5);
        u2.addTweet(t6);

        u3.addTweet(t7);
        u2.addTweet(t8);
        u2.addTweet(t9);
        
        u3.addTweet(t10);        
        
        userDAO.create(u1);
        userDAO.create(u2);
        userDAO.create(u3);
        userDAO.create(u4);
    }
}