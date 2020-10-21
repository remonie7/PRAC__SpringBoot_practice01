package net.webprac1.web;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
		if(userRepository.findByUserId(user.getUserId())!=null) {
			System.out.println("아이디 중복");
			return "redirect:/user/joinForm";
		}
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
		session.setAttribute("sessionedUser", user);
		return "redirect:/";                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                              
	}
	
	@GetMapping("/logout")
	public String logout(HttpSession session) {
		session.removeAttribute("sessionedUser");
		return "redirect:/";
	}
	
	@GetMapping("/{id}/infoForm")
	public String infoForm(@PathVariable Long id, Model model, HttpSession session) {
		Object tempUser = session.getAttribute("sessionedUser");		
		if(tempUser == null) {
			return "redirect:/user/loginForm";
		}
		
		User sessionedUser = (User) tempUser;	
		System.out.println(tempUser.toString());
		if(!sessionedUser.isSameId(id)) {
			model.addAttribute("user_info_not_same", "자신의 정보만 수정 가능합니다");
			return "user/infoForm";
		}
		
		User user = userRepository.findById(id).get();
		model.addAttribute("infoUser", user);
		return "user/infoForm";
	}

}
