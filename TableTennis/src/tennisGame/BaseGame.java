package tennisGame;

import javafx.animation.AnimationTimer;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public abstract class BaseGame extends AnimationTimer {
	protected Scene scene;
	protected Group root;
	protected Canvas canvas;

	private double lastTime = -1;

	public void start(Stage primaryStage) {
		primaryStage.setTitle("Ping-pong");
		root = new Group();

		scene = new Scene(root);
		canvas = new Canvas(800, 600);
		root.getChildren().add(canvas);

		addEventListeners();
		setupGame();

		this.start();
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	@Override
	public void handle(long now) {
		if (lastTime == -1) {
			this.lastTime = now;
		}
		final double delta = (now - lastTime)/1000000000;
		lastTime = now;
		updateGame(delta);
		drawGame();
	}

	protected abstract void setupGame();

	protected abstract void updateGame(double deltaTime);
	protected abstract void drawGame();

	protected abstract void onMousePressed(MouseEvent event);
	protected abstract void onMouseReleased(MouseEvent event);
	protected abstract void onKeyPressed(KeyEvent event);
	protected abstract void onKeyReleased(KeyEvent event);

	private void addEventListeners() {

		scene.setOnKeyPressed(new EventHandler<KeyEvent>(){
			@Override
			public void handle(KeyEvent event) {
				onKeyPressed(event);
			}
		});

		scene.setOnKeyReleased(new EventHandler<KeyEvent>(){
			@Override
			public void handle(KeyEvent event) {
				onKeyReleased(event);
			}
		});
	}
}
