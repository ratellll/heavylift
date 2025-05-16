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
public class BoardPost {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Lob
    private String content;

    private String imageUrl;

    private int likeCount;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private final List<Comment> comments = new ArrayList<>();


    @Builder
    public BoardPost(User user, String content, String imageUrl ) {
        this.user = user;
        this.content = content;
        this.imageUrl = imageUrl;
        this.likeCount = 0;
    }

    public void incrementLike() {
        this.likeCount++;
    }

    public void decrementLike() {
        this.likeCount--;
    }
}
