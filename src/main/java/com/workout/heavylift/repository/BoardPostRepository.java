package com.workout.heavylift.repository;

import com.workout.heavylift.domain.BoardPost;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardPostRepository extends JpaRepository<BoardPost, Long> {
}
