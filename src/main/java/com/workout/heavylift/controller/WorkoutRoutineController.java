package com.workout.heavylift.controller;


import com.workout.heavylift.dto.workoutroutine.CreateWorkoutRoutineRequest;
import com.workout.heavylift.dto.workoutroutine.UpdateWorkoutRoutineRequest;
import com.workout.heavylift.dto.workoutroutine.WorkoutRoutineResponse;
import com.workout.heavylift.service.FavoriteRoutineService;
import com.workout.heavylift.service.RoutineService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/routines")
@RequiredArgsConstructor
public class WorkoutRoutineController {
    private final RoutineService routineService;
    private final FavoriteRoutineService favoriteRoutineService;


    @PostMapping
    public ResponseEntity<WorkoutRoutineResponse> createRoutine(@RequestBody @Valid CreateWorkoutRoutineRequest request) {
        return ResponseEntity.ok(routineService.createRoutine(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<WorkoutRoutineResponse> getRoutine(@PathVariable Long id) {
        return ResponseEntity.ok(routineService.getRoutine(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<WorkoutRoutineResponse> updateRoutine(@PathVariable Long id,@RequestBody @Valid UpdateWorkoutRoutineRequest request) {
        return ResponseEntity.ok(routineService.updateRoutine(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<WorkoutRoutineResponse> deleteRoutine(@PathVariable Long id) {
        routineService.deleteRoutine(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{routineId}/favorite")
    public ResponseEntity<Void> favorite(@PathVariable Long routineId) {
        favoriteRoutineService.addFavorite(routineId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{routineId}/favorite")
    public ResponseEntity<Void> unfavorite(@PathVariable Long routineId) {
        favoriteRoutineService.removeFavorite(routineId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/favorites")
    public ResponseEntity<List<WorkoutRoutineResponse>> getFavorites() {
        return ResponseEntity.ok(favoriteRoutineService.getMyFavoriteRoutines());
    }

    @GetMapping("/{routineId}/favorite")
    public ResponseEntity<Boolean> checkFavorite(@PathVariable Long routineId) {
        return ResponseEntity.ok(favoriteRoutineService.isFavorite(routineId));
    }

}
