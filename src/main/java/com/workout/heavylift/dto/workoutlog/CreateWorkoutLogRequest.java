package com.workout.heavylift.dto.workoutlog;

import com.workout.heavylift.domain.User;
import com.workout.heavylift.domain.WorkoutLog;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
public class CreateWorkoutLogRequest {

    @NotNull(message = "회원 ID는 필수입니다.")
    private Long userId;

    private LocalDateTime workoutDateTime = LocalDateTime.now();

    private int durationMinutes;

    @Valid
    private List<WorkoutLogExerciseDto> logExercises;

    private double caloriesBurned;

    public WorkoutLog toEntity(User user) {
        return WorkoutLog.builder()
                .user(user)
                .workoutDateTime(LocalDateTime.now())
                .durationMinutes(this.durationMinutes)
                .build();
    }
}