package titleGame;

public class Launch {

	public static void main(String[] args) {

		Sound sound = new Sound();
		Game game = new Game("Simple RPG Game", 1280, 1000, sound);
		game.start();
	}
}
