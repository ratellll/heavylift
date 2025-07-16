package com.workout.heavylift.repository;

import com.workout.heavylift.domain.WorkoutLog;
import com.workout.heavylift.domain.WorkoutLogExercise;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface WorkoutLogExerciseRepository extends JpaRepository<WorkoutLogExercise, Long> {
    List<WorkoutLogExercise> findByWorkoutLog(WorkoutLog workoutLog);

}
