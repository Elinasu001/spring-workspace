package com.kh.spring.exception;

public class BoardSaveFailedException extends RuntimeException {
	public BoardSaveFailedException(String msg) {
		super(msg);
	}
}
