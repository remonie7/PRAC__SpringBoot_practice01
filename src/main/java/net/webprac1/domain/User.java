package net.webprac1.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class User {
	@Id
	@GeneratedValue
	private Long id;
	
	@Column(nullable=false, length=20)
	private String userId;
	private String userPassword;
	private String userName;
	private String userEmail;
	
	
	public void setUserId(String userId) {
		this.userId = userId;
	}


	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}


	public void setUserName(String userName) {
		this.userName = userName;
	}


	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}
	
	@Override
	public String toString() {
		return "User [userId=" + userId + ", userPassword=" + userPassword + ", userName=" + userName + ", userEmail="
				+ userEmail + "]";
	}
}
