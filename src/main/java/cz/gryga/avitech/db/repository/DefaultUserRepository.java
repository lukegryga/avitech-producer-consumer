package cz.gryga.avitech.db.repository;

import cz.gryga.avitech.db.entity.User;
import org.hibernate.Session;

import java.util.List;

/**
 * This implementation of {@link UserRepository} wraps all operations in transaction.
 */
public class DefaultUserRepository implements UserRepository {

    private final Session em;

    public DefaultUserRepository(Session em) {
        this.em = em;
    }

    @Override
    public void persist(User user) {
        transactional(() ->
                em.persist(user));
    }

    @Override
    public void deleteAll() {
        transactional(() ->
                em.createMutationQuery("DELETE FROM User").executeUpdate());
    }

    @Override
    public List<User> listAll() {
        return em.createQuery("SELECT u FROM User u", User.class).getResultList();
    }

    private void transactional(Runnable runnable) {
        try {
            em.beginTransaction();
            runnable.run();
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction() != null) {
                em.getTransaction().rollback();
            }
            //TODO log
            throw new RuntimeException(e);
        }
    }
}
