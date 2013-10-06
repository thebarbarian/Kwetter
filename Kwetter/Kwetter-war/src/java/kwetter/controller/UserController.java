/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package kwetter.controller;

import java.io.Serializable;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import kwetter.beans.KwetterController;
import kwetter.domain.TweetUser;

/**
 *
 * @author grave
 */
@Named
@SessionScoped
public class UserController implements Serializable { 
    private TweetUser user = new TweetUser();
    
    public String login(){
        return "not implemented yet.";
    }
    
    public String login(String username, String password){
        if(username != null && password !=null){
            KwetterController kc = new KwetterController();
            TweetUser u = kc.login(username, password);
            if(u != null){
                return "profile.xhtml";
            }else{
                return "403.xhtml";
            }        
        }else{
            return "Error.xhtml";
        }
    
    }
    
    
    public String doCreateUser(String username) {
        if (username != null) {
            createUser(user, username);
            return "userCreated.xhtml";
        } else {
            return "Error.xhtml";
        }
    }

    /**
     * Creates a user
     * @param username
     * @param password
     * @return a user if username and password are not null.
     */
    public TweetUser createUser(String username, String password){
        if(username != null && password != null){
            return new TweetUser(username, password);            
        }else{
            throw new IllegalArgumentException("Username or password are null.");
        }
    }
    
    public void createUser(TweetUser user, String username) {
        user.setName(username);
    }

    public TweetUser getUser() {
        return user;
    }
    
}
