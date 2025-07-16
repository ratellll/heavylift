package com.workout.heavylift.dto.board;

import com.workout.heavylift.domain.BoardPost;
import com.workout.heavylift.domain.Comment;
import com.workout.heavylift.domain.User;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateCommentRequest {

    private Long postId;

    @NotNull(message = "작성자 ID는 필수입니다.")
    private Long userId;

    @NotBlank(message = "댓글 내용은 필수입니다.")
    @Size(max = 30, message = "댓글은 30자 이하로 작성해주세요.")
    private String content;

    public Comment toEntity(BoardPost post, User user) {
        return Comment.builder()
                .post(post)
                .user(user)
                .content(this.content)
                .build();
    }
}