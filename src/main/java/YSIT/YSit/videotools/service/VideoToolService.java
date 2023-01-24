package YSIT.YSit.videotools.service;

import YSIT.YSit.videotools.entity.VideoTool;
import YSIT.YSit.videotools.repository.VideoToolRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class VideoToolService {
    private final VideoToolRepository videoToolRepository;
    @Transactional(readOnly = false)
    public String save(VideoTool videoTool) {
        return videoToolRepository.save(videoTool);
    }

    public VideoTool findOne(String id) {
        return videoToolRepository.findOne(id);
    }

    public List<VideoTool> findByName(String name) {
        return videoToolRepository.findByName(name);
    }
    public List<VideoTool> findAll() {
        return videoToolRepository.findAll();
    }
}