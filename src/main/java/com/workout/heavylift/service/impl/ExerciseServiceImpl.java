package com.workout.heavylift.service.impl;

import com.workout.heavylift.dto.exercise.ExerciseResponse;
import com.workout.heavylift.repository.ExerciseRepository;
import com.workout.heavylift.service.ExerciseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ExerciseServiceImpl implements ExerciseService {


    private final ExerciseRepository exerciseRepository;


    @Override
    public List<ExerciseResponse> findAllExercises() {
        return List.of();
    }
}
