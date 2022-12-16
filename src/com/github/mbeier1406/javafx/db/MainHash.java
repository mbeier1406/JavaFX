package com.github.mbeier1406.javafx.db;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class MainHash {

	public static final void main(String[] args) throws NoSuchAlgorithmException {
		MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
		messageDigest.update("12345".getBytes());
		String stringHash = new String(messageDigest.digest());
		System.out.println("Hash: "+Base64.getEncoder().encodeToString(stringHash.getBytes()));
	}

}
