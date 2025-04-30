package com.workout.heavylift.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RoutineExercise {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "routine_id")
    private WorkoutRoutine routine;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "exercise_id")
    private Exercise exercise;

    private int sets;
    private int reps;
    private double weight;

    @Builder
    public RoutineExercise(WorkoutRoutine routine, Exercise exercise, int sets, int reps, double weight) {
        this.routine = routine;
        this.exercise = exercise;
        this.sets = sets;
        this.reps = reps;
        this.weight = weight;
    }

    public void updateExerciseInfo(int sets, int reps, double weight) {
        this.sets = sets;
        this.reps = reps;
        this.weight = weight;
    }
}
