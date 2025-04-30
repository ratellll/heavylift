package com.workout.heavylift.dto.workoutlog;

import com.workout.heavylift.domain.WorkoutLog;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Builder
public class WorkoutLogResponse {
    private Long id;
    private LocalDateTime workoutDateTime;
    private int durationMinutes;
    private int caloriesBurned;
    private List<WorkoutLogExerciseDto> logExercises;

    public static WorkoutLogResponse fromEntity(WorkoutLog log) {
        return WorkoutLogResponse.builder()
                .id(log.getId())
                .workoutDateTime(LocalDateTime.now())
                .durationMinutes(log.getDurationMinutes())
                .logExercises(
                        log.getLogExercises().stream()
                                .map(e -> WorkoutLogExerciseDto.builder()
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
