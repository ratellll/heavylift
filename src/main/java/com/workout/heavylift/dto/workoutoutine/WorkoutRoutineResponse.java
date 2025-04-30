package com.workout.heavylift.dto.workoutoutine;

import com.workout.heavylift.domain.WorkoutRoutine;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Builder
public class WorkoutRoutineResponse {
    private Long id;
    private String title;
    private String description;
    private boolean shared;
    private List<RoutineExerciseDto> exercises;

    public static WorkoutRoutineResponse fromEntity(WorkoutRoutine routine) {
        return WorkoutRoutineResponse.builder()
                .id(routine.getId())
                .title(routine.getTitle())
                .description(routine.getDescription())
                .shared(routine.isShared())
                .exercises(
                        routine.getExercises().stream()
                                .map(e -> RoutineExerciseDto.builder()
                                        .exerciseId(e.getExercise().getId())
                                        .sets(e.getSets())
                                        .reps(e.getReps())
                                        .weight(e.getWeight())
                                        .build())
                                .collect(Collectors.toList())
                )
                .build();
    }
}
