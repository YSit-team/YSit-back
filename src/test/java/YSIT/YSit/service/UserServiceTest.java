package YSIT.YSit.service;

import YSIT.YSit.domain.SchoolCategory;
import YSIT.YSit.domain.User;
import YSIT.YSit.repository.UserRepository;
import jakarta.persistence.EntityManager;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@RunWith(SpringRunner.class)
@Transactional
class UserServiceTest {
    @Autowired
    UserRepository userRepository;
    @Autowired
    UserService userService;
    @Autowired
    EntityManager em;

    @Test
    public void registerTest() throws Exception {
        String name = "Test";
        String loginId = "Test";
        String loginPw = "Test";
        User user = User.builder()
                .name(name)
                .loginId(loginId)
                .loginPw(loginPw)
                .build();
        Long saveId = userService.register(user);

        assertEquals(user, userRepository.findOne(saveId));
    }

    @Test
    public void UserBuilderTest() {
        String name = "Test";
        String loginId = "Test";
        String loginPw = "Test";
        LocalDateTime now = LocalDateTime.now();

        User user1 = User.builder()
                .name(name)
                .loginId(loginId)
                .loginPw(loginPw)
                .schoolCategory(SchoolCategory.STUDENT)
                .regDate(now)
                .build();

        assertEquals(user1.getName(), name);
        assertEquals(user1.getLoginId(), loginId);
        assertEquals(user1.getLoginPw(), loginPw);
        assertEquals(user1.getSchoolCategory(), SchoolCategory.STUDENT);
        assertEquals(user1.getRegDate(), now);
    }

    @Test
    public void doublecheckException() throws Exception {
        String name = "Test";
        String loginId = "Test";
        String loginPw = "Test";
        User user1 = User.builder()
                .name(name)
                .loginId(loginId)
                .loginPw(loginPw)
                .build();
        User user2 = User.builder()
                .name(name)
                .loginId(loginId)
                .loginPw(loginPw)
                .build();

        userService.register(user1);
        try {
            userService.register(user2);
        } catch (IllegalStateException e) {
            return;
        }

        Assert.fail("예외가 발생하지 않았습니다.");
    }

    @Test
    public void encryptionTest() {
        String sha256 = "8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92";
        assertEquals(sha256, userRepository.encryption("123456"));
    }

    @Test
    public void matchLoginTest() {
        String name = "Test";
        String loginId = "Test";
        String loginPw = "Test";

        String pw_encrypt = userRepository.encryption(loginPw);

        User user1 = User.builder()
                .name(name)
                .loginId(loginId)
                .loginPw(pw_encrypt)
                .build();

        userService.register(user1);
        userService.matchLogins(loginId, loginPw);
    }

    @Test
    public void matchLoginExceptionTest() {
        String name = "Test";
        String loginId = "Test";
        String loginPw = "Test";
        User user1 = User.builder()
                .name(name)
                .loginId(loginId)
                .loginPw(userRepository.encryption(loginPw))
                .build();
        userService.register(user1);

        try {
            userService.matchLogins("1111", "1111");
        } catch (IllegalStateException e) {
            return;
        }

        fail("예외가 발생해야 한다");
    }

    @Test
    public void updateUserTest() {
        String name = "Test";
        String loginId = "Test";
        String loginPw = "Test";
        User user1 = User.builder()
                .name(name)
                .loginId(loginId)
                .loginPw(userRepository.encryption(loginPw))
                .build();
        userService.register(user1);
        String name2 = "Complete";
        String loginId2 = "Complete";
        User user = User.builder()
                .id(user1.getId())
                .name(name2)
                .loginId(loginId2)
                .loginPw(userRepository.encryption(loginPw))
                .build();
        userRepository.updateUser(user);
        em.flush();
        User result = userRepository.findOne(user1.getId());
        if (result.getName() == name2) {
            return;
        }
        fail("실패");
    }
}