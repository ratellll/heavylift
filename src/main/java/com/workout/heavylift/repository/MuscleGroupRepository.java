package com.workout.heavylift.repository;

import com.workout.heavylift.domain.MuscleGroup;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MuscleGroupRepository extends JpaRepository<MuscleGroup, Long> {
}
