package com.mpsib.messageboard.domain;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Message {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private Timestamp timestamp;
	private String messageContent;
	@ManyToOne
	@JoinColumn(name = "topicid")
	private Topic topic;
	@ManyToOne
	@JoinColumn(name = "userid")
	private User user;
	
	public Message() {
		this.timestamp = new Timestamp(System.currentTimeMillis());
	}

	public Message(Timestamp timestamp, String messageContent, Topic topic, User user) {
		super();
		this.timestamp = timestamp;
		this.messageContent = messageContent;
		this.topic = topic;
		this.user = user;
	}

	public Message(Long id, Timestamp timestamp, String messageContent, Topic topic, User user) {
		super();
		this.id = id;
		this.timestamp = timestamp;
		this.messageContent = messageContent;
		this.topic = topic;
		this.user = user;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Timestamp getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Timestamp timestamp) {
		this.timestamp = timestamp;
	}

	public String getMessageContent() {
		return messageContent;
	}

	public void setMessageContent(String messageContent) {
		this.messageContent = messageContent;
	}

	public Topic getTopic() {
		return topic;
	}

	public void setTopic(Topic topic) {
		this.topic = topic;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	
}
