package com.workout.heavylift.dto.user;


import com.workout.heavylift.domain.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CreateUserRequest {


    @NotBlank(message = "이메일은 필수입니다.")
    @Email(message = "유효한 이메일 형식이어야 합니다.")
    private String email;

    @NotBlank(message = "이름은 필수입니다.")
    @Size(min = 2, max = 20, message = "이름은 2자 이상 20자 이하로 입력해주세요.")
    private String name;

    @NotBlank(message = "비밀번호는 필수입니다.")
    @Size(min = 6, max = 100, message = "비밀번호는 6자 이상이어야 합니다.")
    private String password;

    public User toEntity() {
        return User.builder()
                .email(this.email)
                .name(this.name)
                .password(this.password)
                .build();
    }
}
