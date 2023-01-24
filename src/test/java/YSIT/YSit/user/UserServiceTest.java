//package YSIT.YSit.user;
//
//import YSIT.YSit.user.SchoolCategory;
//import YSIT.YSit.user.entity.User;
//import YSIT.YSit.user.repository.UserRepository;
//import YSIT.YSit.user.service.UserService;
//import jakarta.persistence.EntityManager;
//import org.junit.Assert;
//import org.junit.jupiter.api.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.junit4.SpringRunner;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.time.LocalDateTime;
//import java.util.List;
//import java.util.Objects;
//
//import static org.junit.jupiter.api.Assertions.*;
//@SpringBootTest
//@RunWith(SpringRunner.class)
//@Transactional
//class UserServiceTest {
//    @Autowired
//    UserRepository userRepository;
//    @Autowired
//    UserService userService;
//    @Autowired
//    EntityManager em;
//
//    @Test
//    public void registerTest() throws Exception {
//        String name = "Test";
//        String loginId = "Test";
//        String loginPw = "Test";
//        User user = User.builder()
//                .name(name)
//                .loginId(loginId)
//                .loginPw(loginPw)
//                .build();
//        String saveId = userService.register(user);
//
//        assertEquals(user, userRepository.findOne(saveId));
//    }
//
//    @Test
//    public void UserBuilderTest() {
//        String name = "Test";
//        String loginId = "Test";
//        String loginPw = "Test";
//        LocalDateTime now = LocalDateTime.now();
//
//        User user1 = User.builder()
//                .name(name)
//                .loginId(loginId)
//                .loginPw(loginPw)
//                .schoolCategory(SchoolCategory.STUDENT)
//                .regDate(now)
//                .build();
//
//        assertEquals(user1.getName(), name);
//        assertEquals(user1.getLoginId(), loginId);
//        assertEquals(user1.getLoginPw(), loginPw);
//        assertEquals(user1.getSchoolCategory(), SchoolCategory.STUDENT);
//        assertEquals(user1.getRegDate(), now);
//    }
//
//    @Test
//    public void doubleCheckException() {
//        String name = "Test";
//        String loginId = "Test";
//        String loginPw = "Test";
//        User user1 = User.builder()
//                .name(name)
//                .loginId(loginId)
//                .loginPw(loginPw)
//                .build();
//        User user2 = User.builder()
//                .name(name)
//                .loginId(loginId)
//                .loginPw(loginPw)
//                .build();
//
//        userService.register(user1);
//        try {
//            userService.register(user2);
//        } catch (IllegalStateException e) {
//            return;
//        }
//
//        Assert.fail("예외가 발생하지 않았습니다.");
//    }
//
//    @Test
//    public void encryptionTest() {
//        String sha256 = "8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92";
//        assertEquals(sha256, userService.encryption("123456"));
//    }
//
//    @Test
//    public void matchLoginTest() {
//        String name = "Test";
//        String loginId = "Test";
//        String loginPw = "Test";
//
//        User user1 = User.builder()
//                .name(name)
//                .loginId(loginId)
//                .loginPw(loginPw)
//                .build();
//
//        userService.register(user1);
//        Boolean matchLogins = userService.matchLogins(loginId, loginPw);
//        if (!matchLogins) {
//            fail("유저가 존재하지 않습니다");
//        }
//    }
//
//    @Test
//    public void matchLoginExceptionTest() {
//        String name = "Test";
//        String loginId = "Test";
//        String loginPw = "Test";
//        User user1 = User.builder()
//                .name(name)
//                .loginId(loginId)
//                .loginPw(userService.encryption(loginPw))
//                .build();
//        userService.register(user1);
//
//        Boolean matchLogins = userService.matchLogins("1111", "1111");
//        if (!matchLogins) {
//            return;
//        }
//        fail("예외가 발생해야 한다");
//    }
//
//    @Test
//    public void updateUserTest() {
//        User user = createUser("Test","Test","Test");
//        String name2 = "Com";
//        User updateUser = User.builder()
//                .id(user.getId())
//                .name("Com")
//                .loginId("Com")
//                .loginPw(userService.encryption("Com"))
//                .build();
//        userService.updateUser(updateUser);
//
//        User result = userService.findOne(user.getId());
//        if (result.getName() == name2) {
//            return;
//        }
//        fail("실패");
//    }
//
//    @Test
//    public void deleteTest() {
//        User user1 = createUser("Test","Test","Test");
//        userService.deleteById(user1.getId());
//        em.flush();
//        User user = userService.findOne(user1.getId());
//        if (!Objects.isNull(user)){
//            fail("실패");
//        }
//    }
//
//    public User createUser(String name, String loginId, String loginPw) {
//        User user1 = User.builder()
//                .name(name)
//                .loginId(loginId)
//                .loginPw(userService.encryption(loginPw))
//                .build();
//        userService.register(user1);
//        return user1;
//    }
//}