package YSIT.YSit.videotools.service;

import YSIT.YSit.videotools.entity.Rental;
import YSIT.YSit.videotools.entity.VideoTool;
import YSIT.YSit.videotools.repository.RentalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class RentalService {
    private final RentalRepository rentalRepository;
    private final VideoToolService videoToolService;

    public String save(Rental rental) {
        return rentalRepository.save(rental);
    }

    public Rental findOne(String rentalId) {
        return rentalRepository.findOne(rentalId);
    }

    public List<Rental> findAll() {
        return rentalRepository.findAll();
    }
    public String deleteById(String id) {
        return rentalRepository.deleteById(id);
    }
    public List<VideoTool> changeListVT(List<String> rentalVts) {
        List<VideoTool> res = new ArrayList<VideoTool>();
        for (int i = 0; i < rentalVts.size(); i++) {
            for (String vtName :
                    rentalVts) {
                List<VideoTool> videoToolAry = videoToolService.findByName(vtName);
                VideoTool videoTool = null;
                for (VideoTool temp : videoToolAry) {
                    videoTool = temp;
                }
                if (videoTool == null || Objects.isNull(videoTool)) {
                    throw new IllegalStateException("데이터가 없습니다.");
                }
                res.add(videoTool);
            }
        }
        return res;
    }

    public List<Rental> findRequestById(String id) {
        return rentalRepository.findRequestById(id);
    }
}
