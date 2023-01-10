package YSIT.YSit.user.repository;

import YSIT.YSit.user.SchoolCategory;
import YSIT.YSit.user.entity.User;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class UserRepository {
    private final EntityManager em;
    public void save(User user) {
        em.persist(user);
    }

    public User findOne(Long id) {
        return em.find(User.class, id);
    }

    public List<User> findLoginIdAndPw(String loginId, String loginPw) {
        return em.createQuery("select u from User u where u.loginId = :loginId and u.loginPw = :loginPw", User.class)
                .setParameter("loginId", loginId)
                .setParameter("loginPw", loginPw)
                .getResultList();
    }

    public List<User> findLoginId(String loginId) {
        return em.createQuery("select u from User u where u.loginId = :loginId", User.class)
                .setParameter("loginId", loginId)
                .getResultList();
    }

    public List<User> findAll() {
        return em.createQuery("select u from User u", User.class)
                .getResultList();
    }

    public List<User> findTeachers() {
        return em.createQuery("select u from User u where u.schoolCategory = :userType", User.class)
                .setParameter("userType", SchoolCategory.TEACHER)
                .getResultList();
    }

    public List<User> findStudents() {
        return em.createQuery("select u from User u where u.schoolCategory = :userType",User.class)
                .setParameter("userType", SchoolCategory.STUDENT)
                .getResultList();
    }

    public void deleteById(Long Id) {
        User user = em.find(User.class, Id);
        em.remove(user);
    }
}
