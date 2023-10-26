package blog.manager.domain.model;

import java.util.Optional;

public interface UserRepository {
    Optional<User> findByName(String name);

    long countByNameAndPassword(String name, String password);
}
