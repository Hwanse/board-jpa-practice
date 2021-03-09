package com.pratice.boardjpa.service;

import com.pratice.boardjpa.domain.Comment;
import com.pratice.boardjpa.domain.Post;
import com.pratice.boardjpa.repository.CommentRepository;
import com.pratice.boardjpa.repository.PostRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class CommentService {

    private final PostRepository postRepository;

    private final CommentRepository commentRepository;

    @Transactional(readOnly = true)
    public List<Comment> findAllComment(Long postId) {
        return commentRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Comment findCommentById(Long commentId) {
        return commentRepository.findById(commentId);
    }

    public void createComment(Long postId, Comment comment) {
        Post findPost = postRepository.findById(postId);
        Comment newComment = Comment.createComment(findPost, comment.getContents(),
                                                   comment.getUseFlag());

        commentRepository.save(newComment);
    }

    public void createReplyComment(Long postId, Long commentId, Comment comment) {
        Post findPost = postRepository.findById(postId);
        Comment findComment = commentRepository.findById(commentId);

        Comment replyComment = Comment.createReplyComment(findPost, findComment,
                                                          comment.getContents(),
                                                          comment.getUseFlag());

        commentRepository.save(replyComment);
    }

    public void modifyComment(Comment comment) {
        Comment beModifyComment = commentRepository.findById(comment.getId());
        beModifyComment.modifyComment(comment);
    }

    public void deleteComment(Long commentId) {
        Comment beDeleteComment = commentRepository.findById(commentId);
        commentRepository.delete(beDeleteComment);
    }

}
