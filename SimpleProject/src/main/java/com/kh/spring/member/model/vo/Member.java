package com.kh.spring.member.model.vo;

import java.util.Objects;

public class Member {
	private String userId;
	private String userPwd;
	public Member() {
		super();
		
	}
	public Member(String userId, String userPwd) {
		super();
		this.userId = userId;
		this.userPwd = userPwd;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUserPwd() {
		return userPwd;
	}
	public void setUserPwd(String userPwd) {
		this.userPwd = userPwd;
	}
	@Override
	public int hashCode() {
		return Objects.hash(userId, userPwd);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Member other = (Member) obj;
		return Objects.equals(userId, other.userId) && Objects.equals(userPwd, other.userPwd);
	}
	@Override
	public String toString() {
		return "Member [userId=" + userId + ", userPwd=" + userPwd + "]";
	}
	
}
