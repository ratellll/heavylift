package com.workout.heavylift.dto.exercise;

import com.workout.heavylift.domain.Exercise;
import com.workout.heavylift.domain.MuscleGroup;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Builder
public class ExerciseResponse {
    private Long id;
    private String name;
    private List<String> muscleGroups;

    public static ExerciseResponse fromEntity(Exercise exercise) {
        return ExerciseResponse.builder()
                .id(exercise.getId())
                .name(exercise.getName())
                .muscleGroups(
                        exercise.getMuscleGroups().stream()
                                .map(MuscleGroup::getName)
                                .collect(Collectors.toList())
                )
                .build();
    }
}