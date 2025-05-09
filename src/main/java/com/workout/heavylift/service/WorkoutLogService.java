package com.workout.heavylift.service;

import com.workout.heavylift.dto.workoutlog.CreateWorkoutLogRequest;
import com.workout.heavylift.dto.workoutlog.WorkoutLogResponse;

import java.time.LocalDate;
import java.util.List;

public interface WorkoutLogService {


    WorkoutLogResponse createLog(CreateWorkoutLogRequest request);

    List<WorkoutLogResponse> getLogsByDate(LocalDate date);

    WorkoutLogResponse getLogById(Long logId);

    void deleteLog(Long logId);

    List<WorkoutLogResponse> getWeeklyStats();

    List<WorkoutLogResponse> getMonthlyStats();
}
