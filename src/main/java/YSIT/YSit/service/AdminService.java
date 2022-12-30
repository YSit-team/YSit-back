package YSIT.YSit.service;

import YSIT.YSit.domain.Admins;
import YSIT.YSit.repository.AdminRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AdminService {
    private final AdminRepository adminRepository;

    @Transactional(readOnly = false)
    public Long save(Admins admins) {
        adminRepository.save(admins);
        return admins.getId();
    }
    public Admins findOne(Long id) {
        return adminRepository.findById(id);
    }
    public List<Admins> findByLoginCode(String LoginCode) {
        return adminRepository.findByLoginCode(LoginCode);
    }
    public List<Admins> findByName(String name) {
        return adminRepository.findByName(name);
    }
}
