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
	
	
	
	public boolean isSameId(Long compareId) {
		if(id.equals(compareId)) return true;
		return false;
	}
	

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	public String getUserPassword() {
		return userPassword;
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
	
	
	public void update(User updateUser) {
		this.userPassword = updateUser.userPassword;
		this.userName = updateUser.userName;
		this.userEmail = updateUser.userEmail;		
	}
	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "User [userId=" + userId + ", userPassword=" + userPassword + ", userName=" + userName + ", userEmail="
				+ userEmail + "]";
	}



}
