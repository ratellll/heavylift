package com.workout.heavylift.service;

import com.workout.heavylift.dto.workoutoutine.WorkoutRoutineResponse;

import java.util.List;

public interface RoutineSearchService {

        List<WorkoutRoutineResponse> searchByTitle(String keyword);

        List<WorkoutRoutineResponse> filterByMuscleGroup(String muscleGroup);

        List<WorkoutRoutineResponse> searchFavoritesByKeyword(Long userId, String keyword);
    }
