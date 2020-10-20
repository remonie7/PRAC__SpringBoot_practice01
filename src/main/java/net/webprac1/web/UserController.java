package net.webprac1.web;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import net.webprac1.domain.User;
import net.webprac1.domain.UserRepository;

@Controller
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	private UserRepository userRepository;
	
	@GetMapping("/joinForm")
	public String joinForm() {
		return "user/joinForm";
	}
	
	@PostMapping("/join")
	public String userJoin(User user) {
		System.out.println(user.toString());
		userRepository.save(user);
		return "redirect:/";
	}
	
	@GetMapping("/loginForm")
	public String loginForm() {
		return "user/loginForm";
	}
	
	@PostMapping("/login")
	public String userLogin(String userId, String userPassword, HttpSession session) {
		User user = userRepository.findByUserId(userId);
		
		if(user==null) {
			System.out.println("로그인 실패");
			return "redirect:/user/loginForm";
		}
		if(!userPassword.equals(user.getUserPassword())) {
			System.out.println("로그인 실패");
			return "redirect:/user/loginForm";
		}
		System.out.println("로그인 성공");
		session.setAttribute("user", user);
		return "redirect:/";
	}
}
