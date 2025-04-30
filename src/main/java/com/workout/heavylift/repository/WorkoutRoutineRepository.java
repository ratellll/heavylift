package com.workout.heavylift.repository;

import com.workout.heavylift.domain.WorkoutRoutine;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkoutRoutineRepository extends JpaRepository<WorkoutRoutine, Long> {
}
