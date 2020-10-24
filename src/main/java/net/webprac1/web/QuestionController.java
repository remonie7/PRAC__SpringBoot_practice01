package net.webprac1.web;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import net.webprac1.domain.Question;
import net.webprac1.domain.QuestionRepository;
import net.webprac1.domain.User;

@Controller
@RequestMapping("/question")
public class QuestionController {
	
	@Autowired
	private QuestionRepository questionRepository;
	

	@GetMapping("/writeForm")
	public String writrForm(HttpSession session, RedirectAttributes redirect) {
		if(session.getAttribute("sessionedUser")==null) {
			redirect.addFlashAttribute("massageBox", "로그인을 해야 글을 작성할 수 있습니다.");
			return "redirect:/user/loginForm";
		}
		return "qna/writeForm";
	}
	
	@PostMapping("/write")
	public String write(String title, String contents, HttpSession session, RedirectAttributes redirect) {
		if(session.getAttribute("sessionedUser")==null) {
			redirect.addFlashAttribute("massageBox", "로그인을 해야 글을 작성할 수 있습니다.");
			return "redirect:/user/loginForm";
		}

		User sessionUser = (User) session.getAttribute("sessionedUser");
		Question question = new Question(sessionUser, title, contents);
		questionRepository.save(question);
		return "redirect:/";
	}
	
}
