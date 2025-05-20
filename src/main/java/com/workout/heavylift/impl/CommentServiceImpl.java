package com.workout.heavylift.impl;


import com.workout.heavylift.domain.BoardPost;
import com.workout.heavylift.domain.Comment;
import com.workout.heavylift.domain.User;
import com.workout.heavylift.dto.board.CommentResponse;
import com.workout.heavylift.dto.board.CreateCommentRequest;
import com.workout.heavylift.dto.board.UpdateCommentRequest;
import com.workout.heavylift.repository.BoardPostRepository;
import com.workout.heavylift.repository.CommentRepository;
import com.workout.heavylift.repository.UserRepository;
import com.workout.heavylift.service.CommentService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final BoardPostRepository boardPostRepository;

    @Override
    public CommentResponse create(Long postId, CreateCommentRequest request) {
        BoardPost post = boardPostRepository.findById(postId)
                .orElseThrow(() -> new EntityNotFoundException("게시글 없음"));
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new EntityNotFoundException("사용자 없음"));
        Comment comment = request.toEntity(post, user);
        return CommentResponse.fromEntity(commentRepository.save(comment));
    }

    @Override
    public List<CommentResponse> getCommentsByPost(Long postId) {
        return commentRepository.findByPostIdOrderByCreatedAtDesc(postId).stream()
                .map(CommentResponse::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public CommentResponse update(Long commentId, UpdateCommentRequest request) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new EntityNotFoundException("댓글 없음"));
        comment.updateContent(request.getContent());
        return CommentResponse.fromEntity(comment);
    }

    @Override
    public void delete(Long commentId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new EntityNotFoundException("댓글 없음"));
        commentRepository.delete(comment);
    }

    @Override
    public void validateAuthor(Long commentId, Long userId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new EntityNotFoundException("댓글 없음"));
        if (!comment.getUser().getId().equals(userId)) {
            throw new SecurityException("작성자만 수정/삭제할 수 있습니다.");
        }
    }
}
