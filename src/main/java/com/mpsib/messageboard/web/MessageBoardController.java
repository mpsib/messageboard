package com.mpsib.messageboard.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
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
	
	
	@GetMapping("/topics/{id}")
	public @ResponseBody List<Message> restListMessagesByTopicId(@PathVariable("id") Long topicId){
		return (List<Message>)messageRepository.findByTopicId(topicId);
	}
	
	
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
	public String addNewTopic(String topicName) {
		Topic topic = new Topic();
		topic.setTopicTitle(topicName);
		UserDetails ud = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		topic.setUser(userRepository.findByUsername(ud.getUsername()));
		topicRepository.save(topic);
		return "redirect:/";
	}
	
	@GetMapping("/topic/edit/{id}")
	public String editTopic(@PathVariable("id") Long topicId, Model model) {
		Topic topic = topicRepository.findById(topicId).get();
		model.addAttribute("topicTitle", topic.getTopicTitle());
		model.addAttribute("id", topicId);
		
		return "editTopic";
	}
	
	@PostMapping("/topic/edit/{id}")
	public String editTopicSave(@PathVariable("id") Long topicId, String topicTitle) {
		Topic topic = topicRepository.findById(topicId).get();
		topic.setTopicTitle(topicTitle);
		topicRepository.save(topic);
		return "redirect:/";
	}
	
	@PreAuthorize("hasAuthority('ADMIN')")
	@RequestMapping("/topic/delete/{id}")
	public String deleteTopic(@PathVariable("id") Long topicId, Model model) {
		topicRepository.deleteById(topicId);
		return "redirect:/";
	}
	
	@PreAuthorize("hasAuthority('ADMIN')")
	@RequestMapping("/topic/{id}/delete/{messageId}")
	public String deleteMessage(@PathVariable("id") Long topicId, @PathVariable("messageId") Long messageId, Model model) {
		messageRepository.deleteById(messageId);
		String url = "redirect:/topic/"+topicId;
		return url;
	}
}
