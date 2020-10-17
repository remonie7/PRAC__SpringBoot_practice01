package net.webprac1.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

	
	@GetMapping("/")
	public String home() {
		return "navigation";
	}
	
	@GetMapping("/test")
	public String homeTest() {
		return "test";
	}

}
