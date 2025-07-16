package com.workout.heavylift.repository;

import com.workout.heavylift.domain.FavoriteRoutine;
import com.workout.heavylift.domain.User;
import com.workout.heavylift.domain.WorkoutRoutine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface FavoriteRoutineRepository extends JpaRepository<FavoriteRoutine, Long> {

    Optional<FavoriteRoutine> findByUserAndRoutine(User user, WorkoutRoutine routine);

    List<FavoriteRoutine> findByUser(User user);

    boolean existsByUserAndRoutine(User user, WorkoutRoutine routine);

    void deleteByUserAndRoutine(User user, WorkoutRoutine routine);
}
