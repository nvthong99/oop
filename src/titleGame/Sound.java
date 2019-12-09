package titleGame;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

public class Sound extends Thread {

	public void run() {
		try {
			InputStream file = new FileInputStream(new File("res/music/background.mp3"));
			Player player = new Player(file);
			player.play();
		} catch (JavaLayerException | FileNotFoundException e) {
			e.printStackTrace();
		}
	}

}
