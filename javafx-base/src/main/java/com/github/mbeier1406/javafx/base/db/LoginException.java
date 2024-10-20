package com.github.mbeier1406.javafx.base.db;

public class LoginException extends Exception {
	private static final long serialVersionUID = -7401016120875234229L;
	public LoginException() { super(); }
	public LoginException(String s) { super(s); }
	public LoginException(Exception e) { super(e); }	
}
