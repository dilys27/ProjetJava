package projetGui;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class Gui {

	private final JFrame frame1 = new JFrame("Strategy Game - Player 1");
	private final JFrame frame2 = new JFrame("Strategy Game - Player 2");

	private Map m = new Map();
	private Panel1 panel1 = new Panel1(m);
	private Panel2 panel2 = new Panel2(m);

	public Gui() {
		// AFFICHE LES INFOS DU JEU
		String output = "Informations du jeu\n\n"
				+ "Le but du jeu est de récolter le plus de pièces d'or possible avant qu'il n'y en ait plus sur le terrain !\n"
				+ "Joueur 1 :\n"
				+ "Veuillez sélectionner une de vos unités avec le clic gauche de votre souris puis utilisez ce même bouton pour effectuer le déplacement\net attendez d'avoir atteint cette position avant de lui en donner une nouvelle.\n"
				+ "Vous pouvez également changer le rôle de votre unité à n'importe quel moment.\n"
				+ "Même bouton pour la déselection.\n\n" + "Joueur 2 :\n"
				+ "Veuillez sélectionner une de vos unités avec le clic gauche de votre souris puis utilisez ce même bouton pour effectuer le déplacement\net attendez d'avoir atteint cette position avant de lui en donner une nouvelle.\n"
				+ "Vous pouvez également changer le rôle de votre unité à n'importe quel moment.\n"
				+ "Même bouton pour la déselection.\n\n"
				+ "Pour déplacer la vue de la carte : utilisez les flèches directionnelles de votre clavier\n";
		JOptionPane.showMessageDialog(null, output, "BIENVENUE", JOptionPane.INFORMATION_MESSAGE);

		frame1.setContentPane(panel1);
		frame2.setContentPane(panel2);

		frame1.setSize(panel1.getSize());
		frame2.setSize(panel2.getSize());

		frame1.setLocation(50, 100);
		frame2.setLocation(700, 100);

		frame1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		frame1.setResizable(false);
		frame2.setResizable(false);

		frame1.setVisible(true);
		frame2.setVisible(true);

		gameloop();

	}

	public void gameloop() {

		long lastTime = System.nanoTime();
		double amountOfTicks = 60.0;
		double ns = 1000000000 / amountOfTicks;
		double delta = 0;
		long timer = System.currentTimeMillis();

		while (true) {
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			while (delta >= 1) {
				update();
				delta--;
			}
			if (System.currentTimeMillis() - timer > 1000) {
				timer += 1000;
				;
			}
		}
	}

	public void update() {
		panel1.getM().updateMap(panel1);
		panel2.getM().updateMap(panel2);
	}

}
