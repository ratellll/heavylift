package com.workout.heavylift.controller;


import com.workout.heavylift.dto.workoutoutine.WorkoutRoutineResponse;
import com.workout.heavylift.service.FavoriteRoutineService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/routines")
@RequiredArgsConstructor
public class WorkoutRoutineController {

    private final FavoriteRoutineService favoriteRoutineService;

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
