package YSIT.YSit.board.comment.service;

import YSIT.YSit.board.comment.entity.Comment;
import YSIT.YSit.board.comment.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
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
    public void delCommentByUser(Comment delCom) {
        Comment comment = commentRepository.findOne(delCom.getId());
        String deleteMention = "삭제되었습니다";
        comment.changeBody(deleteMention);
    }
    @Transactional(readOnly = false)
    public void delCommentByAdmin(Comment delCom) {
        Comment comment = commentRepository.findOne(delCom.getId());
        String deleteMention = "관리자에 의해 삭제되었습니다";
        comment.changeBody(deleteMention);
    }
}
