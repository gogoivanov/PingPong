package tennisGame;

public class Paddle extends Sprite{

	private double velocity;
	private final double MAX_VELOCITY = 2400;

	public Paddle(String imagePath, double x, double y, double width, double height) {
		super(imagePath, x, y, width, height);
	}

	public void increaseVelocity(double velocity){
		setVelocity(this.velocity+velocity);
	}

	public double getVelocity(){
		return velocity;
	}

	public void setVelocity(double velocity) {
		this.velocity = velocity;
		if (this.velocity > MAX_VELOCITY) {
			this.velocity = MAX_VELOCITY;
		}

		if (this.velocity < -MAX_VELOCITY){
			this.velocity = -MAX_VELOCITY;
		}
	}

	public void update(double deltaTime){
		double yOffset = getVelocity()*deltaTime;
		move(yOffset);
	}

	public void move(double amount) {
		double newY = getY() + amount;
		setY(newY);
	}
}
