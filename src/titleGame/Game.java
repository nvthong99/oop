package titleGame;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;

import titleGame.Display.Display;
import titleGame.gfx.Assets;
import titleGame.gfx.GameCamera;
import titleGame.input.KeyManager;
import titleGame.states.GameState;
import titleGame.states.MenuState;
import titleGame.states.State;
import titleGame.states.WinState;

public class Game implements Runnable {

	private Display display;
	private int width, height;
	public String title;

	private boolean running = false;
	private Thread thread;

	private BufferStrategy bs;
	private Graphics g;

	// Sound
	private Sound sound;

	// State
	public static GameState GAME_STATE;
	public static MenuState MENU_STATE;
	public static WinState WIN_STATE;

	// Input
	private KeyManager keyManager;

	// Camera
	private GameCamera gameCamera;

	// Handler
	private Handler handler;

	public Game(String title, int width, int height, Sound sound) {
		this.width = width;
		this.height = height;
		this.title = title;
		this.sound = sound;
		keyManager = new KeyManager();
	}

	private void init() {
		handler = new Handler(this);
		display = new Display(title, width, height);
		display.getFrame().addKeyListener(keyManager);
		Assets.init();

		gameCamera = new GameCamera(handler, 0, 0);

		GAME_STATE = new GameState(handler);
		MENU_STATE = new MenuState(title, width, height, this);
		WIN_STATE = new WinState(width, height, this);

		State.setState(MENU_STATE);
	}

	int x = 0;

	private void tick() {
		keyManager.tick();

		if (State.getState() != null)
			State.getState().tick();
	}

	private void render() {
		if (display.getCanvas() == null)
			return;
		bs = display.getCanvas().getBufferStrategy();
		if (bs == null) {
			display.getCanvas().createBufferStrategy(3);
			return;
		}
		g = bs.getDrawGraphics();
		// Clear Screnn
		g.clearRect(0, 0, width, height);

		if (State.getState() != null)
			State.getState().render(g);
		if (State.getState() == GAME_STATE) {
			g.setColor(Color.white);
			g.fillRect(120, 30, 300, 30);
			g.setColor(Color.green);
			g.setFont(new Font("Arial", Font.BOLD, 30));
			int health = handler.getWorld().getEntityManager().getPlayer().getHealth();
			if (health < 15)
				g.setColor(Color.red);
			g.drawString("HP", 50, 55);
			g.fillRect(120, 30, health * 10, 30);
		}

		// End Draw
		bs.show();
		g.dispose();
	}

	public void run() {

		init();

		int fps = 60;
		double timePerTick = 1000000000 / fps;
		double delta = 0;
		long now;
		long lastTime = System.nanoTime();
		long timer = 0;
		int ticks = 0;

		while (running) {
			now = System.nanoTime();
			delta += (now - lastTime) / timePerTick;
			timer += now - lastTime;
			lastTime = now;

			if (delta >= 1) {
				tick();
				render();
				delta--;
				ticks++;
			}

			if (timer >= 1000000000) {
				System.out.println("FPS : " + ticks);
				ticks = 0;
				timer = 0;
			}
		}

		stop();
	}

	public Display getDisplay() {
		return this.display;
	}

	public KeyManager getKeyManager() {
		return keyManager;
	}

	public GameCamera getGameCamera() {
		return gameCamera;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public Handler getHandler() {
		return handler;
	}

	public void setHandler(Handler handler) {
		this.handler = handler;
	}

	public Sound getSound() {
		return this.sound;
	}

	public synchronized void start() {
		if (running)
			return;
		running = true;
		thread = new Thread(this);
		thread.start();
	}

	public synchronized void stop() {
		if (!running)
			return;
		running = false;
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
