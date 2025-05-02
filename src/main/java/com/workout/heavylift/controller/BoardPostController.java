package com.workout.heavylift.controller;


import com.workout.heavylift.domain.BoardPost;
import com.workout.heavylift.domain.User;
import com.workout.heavylift.dto.board.BoardPostResponse;
import com.workout.heavylift.dto.board.CreateBoardPostRequest;
import com.workout.heavylift.repository.BoardPostRepository;
import com.workout.heavylift.repository.UserRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class BoardPostController {

    private final BoardPostRepository boardPostRepository;
    private final UserRepository userRepository;

    @PostMapping
    public ResponseEntity<BoardPostResponse> createPost(@Valid @RequestBody CreateBoardPostRequest request) {
        User user = userRepository.findById(request.getUserId()).orElseThrow(() -> new IllegalArgumentException("로그인을 확인해주십시오"));

        BoardPost post = boardPostRepository.save(request.toEntity(user));
        return ResponseEntity.ok(BoardPostResponse.fromEntity(post));
    }

    @GetMapping
    public ResponseEntity<List<BoardPostResponse>> getAllPosts() {
        List<BoardPost> posts = boardPostRepository.findAll();
        List<BoardPostResponse> responses = posts.stream()
                .map(BoardPostResponse::fromEntity)
                .collect(Collectors.toList());
        return ResponseEntity.ok(responses);
    }

}
