/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package kwetter.controller;

import java.io.Serializable;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import kwetter.domain.User;

/**
 *
 * @author grave
 */
@Named
@SessionScoped
public class UserController implements Serializable{
    
    @Inject
    private String username;    
    private User user = new User();
    
    
    public String doCreateUser(String username){
        if(username!=null){
        createUser(user, username);        
        return "userCreated.xhtml";
        } else return "Error.xhtml";        
    }
    
    public void createUser(User user, String username){
        user.setName(username);        
    }
    
     
    
}
