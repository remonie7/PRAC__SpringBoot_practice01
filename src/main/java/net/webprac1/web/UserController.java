package net.webprac1.web;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
	public String userJoin(User user, RedirectAttributes redirect) {
		System.out.println(user.toString());
		if(userRepository.findByUserId(user.getUserId())!=null) {
			redirect.addFlashAttribute("massageBox", "이미 사용중인 아이디입니다");
			return "redirect:/user/joinForm";
		}
		userRepository.save(user);
		redirect.addFlashAttribute("massageBox", "회원가입이 완료되었습니다");
		return "redirect:/";
	}
	
	@GetMapping("/loginForm")
	public String loginForm() {
		return "user/loginForm";
	}
	
	@PostMapping("/login")
	public String userLogin(String userId, String userPassword, HttpSession session, RedirectAttributes redirect) {
		User user = userRepository.findByUserId(userId);
		
		if(user==null) {
			redirect.addFlashAttribute("massageBox", "존재하지 않는 아이디입니다");
			return "redirect:/user/loginForm";
		}
		if(!userPassword.equals(user.getUserPassword())) {
			redirect.addFlashAttribute("massageBox", "비밀번호가 틀립니다");
			return "redirect:/user/loginForm";
		}
		session.setAttribute("sessionedUser", user);
		redirect.addFlashAttribute("massageBox", "로그인 되었습니다");
		return "redirect:/";                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                              
	}
	
	@GetMapping("/logout")
	public String logout(HttpSession session, RedirectAttributes redirect) {
		session.removeAttribute("sessionedUser");
		redirect.addFlashAttribute("massageBox", "로그아웃 되었습니다");
		return "redirect:/";
	}
	
	@GetMapping("/{id}/infoForm")
	public String infoForm(@PathVariable Long id, Model model, HttpSession session, RedirectAttributes redirect) {
		Object tempUser = session.getAttribute("sessionedUser");		
		if(tempUser == null) {
			return "redirect:/user/loginForm";
		}
		
		User sessionedUser = (User) tempUser;	
		System.out.println(tempUser.toString());
		if(!sessionedUser.isSameId(id)) {
			redirect.addFlashAttribute("massageBox", "자신의 정보만 수정 가능합니다");
			return "redirect:/";
		}
		
		User user = userRepository.findById(id).get();
		model.addAttribute("infoUser", user);
		return "user/infoForm";
	}
	
	
	@PostMapping("/{id}/updateInfo")
	public String updateInfo(@PathVariable Long id, User updateUser, RedirectAttributes redirect) {
		User user = userRepository.findById(id).get();
		user.update(updateUser);
		userRepository.save(user);
		redirect.addFlashAttribute("massageBox", "수정이 완료되었습니다.");
		return "redirect:/";
	}

}
