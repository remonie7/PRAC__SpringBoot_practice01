package net.webprac1.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import net.webprac1.domain.QuestionRepository;

@Controller
public class HomeController {
	@Autowired
	private QuestionRepository questionRepository;
	
	@GetMapping("/")
	public String home(Model model) {		
		model.addAttribute("questions", questionRepository.findAll());
		return "home";
	}
	
	@GetMapping("/boardA")
	public String boardAform() {
		return "boardAform";
	}
	
	@GetMapping("/boardB")
	public String boardBform() {
		return "boardBform";
	}
	
}
