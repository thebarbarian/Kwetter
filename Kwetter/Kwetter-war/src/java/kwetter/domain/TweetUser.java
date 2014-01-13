package kwetter.domain;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

/* Omdat het makkelijk is : 
 * 
 * insert into tweetuser values ('Hans','Bio van Hansmans','6428a8fbbb0e120528bb604a66787102280e1b5e26db76633ce37c8a4526e445','Web van HansWorst');
 insert into tweetuser values ('Klaas','Bio van Klaas vaak','6c399ee6a9c60eb1b2b269d5a6e0c166eee2a9cca6da4dc1c38fa274ec371f57','Webadres van de Klazige');
 * Hans - hans
 * Klaas - klaas
 * SHA-256, Hex encoded.
 */
/**
 *
 * @author 871641
 */
@Entity
public class TweetUser implements Serializable {

    @Id
    private String name;
    private String web;
    private String bio;
    private String password;
    @ManyToMany
    private List<TweetRole> roleList;
    @ManyToMany(mappedBy = "following")
    private List<TweetUser> followers;
    //TODO DV zorgen dat bij het followen beide kanten geset worden.
    @ManyToMany
    private List<TweetUser> following = new ArrayList();
    @OneToMany(mappedBy = "tweetUser")
    private List<Tweet> tweets = new ArrayList();

    public TweetUser() {
    }

    public TweetUser(String naam) {
        this.name = naam;
    }

    public TweetUser(String naam, String password) {
        this.name = naam;
        hash(password);
    }

    public TweetUser(String naam, String password, String web, String bio) {
        this.name = naam;
        hash(password);
        this.web = web;
        this.bio = bio;
    }

    public List<TweetRole> getRoleList() {
        return roleList;
    }

    public void setRoleList(List<TweetRole> roleList) {
        this.roleList = roleList;
    }

    public List<TweetUser> getFollowers() {
        return followers;
    }

    public void setFollowers(List<TweetUser> followers) {
        this.followers = followers;
    }

    public List<Tweet> getTweets() {
        return tweets;
    }

    public void setTweets(List<Tweet> tweets) {
        this.tweets = tweets;
    }

    public void setPassword(String hashedPassword) {
        this.password = hashedPassword;
    }

    public boolean isPasswordCorrect(String testedPassword) {
        return this.password.equals(testedPassword);
    }

    public String getPassword() {
        return password;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getWeb() {
        return web;
    }

    public void setWeb(String web) {
        this.web = web;
    }

    public List<TweetUser> getFollowing() {
        return following;
    }

    public void setFollowing(ArrayList<TweetUser> following) {
        this.following = following;
    }

    public void setTweets(ArrayList<Tweet> tweets) {
        this.tweets = tweets;
    }

    public boolean addFollowing(TweetUser following) {
        return this.following.add(following);
    }

    public boolean addTweet(Tweet tweet) {
        return this.tweets.add(tweet);
    }  

    public Tweet getLastTweet(){
        if(tweets.isEmpty()){
            return null;
        }else{
            return tweets.get(tweets.size()-1);
        }
        
    }
    
    @Override
    public String toString() {
        return "User{" + "name=" + name + ", web=" + web + ", bio=" + bio + ", password=" + password + '}' + "\n";
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone(); //To change body of generated methods, choose Tools | Templates.
    }

    private void hash(String unhashed) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(unhashed.getBytes("UTF-8"));
            byte[] hash = md.digest();
            StringBuilder hexString = new StringBuilder();

            for (int i = 0; i < hash.length; i++) {
                String hex = Integer.toHexString(0xff & hash[i]);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }

            this.password = hexString.toString();
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException ex) {
            Logger.getLogger(TweetUser.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
