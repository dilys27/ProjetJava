package projetGui;

import java.awt.Color;
import java.awt.Graphics;

import projetModel.Coordinate;
import projetModel.UnitState.State;

public class Panel2 extends MyPanel {

	private static final long serialVersionUID = 1L;

	public Panel2(Map m) {
		super(m);
		this.clk = new MListener2(this, m);
		addMouseListener(clk);
		setFocusable(true);
		setFocusTraversalKeysEnabled(false);
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

		// GRILLE
		setBackground(Color.gray);
		int width = getSize().width;
		int height = getSize().height;
		g.setColor(Color.gray);
		drawVLines(g, width, height, Map.width);
		drawHLines(g, width, height, Map.height);

		// ZOOM ET AFFICHAGE
		for (int i = l; i < Map.height; i++) {
			int cpt1 = 0;
			for (int j = c; j < Map.width; j++) {
				int cpt2 = 0;
				// ZOOM
				int x = 0, y = 0, xWidth = 0, yWidth = 0;
				if (l == 0 && c == 0) {
					x = getSize().width * j / view;
					xWidth = getSize().width / view;
					y = getSize().height * i / view;
					yWidth = getSize().height / view;
				} else if (l > 0 && c == 0) {
					x = getSize().width * j / view;
					xWidth = getSize().width / view;
					y = getSize().height * (i - (l + cpt1)) / view;
					yWidth = getSize().height / view;
				} else if (c > 0 && l == 0) {
					x = getSize().width * (j - (c + cpt2)) / view;
					xWidth = getSize().width / view;
					y = getSize().height * i / view;
					yWidth = getSize().height / view;
				} else if (c > 0 && l > 0) {
					x = getSize().width * (j - (c + cpt2)) / view;
					xWidth = getSize().width / view;
					y = getSize().height * (i - (l + cpt1)) / view;
					yWidth = getSize().height / view;
				}
				// AFFICHAGE
				if (m.map[i][j] == 'B') { // MAISON-MERE JOUEUR 1
					g.setColor(Color.BLUE);
					g.fillRect(x, y, xWidth, yWidth);
				} else if (m.map[i][j] == 'R') { // MAISON-MERE JOUEUR 2
					g.setColor(Color.RED);
					g.fillRect(x, y, xWidth, yWidth);
				} else if (m.map[i][j] == 'P') { // TELEPORTEURS
					g.setColor(Color.WHITE);
					g.fillRect(x, y, xWidth, yWidth);
				} else if (m.map[i][j] == 'G') { // OR
					g.setColor(Colors.GOLD);
					g.fillRect(x, y, xWidth, yWidth);
				} else if (m.map[i][j] == 'W') { // MURS
					g.setColor(Colors.WALL);
					g.fillRect(x, y, xWidth, yWidth);
				} else if (m.map[i][j] == 'M') { // ZONES BOUEUSES
					g.setColor(Colors.BROWN);
					g.fillRect(x, y, xWidth, yWidth);
				} else if (m.map[i][j] == 'T') { // ARBRES
					g.setColor(Colors.DARK_GREEN);
					g.fillOval(x, y, xWidth, yWidth);
				} else if (m.map[i][j] == (char) 1) { // UNITES DU JOUEUR 1
					Coordinate c = new Coordinate(i, j);
					if (m.getGame().getU(c).isSelected() == true || m.getGame().getU(c).isSelected() == false) {
						g.setColor(Color.BLUE);
						g.fillOval(x, y, xWidth, yWidth);
					}
				} else if (m.map[i][j] == (char) 2) { // UNITES DU JOUEUR 2
					Coordinate c = new Coordinate(i, j);
					if (m.getGame().getU(c).isSelected() == true) {
						if (m.getGame().getU(c).getState() == State.HARVESTER) {
							g.setColor(Colors.CORAL);
						} else if (m.getGame().getU(c).getState() == State.SENTINEL) {
							g.setColor(Colors.CRIMSON);
						} else if (m.getGame().getU(c).getState() == State.ATTACKER) {
							g.setColor(Colors.DARK_RED);
						} else if (m.getGame().getU(c).getState() == State.NORMAL) {
							g.setColor(Color.PINK);
						}

						g.fillOval(x, y, xWidth, yWidth);
					} else if (m.getGame().getU(c).isSelected() == false) {
						g.setColor(Color.RED);
						g.fillOval(x, y, xWidth, yWidth);
					}
				}
				g.setColor(Color.BLACK);
				g.drawRect(x, y, xWidth, yWidth);
				cpt2++;
			}
			cpt1++;
		}

		if (m.getGame().game_over())
			m.getGame().winner(this);

	}

}
