package kwetter.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

/**
 *
 * @author 871641
 */
@Entity
public class TweetUser implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String web;
    private String bio;
    private String password;
    @OneToMany
    private List<TweetUser> following = new ArrayList();
    @OneToMany
    private List<Tweet> tweets = new ArrayList();

    public TweetUser() {
    }

    public TweetUser(String naam) {
        this.name = naam;
    }

    public TweetUser(String naam, String password) {
        this.name = naam;
        this.password = password;
    }

    public TweetUser(String naam, String password, String web, String bio) {
        this.name = naam;
        this.web = web;
        this.bio = bio;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isPasswordCorrect(String testedPassword) {
        return this.password.equals(testedPassword);
    }

    public String getPassword() {
        return password;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Collection<Tweet> getTweets() {
        return Collections.unmodifiableCollection(tweets);
    }

    public void setTweets(ArrayList<Tweet> tweets) {
        this.tweets = tweets;
    }

    public Boolean addFollowing(TweetUser following) {
        return this.following.add(following);
    }

    public Boolean addTweet(Tweet tweet) {
        return this.tweets.add(tweet);
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 71 * hash + Objects.hashCode(this.id);
        hash = 71 * hash + Objects.hashCode(this.name);
        hash = 71 * hash + Objects.hashCode(this.web);
        hash = 71 * hash + Objects.hashCode(this.password);
        hash = 71 * hash + Objects.hashCode(this.following);
        hash = 71 * hash + Objects.hashCode(this.tweets);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final TweetUser other = (TweetUser) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        if (!Objects.equals(this.web, other.web)) {
            return false;
        }
        if (!Objects.equals(this.bio, other.bio)) {
            return false;
        }
        if (!Objects.equals(this.password, other.password)) {
            return false;
        }
        if (!Objects.equals(this.following, other.following)) {
            return false;
        }
        if (!Objects.equals(this.tweets, other.tweets)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "User{" + "id=" + id + ", name=" + name + ", web=" + web + ", bio=" + bio + ", password=" + password + '}' + "\n";
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone(); //To change body of generated methods, choose Tools | Templates.
    }
}
