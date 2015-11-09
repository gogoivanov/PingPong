package tennisGame;

import com.sun.javafx.geom.AreaOp.XorOp;

public class Ball extends Sprite{

	private double ballVelocityX = 0.5;
	private double ballVelocityY = -0.5;
	private double ballSpeed = 400; // to set ballmaxvelocity in intersects
	private double ballMaxSpeed = 1100;

	private boolean isStopped = true;
	private double timePassed = 0;
	private boolean forbidSwap = false;

	public double getBallSpeed() {
		return ballSpeed;
	}

	public void setBallSpeed(double ballSpeed) {
		this.ballSpeed = ballSpeed;
	}


	public double getBallMaxSpeed() {
		return ballMaxSpeed;
	}

	public void setBallMaxSpeed(double ballMaxSpeed) {
		this.ballMaxSpeed = ballMaxSpeed;
	}



	public Ball(String imagePath, double x, double y, double width, double height) {
		super(imagePath, x, y, width, height);
	}

	public void stopBall() {
		this.isStopped = true;
	}

	public void startBall() {
		this.isStopped = false;
	}

	public void update(double deltaTime){
		if (!isStopped) {
			doUpdate(deltaTime);
		}
	}

	private void doUpdate(double deltaTime) {
		double xOffset = ballVelocityX*deltaTime*ballSpeed;
		double yOffset = ballVelocityY*deltaTime*ballSpeed;
		moveX(xOffset);
		moveY(yOffset);

		if (forbidSwap) {
			timePassed += deltaTime;
			if (timePassed > 350) {
				forbidSwap = false;
				timePassed = 0;
			}
		}
	}

	public void moveX(double amount) {
		double newX = getX() + amount;
		setX(newX);
	}

	public void moveY(double amount) {
		double newY = getY() + amount;
		setY(newY);
	}

	public void swapAroundX() { // za stenite
		ballVelocityY = -ballVelocityY;
		forbidSwap = true;
	}

	public void swapAroundY() { // za hilkite
		ballVelocityX = -ballVelocityX;
		forbidSwap = true;
	}

	public boolean isStopped() {
		return isStopped;
	}

}
