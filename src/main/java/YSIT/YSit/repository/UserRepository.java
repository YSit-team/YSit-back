package YSIT.YSit.repository;

import YSIT.YSit.domain.SchoolCategory;
import YSIT.YSit.domain.User;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
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

    public String encryption(String password) {
        StringBuffer hexSHA256hash = null;
        try {

            /* MessageDigest 클래스의 getInstance() 메소드의 매개변수에 "SHA-256" 알고리즘 이름을 지정함으로써
                해당 알고리즘에서 해시값을 계산하는 MessageDigest를 구할 수 있다 */
            MessageDigest mdSHA256 = MessageDigest.getInstance("SHA-256");

            // 데이터(패스워드 평문)를 한다. 즉 '암호화'와 유사한 개념
            mdSHA256.update(password.getBytes("UTF-8"));

            // 바이트 배열로 해쉬를 반환한다.
            byte[] sha256Hash = mdSHA256.digest();

            // StringBuffer 객체는 계속해서 append를 해도 객체는 오직 하나만 생성된다. => 메모리 낭비 개선
            hexSHA256hash = new StringBuffer();

            // 256비트로 생성 => 32Byte => 1Byte(8bit) => 16진수 2자리로 변환 => 16진수 한 자리는 4bit
            for (byte b : sha256Hash) {
                String hexString = String.format("%02x", b);
                hexSHA256hash.append(hexString);
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String sha256 = hexSHA256hash.toString();
        return sha256;
    }
}
