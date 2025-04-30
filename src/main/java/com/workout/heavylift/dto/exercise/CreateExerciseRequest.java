package com.workout.heavylift.dto.exercise;

import com.workout.heavylift.domain.Exercise;
import com.workout.heavylift.domain.MuscleGroup;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class CreateExerciseRequest {

    @NotBlank(message = "운동 이름은 필수입니다.")
    @Size(max = 10, message = "운동 이름은 10자 이내로 작성해주세요.")
    private String name;

    @NotEmpty(message = "근육 그룹은 1개 이상 선택해야 합니다.")
    private List<Long> muscleGroupIds;

    public Exercise toEntity(List<MuscleGroup> muscleGroups) {
        Exercise exercise = Exercise.builder()
                .name(this.name)
                .build();
        muscleGroups.forEach(exercise::addMuscleGroup);
        return exercise;
    }
}