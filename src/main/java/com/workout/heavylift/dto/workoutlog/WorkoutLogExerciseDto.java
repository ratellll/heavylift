package com.workout.heavylift.dto.workoutlog;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class WorkoutLogExerciseDto {

    private Long exerciseId;

    private int sets;
    private int reps;
    private double weight;
}