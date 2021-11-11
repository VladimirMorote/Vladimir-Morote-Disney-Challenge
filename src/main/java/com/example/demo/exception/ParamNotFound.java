package com.example.demo.exception;

@SuppressWarnings("serial")
public class ParamNotFound extends RuntimeException {

	public ParamNotFound(String error) {
		super(error);
	}
}
