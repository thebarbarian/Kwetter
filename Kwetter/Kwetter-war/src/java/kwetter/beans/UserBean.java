/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package kwetter.beans;

import java.util.ArrayList;
import java.util.Collection;
import javax.inject.Inject;
import javax.inject.Named;
import kwetter.domain.Tweet;
import kwetter.domain.TweetUser;

/**
 *
 * @author grave
 */
@Named
public class UserBean {
        
    private static final long serialVersionUID=1;
    
    @Inject
    private String name;
    private String web;
    private String bio;

    private Collection<TweetUser> following = new ArrayList();
    private Collection<Tweet> tweets = new ArrayList();       
    
    /**
     * Creates a new instance of UserBean
     */    
    public UserBean() {                        
    }    
    
    public String getName(){
        return this.name;
    }
    
    public void setName(String naam){
        this.name = naam;
    }
    
    public String getBio(){
        return this.bio;
    }
    
    public void setBio(String bio){
        this.bio = bio;
    }
    
    public Collection<TweetUser> getFollowing(){
        return this.following;
    }
    
    public void setFollowing(Collection<TweetUser> following) {
        this.following = following;
    }
    
    public Collection<Tweet> getTweets(){
        return this.tweets;
    }
    
    public void setTweets(Collection<Tweet> tweets) {
        this.tweets = tweets;
    }

    public Boolean addFollowing(TweetUser following){
        return this.following.add(following);
    }   
    
}
