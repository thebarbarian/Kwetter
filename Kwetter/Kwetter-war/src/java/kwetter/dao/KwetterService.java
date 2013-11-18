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
import javax.ejb.Startup;
import javax.ejb.Stateless;
import javax.inject.Named;
import kwetter.domain.Tweet;
import kwetter.domain.TweetUser;

/**
 *
 * @author grave
 * @author  David
 */
@Named
@Stateless
@Startup
public class KwetterService {

    @EJB
    UserDataBean userDataBean;

    /**
     * initUsers maakt een paar users en dummy tweets aan.
     */
    /*
     public KwetterService() {
     initUsers();
     } */
    /**
     * create user, lijkt logisch
     *
     * @param user
     */
    public TweetUser create(TweetUser user) {
        return userDataBean.create(user);
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
    public void createNewTweet(TweetUser user, String tekst) {
        Tweet t = new Tweet(tekst, new Date(), "internet", user);
        // vanaf = het device waarvan de tweet gestuurd werd.
        TweetUser u = userDataBean.find(user.getId());
        u.addTweet(t);
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
        userDataBean.remove(user);
    }

    public TweetUser find(Long id) {
        return userDataBean.find(id);
    }

    /**
     *
     * @return lijst met alle usernames
     */
    public List<TweetUser> findAll() {
        return userDataBean.findAll();
    }

    /**
     *
     * @return
     */
    public int count() {
        return userDataBean.count();
    }

    /**
     * Deze nog even checken of dit niet aantal van followers en following moet
     * zijn ipv alles.
     *
     * @return het totale aantal tweets in de site
     */
    public int getAllTweetsCount() {
        int totalNrOfTweets = 0;
        for (TweetUser u : this.findAll()) {
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
        if (username == null) {
            throw new NullPointerException("param username cannot be null");
        }
        return userDataBean.find(username);
    }

    //TODO DV is dit wel de gevraagde opdracht
    public List<Tweet> getTweetsFromFollowers(TweetUser jan) {
        List<TweetUser> allUsers = this.findAll();
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
        for (TweetUser u : userDataBean.findAll()) {
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
        for (TweetUser p : this.findAll()) {
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
        for (TweetUser p : this.findAll()) {
            if (p.getFollowing().contains(u)) {
                nrOfFollowers++;
            }
        }
        return nrOfFollowers;
    }

    @Deprecated
    private void initUsers() {
        TweetUser u1 = new TweetUser("Hans", "Hans123", "http", "geboren 1");
        TweetUser u2 = new TweetUser("Frank", "Fank123", "httpF", "geboren 2");
        TweetUser u3 = new TweetUser("Tom", "Tom123", "httpT", "geboren 3");
        TweetUser u4 = new TweetUser("Sjaak", "Sjaak123", "httpS", "geboren 4");

        u1.setId(0L);
        u2.setId(1L);
        u3.setId(2L);
        u4.setId(3L);

        u1.addFollowing(u2);
        u1.addFollowing(u3);
        u1.addFollowing(u4);

        Tweet t1 = new Tweet("Hallo", new Date(), "PC", u1);
        Tweet t2 = new Tweet("Hallo again", new Date(), "PC", u1);
        Tweet t3 = new Tweet("Hallo where are you", new Date(), "PC", u1);
        Tweet t4 = new Tweet("Time to rock!", new Date(), "PC", u2);
        Tweet t5 = new Tweet("time to sleep -_-", new Date(), "PC", u2);
        Tweet t6 = new Tweet("How about jij gaat aan het werk of zo", new Date(), "PC", u2);
        Tweet t7 = new Tweet("Niks beters te doen dan ?", new Date(), "PC", u3);
        Tweet t8 = new Tweet("Prinsjesdag maakt koekjes goedkoper!", new Date(), "PC", u2);
        Tweet t9 = new Tweet("imma let you finish.", new Date(), "PC", u2);
        Tweet t10 = new Tweet("OMG NOOOOooooooo", new Date(), "PC", u3);
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
        userDataBean.create(u1);
        userDataBean.create(u2);
        userDataBean.create(u3);
        userDataBean.create(u4);
    }
}