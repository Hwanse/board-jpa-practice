package com.pratice.boardjpa.service;

import com.pratice.boardjpa.domain.Board;
import com.pratice.boardjpa.domain.Post;
import com.pratice.boardjpa.repository.BoardRepository;
import com.pratice.boardjpa.repository.PostRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class PostService {

    private final PostRepository postRepository;

    private final BoardRepository boardRepository;

    @Transactional(readOnly = true)
    public Post findPostById(Long postId) {
        return postRepository.findById(postId);
    }

    public void createPost(Post post, Long boardId) {
        Board board = boardRepository.findById(boardId);
        Post newPost = Post.createPost(post.getName(), post.getContents(), post.getUseFlag(),
                                       board);
        postRepository.save(newPost);
    }

    public void createReplyPost(Post post, Long boardId, Long postId) {
        Board board = boardRepository.findById(boardId);
        Post parentPost = postRepository.findById(postId);
        Post replyPost = Post.createReplyPost(post.getName(), post.getContents(), post.getUseFlag(),
                                              board, parentPost);

        postRepository.save(replyPost);
    }

    public void deletePost(Long postId) {
        Post beDeletePost = postRepository.findById(postId);
        postRepository.delete(beDeletePost);
    }

    public void modifyPost(Long postId) {
        Post beModifyPost = postRepository.findById(postId);
        beModifyPost.modifyPost(beModifyPost);
    }

}
