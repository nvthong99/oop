package titleGame;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

public class Sound {

	private final static int PLAYING = 1;
	private final static int PAUSED = 2;

	private Player player = null;
	private final Object playerLock = new Object();

	private int playerStatus;

	public boolean pause() {
		synchronized (playerLock) {
			if (playerStatus == PLAYING) {
				playerStatus = PAUSED;
			}
			return playerStatus == PAUSED;
		}
	}

	public boolean resume() {
		synchronized (playerLock) {
			if (playerStatus == PAUSED) {
				playerStatus = PLAYING;
				playerLock.notifyAll();
			}
			return playerStatus == PLAYING;
		}
	}

	public Sound() {
		final Runnable r = new Runnable() {
			public void run() {
				try {
					while (true) {
						if (player == null || !player.play(1)) {
							InputStream file = new FileInputStream(new File("res/music/background.mp3"));
							player = new Player(file);
						}
						synchronized (playerLock) {
							while (playerStatus == PAUSED) {
								try {
									playerLock.wait();
								} catch (final InterruptedException e) {
									break;
								}
							}
						}
					}
				} catch (JavaLayerException | FileNotFoundException e) {
					e.printStackTrace();
				}
			}
		};
		final Thread thread = new Thread(r);
		thread.setDaemon(true);
		thread.setPriority(Thread.MAX_PRIORITY);
		playerStatus = PLAYING;
		thread.start();

	}

}
