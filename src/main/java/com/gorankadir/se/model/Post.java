package com.gorankadir.se.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonView;
import com.gorankadir.se.controller.PostRestController;

@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
@Table(name = "post")
public class Post {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "postid")
	private long postid;

	@Column(name = "title")
	private String title;
	
	@Column(name = "body")
	private String body;
	
	@ManyToOne
	@JoinColumn(name = "authorid", referencedColumnName = "userid")
	@JsonView(PostRestController.class)
	private Fighter author;
	
	private Date date = new Date();

//	@Transient
//	int postSizeNumber = 0;
//	public int postSize(){
//		return postSizeNumber = author.getPosts().size();
//	}

	public Post(){}

	public Post(long postid, String title, String body, Fighter author, Date date) {
		this.postid = postid;
		this.title = title;
		this.body = body;
		this.author = author;
		this.date = date;
	}

	public long getPostid() {
		return postid;
	}

	public void setPostid(long postid) {
		this.postid = postid;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public Fighter getAuthor() {
		return author;
	}

	public void setAuthor(Fighter author) {
		this.author = author;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
	
}
