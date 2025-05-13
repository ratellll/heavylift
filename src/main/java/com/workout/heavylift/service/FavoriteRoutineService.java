package com.workout.heavylift.service;


import com.workout.heavylift.dto.workoutoutine.WorkoutRoutineResponse;

import java.util.List;

public interface FavoriteRoutineService {
    void addFavorite(Long routineId);

    void removeFavorite(Long routineId);

    List<WorkoutRoutineResponse> getMyFavoriteRoutines();

    boolean isFavorite(Long routineId);

}
