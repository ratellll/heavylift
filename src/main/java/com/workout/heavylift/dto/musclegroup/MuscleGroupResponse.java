package com.workout.heavylift.dto.musclegroup;

import com.workout.heavylift.domain.MuscleGroup;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MuscleGroupResponse {
    private Long id;
    private String name;

    public static MuscleGroupResponse fromEntity(MuscleGroup group) {
        return MuscleGroupResponse.builder()
                .id(group.getId())
                .name(group.getName())
                .build();
    }
}