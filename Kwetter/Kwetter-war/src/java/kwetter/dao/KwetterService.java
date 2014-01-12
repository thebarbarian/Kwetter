package kwetter.dao;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import kwetter.domain.Tweet;
import kwetter.domain.TweetUser;
import kwetter.sessionBeans.TweetFacade;
import kwetter.sessionBeans.TweetUserFacade;

/**
 *
 * @author grave
 * @author  David
 */
@Stateless
public class KwetterService {
    @EJB
    private TweetUserFacade tweetUserFacade;
    @EJB
    private TweetFacade tweetFacade;
    
    
     public TweetUser createUser(TweetUser user) {
       tweetUserFacade.create(user);
       return user;
    }
    
    /**
     *
     * @param user
     */
    public void editUser(TweetUser user) {
        tweetUserFacade.edit(user);
    }

    /*
     * Tweet toevoegen van opdracht 3 :
     * 
     */
    public Tweet createTweet(TweetUser user, String message) {
        Tweet tweet = new Tweet(message, new Date(), "internet", user);
        // vanaf = het device waarvan de tweet gestuurd werd.
        TweetUser u = tweetUserFacade.find(user.getName());
        u.addTweet(tweet);
        tweetFacade.create(tweet);
        return tweet;
    }
    
    public Tweet createTweet(TweetUser user, Tweet tweet){
        if(tweet.getUser() == null){
            tweet.setUser(user);
        }
        if(!user.getTweets().contains(tweet)){
            user.addTweet(tweet);
        }
        tweetFacade.create(tweet);
        return tweet;
    }

    public List<Tweet> getTimeLine(TweetUser u) {
        ArrayList<Tweet> t = new ArrayList<>();
        for (TweetUser k : u.getFollowing()) {
            t.addAll(k.getTweets());
        }
        for (Tweet y : u.getTweets()) {
            t.add(y);
        }
        orderTweetsByDate(t);
        return t;
    }

    public List<Tweet> getMentions(TweetUser u) {
        return searchAllTweets("@" + u.getName());
    }

    
    //TODO DV refactoren
    public HashMap<String, Integer> getTrends() {
        // maak een lijst van alle hashtags in alle tweets :
        HashMap<String, Integer> hashList = new HashMap<>();
        // doorzoek alle tweets op hashtags '#+tekst' 
        // Geef alle tweets terug met een hashtag erin :
        List<Tweet> t = searchAllTweets("#");
        List<String> strlijst;
        strlijst = new ArrayList<>();
        // Haal de hashtags uit de tweets :
        for (Tweet e : t) {
            String str = e.getTweet();
            Pattern MY_PATTERN = Pattern.compile("#(\\w+|\\W+)");
            Matcher mat = MY_PATTERN.matcher(str);
            while (mat.find()) {
                //System.out.println(mat.group(1));
                strlijst.add(mat.group(1));
            }
        }
        for (String w : strlijst) {
            if (hashList.containsKey(w)) {
                hashList.put(w, (hashList.get(w) + 1));
            } else {
                hashList.put(w, 1);
            }
        }
        return hashList;
    }

    /*
     * Sorteer de tweets op volgorde van timestamp
     * 
     */
    public List<Tweet> orderTweetsByDate(ArrayList<Tweet> tweets) {

        // als het goed is komt de laatste datum bovenaan.        
        Collections.sort(tweets, new Comparator<Tweet>() {
            @Override
            public int compare(Tweet o1, Tweet o2) {
                return o2.getDatum().compareTo(o1.getDatum());
            }
        });
        return tweets;
    }

    /**
     *
     * @param user
     */
    public void remove(TweetUser user) {
        tweetUserFacade.remove(user);
    }   

    /**
     *
     * @return lijst met alle usernames
     */
    public List<TweetUser> findAllTweetUsers() {
        return tweetUserFacade.findAll();
    }

    /**
     *
     * @return
     */
    public int tweetUserCount() {
        return tweetUserFacade.count();
    }

    /**
     * Deze nog even checken of dit niet aantal van followers en following moet
     * zijn ipv alles.
     *
     * @return het totale aantal tweets in de site
     */
    public int getAllTweetsCount() {
        int totalNrOfTweets = 0;
        for (TweetUser u : this.findAllTweetUsers()) {
            totalNrOfTweets = totalNrOfTweets + u.getTweets().size();
        }
        return totalNrOfTweets;
    }

    /**
     *
     * @param u
     * @return aantal users dat User u zelf volgt
     */
    public int getNrOfFollowing(TweetUser u) {
        if (u == null || u.getFollowing() == null) {
            return 0;
        } else {
            return u.getFollowing().size();
        }
    }

    //TODO DV is dit wel de gevraagde opdracht?
    public List<Tweet> getTweetsFromFollowedBy(TweetUser gebruiker) {
        ArrayList<TweetUser> u = this.getAllUsersFollowedBy(gebruiker);
        ArrayList<Tweet> t = new ArrayList<>();
        for (TweetUser k : u) {
            t.addAll(k.getTweets());
        }
        return t;
    }

    public TweetUser findUser(String username) {
        return tweetUserFacade.find(username);
    }

    //TODO DV is dit wel de gevraagde opdracht
    public List<Tweet> getTweetsFromFollowers(TweetUser jan) {
        List<TweetUser> allUsers = this.findAllTweetUsers();
        ArrayList<Tweet> msgs = new ArrayList<>();
        for (TweetUser h : allUsers) {
            if (h.getName() == null ? jan.getName() == null : h.getName().equals(jan.getName())) {
                msgs.addAll(h.getTweets());
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
     * 
     * DV TODO refactoren naar een fatsoenlijke jpa q.
     */
    public List<Tweet> searchAllTweets(String search) {
        ArrayList<Tweet> allTweets = new ArrayList<>();
        ArrayList<Tweet> resultaat = new ArrayList<>();
        for (TweetUser u : tweetUserFacade.findAll()) {
            allTweets.addAll(u.getTweets());
        }
        for (Tweet r : allTweets) {
            if (r.getTweet().toLowerCase().contains(search.toLowerCase())) {
                resultaat.add(r);
            }
            if (r.getUser().getName().toLowerCase().contains(search.toLowerCase())) {
                resultaat.add(r);
            }
        }
        return resultaat;
    }

    /**
     *
     * @param u
     * @return aantal users dat User u door gevolg wordt.
     */
    public ArrayList<TweetUser> getAllUsersFollowedBy(TweetUser u) {
        ArrayList<TweetUser> c = new ArrayList<>();
        for (TweetUser p : this.findAllTweetUsers()) {
            if (p.getFollowing().contains(u)) {
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
    public int getFollowedBy(TweetUser u) {
        int nrOfFollowers = 0;
        for (TweetUser p : this.findAllTweetUsers()) {
            if (p.getFollowing().contains(u)) {
                nrOfFollowers++;
            }
        }
        return nrOfFollowers;
    }
}