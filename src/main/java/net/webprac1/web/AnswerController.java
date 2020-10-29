package net.webprac1.web;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import net.webprac1.domain.Answer;
import net.webprac1.domain.AnswerRepository;
import net.webprac1.domain.Question;
import net.webprac1.domain.QuestionRepository;
import net.webprac1.domain.User;

@Controller
@RequestMapping("/answer/{questionId}")
public class AnswerController {
	
	@Autowired
	private QuestionRepository questionRepository;
	
	@Autowired
	private AnswerRepository answerRepository;
	
	@PostMapping("")
	public String create(@PathVariable Long questionId, String contents, HttpSession session, RedirectAttributes redirect) {
		
		Object tempUser = session.getAttribute("sessionedUser");		
		if(tempUser==null) {
			redirect.addFlashAttribute("massageBox", "로그인을 해야 댓글을 작성할 수 있습니다.");
			return "redirect:/user/loginForm";
		}
		User loginUser = (User) tempUser;
		Question question = questionRepository.findById(questionId).get();
		Answer answer = new Answer(loginUser, question, contents);
		answerRepository.save(answer);
		//redirect.addFlashAttribute("answer", answer);
		System.out.println("답글추가");
		//return String.format("redirect:/question/show/%d", questionId);
		return "redirect:/question/show/{questionId}";
	}	
	
	@GetMapping("/delete/{answerId}")
	public String delete(@PathVariable Long questionId, @PathVariable Long answerId, HttpSession session, RedirectAttributes redirect) {
		Object tempUser = session.getAttribute("sessionedUser");
		if(tempUser==null) {
			redirect.addFlashAttribute("massageBox", "로그인을 해야 댓글을 삭제할 수 있습니다.");
			return "redirect:/user/loginForm";
		}
		User loginUser = (User) tempUser;
		Answer answer = answerRepository.findById(answerId).get();
		User answerUser = answer.getWriter();
		if(!answerUser.getId().equals(loginUser.getId())) {
			redirect.addFlashAttribute("massageBox", "자신의 댓글만 삭제할 수 있습니다.");
			return "redirect:/question/show/{questionId}";
		}
		
		answerRepository.deleteById(answerId);
	
		
		return "redirect:/question/show/{questionId}";
	}
}
