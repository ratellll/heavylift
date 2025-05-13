package com.workout.heavylift.impl;


import com.workout.heavylift.config.SecurityUtil;
import com.workout.heavylift.domain.FavoriteRoutine;
import com.workout.heavylift.domain.RoutineExercise;
import com.workout.heavylift.domain.User;
import com.workout.heavylift.domain.WorkoutRoutine;
import com.workout.heavylift.dto.workoutoutine.WorkoutRoutineResponse;
import com.workout.heavylift.repository.FavoriteRoutineRepository;
import com.workout.heavylift.repository.UserRepository;
import com.workout.heavylift.repository.WorkoutRoutineRepository;
import com.workout.heavylift.service.FavoriteRoutineService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class FavoriteRoutineServiceImpl implements FavoriteRoutineService {

    private final FavoriteRoutineRepository favoriteRoutineRepository;
    private final UserRepository userRepository;
    private final WorkoutRoutineRepository workoutRoutineRepository;

    @Override
    public void addFavorite(Long routineId) {
        Long userId = SecurityUtil.getCurrentUserId();
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("유저를 찾을수없습니다"));

        WorkoutRoutine routine = workoutRoutineRepository.findById(routineId)
                .orElseThrow(() -> new EntityNotFoundException("루틴이 존재하지않습니다"));

        if (favoriteRoutineRepository.existsByUserAndRoutine(user, routine)) {
            return;
        }
        favoriteRoutineRepository.save(FavoriteRoutine.builder()
                .user(user)
                .routine(routine)
                .build());
    }

    @Override
    public void removeFavorite(Long routineId) {
        Long userId = SecurityUtil.getCurrentUserId();
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("유저를 찾을수없습니다"));

        WorkoutRoutine routine = workoutRoutineRepository.findById(routineId)
                .orElseThrow(() -> new EntityNotFoundException("루틴이 존재하지않습니다"));

        favoriteRoutineRepository.deleteByUserAndRoutine(user, routine);
    }

    @Override
    public List<WorkoutRoutineResponse> getMyFavoriteRoutines() {
        Long userId = SecurityUtil.getCurrentUserId();
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("유저를 찾을수없습니다"));

        return favoriteRoutineRepository.findByUser(user).stream()
                .map(fav -> WorkoutRoutineResponse.fromEntity(fav.getRoutine()))
                .collect(Collectors.toList());
    }

    @Override
    public boolean isFavorite(Long routineId) {
        Long userId = SecurityUtil.getCurrentUserId();
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("유저를 찾을수없습니다"));

        WorkoutRoutine routine = workoutRoutineRepository.findById(routineId)
                .orElseThrow(() -> new EntityNotFoundException("루틴이 존재하지않습니다"));

        return favoriteRoutineRepository.existsByUserAndRoutine(user, routine);
    }

    public WorkoutRoutineResponse copyRoutine(Long routineId) {
        Long userId = SecurityUtil.getCurrentUserId();
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("유저를 찾을수없습니다"));

        WorkoutRoutine original = workoutRoutineRepository.findById(routineId)
                .orElseThrow(() -> new EntityNotFoundException("루틴이 존재하지않습니다"));

        WorkoutRoutine copy = WorkoutRoutine.builder()
                .user(user)
                .title(original.getTitle() + " (복사본)")
                .description(original.getDescription())
                .build();

        workoutRoutineRepository.save(copy);

        List<RoutineExercise> copyExercises = original.getExercises().stream()
                .map(e -> RoutineExercise.builder()
                        .routine(copy)
                        .exercise(e.getExercise())
                        .sets(e.getSets())
                        .reps(e.getReps())
                        .weight(e.getWeight())
                        .build())
                .toList();

        routineExerciseRepository.saveAll(copyExercises);

        copy.getExercises().addAll(copyExercises);

        return WorkoutRoutineResponse.from(copy);
    }
}
