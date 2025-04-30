package com.workout.heavylift.repository;

import com.workout.heavylift.domain.WorkoutLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkoutLogRepository extends JpaRepository<WorkoutLog, Long> {
}
