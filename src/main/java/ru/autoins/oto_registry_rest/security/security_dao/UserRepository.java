package ru.autoins.oto_registry_rest.security.security_dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.autoins.oto_registry_rest.security.models.User;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.Optional;

@Repository
public class UserRepository {

    private final EntityManager em;

    @Autowired
    public UserRepository(EntityManager em) {
        this.em = em;
    }

    @Transactional
    public Optional<User> getUserByName(String userName) {
        return Optional.ofNullable(this.em.createQuery("from User u where u.userName = :userName", User.class)
                .setParameter("userName", userName)
                .getSingleResult());
    }

    @Transactional
    public void persistUser(User user) {
        this.em.persist(user);
    }
}
