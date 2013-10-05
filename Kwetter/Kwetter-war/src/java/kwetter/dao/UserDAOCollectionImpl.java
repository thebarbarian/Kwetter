package kwetter.dao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import kwetter.domain.User;

public class UserDAOCollectionImpl implements UserDAO {

    private ArrayList<User> users;

    public UserDAOCollectionImpl() {
        users = new ArrayList();
    }

    @Override
    public int count() {
        return users.size();
    }

    @Override
    public void create(User user) {
        users.add(user);
    }

    @Override
    public void edit(User user) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public ArrayList<User> findAll() {
        return new ArrayList(users);
    }

    @Override
    public void remove(User user) {
        users.remove(user);
    }

    @Override
    public User find(Long id) {
        if(id == null){
            throw new NullPointerException("param id cannot be null");
        }
        System.out.println(users);
        for(User u : users){
            if(u.getId().equals(id)){
                return u;
            }
        }
        throw new IllegalArgumentException("user with id" + id+ " not found.");
    }
    
    // Overloaded methode om op username te zoeken tbv logingedoe.
    @Override
    public User find(String username) {
        if(username == null){
            throw new NullPointerException("param username cannot be null");
        }
        System.out.println(users);
        for(User u : users){
            if(u.getName().equals(username)){
                return u;
            }
        }
        throw new IllegalArgumentException("user with username" + username + " not found.");
    }
}
