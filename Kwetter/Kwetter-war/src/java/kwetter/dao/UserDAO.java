package kwetter.dao;

import java.util.ArrayList;
import kwetter.domain.User;

public interface UserDAO {

    int count();

    void create(User user);

    void edit(User user);

    ArrayList<User> findAll();

    User find(Long id);

    void remove(User user);
}
