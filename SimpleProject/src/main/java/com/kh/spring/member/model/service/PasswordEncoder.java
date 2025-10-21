package com.kh.spring.member.model.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class PasswordEncoder {  // BCryptPasswordEncoder 에서 ArgontPasswordEncoder 변경하는 책임을 PasswordEncoder가 지게 한다.
	
	private final BCryptPasswordEncoder passwordEncoder; // 밖에서는 passwordEncoder 못건듦.
	
	public String encode(String rawPassword) {
		return passwordEncoder.encode(rawPassword);
	}
	
	public boolean matches(String rawPassword, String encodedPassword) {
		return passwordEncoder.matches(rawPassword, encodedPassword);
	}
}
