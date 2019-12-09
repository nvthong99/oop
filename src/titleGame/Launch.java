package titleGame;

public class Launch {

	public static void main(String[] args) {

		Sound sound = new Sound();
		sound.start();

		Game game = new Game("Simple RPG Game", 1280, 1000);
		game.start();
	}
}
