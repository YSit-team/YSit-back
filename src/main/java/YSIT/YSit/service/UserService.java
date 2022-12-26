package YSIT.YSit.service;

import YSIT.YSit.domain.User;
import YSIT.YSit.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {
    private final UserRepository userRepository;

    @Transactional(readOnly = false)
    public Long register(User user) {
        List<User> findDoubleCheck = doublecheckLoginId(user.getLoginId());
        if (!findDoubleCheck.isEmpty()){
            throw new IllegalStateException("Id already exists");
        }
        userRepository.save(user);
        return user.getId();
    }

    public List<User> doublecheckLoginId(String LoginId) { // 아이디 중복확인
        return userRepository.findLoginId(LoginId);
    }

    public List<User> matchLogins(String loginId, String loginPw) { //
        return userRepository.findLoginIdAndPw(loginId, userRepository.encryption(loginPw));
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
    public void deleteById(Long Id) {
        userRepository.deleteById(Id);
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
