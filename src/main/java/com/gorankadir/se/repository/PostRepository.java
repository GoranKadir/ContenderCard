package com.gorankadir.se.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.gorankadir.se.model.Post;

public interface PostRepository extends JpaRepository<Post, Long> {
	//List<Post> findLatest5Posts(Pageable pageable);

}
