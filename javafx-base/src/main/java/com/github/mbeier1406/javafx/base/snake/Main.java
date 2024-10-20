package com.github.mbeier1406.javafx.base.snake;

import java.util.Arrays;

import javax.print.attribute.standard.Media;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
//import javafx.scene.media.MediaPlayer;
//import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Main extends Application {

	public enum Direction {
		UP, DOWN, LEFT, RIGHT
	}

	public static final int SNAKE_SIZE = 20;

	public static final int GAME_WIDTH = 30 * SNAKE_SIZE;

	public static final int GAME_HEIGHT = 20 * SNAKE_SIZE;

	private static final GameSpeed GAME_SPEED = new GameSpeed(new GameSpeedInfo[] {
			new GameSpeedInfo(0.5, "Leicht"),
			new GameSpeedInfo(0.15, "Mittel"),
			new GameSpeedInfo(0.09, "Schwer") });

	private static boolean isEndless = false;

	private Direction direction = Direction.DOWN;

	private boolean moved = false;

	private boolean running = false;

	private boolean pause = false;

	private Timeline timeline = new Timeline();

	private ObservableList<Node> snake;

//	private MediaPlayer mediaPlayer;

	private Slider volumeSlider = new Slider();

	private Label volumeLabel = new Label("1.0");

	private int score = 0;

	private Label scoreLabel = new Label("Score: "+score);

	private Label infoLabel = new Label("Drücke ESC für Ende und SPACE für Pause");

	private Pane centerPane;

	@Override
	public void init() {
		volumeSlider.setValue(100);
		volumeSlider.setPrefWidth(80);
		volumeSlider.setShowTickLabels(true);
//		volumeSlider.valueProperty().addListener(observable -> {
//			if ( mediaPlayer != null ) mediaPlayer.setVolume(volumeSlider.getValue()/100);
//			volumeLabel.setText(String.format("%.2f", volumeSlider.getValue()/100));
//		});
//		try {
//			mediaPlayer = new MediaPlayer(new Media(getClass().getResource("music/snakeMusic.mp3").toString()));
//			mediaPlayer.play();
//			mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
//			volumeSlider.setValue(mediaPlayer.getVolume()*100);
//		}
//		catch ( Throwable t ) {
//			// t.printStackTrace();
//			mediaPlayer = null;
//		}
	}

//	private Label info = new Label();
	private Node getTopPane() {
		ImageView imageView = new ImageView(new Image(getClass().getResourceAsStream("images/snake.png")));
		Label label = new Label();
		label.setGraphic(imageView);
//		final var hBox = new HBox();
//		hBox.getChildren().addAll(label, info);
		return label;
	}

	private Node getLeftPane() {
		final var vBox = new VBox(5);
		final var buttonStart = new Button("Start");
		buttonStart.setMaxWidth(Double.MAX_VALUE);
		buttonStart.setOnAction(event -> {
			System.out.println("Spielstart...");
			startGame();
		});
		final var buttonEnde = new Button("Ende");
		buttonEnde.setMaxWidth(Double.MAX_VALUE);
		buttonEnde.setOnAction(event -> {
			System.out.println("Ende.");
			Platform.exit();
		});
		final var labelSpeed = new Label(GAME_SPEED.getCurrentGameSpeedInfo().getInfo());
		labelSpeed.setMaxWidth(Double.MAX_VALUE);
		labelSpeed.setAlignment(Pos.CENTER);
		labelSpeed.setPadding(new Insets(0, 0, 10, 0));
		final var buttonSpeed = new Button("Speed");
		buttonSpeed.setMaxWidth(Double.MAX_VALUE);
		buttonSpeed.setOnAction(event -> {
			labelSpeed.setText(GAME_SPEED.nextIndex().getCurrentGameSpeedInfo().getInfo());			
		});
		final var buttonRand = new Button("Rand ☓");
		buttonRand.setMaxWidth(Double.MAX_VALUE);
		buttonRand.setOnAction(event -> {
			buttonRand.setText("Rand "+((Main.isEndless = !Main.isEndless)?"✓":"☓"));
		});
		final var separator = new Separator();
		separator.setPadding(new Insets(10,0, 10, 0));
		vBox.getChildren().addAll(buttonStart, buttonEnde, separator, buttonSpeed, labelSpeed, buttonRand);
		return vBox;
	}

	private Node getRightPane() {
		final var vBox = new VBox(5);
		final var buttonMute = new Button("", new ImageView(new Image(getClass().getResourceAsStream("images/mute.png"))));
//		buttonMute.setOnAction(event -> { if ( mediaPlayer != null ) mediaPlayer.pause(); });
		final var buttonUnMute = new Button("", new ImageView(new Image(getClass().getResourceAsStream("images/unmute.png"))));
//		buttonUnMute.setOnAction(event -> { if ( mediaPlayer != null ) mediaPlayer.play(); });
		final var hBox = new HBox(5);
		hBox.getChildren().addAll(volumeSlider, volumeLabel);
		vBox.getChildren().addAll(buttonMute, buttonUnMute, new Separator(), hBox);
		vBox.setAlignment(Pos.CENTER_RIGHT);
		return vBox;
	}

	private Pane getCenterPane() {
		final var pane = new Pane();
		pane.setPrefSize(GAME_WIDTH, GAME_HEIGHT);
		pane.setStyle(""
				+ "-fx-background-image: url(images/background.png);"
				+ "-fx-background-size: "+SNAKE_SIZE+" "+SNAKE_SIZE+";"
				+ "-fx-background-repeat: repeat;"
//				+ "-fx-background-color: blue;"
				+ "-fx-border-color: black;"
				+ "-fx-border-style: solid;"
				+ "-fx-border-width: 2;"
				+ "");

		final var snakeBody = new Group();
		snake = snakeBody.getChildren();

		final var food = new Rectangle(SNAKE_SIZE, SNAKE_SIZE);
//		food.setFill(new ImagePattern(new Image(getClass().getResourceAsStream("images/food.png"))));

		newFoodPosition(food);

		final var keyFrame = new KeyFrame(Duration.seconds(GAME_SPEED.getCurrentGameSpeedInfo().getSpeed()), event -> {
			if ( !running ) return;
			centerPane.requestFocus();
			boolean toRemove = snake.size() > 1;
			final Node tail = ( toRemove ) ? snake.remove(snake.size()-1) : snake.get(0);
			double tailX = tail.getTranslateX(), tailY = tail.getTranslateY();
			switch ( direction ) {
				case UP: tail.setTranslateX(snake.get(0).getTranslateX()); tail.setTranslateY(snake.get(0).getTranslateY()-SNAKE_SIZE); break;
				case RIGHT: tail.setTranslateX(snake.get(0).getTranslateX()+SNAKE_SIZE); tail.setTranslateY(snake.get(0).getTranslateY()); break;
				case DOWN: tail.setTranslateX(snake.get(0).getTranslateX()); tail.setTranslateY(snake.get(0).getTranslateY()+SNAKE_SIZE); break;
				case LEFT: tail.setTranslateX(snake.get(0).getTranslateX()-SNAKE_SIZE); tail.setTranslateY(snake.get(0).getTranslateY()); break;
				default: throw new IllegalArgumentException("Direction:"+direction);
			}
			moved = true;
			if ( toRemove ) snake.add(tail);
			for ( Node rect : snake ) // Kollision prüfen
				if ( rect != tail && rect.getTranslateX() == tail.getTranslateX() && rect.getTranslateY() == tail.getTranslateY() ) {
					score = 0;
					scoreLabel.setText("Score: " + score);
					restartGame();
					break;
				}

			if ( isEndless )
				gameIsEndless(tail, pane);
			else
				gameIsNotEndless(tail, food);

			if ( tail.getTranslateX() == food.getTranslateX() && tail.getTranslateY() == food.getTranslateY() ) {
				newFoodPosition(food);
				score += 20;
				scoreLabel.setText("Score: " + score);
				final var rectangle = new Rectangle(SNAKE_SIZE, SNAKE_SIZE);
				rectangle.setTranslateX(tailX);
				rectangle .setTranslateY(tailY);
				snake.add(rectangle);
			}

//			info.setText("x="+tail.getTranslateX()+"; y="+tail.getTranslateY());

		});

		timeline.getKeyFrames().add(keyFrame);
		timeline.setCycleCount(Timeline.INDEFINITE);

		scoreLabel.setFont(Font.font("Arial", 20));
		scoreLabel.setTranslateX(GAME_WIDTH/2);
		infoLabel.setFont(Font.font("Arial", FontPosture.ITALIC, 10));

		pane.getChildren().addAll(food, snakeBody, scoreLabel, infoLabel);
		pane.setOnKeyPressed(event -> {
			System.out.println("Code: "+event.getCode());
			if ( !moved ) return;
			switch ( event.getCode() ) {
				case W: case UP:
					if ( direction != Direction.DOWN ) direction = Direction.UP;
					break;
				case S: case DOWN:
					if ( direction != Direction.UP ) direction = Direction.DOWN;
					break;
				case A: case LEFT:
					if ( direction != Direction.RIGHT ) direction = Direction.LEFT;
					break;
				case D: case RIGHT:
					if ( direction != Direction.LEFT ) direction = Direction.RIGHT;
					break;
				case SPACE:
					if ( pause ) {
						pause = false;
						timeline.playFromStart();
					}
					else {
						pause = true;
						timeline.pause();
					}
					break;
				case ESCAPE:
					Platform.exit();
					break;
				default:
					break;
			}
			moved = false;
		});

		return pane;
	}

	private void gameIsEndless(Node tail, Parent pane) {
		pane.setStyle(""
				+ "-fx-background-image: url(images/background.png);"
				+ "-fx-background-size: "+SNAKE_SIZE+" "+SNAKE_SIZE+";"
				+ "-fx-background-repeat: repeat;"
				+ "");
		if ( tail.getTranslateX() < 0 ) tail.setTranslateX(GAME_WIDTH-SNAKE_SIZE);
		else if ( tail.getTranslateX() >= GAME_WIDTH ) tail.setTranslateX(0);
		if ( tail.getTranslateY() < 0 ) tail.setTranslateY(GAME_HEIGHT-SNAKE_SIZE);
		else if ( tail.getTranslateY() >= GAME_HEIGHT ) tail.setTranslateY(0);
	}

	private void gameIsNotEndless(Node tail, Node food) {
		if ( tail.getTranslateX() < 0 || tail.getTranslateX() >= GAME_WIDTH ||
			 tail.getTranslateY() < 0 || tail.getTranslateY() >= GAME_HEIGHT ) {
			score = 0;
			scoreLabel.setText("Score: " + score);
			restartGame();
			newFoodPosition(food);
		}
	}

	private void newFoodPosition(Node food) {
		food.setTranslateX((int) (Math.random() * (GAME_WIDTH-SNAKE_SIZE)));
		food.setTranslateY((int) (Math.random() * (GAME_HEIGHT-SNAKE_SIZE)));
	}

	private void startGame() {
		final var head = new Rectangle(SNAKE_SIZE, SNAKE_SIZE);
		snake.add(head);
		timeline.play();
		running = true;
		pause = false;
	}

	private void restartGame() {
		stopGame();
		startGame();
	}

	private void stopGame() {
		running = false;
		timeline.stop();
		snake.clear();
	}

	@Override
	public void start(Stage stage) throws Exception {

		final var borderPane = new BorderPane();
		borderPane.setTop(getTopPane());
		BorderPane.setAlignment(borderPane.getTop(), Pos.CENTER);
		borderPane.setLeft(getLeftPane());
		BorderPane.setMargin(borderPane.getLeft(), new Insets(50, 15, 50, 15));
		borderPane.setRight(getRightPane());
		BorderPane.setMargin(borderPane.getRight(), new Insets(50, 15, 50, 15));
		centerPane = getCenterPane();
		borderPane.setCenter(centerPane);

		final var scene = new Scene(borderPane, GAME_WIDTH+230, GAME_HEIGHT+52);

		stage.setScene(scene);
		stage.setResizable(false);
		stage.setTitle("Snake");
		stage.show();

	}

	public static final void main(String[] args) {
		launch(args);
	}

	public static final class GameSpeed {
		private int index;
		private GameSpeedInfo[] gameSpeedInfo;
		public GameSpeed(GameSpeedInfo[] gameSpeedInfo) {
			this.index = 0;
			this.gameSpeedInfo = gameSpeedInfo;
		}
		public int getIndex() {
			return index;
		}
		public GameSpeed nextIndex() {
			this.index = ++this.index%this.gameSpeedInfo.length;
			return this;
		}
		public GameSpeedInfo getCurrentGameSpeedInfo() {
			return this.gameSpeedInfo[this.index];
		}
		@Override
		public String toString() {
			return "GameSpeed [index=" + index + ", gameSpeedInfo=" + Arrays.toString(gameSpeedInfo) + "]";
		}
	}

	public static final class GameSpeedInfo {
		private double speed;
		private String info;
		public GameSpeedInfo(double speed, String info) {
			this.speed = speed;
			this.info = info;
		}
		public double getSpeed() {
			return speed;
		}
		public String getInfo() {
			return info;
		}
		@Override
		public String toString() {
			return "GameSpeedInfo [speed=" + speed + ", info=" + info + "]";
		}
	}

}
