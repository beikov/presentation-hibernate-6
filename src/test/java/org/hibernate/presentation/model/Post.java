package org.hibernate.presentation.model;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import java.util.Set;

@Entity
@Table( name = "posts" )
public class Post {
	@Id
	private Integer id;
	String title;
	@ManyToOne
	Forum forum;
	@ManyToOne
	@JoinColumn(name = "forum_id", insertable = false, updatable = false)
	Forum forum2;
	@Enumerated(EnumType.STRING)
//	@Convert(converter = MyConverter.class)
	Set<TopicTag> topicTags;

	protected Post() {
	}

	public Post(Integer id, String title, Forum forum) {
		this.id = id;
		this.title = title;
		this.forum = forum;
	}

	public Integer getId() {
		return id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Forum getForum() {
		return forum;
	}

	public void setForum(Forum forum) {
		this.forum = forum;
	}

	public Set<TopicTag> getTopicTags() {
		return topicTags;
	}

	public void setTopicTags(Set<TopicTag> topicTags) {
		this.topicTags = topicTags;
	}
}
