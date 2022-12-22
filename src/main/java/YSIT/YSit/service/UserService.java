package YSIT.YSit.service;

import YSIT.YSit.domain.User;
import YSIT.YSit.repository.UserRepository;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.io.UnsupportedEncodingException;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {
    private final UserRepository userRepository;
    private final EntityManager em;

    @Transactional(readOnly = false)
    public Long register(User user) {
        doublecheckLoginId(user);
        userRepository.save(user);
        return user.getId();
    }

    private void doublecheckLoginId(User user) { // 아이디 중복확인
        List<User> findLoginId = userRepository.findLoginId(user.getLoginId());
        if (!findLoginId.isEmpty()) {
            throw new IllegalStateException("Id already exists");
        }
    }

    public void matchLogins(String loginId, String loginPw) { //
        List<User> findLogins = userRepository.findIdAndPw(loginId, userRepository.encryption(loginPw));
        if (findLogins.isEmpty()) {
            throw new IllegalStateException("ID 혹은 PASSWORD가 틀렸습니다.");
        }
    }

    public List<User> findUserAll() {
        return userRepository.findAll();
    }
    public List<User> findStudentAll() {
        return userRepository.findStudents();
    }
    public List<User> findTeacherAll() {
        return userRepository.findTeachers();
    }
    public List<User> findLoginId(String loginId) {
        return userRepository.findLoginId(loginId);
    }
}
