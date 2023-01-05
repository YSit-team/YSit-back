package YSIT.YSit.service;

import YSIT.YSit.domain.Admins;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

import static org.junit.Assert.*;

@Transactional
@SpringBootTest
@RunWith(SpringRunner.class)
public class AdminServiceTest {
    @Autowired
    private AdminService adminService;

    @Test
    public void save() {
        Admins admin1 = Admins.builder()
                .loginCode("test")
                .name("test")
                .build();
        adminService.save(admin1);
        Admins adminTest = adminService.findOne(admin1.getId());
        if (Objects.isNull(adminTest)){
            fail("실패");
        } else if (adminTest.getLoginCode() != "test") {
            fail("실패");
        }
    }

    @Test
    public void findByLoginCode() {
        Admins admin1 = Admins.builder()
                .loginCode("test")
                .name("test")
                .build();
        adminService.save(admin1);
        List<Admins> adminTest = adminService.findByLoginCode(admin1.getLoginCode());
        if (Objects.isNull(adminTest)){
            fail("실패");
        }
    }

    @Test
    public void findByName() {
        Admins admins = Admins.builder()
                .loginCode("test")
                .name("test")
                .build();
        adminService.save(admins);
        List<Admins> adminTest = adminService.findByLoginCode(admins.getLoginCode());
        if (Objects.isNull(adminTest)){
            fail("실패");
        }
    }
}