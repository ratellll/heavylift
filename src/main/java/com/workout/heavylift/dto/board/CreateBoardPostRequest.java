package com.workout.heavylift.dto.board;

import com.workout.heavylift.domain.BoardPost;
import com.workout.heavylift.domain.User;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CreateBoardPostRequest {

    private Long userId;

    @NotBlank(message = "제목은 필수입니다.")
    @Size(max = 20, message = "제목은 20자 이하로 작성해주세요.")
    private String title;

    @NotBlank(message = "내용은 필수입니다.")
    private String content;

    private String imageUrl;


    public BoardPost toEntity(User user) {
        return BoardPost.builder()
                .user(user)
                .title(this.title)
                .content(this.content)
                .imageUrl(this.imageUrl)
                .build();
    }
}
