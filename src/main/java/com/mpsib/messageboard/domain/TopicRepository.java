package com.mpsib.messageboard.domain;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface TopicRepository extends CrudRepository<Topic, Long>{
	List<Topic> findByUser(Long UserId);

}
