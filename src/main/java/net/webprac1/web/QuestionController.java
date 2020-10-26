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
	
	@GetMapping("/show/{questionId}")
	public String show(@PathVariable Long questionId, Model model) {
		Question question = questionRepository.findById(questionId).get();
		model.addAttribute("selectQuestion", question);
		return "qna/show";
	}
	
	@GetMapping("/modify/{questionId}")
	public String modifyQuestion(@PathVariable Long questionId, Model model, HttpSession session, RedirectAttributes redirect) {
		Object tempUser = session.getAttribute("sessionedUser");		
		if(tempUser==null) {
			redirect.addFlashAttribute("massageBox", "로그인을 해야 글을 수정할 수 있습니다.");
			return "redirect:/user/loginForm";
		}
		
		User sessionedUser = (User) tempUser;	
		Question question = questionRepository.findById(questionId).get();
		if(!sessionedUser.isSameId(question.getWriter().getId())) {
			redirect.addFlashAttribute("massageBox", "자신이 쓴 글만 수정 가능합니다");
			return "redirect:/question/show/{questionId}";
		}
		
		model.addAttribute("modifyQuestion", question);
		return "qna/modifyForm";
	}
	
	@PostMapping("/updateModify/{questionId}")
	public String modify(@PathVariable Long questionId, HttpSession session, RedirectAttributes redirect, String title, String contents) {
		Question question = questionRepository.findById(questionId).get();
		question.update(title, contents);
		questionRepository.save(question);
		redirect.addFlashAttribute("massageBox", "수정이 완료되었습니다.");
		return "redirect:/question/show/{questionId}";
	}
	
	@GetMapping("/delete/{questionId}")
	public String deleteQuestion(@PathVariable Long questionId, Model model, HttpSession session, RedirectAttributes redirect) {
		Object tempUser = session.getAttribute("sessionedUser");		
		if(tempUser==null) {
			redirect.addFlashAttribute("massageBox", "로그인을 해야 글을 삭제할 수 있습니다.");
			return "redirect:/user/loginForm";
		}
		
		User sessionedUser = (User) tempUser;	
		Question question = questionRepository.findById(questionId).get();
		if(!sessionedUser.isSameId(question.getWriter().getId())) {
			redirect.addFlashAttribute("massageBox", "자신이 쓴 글만 삭제 가능합니다");
			return "redirect:/question/show/{questionId}";
		}
		questionRepository.deleteById(questionId);
		redirect.addFlashAttribute("massageBox", "글이 정상적으로 삭제되었습니다.");
		return "redirect:/";
	}
	
	
}
