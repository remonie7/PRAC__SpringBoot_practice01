package net.webprac1.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

	
	@GetMapping("/")
	public String home() {
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
