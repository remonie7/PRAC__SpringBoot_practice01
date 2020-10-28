package net.webprac1.domain;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;

@Entity
public class Question {

	@Id
	@GeneratedValue
	private Long questionId;
	
	@ManyToOne
	@JoinColumn(foreignKey = @ForeignKey(name = "fk_question_writer"))
	private User writer;
	
	private String title;
	private String contents;
	
	private LocalDateTime createDate;
	
	@OneToMany(mappedBy="question") //mappedBy의 값은 Answer.java에서 ManyToOne 해준 부분에서 Question 형 변수명
	@OrderBy("answerId ASC")	//답변의 ID 기준으로 오름차순 정렬
	private List<Answer> answers;
	
	
	
	public Question() {};
	
	public Question(User writer, String title, String contents) {
		super();
		this.writer = writer;
		this.title = title;
		this.contents = contents;
		this.createDate = LocalDateTime.now();
	}
	
	
	
	public User getWriter() {
		return writer;
	}

	public String getFormattedCreateDate() {
		if(createDate == null) return "";
		return createDate.format(DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm:ss"));
	}
	
	public void update(String title, String contents) {
		this.title=title;
		this.contents=contents;
	}
}
