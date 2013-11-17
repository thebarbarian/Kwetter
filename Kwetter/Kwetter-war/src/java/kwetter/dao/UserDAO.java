package kwetter.dao;

import java.util.List;
import kwetter.domain.TweetUser;

public interface UserDAO {

    int count();

    void create(TweetUser user);

    void edit(TweetUser user);

    List<TweetUser> findAll();

    TweetUser find(Long id);
    TweetUser find(String username);

    void remove(TweetUser user);
}
