package com.mpsib.messageboard.domain;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface MessageRepository extends CrudRepository<Message, Long>{
	List<Message> findByTopic(Long TopicId);
	List<Message> findByUser(Long UserId);

}
