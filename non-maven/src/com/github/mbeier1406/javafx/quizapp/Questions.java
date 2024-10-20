package com.github.mbeier1406.javafx.quizapp;

/**
 * Liste der Fragen und Antworten für das Quiz.
 * @author mbeier
 * @see Question
 */
public class Questions {

	private final Question[] questions;

	public Questions() {
		questions = new Question[] {
				new Question("Ist die Erde rund?", true),
				new Question("Gibt es in Deutschland 20 Bundesländer?", false),
				new Question("Schmeckt Bier?", true),
				new Question("Ist Frauen in Arizona gesetzlich verboten Hosen zu tragen?", true),
				new Question("Der größter Mann der Welt ist 2,47m", true),
				new Question("Der Blauwal ist das größte Tier aller Zeiten", true),
				new Question("Fische sind Säugetieren", false),
				new Question("Können Elefanten schwimmen", true),
				new Question("Ein Elefant kann fliegen", false),
				new Question("Albert Einstein wurde 80 Jahre alt", false),
				new Question("Die Welt besteht aus 8 Kontinenten", false)
		};
	}

	public Question[] getQuestions() {
		return questions;
	}

}
