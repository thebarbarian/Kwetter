/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package kwetter.domain;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;


/**
 *
 * @author grave
 */
@Entity
public class TweetRole implements Serializable{
    @Id        
    private String roleName;    
   
    @ManyToMany(mappedBy = "roleList")
    List<TweetUser> userlist;

    public TweetRole() {
    }

    public TweetRole(String roleName){         
         this.roleName = roleName;
     }
    
    
     
        
       
}