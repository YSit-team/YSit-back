package YSIT.YSit.videotools;

import YSIT.YSit.user.SchoolCategory;
import YSIT.YSit.user.entity.User;
import YSIT.YSit.user.service.UserService;
import YSIT.YSit.videotools.dto.RentalListForm;
import YSIT.YSit.videotools.entity.Rental;
import YSIT.YSit.videotools.entity.VideoTool;
import YSIT.YSit.videotools.service.RentalService;
import YSIT.YSit.videotools.service.VideoToolService;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.junit.Assert.*;
@SpringBootTest
@RunWith(SpringRunner.class)
@Transactional
@WebAppConfiguration
public class RentalServiceTest {

    @Autowired
    private VideoToolService videoToolService;
    @Autowired
    private UserService userService;
    @Autowired
    private RentalService rentalService;

    private User user = null;
    private VideoTool videoTool = null;
    private Rental rent = null;

    @BeforeEach
    public void setting() {
        this.user = User.builder()
                .loginId("tester")
                .loginPw("pw")
                .name("test")
                .schoolCategory(SchoolCategory.STUDENT)
                .build();
        userService.register(user);

        this.videoTool = VideoTool.builder()
                .name("VSLR")
                .quantity(10)
                .maxQuantity(10)
                .user(user)
                .build();
        videoToolService.save(videoTool);


        List<String> rentalTools = new ArrayList<String>();
        rentalTools.add(videoTool.getName());

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss");
        LocalDate rentalDate = LocalDate.parse("2023-03-13 11:11:00", formatter);
        LocalDate returnDate = LocalDate.parse("2023-03-14 12:12:00", formatter);

        this.rent = Rental.builder()
                .rentalTools(rentalTools)
                .uploader(user.getId())
                .headCnt(3)
                .rentalDate(rentalDate.atStartOfDay())
                .returnDate(returnDate.atStartOfDay())
                .reason(null)
                .status(RentalStatus.wait)
                .build();
        rentalService.save(rent);
    }
    @Test
    public void save() {

        User user = User.builder()
                .loginId("tester")
                .loginPw("pw")
                .name("test")
                .schoolCategory(SchoolCategory.STUDENT)
                .build();
        userService.register(user);

        VideoTool videoTool = VideoTool.builder()
                .name("VSLR")
                .quantity(10)
                .maxQuantity(10)
                .user(user)
                .build();
        videoToolService.save(videoTool);

        List<String> rentalTools = new ArrayList<String>();
        rentalTools.add(videoTool.getName());

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss");
        LocalDate rentalDate = LocalDate.parse("2023-03-13 11:11:00", formatter);
        LocalDate returnDate = LocalDate.parse("2023-03-14 12:12:00", formatter);

        Rental rental = Rental.builder()
                .rentalTools(rentalTools)
                .uploader(user.getId())
                .headCnt(3)
                .rentalDate(rentalDate.atStartOfDay())
                .returnDate(returnDate.atStartOfDay())
                .reason(null)
                .status(RentalStatus.wait)
                .build();
        rentalService.save(rental);

        List<Rental> rentals = rentalService.findAll();
        if (rentals.isEmpty()) {
            fail();
        }
    }

    @Test
    public void findOne() {
    }

    @Test
    public void findAll() {
    }

    @Test
    public void deleteById() {

        this.user = User.builder()
                .loginId("tester")
                .loginPw("pw")
                .name("test")
                .schoolCategory(SchoolCategory.STUDENT)
                .build();
        userService.register(user);

        this.videoTool = VideoTool.builder()
                .name("VSLR")
                .quantity(10)
                .maxQuantity(10)
                .user(user)
                .build();
        videoToolService.save(videoTool);


        List<String> rentalTools = new ArrayList<String>();
        rentalTools.add(videoTool.getName());

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss");
        LocalDate rentalDate = LocalDate.parse("2023-03-13 11:11:00", formatter);
        LocalDate returnDate = LocalDate.parse("2023-03-14 12:12:00", formatter);

        this.rent = Rental.builder()
                .rentalTools(rentalTools)
                .uploader(user.getId())
                .headCnt(3)
                .rentalDate(rentalDate.atStartOfDay())
                .returnDate(returnDate.atStartOfDay())
                .reason(null)
                .status(RentalStatus.wait)
                .build();
        rentalService.save(rent);
        try {
            rentalService.deleteById(rent.getId());
        } catch (IllegalStateException e) {
            fail();
        }
    }
}