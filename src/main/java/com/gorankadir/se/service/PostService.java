package com.gorankadir.se.service;

import java.util.List;

import com.gorankadir.se.model.Post;

public interface PostService {

	List<Post> findAllPost();

	List<Post> findLatest5();

	Post findById(Long id);

	Post createPost(Post post);

	Post editPost(Post post);

	void deleteById(Long id);
}
