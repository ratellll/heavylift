package com.workout.heavylift.dto.user;

import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UpdateUserRequest {

    @Size(min = 2, max = 20, message = "이름은 2자 이상 20자 이하로 입력해주세요.")
    private String name;

    @Size(min = 6, max = 100, message = "비밀번호는 6자 이상이어야 합니다.")
    private String password;
}
