package ru.autoins.oto_registry_rest.security.security_dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.autoins.oto_registry_rest.security.models.Role;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

@Repository
public class RoleRepository {

    private final EntityManager em;

    @Autowired
    public RoleRepository(EntityManager em) {
        this.em = em;
    }

    @Transactional
    public void persistRole(Role role) {
        this.em.persist(role);
    }

    @Transactional
    public Role getRoleByName(String roleName) {
        return this.em.createQuery("from Role r where r.roleName = :roleName", Role.class)
                .setParameter("roleName", roleName)
                .getSingleResult();
    }
}
