package com.workout.heavylift.dto.workoutoutine;


import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class RoutineExerciseDto {

    private Long exerciseId;

    private int sets;
    private int reps;
    private double weight;

}
