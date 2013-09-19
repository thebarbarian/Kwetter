package kwetter.dao;

import kwetter.domain.User;
import java.util.List;

public interface UserDAO {

    int count();

    void create(User user);

    void edit(User user);

    List<User> findAll();

    User find(Long id);

    void remove(User user);
}
