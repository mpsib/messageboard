package com.mpsib.messageboard.web;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mpsib.messageboard.domain.Message;
import com.mpsib.messageboard.domain.MessageRepository;
import com.mpsib.messageboard.domain.Topic;
import com.mpsib.messageboard.domain.TopicRepository;
import com.mpsib.messageboard.domain.UserRepository;

@Controller
public class MessageBoardController {
	@Autowired
	private MessageRepository messageRepository;
	@Autowired
	private TopicRepository topicRepository;
	@Autowired
	private UserRepository userRepository;
	
	@RequestMapping(value="/login")
	public String login() {
		return "login";
	}
	
	//REST
	@GetMapping("/topics")
	public @ResponseBody List<Topic> restListTopics(){
		return (List<Topic>)topicRepository.findAll();
	}
	
	/*
	@GetMapping("/topics/{id}/messages")
	public @ResponseBody List<Message> restListMessagesByTopicId(@PathVariable("id") Long id){
		return (List<Message>)messageRepository.findByTopic(id);
	}*/
	
	//TODO messages by user
	
	
	//Template views
	@GetMapping("/")
	public String listTopicsPage(Model model) {
		model.addAttribute("topics", topicRepository.findAll());
		return "topicslist";
	}
	
	@GetMapping("/topic/{id}")
	public String listMessagesOfTopic(@PathVariable("id") Long topicId, Model model) {
		model.addAttribute("messages", messageRepository.findByTopicId(topicId));
		model.addAttribute("id", topicId);
		return "topic";
	}
	
	@PostMapping("/topic/{id}")
	public String sendMessage(String messageText, Long id) {
		Message message = new Message();
		message.setMessageContent(messageText);
		message.setTopic(topicRepository.findById(id).get());
		UserDetails ud = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		message.setUser(userRepository.findByUsername(ud.getUsername()));
		messageRepository.save(message);
		return "redirect:/topic/{id}";
	}
	
	
	@GetMapping("/newtopic")
	public String newTopicPage(Model model) {
		model.addAttribute("topic", new Topic());
		return "newtopic";
	}
	
	@PostMapping("/newtopic")
	public String addNewTopic(Topic topic) {
		topic.setTimestamp(new Timestamp(System.currentTimeMillis()));
		topicRepository.save(topic);
		return "redirect:/";
	}
}
