package net.webprac1.web;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/question")
public class QuestionController {

	@GetMapping("/writeForm")
	public String writrForm(HttpSession session, RedirectAttributes redirect) {
		if(session.getAttribute("sessionedUser")==null) {
			redirect.addFlashAttribute("massageBox", "로그인을 해야 글을 작성할 수 있습니다.");
			return "redirect:/user/loginForm";
		}
		return "qna/writeForm";
	}
	
	
}
