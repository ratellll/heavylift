package com.workout.heavylift.dto.board;

import com.workout.heavylift.domain.BoardPost;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class BoardPostResponse {

    private Long id;
    private String title;
    private String content;
    private String imageUrl;
    private int likeCount;
    private String author;

    public static BoardPostResponse fromEntity(BoardPost post) {
        return BoardPostResponse.builder()
                .id(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .imageUrl(post.getImageUrl())
                .likeCount(post.getLikeCount())
                .author(post.getUser().getName())
                .build();
    }
}