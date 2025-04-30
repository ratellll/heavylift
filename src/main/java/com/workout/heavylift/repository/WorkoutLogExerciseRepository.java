package com.workout.heavylift.repository;

import com.workout.heavylift.domain.WorkoutLogExercise;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkoutLogExerciseRepository extends JpaRepository<WorkoutLogExercise, Long> {
}
