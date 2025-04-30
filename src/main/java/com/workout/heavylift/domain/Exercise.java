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
public class Exercise {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @ManyToMany
    @JoinTable(
            name = "exercise_muscle_group",
            joinColumns = @JoinColumn(name = "exercise_id"),
            inverseJoinColumns = @JoinColumn(name = "muscle_group_id")
    )
    private final List<MuscleGroup> muscleGroups = new ArrayList<>();

    @Builder
    public Exercise(String name) {
        this.name = name;
    }

    public void addMuscleGroup(MuscleGroup muscleGroup) {
        this.muscleGroups.add(muscleGroup);
    }
}

