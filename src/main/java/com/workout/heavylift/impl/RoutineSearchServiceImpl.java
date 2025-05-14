package com.workout.heavylift.impl;

import com.workout.heavylift.domain.MuscleGroup;
import com.workout.heavylift.domain.WorkoutRoutine;
import com.workout.heavylift.dto.workoutoutine.WorkoutRoutineResponse;
import com.workout.heavylift.repository.WorkoutRoutineRepository;
import com.workout.heavylift.service.RoutineSearchService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

    @Service
    @Transactional
    @RequiredArgsConstructor
    public class RoutineSearchServiceImpl implements RoutineSearchService {

        private final WorkoutRoutineRepository routineRepository;

        @Override
        public List<WorkoutRoutineResponse> searchByTitle(String keyword) {
            List<WorkoutRoutine> routines = routineRepository.findByTitleContainingIgnoreCaseAndSharedTrue(keyword);
            return routines.stream()
                    .map(WorkoutRoutineResponse::fromEntity)
                    .collect(Collectors.toList());
        }

        @Override
        public List<WorkoutRoutineResponse> filterByMuscleGroup(String muscleGroup) {
            MuscleGroup group;
            try {
                group = MuscleGroup.valueOf(muscleGroup.toUpperCase());
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException("존재하지 않는 근육 그룹입니다.");
            }
            List<WorkoutRoutine> routines = routineRepository.findByMuscleGroupAndSharedTrue(group);
            return routines.stream()
                    .map(WorkoutRoutineResponse::fromEntity)
                    .collect(Collectors.toList());
        }

        @Override
        public List<WorkoutRoutineResponse> searchFavoritesByKeyword(Long userId, String keyword) {
            List<WorkoutRoutine> routines = routineRepository.findFavoritesByUserIdAndTitleContainingIgnoreCase(userId, keyword);
            return routines.stream()
                    .map(WorkoutRoutineResponse::fromEntity)
                    .collect(Collectors.toList());
        }

}
