package tennisGame;

import java.util.ArrayList;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class Game extends BaseGame {

	private Sprite background;
	private Paddle paddleOne;
	private Paddle paddleTwo;
	private Ball tennisBall;
	private double timePassed = 0;
	Integer PlayerOneScore = 0;
	Integer PlayerTwoScore = 0;
	private String GameOver;
	private String StartGame = "Press 'Space' to start the game!\nFirst to reach 11 points wins!";
	private boolean ballInPaddleOne;

	@Override
	protected void setupGame() {

		background = new Sprite("background.png", 0, 0, canvas.getWidth(), canvas.getHeight());

		paddleOne = new Paddle("playerOnePaddle.png", 0, 0, 22, 150);
		double paddleOneY = (canvas.getHeight() - paddleOne.getHeight()) / 2;
		paddleOne.setY(paddleOneY);

		paddleTwo = new Paddle("playerTwoPaddle.png", 778, 0, 22, 150);
		double paddleTwoY = (canvas.getHeight() - paddleTwo.getHeight()) / 2;
		paddleTwo.setY(paddleTwoY);

		tennisBall = new Ball("ball.png", 0, 0, 33, 33);
		placeBallOnPaddleOne();

	}

	@Override
	protected void updateGame(double deltaTime) {
		paddleOne.update(deltaTime);
		if (paddleOne.getY() < 0) {
			paddleOne.setY(0);
		}

		double paddleOneMaxY = canvas.getHeight() - paddleOne.getHeight();
		if (paddleOne.getY() > paddleOneMaxY) {
			paddleOne.setY(paddleOneMaxY);
		}

		paddleTwo.update(deltaTime);
		if (paddleTwo.getY() < 0) {
			paddleTwo.setY(0);
		}

		double paddleTwoMaxY = canvas.getHeight() - paddleTwo.getHeight();
		if (paddleTwo.getY() > paddleTwoMaxY) {
			paddleTwo.setY(paddleTwoMaxY);
		}

		tennisBall.update(deltaTime);

		if (tennisBall.getBoundery().intersects(paddleOne.getX(), paddleOne.getY(), paddleOne.getWidth(),
				paddleOne.getHeight())) {
			tennisBall.swapAroundY();
			tennisBall.setX(paddleOne.getX() + tennisBall.getWidth()+1);

			if (tennisBall.getBallSpeed() < tennisBall.getBallMaxSpeed()) {
				tennisBall.setBallSpeed(tennisBall.getBallSpeed() + 100);
			}

		} else if (tennisBall.getBoundery().intersects(paddleTwo.getX(), paddleTwo.getY(), paddleTwo.getWidth(),
				paddleTwo.getHeight())) {
			tennisBall.swapAroundY();
			tennisBall.setX(paddleTwo.getX() - tennisBall.getWidth()-1);

			if (tennisBall.getBallSpeed() < tennisBall.getBallMaxSpeed()) {
				tennisBall.setBallSpeed(tennisBall.getBallSpeed() + 100);
			}

		}

		if (tennisBall.getY() < 0) {
			tennisBall.swapAroundX();
			tennisBall.setY(1);
		}

		if (tennisBall.getY() > canvas.getHeight() - tennisBall.getHeight()) {
			tennisBall.swapAroundX();
			tennisBall.setY(canvas.getHeight() - tennisBall.getHeight()-1);
		}



		if (tennisBall.getX() < 0 && !tennisBall.isStopped()) {
			PlayerTwoScore++;
			tennisBall.stopBall();
			placeBallOnPaddleTwo();
			tennisBall.setBallSpeed(400);

		} else if (tennisBall.getX() > canvas.getWidth() - tennisBall.getWidth() && !tennisBall.isStopped()) {
			PlayerOneScore++;
			tennisBall.stopBall();
			placeBallOnPaddleOne();
			tennisBall.setBallSpeed(400);
		}

		if (PlayerOneScore >= 11 && PlayerOneScore - PlayerTwoScore >1) {
			resetResult();
			GameOver = "Player One has Won!\nPress 'Space' to start New Game and release the ball.";
			placeBallOnPaddleOne();
		} else if (PlayerTwoScore >= 11 && PlayerTwoScore - PlayerOneScore >1) {
			resetResult();
			GameOver = "Player One has Won!\nPress 'Space' to start New Game and release the ball.";
			placeBallOnPaddleTwo();
		}



		if (tennisBall.isStopped() && this.ballInPaddleOne) {
			placeBallOnPaddleOne();
		} else if (tennisBall.isStopped() && !this.ballInPaddleOne) {
			placeBallOnPaddleTwo();
		}

	}

	void placeBallOnPaddleOne() {
		this.ballInPaddleOne = true;
		tennisBall.setX(paddleOne.getX() + paddleOne.getWidth());
		tennisBall.setY(paddleOne.getY() + (paddleOne.getHeight() - tennisBall.getHeight()) / 2);
	}

	void placeBallOnPaddleTwo() {
		this.ballInPaddleOne = false;
		tennisBall.setX(paddleTwo.getX() - tennisBall.getWidth());
		tennisBall.setY(paddleTwo.getY() + (paddleTwo.getHeight() - tennisBall.getHeight()) / 2);
	}

	void resetResult() {
		PlayerOneScore = 0;
		PlayerTwoScore = 0;
	}

	@Override
	protected void drawGame() {

		GraphicsContext graphicsContext = canvas.getGraphicsContext2D();
		graphicsContext.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());

		background.render(graphicsContext);

		graphicsContext.setFill(Color.WHITE);
		Font font = new Font(30);
		graphicsContext.setFont(font);
		graphicsContext.fillText(PlayerOneScore.toString(), canvas.getWidth() / 2 - 50, 50);
		graphicsContext.fillText(PlayerTwoScore.toString(), canvas.getWidth() / 2 + 25, 50);

		paddleOne.render(graphicsContext);
		paddleTwo.render(graphicsContext);
		tennisBall.render(graphicsContext);

		graphicsContext.setFill(Color.BLACK);
		graphicsContext.fillText(StartGame, 225, 250);

		Font endGame = new Font(30);
		graphicsContext.setFont(endGame);
		graphicsContext.fillText(GameOver, 50, 220 );
	}

	@Override
	protected void onKeyPressed(KeyEvent event) {
		if (event.getCode().equals(KeyCode.W)) {
			paddleOne.increaseVelocity(-800);
		} else if (event.getCode().equals(KeyCode.S)) {
			paddleOne.increaseVelocity(800);
		}

		if (event.getCode().equals(KeyCode.UP)) {
			paddleTwo.increaseVelocity(-800);
		} else if (event.getCode().equals(KeyCode.DOWN)) {
			paddleTwo.increaseVelocity(800);
		}

		if (event.getCode().equals(KeyCode.SPACE)) {
			tennisBall.startBall();
			StartGame = "";
			GameOver = "";
		}
	}

	@Override
	protected void onKeyReleased(KeyEvent event) {
		if (event.getCode().equals(KeyCode.W) || event.getCode().equals(KeyCode.S)) {
			paddleOne.setVelocity(0);
		}

		if (event.getCode().equals(KeyCode.UP) || event.getCode().equals(KeyCode.DOWN)) {
			paddleTwo.setVelocity(0);
		}
	}

	@Override
	protected void onMousePressed(MouseEvent event) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void onMouseReleased(MouseEvent event) {
		// TODO Auto-generated method stub

	}
}