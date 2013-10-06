package kwetter.dao;

import java.util.List;
import kwetter.domain.User;

public interface UserDAO {

    int count();

    void create(User user);

    void edit(User user);

    List<User> findAll();

    User find(Long id);
    User find(String username);

    void remove(User user);
}
