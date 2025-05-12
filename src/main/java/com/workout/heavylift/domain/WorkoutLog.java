package com.workout.heavylift.domain;


import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class WorkoutLog {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    private LocalDateTime workoutDateTime = LocalDateTime.now();

    private int durationMinutes;
    private int caloriesBurned;

    @OneToMany(mappedBy = "workoutLog", cascade = CascadeType.ALL, orphanRemoval = true)
    private final List<WorkoutLogExercise> logExercises = new ArrayList<>();

    @Builder
    public WorkoutLog(User user, LocalDateTime workoutDateTime, int durationMinutes, int caloriesBurned) {
        this.user = user;
        this.workoutDateTime = workoutDateTime;
        this.durationMinutes = durationMinutes;
        this.caloriesBurned = caloriesBurned;
    }
    public void updateExercises(List<WorkoutLogExercise> newExercises) {
        this.logExercises.clear();
        this.logExercises.addAll(newExercises);


}
