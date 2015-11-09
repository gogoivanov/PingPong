package tennisGame;
import javafx.application.Application;
import javafx.stage.Stage;

public class GameApplication extends Application {

	@Override
	public void start(Stage primaryStage) {
		BaseGame game = new Game();
		game.start(primaryStage);
	}

	public static void main(String[] args) {
		launch(args);
	}
}