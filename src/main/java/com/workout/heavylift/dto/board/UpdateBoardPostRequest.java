package com.workout.heavylift.dto.board;


import lombok.Getter;

@Getter
public class UpdateBoardPostRequest {

    private String title;
    private String content;
    private String imageUrl;

}
