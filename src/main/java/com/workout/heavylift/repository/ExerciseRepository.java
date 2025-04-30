package com.workout.heavylift.repository;

import com.workout.heavylift.domain.Exercise;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExerciseRepository extends JpaRepository<Exercise, Long> {
}
