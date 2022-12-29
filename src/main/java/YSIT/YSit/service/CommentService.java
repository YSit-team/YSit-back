package YSIT.YSit.service;

import YSIT.YSit.domain.Comment;
import YSIT.YSit.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentService {
    private final CommentRepository commentRepository;

    @Transactional(readOnly = false)
    public Long save(Comment comment) {
        commentRepository.save(comment);
        return comment.getId();
    }
    public Long getMaxRef() {
        return commentRepository.maxRef();
    }
    public List<Comment> findByArt(Long artId) {
        return commentRepository.findByArt(artId);
    }
    public Comment findOne(Long comId) {
        return commentRepository.findOne(comId);
    }
}
