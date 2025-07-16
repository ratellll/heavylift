package com.workout.heavylift.dto.exercise;

import com.workout.heavylift.domain.Exercise;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class ExerciseResponse {
    private Long id;
    private String name;
    private List<String> muscleGroups;

    public static ExerciseResponse fromEntity(Exercise exercise) {
        return new ExerciseResponse(
                exercise.getId(),
                exercise.getName(),
                new ArrayList<>(exercise.getMuscleGroups())
        );
    }
}