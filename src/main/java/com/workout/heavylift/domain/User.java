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
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String nickName;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private final List<WorkoutRoutine> workoutRoutines = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private final List<WorkoutLog> workoutLogs = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<BoardPost> postList = new ArrayList<>();

    @Builder
    public User(String email, String name,String nickName, String password) {
        this.email = email;
        this.name = name;
        this.nickName = nickName;
        this.password = password;
    }

    public void changeUserName(String name) {
        this.name = name;
    }

    public void changeUserNickName(String nickName) {
        this.nickName = nickName;
    }

    public void changeUserEmail(String email) {
        this.email = email;
    }

    public void changeUserPassword(String password) {
        this.password = password;
    }

}
