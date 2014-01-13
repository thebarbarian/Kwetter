package kwetter.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;

/**
 *
 * @author David
 */
@Entity
public class Tweet implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String tweet;
    
    @ManyToOne
    private TweetUser tweetUser;
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date postDate;
    private String postedFrom;

    public Tweet() {
    }

    public Tweet(String tweet) {
        this.tweet = tweet;
    }

    public Tweet(String tweet, Date datum, String vanaf, TweetUser user) {
        this.tweet = tweet;
        this.postDate = datum;
        this.postedFrom = vanaf;
        this.tweetUser = user;        
    }

    public Long getId() {
	return id;
    }

    public void setId(Long id) {
	this.id = id;
    }    
    
    public String getTweet() {
        return tweet;
    }

    public void setTweet(String tweet) {
        this.tweet = tweet;
    }

    public Date getDatum() {
        return postDate;
    }

    public void setDatum(Date datum) {
        this.postDate = datum;
    }

    public String getVanaf() {
        return postedFrom;
    }

    public void setVanaf(String vanaf) {
        this.postedFrom = vanaf;
    }


    @Override
    public String toString() {
        return "twitter.domain.Tweet[id=" + postDate.toString() + "]";
    }

    /**
     * @return the user
     */
    public TweetUser getUser() {
        return tweetUser;
    }

    /**
     * @param user the user to set
     */
    public void setUser(TweetUser user) {
        this.tweetUser = user;
    }

}
