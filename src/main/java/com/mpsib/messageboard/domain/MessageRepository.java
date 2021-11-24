package com.mpsib.messageboard.domain;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface MessageRepository extends CrudRepository<Message, Long>{
	List<Message> findByTopicId(Long topicId);
	List<Message> findByUser(Long userId);

}
