/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package kwetter.domain;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;


/**
 *
 * @author grave
 */
@Entity
public class TweetRole {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long roleId;      
    private String roleName;
    private TweetUser tweeter;
   
    @ManyToMany(mappedBy = "roleList")
    List<TweetUser> userlist;

    public TweetRole() {
    }

    public TweetRole(Long id, String rolenm){
         this.roleId=id;
         this.roleName=rolenm;
     }
    
    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }
    
     
        
       
}