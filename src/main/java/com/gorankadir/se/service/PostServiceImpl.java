package com.gorankadir.se.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.gorankadir.se.model.Post;
import com.gorankadir.se.repository.PostRepository;

@Service
@Primary
public class PostServiceImpl implements PostService {
	
	@Autowired
	PostRepository postRepository;

	@Override
	public List<Post> findAllPost() {
		return postRepository.findAll();
	}
	
	@Override
	public List<Post> findLatest5() {
		return postRepository.findAll();
	}

	@Override
	public Post findById(Long id) {
		return postRepository.findOne(id);
	}

	@Override
	public Post createPost(Post post) {
		return postRepository.save(post);
	}

	@Override
	public Post editPost(Post post) {
		return postRepository.save(post);
	}

	@Override
	public void deleteById(Long id) {
		postRepository.delete(id);
		
	}

}
