package YSIT.YSit.videotools.service;

import YSIT.YSit.videotools.entity.Rental;
import YSIT.YSit.videotools.repository.RentalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RentalService {
    private final RentalRepository rentalRepository;

    public String save(Rental rental) {
        return rentalRepository.save(rental);
    }

    public Rental findOne(String rentalId) {
        return rentalRepository.findOne(rentalId);
    }

    public List<Rental> findRequestsByWait() {
        return rentalRepository.findRequestsByWait();
    }
    public List<Rental> findRequests() {
        return rentalRepository.findRequestsAll();
    }
}
