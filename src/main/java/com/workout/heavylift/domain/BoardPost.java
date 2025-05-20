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

    @ManyToMany
    @JoinTable(name = "boardpost_likes",
            joinColumns = @JoinColumn(name = "post_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    private List<User> likedUsers = new ArrayList<>();

    @Builder
    public BoardPost(User user, String content, String imageUrl ) {
        this.user = user;
        this.content = content;
        this.imageUrl = imageUrl;
        this.likeCount = 0;
    }

    public void update(String content, String imageUrl) {
        this.content = content;
        this.imageUrl = imageUrl;
    }
    public void incrementLike() {
        this.likeCount++;
    }

    public void decrementLike() {
        this.likeCount--;
    }

    public void like(User user) {
        if (!likedUsers.contains(user)) {
            likedUsers.add(user);
            incrementLike();
        }
    }

    public void unlike(User user) {
        if (likedUsers.remove(user)) {
            decrementLike();
        }
    }

    public boolean isLikedBy(Long userId) {
        return likedUsers.stream().anyMatch(user -> user.getId().equals(userId));
    }
}
