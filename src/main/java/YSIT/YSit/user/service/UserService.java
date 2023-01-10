package YSIT.YSit.user.service;

import YSIT.YSit.user.entity.User;
import YSIT.YSit.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;


@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;

    @Transactional(readOnly = false)
    public Long register(User user) {
        List<User> findDoubleCheck = doubleCheckLoginId(user.getLoginId());
        if (!findDoubleCheck.isEmpty()){
            throw new IllegalStateException("Id already exists");
        }
        userRepository.save(user);
        return user.getId();
    }

    public List<User> doubleCheckLoginId(String LoginId) { // 아이디 중복확인
        return userRepository.findLoginId(LoginId);
    }

    public List<User> matchLogins(String loginId, String loginPw) { //
        List<User> users = userRepository.findLoginIdAndPw(loginId, encryption(loginPw));
        return users;
    }

    @Transactional(readOnly = false) // Dirty Check
    public void updateUser(User updateData) {
        User user1 = userRepository.findOne(updateData.getId());
        if (updateData.getName() != null) {
            user1.changeName(updateData.getName());
        }
        if (updateData.getLoginId() != null) {
            user1.changeLoginId(updateData.getLoginId());
        }
        if (updateData.getLoginId() != null) {
            user1.changeLoginPw(updateData.getLoginPw());
        }
        if (updateData.getSchoolCategory() != null) {
            user1.changeSchoolCategory(updateData.getSchoolCategory());
        }
    }

    @Transactional(readOnly = false)
    public void deleteById(Long Id) {
        userRepository.deleteById(Id);
    }

    public User findOne(Long id) {
        return userRepository.findOne(id);
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
