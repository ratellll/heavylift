package com.workout.heavylift.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class WorkoutLogExercise {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "log_id")
    private WorkoutLog workoutLog;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "exercise_id")
    private Exercise exercise;

    private int sets;
    private int reps;
    private double weight;
    private double totalWeight;

    @Builder
    public WorkoutLogExercise(WorkoutLog workoutLog, Exercise exercise, int sets, int reps, double weight,double totalWeight) {
        this.workoutLog = workoutLog;
        this.exercise = exercise;
        this.sets = sets;
        this.reps = reps;
        this.weight = weight;
        this.totalWeight = totalWeight;
    }

    public void updateLogExercise(int sets, int reps, double weight) {
        this.sets = sets;
        this.reps = reps;
        this.weight = weight;
    }
}
