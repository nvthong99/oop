package titleGame.states;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

import titleGame.Game;
import titleGame.gfx.Assets;

public class WinState extends State {

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

		mainPanel.setSize(width, height);
		mainPanel.setLayout(null);

		JLabel background = new JLabel(new ImageIcon(Assets.BACKGROUND)) {
			private static final long serialVersionUID = 1L;

			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				Color color = getBackground();
				color = new Color(color.getRed(), color.getGreen(), color.getBlue(), 32);
				g.setColor(color);
				g.fillRect(0, 0, getWidth(), getHeight());
			}
		};
		background.setBounds(0, 0, width, height);

		JLabel banner = new JLabel("CONGRATULATIONS");
		banner.setHorizontalAlignment(SwingConstants.CENTER);
		banner.setForeground(new Color(99, 24, 1));
		banner.setFont(new Font("Century Gothic", Font.BOLD, 90));
		banner.setBounds(0, 150, width, 152);

		JLabel smallBanner = new JLabel("YOU WIN");
		smallBanner.setHorizontalAlignment(SwingConstants.CENTER);
		smallBanner.setForeground(Color.RED);
		smallBanner.setFont(new Font("Century Gothic", Font.BOLD, 70));
		smallBanner.setBounds(0, 275, width, 152);

		JButton menuButton = new JButton("Return MainMenu");
		menuButton.setBorder(new LineBorder(new Color(99, 24, 1), 5));
		menuButton.setFont(new Font("Tahoma", Font.PLAIN, 51));
		menuButton.setForeground(Color.white);
		menuButton.setBackground(new Color(99, 24, 1));
		menuButton.setFocusable(false);
		menuButton.setBounds((width - 450) / 2, 475, 450, 100);
		menuButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				menuButton.setBackground(new Color(255, 173, 1));
			}

			@Override
			public void mouseExited(MouseEvent e) {
				menuButton.setBackground(new Color(99, 24, 1));
			}
		});
		menuButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				mainPanel.setVisible(false);
				State.setState(Game.MENU_STATE);
			}
		});

		mainPanel.add(banner);
		mainPanel.add(smallBanner);
		mainPanel.add(menuButton);
		mainPanel.add(background);

		mainPanel.setVisible(false);
		game.getDisplay().getFrame().add(mainPanel);
	}

	@Override
	public void enableUI() {
		mainPanel.setVisible(true);
		game.getDisplay().getCanvas().setVisible(false);
		game.getDisplay().getFrame().revalidate();
	}

	@Override
	public void tick() {
	}

	@Override
	public void render(Graphics g) {
	}

}
