package kwetter.dao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import kwetter.domain.TweetUser;

public class UserDAOCollectionImpl implements UserDAO {

    private ArrayList<TweetUser> users;

    public UserDAOCollectionImpl() {
        users = new ArrayList();
    }

    @Override
    public int count() {
        return users.size();
    }

    @Override
    public void create(TweetUser user) {
        users.add(user);
    }

    @Override
    public void edit(TweetUser user) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public ArrayList<TweetUser> findAll() {
        return new ArrayList(users);
    }

    @Override
    public void remove(TweetUser user) {
        users.remove(user);
    }

    @Override
    public TweetUser find(Long id) {
        if(id == null){
            throw new NullPointerException("param id cannot be null");
        }
        System.out.println(users);
        for(TweetUser u : users){
            if(u.getId().equals(id)){
                return u;
            }
        }
        throw new IllegalArgumentException("user with id" + id+ " not found.");
    }
    
    // Overloaded methode om op username te zoeken tbv logingedoe.
    @Override
    public TweetUser find(String username) {
        if(username == null){
            throw new NullPointerException("param username cannot be null");
        }
        System.out.println(users);
        for(TweetUser u : users){
            if(u.getName().equals(username)){
                return u;
            }
        }
        throw new IllegalArgumentException("user with username" + username + " not found.");
    }
}
