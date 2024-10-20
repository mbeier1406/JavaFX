package com.github.mbeier1406.javafx.base.quizapp;

/**
 * Definiert eine Frage mit Antwort.
 * @author mbeier
 * @see Questions
 */
public class Question {

	private final String question;
	private final boolean answer;

	public Question(String question, boolean answer) {
		super();
		this.question = question;
		this.answer = answer;
	}

	public String getQuestion() {
		return question;
	}
	public boolean isAnswer() {
		return answer;
	}

	@Override
	public String toString() {
		return "Question [question=" + question + ", answer=" + answer + "]";
	}

}
