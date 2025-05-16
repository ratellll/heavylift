package com.workout.heavylift.service;

import com.workout.heavylift.dto.board.BoardPostResponse;
import com.workout.heavylift.dto.board.CreateBoardPostRequest;

import java.util.List;

public interface BoardPostService {

    BoardPostResponse create(CreateBoardPostRequest request);

    BoardPostResponse getPost(Long id);

    List<BoardPostResponse> getAllPosts();

    void deletePost(Long id);

    void postLike(Long postId);
    void postUnLike(Long postId);
    void isLiked(Long postId);
}
