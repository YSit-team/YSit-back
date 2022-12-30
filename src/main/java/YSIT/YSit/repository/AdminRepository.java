package YSIT.YSit.repository;

import YSIT.YSit.domain.Admins;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class AdminRepository {
    private final EntityManager em;

    public Long save(Admins admins) {
        em.persist(admins);
        return admins.getId();
    }

    public Admins findById(Long adminId) {
        return em.find(Admins.class, adminId);
    }

    public List<Admins> findByLoginCode(String loginCode) {
        return em.createQuery("select a from Admins a where a.loginCode = :loginCode", Admins.class)
                .setParameter("loginCode", loginCode)
                .getResultList();
    }
    public List<Admins> findByName(String name) {
        return em.createQuery("select a from Admins a where a.name = :name", Admins.class)
                .setParameter("name", name)
                .getResultList();
    }
}
