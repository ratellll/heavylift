package com.workout.heavylift.impl;

import com.workout.heavylift.config.SecurityUtil;
import com.workout.heavylift.domain.BoardPost;
import com.workout.heavylift.domain.User;
import com.workout.heavylift.dto.board.BoardPostResponse;
import com.workout.heavylift.dto.board.CreateBoardPostRequest;
import com.workout.heavylift.dto.board.UpdateBoardPostRequest;
import com.workout.heavylift.repository.BoardPostRepository;
import com.workout.heavylift.repository.UserRepository;
import com.workout.heavylift.service.BoardPostService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class BoardPostServiceImpl implements BoardPostService {

    private final BoardPostRepository boardPostRepository;
    private final UserRepository userRepository;

    @Override
    public BoardPostResponse create(CreateBoardPostRequest request) {
        Long userId = SecurityUtil.getCurrentUserId();
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("사용자 없음"));

        BoardPost post = BoardPost.builder()
                .content(request.getContent())
                .imageUrl(request.getImageUrl())
                .user(user)
                .build();

        boardPostRepository.save(post);
        return BoardPostResponse.fromEntity(post);
    }

    @Override
    public BoardPostResponse getPost(Long id) {
        BoardPost post = boardPostRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("게시글 없음"));
        return BoardPostResponse.fromEntity(post);
    }

    @Override
    public List<BoardPostResponse> getAllPosts() {
        return boardPostRepository.findAll().stream()
                .map(BoardPostResponse::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public void deletePost(Long id) {
        BoardPost post = boardPostRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("게시글 없음"));
        boardPostRepository.delete(post);
    }

    @Override
    public BoardPostResponse updatePost(Long id, UpdateBoardPostRequest request) {
        BoardPost post = boardPostRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("게시글 없음"));

        post.update(request.getTitle(), request.getContent(), request.getImageUrl());
        return BoardPostResponse.fromEntity(post);
    }

    @Override
    public void likePost(Long postId) {
        Long userId = SecurityUtil.getCurrentUserId();
        BoardPost post = boardPostRepository.findById(postId)
                .orElseThrow(() -> new EntityNotFoundException("게시글 없음"));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("사용자 없음"));

        post.like(user);
    }

    @Override
    public void unlikePost(Long postId) {
        Long userId = SecurityUtil.getCurrentUserId();
        BoardPost post = boardPostRepository.findById(postId)
                .orElseThrow(() -> new EntityNotFoundException("게시글 없음"));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("사용자 없음"));

        post.unlike(user);
    }

    @Override
    public boolean isLiked(Long postId) {
        Long userId = SecurityUtil.getCurrentUserId();
        BoardPost post = boardPostRepository.findById(postId)
                .orElseThrow(() -> new EntityNotFoundException("게시글 없음"));
        return post.isLikedBy(userId);
    }
}
