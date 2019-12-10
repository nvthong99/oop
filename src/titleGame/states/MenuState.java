package titleGame.states;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.border.LineBorder;

import titleGame.Game;
import titleGame.entities.creatures.Enemy;
import titleGame.gfx.Assets;
import titleGame.states.State;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.SwingConstants;

public class MenuState extends State {

	private final Color NORMAL_COLOR = new Color(99, 24, 1);
	private final Color FOCUS_COLOR = new Color(255, 173, 1);

	private JPanel mainPanel;
	private Game game;

	private MenuButton startButton;
	private MenuButton easyButton;
	private MenuButton hardButton;
	private MenuButton exitButton;
	private MenuButton backButton;
	private JButton soundButton;

	private String title;
	private int width, height;

	private boolean isMute = false;

	public MenuState(String title, int width, int height, Game game) {
		super(game.getHandler());
		this.game = game;
		this.title = title;
		this.width = width;
		this.height = height;
		mainPanel = new JPanel();
		init();
	}

	private void init() {

		mainPanel.setSize(width, height);
		mainPanel.setLayout(null);

		JLabel banner = new JLabel(title);
		banner.setHorizontalAlignment(SwingConstants.CENTER);
		banner.setForeground(NORMAL_COLOR);
		banner.setFont(new Font("Century Gothic", Font.BOLD, 90));
		banner.setBounds(0, 100, width, 152);

		JLabel background = new JLabel(new ImageIcon(Assets.BACKGROUND));
		background.setBounds(0, 0, width, height);

		startButton = new MenuButton("Start Game");
		startButton.setLocation(width / 2 - 150, 400);
		startButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				choseDifficult();
			}
		});

		exitButton = new MenuButton("Exit Game");
		exitButton.setLocation(width / 2 - 150, 550);
		exitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
			}
		});

		easyButton = new MenuButton("Easy Mode");
		easyButton.setVisible(false);
		easyButton.setLocation(width / 2 - 150, 350);
		easyButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				startEasyGame();
			}
		});

		hardButton = new MenuButton("Hard Mode");
		hardButton.setVisible(false);
		hardButton.setLocation(width / 2 - 150, 500);
		hardButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				startHardGame();
			}
		});

		backButton = new MenuButton("Back");
		backButton.setVisible(false);
		backButton.setLocation(width / 2 - 150, 650);
		backButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				backToMenu();
			}
		});

		final ImageIcon soundIcon = new ImageIcon(Assets.SOUND);
		final ImageIcon muteIcon = new ImageIcon(Assets.MUTE);

		soundButton = new JButton(soundIcon);
		soundButton.setBounds(width - 100, 30, 70, 70);
		soundButton.setBorderPainted(false);
		soundButton.setContentAreaFilled(false);
		soundButton.setBorder(new LineBorder(NORMAL_COLOR, 5));
		soundButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				soundButton.setBorderPainted(true);
			}

			@Override
			public void mouseExited(MouseEvent e) {
				soundButton.setBorderPainted(false);
			}
		});
		soundButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (isMute) {
					if (game.getSound().resume()) {
						soundButton.setIcon(soundIcon);
					}
				} else {
					if (game.getSound().pause()) {
						soundButton.setIcon(muteIcon);
					}
				}
				isMute = !isMute;
			}
		});

		mainPanel.add(banner);
		mainPanel.add(soundButton);
		mainPanel.add(startButton);
		mainPanel.add(exitButton);
		mainPanel.add(easyButton);
		mainPanel.add(hardButton);
		mainPanel.add(backButton);
		mainPanel.add(background);

		mainPanel.setVisible(false);
		game.getDisplay().getFrame().add(mainPanel);
	}

	private void backToMenu() {
		startButton.setVisible(true);
		exitButton.setVisible(true);
		easyButton.setVisible(false);
		hardButton.setVisible(false);
		backButton.setVisible(false);
	}

	private void choseDifficult() {
		startButton.setVisible(false);
		exitButton.setVisible(false);
		easyButton.setVisible(true);
		hardButton.setVisible(true);
		backButton.setVisible(true);
	}

	private void startHardGame() {
		backToMenu();
		mainPanel.setVisible(false);
		game.getDisplay().getCanvas().setVisible(true);
		State.setState(Game.GAME_STATE);

		ArrayList<Enemy> ene = game.getHandler().getWorld().getEntityManager().getEnemy();
		for (Enemy e : ene) {
			e.setSpeed(10.0f);
		}
	}

	private void startEasyGame() {
		backToMenu();
		mainPanel.setVisible(false);
		game.getDisplay().getCanvas().setVisible(true);
		State.setState(Game.GAME_STATE);

		ArrayList<Enemy> ene = game.getHandler().getWorld().getEntityManager().getEnemy();
		for (Enemy e : ene) {
			e.setSpeed(2.0f);
		}
	}

	private class MenuButton extends JButton {

		private static final long serialVersionUID = 1L;

		public MenuButton(String title) {
			super(title);
			setBorder(new LineBorder(NORMAL_COLOR, 5));
			setFont(new Font("Tahoma", Font.PLAIN, 51));
			setForeground(Color.white);
			setBackground(NORMAL_COLOR);
			setFocusable(false);
			setSize(300, 100);

			addMouseListener(new MouseAdapter() {
				@Override
				public void mouseEntered(MouseEvent e) {
					setBackground(FOCUS_COLOR);
				}

				@Override
				public void mouseExited(MouseEvent e) {
					setBackground(NORMAL_COLOR);
				}
			});
		}
	}

	@Override
	public void tick() {
	}

	@Override
	public void render(Graphics g) {
	}

	@Override
	public void enableUI() {
		game.getDisplay().getCanvas().setVisible(false);
		mainPanel.setVisible(true);
		game.getDisplay().getFrame().revalidate();
	}
}
