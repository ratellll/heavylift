package com.workout.heavylift.service;

import com.workout.heavylift.dto.exercise.ExerciseResponse;

import java.util.List;

public interface ExerciseService {

    List<ExerciseResponse> findAllExercises();
}
