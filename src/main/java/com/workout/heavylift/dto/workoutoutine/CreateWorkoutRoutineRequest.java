package com.workout.heavylift.dto.workoutoutine;

import com.workout.heavylift.domain.WorkoutRoutine;
import com.workout.heavylift.domain.User;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class CreateWorkoutRoutineRequest {


    @NotBlank(message = "루틴 제목은 필수입니다.")
    @Size(max = 10, message = "제목은 10자 이내로 작성해주세요.")
    private String title;

    @Size(max = 20, message = "설명은 20자 이내로 작성해주세요.")
    private String description;

    private boolean shared;


    private List<RoutineExerciseDto> exercises;

    public WorkoutRoutine toEntity(User user) {
        return WorkoutRoutine.builder()
                .user(user)
                .title(this.title)
                .description(this.description)
                .shared(this.shared)
                .exercises(null)
                .build();
    }
}