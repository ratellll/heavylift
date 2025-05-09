package com.workout.heavylift.repository;

import com.workout.heavylift.domain.RoutineExercise;
import com.workout.heavylift.domain.WorkoutRoutine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface RoutineExerciseRepository extends JpaRepository<RoutineExercise, Long> {


    @Modifying
    @Query("DELETE FROM RoutineExercise re WHERE re.routine = :routine")
    void deleteByRoutine(@Param("routine") WorkoutRoutine routine);
}
