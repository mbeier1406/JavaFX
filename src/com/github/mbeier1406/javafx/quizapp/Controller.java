package com.github.mbeier1406.javafx.quizapp;

import java.util.stream.Stream;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

/**
 * Beinhaltet die Logik für die Quiz-App.
 * @author mbeier
 */
public class Controller {

	/** Liste der Fragen und Antworten */
	private Questions questions = new Questions();

	/** Index der aktuell zu beantwortenden Frage in {@linkplain Questions#getQuestions()} */
	private int questionNumber = 0;

	/** Punktestand */
	private int score = 0;

	/** Initialisiert die App beim (Neu-)Start */
	public void initialize() {
		System.out.println("Start Controller...");
		questionNumber = 0;
		score = 0;
		questionCountLabel.setText("" + (questionNumber+1) +"/"+questions.getQuestions().length);
		questionlabel.setText(questions.getQuestions()[questionNumber].getQuestion());
		progressBarHBox.getChildren().clear();
	}

	@FXML
    private Label questionlabel;

    @FXML
    private Button buttonYes;

    @FXML
    private Button buttonNo;

    @FXML
    private Label questionCountLabel;

    @FXML
    private Label quizScoreLabel;

    @FXML
    private HBox progressBarHBox;

    @FXML
    void buttonPressed(ActionEvent event) {
		progressBarHBox.getChildren().add(new Rectangle((
				(int) progressBarHBox.getWidth())/questions.getQuestions().length,
				progressBarHBox.getHeight(),
				Color.BLACK));
    	Stream.of(((Button) event.getSource()).getId()).peek(System.out::println).forEach(id -> {
    		if ( id.equals("buttonYes") || id.equals("buttonNo") )
    			showStatus("Neuer Punktestand: "+(score += id.equals("buttonYes") == questions.getQuestions()[questionNumber].isAnswer() ? 20 : 0));
    		else
    			throw new IllegalArgumentException("ID: "+id);
    	});
    	quizScoreLabel.setText("Score: "+score);
    	if ( questionNumber < questions.getQuestions().length-1 ) {
    		System.out.println("Score: "+score);
    		questionlabel.setText(questions.getQuestions()[++questionNumber].getQuestion());
    	}
    	else {
    		System.out.println("Fertig: "+questionNumber+"/"+score);
    		restart();
    		
    	}
    	questionCountLabel.setText(""+(questionNumber+1)+"/"+questions.getQuestions().length);
    }

    /** Neustart der Anwendung mit Hinweisfenster */
    public void restart() {
    	final var alert = new Alert(AlertType.INFORMATION);
    	alert.setTitle("Quiz fertig");
    	alert.setHeaderText("Alle Fragen beantwortet.");
    	alert.setContentText("Neustart?");
    	alert.showAndWait();
    	initialize();
    }

    /** Hinweisfenster mit Punktestand nach jeder beantworteten Frage */
    public void showStatus(String text) {
    	final var alert = new Alert(AlertType.INFORMATION);
    	alert.setHeaderText("Status");
    	alert.setContentText(text);
    	alert.show();
    	final var timeline = new Timeline(new KeyFrame(Duration.seconds(2), event -> { alert.close(); }));
    	timeline.setCycleCount(1);
    	timeline.play();
    }

}
