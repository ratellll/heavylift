package com.workout.heavylift.dto.musclegroup;

import com.workout.heavylift.domain.MuscleGroup;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CreateMuscleGroupRequest {

    @NotBlank(message = "근육 그룹명은 필수입니다.")
    @Size(max = 5, message = "근육 그룹명은 5자 이내로 작성해주세요.")
    private String name;

    public MuscleGroup toEntity() {
        return MuscleGroup.builder()
                .name(this.name)
                .build();
    }
}
