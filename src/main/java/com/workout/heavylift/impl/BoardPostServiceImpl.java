package com.workout.heavylift.impl;

import com.workout.heavylift.config.SecurityUtil;
import com.workout.heavylift.domain.BoardPost;
import com.workout.heavylift.domain.User;
import com.workout.heavylift.dto.board.BoardPostResponse;
import com.workout.heavylift.dto.board.CreateBoardPostRequest;
import com.workout.heavylift.repository.BoardPostRepository;
import com.workout.heavylift.repository.UserRepository;
import com.workout.heavylift.service.BoardPostService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

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
                .orElseThrow(() -> new EntityNotFoundException("유저를 찾을 수 없습니다"));

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
        return null;
    }

    @Override
    public List<BoardPostResponse> getAllPosts() {
        return List.of();
    }

    @Override
    public void deletePost(Long id) {

    }

    @Override
    public void postLike(Long postId) {

    }

    @Override
    public void postUnLike(Long postId) {

    }

    @Override
    public void isLiked(Long postId) {

    }
}
