package com.workout.heavylift.service;

import com.workout.heavylift.dto.board.BoardPostResponse;
import com.workout.heavylift.dto.board.CreateBoardPostRequest;
import com.workout.heavylift.dto.board.UpdateBoardPostRequest;

import java.util.List;

public interface BoardPostService {

    BoardPostResponse create(CreateBoardPostRequest request);

    BoardPostResponse getPost(Long id);

    List<BoardPostResponse> getAllPosts();

    void deletePost(Long id);

    BoardPostResponse updatePost(Long id, UpdateBoardPostRequest request);

    void likePost(Long postId);

    void unlikePost(Long postId);

    boolean isLiked(Long postId);

    void validateAuthor(Long postId, Long userId);
}
