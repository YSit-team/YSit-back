package YSIT.YSit.controller.admin;

import YSIT.YSit.controller.form.VideoToolForm;
import YSIT.YSit.controller.form.VideoToolListForm;
import YSIT.YSit.domain.Admins;
import YSIT.YSit.domain.VideoTool;
import YSIT.YSit.service.AdminService;
import YSIT.YSit.service.VideoToolService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
public class VideoToolController {
    private final VideoToolService videoToolService;
    private final AdminService adminService;

    @GetMapping("/admin/videoTool/save")
    public String saveForm(Model model) {
        model.addAttribute("videoToolForm", new VideoToolForm());
        return "/admins/videoTool/Save";
    }
    @PostMapping("/admin/videoTool/save")
    public String videoToolSave(@Valid @ModelAttribute VideoToolForm form,
                                BindingResult result,
                                HttpServletRequest request) {
        if (form.getName().isEmpty()) {
            result.rejectValue("name", "required");
        }
        if (form.getQuantity() == 0) {
            result.rejectValue("quantity", "required");
        }
        if (result.hasErrors()) {
            return "/admins/videoTool/Save";
        }

        HttpSession session = request.getSession();
        Long adminId = (Long) session.getAttribute("adminId");
        Admins admin = adminService.findOne(adminId);

        VideoTool videoTool = VideoTool.builder()
                .name(form.getName())
                .quantity(form.getQuantity())
                .maxQuantity(form.getQuantity())
                .admin(admin)
                .build();
        videoToolService.save(videoTool);

        return "redirect:/";
    }

    @GetMapping("/admin/videoTool/list")
    public String vtListForm(Model model) {
        List<VideoTool> videoTools = videoToolService.findAll();
        model.addAttribute("vts", videoTools);
        model.addAttribute("vtListForm", new VideoToolListForm());
        return "/admins/videoTool/List";
    }

    @PostMapping("/admin/videoTool/list")
    public String vtList(@ModelAttribute VideoToolListForm form,
                         Model model) {
        if (!form.getSearchName().isEmpty()) {
            List<VideoTool> videoTool = videoToolService.findByName(form.getSearchName());
            model.addAttribute("vts", videoTool);
        } else {
            List<VideoTool> videoTools = videoToolService.findAll();
            model.addAttribute("vts", videoTools);
        }
        model.addAttribute("vtListForm", new VideoToolListForm());
        return "/admins/videoTool/List";
    }
}
