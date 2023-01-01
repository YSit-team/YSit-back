package YSIT.YSit.repository;

import YSIT.YSit.domain.VideoTool;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class VideoToolRepository {
    private final EntityManager em;

    public Long save(VideoTool videoTool) {
        em.persist(videoTool);
        return videoTool.getId();
    }

    public VideoTool findOne(Long vtId) {
        return em.find(VideoTool.class, vtId);
    }

    public List<VideoTool> findByName(String name) {
        List<VideoTool> videoTools = em.createQuery("select v from VideoTool v where v.name like :name", VideoTool.class)
                .setParameter("name", "%"+name+"%")
                .getResultList();
        return videoTools;
    }
    public List<VideoTool> findAll() {
        return em.createQuery("select v from VideoTool v", VideoTool.class)
                .getResultList();
    }
}
