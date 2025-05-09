package com.workout.heavylift.repository;

import com.workout.heavylift.domain.WorkoutRoutine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Repository
public interface WorkoutRoutineRepository extends JpaRepository<WorkoutRoutine, Long> {

    @Query("SELECT r FROM WorkoutRoutine r WHERE r.shared = true")
    List<WorkoutRoutine> findAllSharedWithExercises();

    @Query("SELECT r FROM WorkoutRoutine r JOIN r.favoritedBy u WHERE u.id = :userId")
    List<WorkoutRoutine> findFavoritesByUserId(@Param("userId") Long userId);

    @Modifying
    @Query(value = "INSERT INTO routine_favorites (user_id, routine_id) VALUES (:userId, :routineId) ON CONFLICT DO NOTHING", nativeQuery = true)
    void addFavorite(@Param("userId") Long userId, @Param("routineId") Long routineId);
}
