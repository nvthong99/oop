package titleGame.states;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

import titleGame.Game;

public class WinState extends State {

	private final Color NORMAL_COLOR = new Color(99, 24, 1);
	private final Color FOCUS_COLOR = new Color(255, 173, 1);

	private JPanel mainPanel;
	private Game game;
	private int width, height;

	public WinState(int width, int height, Game game) {
		super(game.getHandler());
		this.game = game;
		this.width = width;
		this.height = height;
		mainPanel = new JPanel();
		init();
	}

	private void init() {
		
	}

	@Override
	public void enableUI() {
	}

	@Override
	public void tick() {
	}

	@Override
	public void render(Graphics g) {
	}

}
