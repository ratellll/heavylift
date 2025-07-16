package com.workout.heavylift.dto.board;


import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class UpdateCommentRequest {


    @NotBlank(message = "댓글 내용은 필수입니다.")
    private String content;
}
