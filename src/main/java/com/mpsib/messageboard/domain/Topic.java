package com.mpsib.messageboard.domain;

import java.sql.Timestamp;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Topic {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String topicTitle;
	private Timestamp timestamp;
	@JsonIgnore
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "topic")
	private List<Message> messages;
	
	public Topic () {}

	public Topic(String topicTitle, Timestamp timestamp) {
		super();
		this.topicTitle = topicTitle;
		this.timestamp = timestamp;
	}

	public Topic(Long id, String topicTitle, Timestamp timestamp) {
		super();
		this.id = id;
		this.topicTitle = topicTitle;
		this.timestamp = timestamp;
	}

	public Topic(Long id, String topicTitle, Timestamp timestamp, List<Message> messages) {
		super();
		this.id = id;
		this.topicTitle = topicTitle;
		this.timestamp = timestamp;
		this.messages = messages;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTopicTitle() {
		return topicTitle;
	}

	public void setTopicTitle(String topicTitle) {
		this.topicTitle = topicTitle;
	}

	public Timestamp getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Timestamp timestamp) {
		this.timestamp = timestamp;
	}

	public List<Message> getMessages() {
		return messages;
	}

	public void setMessages(List<Message> messages) {
		this.messages = messages;
	}
	
	
}
