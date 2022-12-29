package YSIT.YSit.service;

import YSIT.YSit.domain.Comment;
import YSIT.YSit.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
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
    public Comment findByRefOrder(Long refOrder) {
        return commentRepository.findByRefOrder(refOrder);
    }

    @Transactional(readOnly = false)
    public void delComment(Long commentId) {
        Comment comment = commentRepository.findOne(commentId);
        log.info("CommentBody = {}", comment.getBody());
        String deleteMention = "삭제되었습니다";
        comment.changeBody(deleteMention);
    }
}
