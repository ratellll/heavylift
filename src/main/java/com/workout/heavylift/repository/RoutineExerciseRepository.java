package com.workout.heavylift.repository;

import com.workout.heavylift.domain.RoutineExercise;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoutineExerciseRepository extends JpaRepository<RoutineExercise, Long> {
}
