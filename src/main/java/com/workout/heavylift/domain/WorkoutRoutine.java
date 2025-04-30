package com.workout.heavylift.domain;


import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class WorkoutRoutine {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(nullable = false)
    private String title;

    private String description;

    private boolean shared;

    @OneToMany(mappedBy = "routine", cascade = CascadeType.ALL, orphanRemoval = true)
    private final List<RoutineExercise> exercises = new ArrayList<>();

    @Builder
    public WorkoutRoutine(User user, String title, String description, boolean shared, List<RoutineExercise> exercises) {
        this.user = user;
        this.title = title;
        this.description = description;
        this.shared = shared;
        if (exercises != null) {
            this.exercises.addAll(exercises);
        }
    }

    public void changeRoutineInfo(String title, String description, boolean shared) {
        this.title = title;
        this.description = description;
        this.shared = shared;
    }
}
