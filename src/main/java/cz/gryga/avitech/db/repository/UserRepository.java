package cz.gryga.avitech.db.repository;

import cz.gryga.avitech.db.entity.User;

import java.util.List;

/**
 * Repository for {@link User} entity.
 */
public interface UserRepository {

    /**
     * Persists given user.
     *
     * @param user user to persist
     */
    void persist(User user);

    /**
     * List all users.
     */
    List<User> listAll();

    /**
     * Delete all users.
     */
    void deleteAll();
}
