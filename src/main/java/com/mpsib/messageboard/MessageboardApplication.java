package com.mpsib.messageboard;

import java.sql.Timestamp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.mpsib.messageboard.domain.Message;
import com.mpsib.messageboard.domain.MessageRepository;
import com.mpsib.messageboard.domain.Topic;
import com.mpsib.messageboard.domain.TopicRepository;
import com.mpsib.messageboard.domain.User;
import com.mpsib.messageboard.domain.UserRepository;

@SpringBootApplication
public class MessageboardApplication {
	private static final Logger log = LoggerFactory.getLogger(MessageboardApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(MessageboardApplication.class, args);
	}
	
	//TODO: FOR TESTING
	@Bean
	public CommandLineRunner studentDemo(
			UserRepository userRepository,
			TopicRepository topicRepository,
			MessageRepository messageRepository) {
		return (args) -> {
			//Usernames are UNIQUE, so you must deleteAll in Heroku. For example urepository.deleteAll() in your CommandLineRunner.
			userRepository.deleteAll();
			
			// user 1: u1, user
			// user 2: u2, user
			// admin:  admin, admin
			User user1 = new User("u1", "$2a$10$8RlLO1MZmOA3pgUI7iq1i.8Qld7/DrBFaNWaRe2l1f84GhNs8F0J2", "USER");
			User user2 = new User("u2", "$2a$10$8RlLO1MZmOA3pgUI7iq1i.8Qld7/DrBFaNWaRe2l1f84GhNs8F0J2", "USER");
			User admin1 = new User("admin", "$2a$10$SUx2Q1/z5Kfn60CdIw5.ouj4o.z1dvS2TRXL4z/uhTFq8BWXPUiBe", "ADMIN");
			userRepository.save(user1);
			userRepository.save(user2);
			userRepository.save(admin1);
			
			Timestamp testTime1 = new Timestamp(System.currentTimeMillis()-100000);
			Timestamp testTime2 = new Timestamp(System.currentTimeMillis()-50000);
			Timestamp testTime3 = new Timestamp(System.currentTimeMillis());
			
			Topic t1 = new Topic("Test topic 1", testTime1);
			Topic t2 = new Topic("Test topic 2", testTime2);
			t1.setUser(user1);
			t2.setUser(user2);
			topicRepository.save(t1);
			topicRepository.save(t2);
			
			Message m1 = new Message(testTime1, "user1:n ensimmäinen testiviesti", t1, user1);
			Message m2 = new Message(testTime2, "user2:n vastaus", t1, user2);
			Message m3 = new Message(testTime3, "user1:n vastaus", t1, user1);
			messageRepository.save(m1);
			messageRepository.save(m2);
			messageRepository.save(m3);
			Message m4 = new Message(testTime3, "user2:n eka viesti toiseen topiciin", t2, user2);
			messageRepository.save(m4);
			
			log.info("fetch all topics");
			for (Topic t : topicRepository.findAll()) {
				log.info(t.toString());
			}
			log.info("fetch all messages");
			for (Message m : messageRepository.findAll()) {
				log.info(m.toString());
			}
			
		};
	}
}
