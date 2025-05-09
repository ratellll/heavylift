package com.workout.heavylift.service;

import com.workout.heavylift.dto.workoutoutine.CreateWorkoutRoutineRequest;
import com.workout.heavylift.dto.workoutoutine.UpdateWorkoutRoutineRequest;
import com.workout.heavylift.dto.workoutoutine.WorkoutRoutineResponse;

import java.util.List;

public interface RoutineService {

    WorkoutRoutineResponse createRoutine(CreateWorkoutRoutineRequest request);

    WorkoutRoutineResponse getRoutine(Long routineId);

    List<WorkoutRoutineResponse> getAllRoutines();

    List<WorkoutRoutineResponse> getFavoriteRoutines();

    WorkoutRoutineResponse updateRoutine(Long routineId, UpdateWorkoutRoutineRequest request);

    void deleteRoutine(Long routineId);

    void addToFavorites(Long routineId);

    WorkoutRoutineResponse getRoutineTemplate();
}
