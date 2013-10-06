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
 * @author 871641
 */
@Entity
public class Tweet implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String tweet;
    @ManyToOne
    private User user;
    
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date postDate;
    private String postedFrom;

    public Tweet() {
    }

    public Tweet(String tweet) {
        this.tweet = tweet;
    }

    public Tweet(String tweet, Date datum, String vanaf, User gebruiker) {
        this.tweet = tweet;
        this.postDate = datum;
        this.postedFrom = vanaf;
        this.user = gebruiker;
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
    public int hashCode() {
        int hash = 5;
        hash = 59 * hash + Objects.hashCode(this.id);
        hash = 59 * hash + Objects.hashCode(this.tweet);
        hash = 59 * hash + Objects.hashCode(this.user);
        hash = 59 * hash + Objects.hashCode(this.postDate);
        hash = 59 * hash + Objects.hashCode(this.postedFrom);
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
        final Tweet other = (Tweet) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.tweet, other.tweet)) {
            return false;
        }
        if (!Objects.equals(this.user, other.user)) {
            return false;
        }
        if (!Objects.equals(this.postDate, other.postDate)) {
            return false;
        }
        if (!Objects.equals(this.postedFrom, other.postedFrom)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "twitter.domain.Tweet[id=" + postDate.toString() + "]";
    }

    /**
     * @return the user
     */
    public User getUser() {
        return user;
    }

    /**
     * @param user the user to set
     */
    public void setUser(User user) {
        this.user = user;
    }

}
