package com.workout.heavylift.repository;

import com.workout.heavylift.domain.WorkoutLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
@Repository
public interface WorkoutLogRepository extends JpaRepository<WorkoutLog, Long> {

    List<WorkoutLog> findByUserId(Long userId);

    List<WorkoutLog> findByUserIdAndWorkoutDateTimeBetween(Long userId, LocalDateTime start, LocalDateTime end);

    List<WorkoutLog> findByUserIdAndWorkoutDateTime(Long userId, LocalDateTime date);
}
