package com.workout.heavylift.impl;


import com.workout.heavylift.config.SecurityUtil;
import com.workout.heavylift.domain.Exercise;
import com.workout.heavylift.domain.RoutineExercise;
import com.workout.heavylift.domain.User;
import com.workout.heavylift.domain.WorkoutRoutine;
import com.workout.heavylift.dto.workoutoutine.CreateWorkoutRoutineRequest;
import com.workout.heavylift.dto.workoutoutine.RoutineExerciseDto;
import com.workout.heavylift.dto.workoutoutine.UpdateWorkoutRoutineRequest;
import com.workout.heavylift.dto.workoutoutine.WorkoutRoutineResponse;
import com.workout.heavylift.repository.ExerciseRepository;
import com.workout.heavylift.repository.RoutineExerciseRepository;
import com.workout.heavylift.repository.UserRepository;
import com.workout.heavylift.repository.WorkoutRoutineRepository;
import com.workout.heavylift.service.RoutineService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class RoutineServiceImpl implements RoutineService {

    private final WorkoutRoutineRepository routineRepository;
    private final ExerciseRepository exerciseRepository;
    private final UserRepository userRepository;
    private final RoutineExerciseRepository routineExerciseRepository;

    @Override
    public WorkoutRoutineResponse createRoutine(CreateWorkoutRoutineRequest request) {
        Long userId = SecurityUtil.getCurrentUserId();
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        WorkoutRoutine routine = WorkoutRoutine.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .shared(request.isShared())
                .user(user)
                .build();

        routineRepository.save(routine);

        List<RoutineExercise> exercises = new ArrayList<>();
        for (RoutineExerciseDto dto : request.getExercises()) {
            Exercise exercise = exerciseRepository.findById(dto.getExerciseId())
                    .orElseThrow(() -> new EntityNotFoundException("Exercise not found"));
            RoutineExercise re = RoutineExercise.builder()
                    .exercise(exercise)
                    .routine(routine)
                    .sets(dto.getSets())
                    .reps(dto.getReps())
                    .weight(dto.getWeight())
                    .build();
            exercises.add(re);
        }
        routineExerciseRepository.saveAll(exercises);
        routine.setExercises(exercises);

        return WorkoutRoutineResponse.fromEntity(routine);
    }

    @Override
    public WorkoutRoutineResponse getRoutine(Long routineId) {
        WorkoutRoutine routine = routineRepository.findById(routineId)
                .orElseThrow(() -> new EntityNotFoundException("Routine not found"));
        return WorkoutRoutineResponse.fromEntity(routine);
    }

    @Override
    public List<WorkoutRoutineResponse> getAllRoutines() {
        return routineRepository.findAllSharedWithExercises()
                .stream()
                .map(WorkoutRoutineResponse::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public List<WorkoutRoutineResponse> getFavoriteRoutines() {
        Long userId = SecurityUtil.getCurrentUserId();
        return routineRepository.findFavoritesByUserId(userId)
                .stream()
                .map(WorkoutRoutineResponse::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public WorkoutRoutineResponse updateRoutine(Long routineId, UpdateWorkoutRoutineRequest request) {
        WorkoutRoutine routine = routineRepository.findById(routineId)
                .orElseThrow(() -> new EntityNotFoundException("Routine not found"));

        routine.update(request.getTitle(), request.getDescription(), request.isShared());
        routineExerciseRepository.deleteByRoutine(routine);

        List<RoutineExercise> newExercises = new ArrayList<>();
        for (RoutineExerciseDto dto : request.getExercises()) {
            Exercise exercise = exerciseRepository.findById(dto.getExerciseId())
                    .orElseThrow(() -> new EntityNotFoundException("Exercise not found"));
            RoutineExercise re = RoutineExercise.builder()
                    .exercise(exercise)
                    .routine(routine)
                    .sets(dto.getSets())
                    .reps(dto.getReps())
                    .weight(dto.getWeight())
                    .build();
            newExercises.add(re);
        }
        routineExerciseRepository.saveAll(newExercises);
        routine.setExercises(newExercises);

        return WorkoutRoutineResponse.fromEntity(routine);
    }

    @Override
    public void deleteRoutine(Long routineId) {
        WorkoutRoutine routine = routineRepository.findById(routineId)
                .orElseThrow(() -> new EntityNotFoundException("Routine not found"));
        routineExerciseRepository.deleteByRoutine(routine);
        routineRepository.delete(routine);
    }

    @Override
    public void addToFavorites(Long routineId) {
        Long userId = SecurityUtil.getCurrentUserId();
        routineRepository.addFavorite(userId, routineId);
    }

    @Override
    public WorkoutRoutineResponse getRoutineTemplate() {
        WorkoutRoutine template = WorkoutRoutine.builder()
                .title("기본 루틴")
                .description("예시 루틴")
                .shared(false)
                .build();
        return WorkoutRoutineResponse.fromEntity(template);
    }


}
