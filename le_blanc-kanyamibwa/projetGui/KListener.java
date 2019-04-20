package projetGui;

import java.awt.event.*;

import javax.swing.JOptionPane;


public class KListener extends KeyAdapter implements KeyListener {
	
	private MyPanel panel;
	
	public KListener(MyPanel panel) {
		this.panel = panel;
	}
	
	
	public void keyTyped(KeyEvent e) {}
	public void keyReleased(KeyEvent e) {}
	
	@Override
	public void keyPressed(KeyEvent e) {

		int id = e.getKeyCode(); 
		if(id == KeyEvent.VK_RIGHT) {
			panel.setC(1);
		}else if(id == KeyEvent.VK_LEFT) {
			panel.setC(-1);
		}else if(id == KeyEvent.VK_DOWN) {
			panel.setL(1);
		}else if(id == KeyEvent.VK_UP) {
			panel.setL(-1);
		}else if (e.getKeyChar() == 'i') {
			String output = "Informations du jeu\n\n"
					+ "Le but du jeu est de récolter le plus de pièces d'or possible avant qu'il n'y en ait plus sur le terrain !\n"
					+ "Joueur :\n"
					+ "Veuillez sélectionner une de vos unités avec le clic gauche de votre souris puis utilisez ce même bouton pour effectuer le déplacement.\n"
					+ "Même bouton pour la déselection.\n\n"
					+ "Vous pouvez également créer de nouvelles unités pour 50 pièces d'or et changer le rôle d'une unité à n'importe quel moment.\n"
					+ "Vous avez le choix entre 3 rôles d'unité en plus de celle d'origine :\n"
					+ "- Récolteur (Gratuit) : choix d'une pile de pièce et allers-retours de l'unité pour ramener les pièces 10 par 10 entre la pile et la maison-mère.\n"
					+ "- Sentinelle (20 pièces) : choix de deux positions, allers-retours entre les 2 positions et si une unité adverse se trouve dans un rayon de 3 cases,\nl'unité attaque à distance (1 pv / seconde) et reprend son déplacement quand plus d'ennemi dans son rayon d'attaque.\n"
					+ "- Attaquant (30 pièces) : choix d'une unité et d'une position, va et reste sur cette position et si une unité adverse se trouve dans un rayon de 3 cases,\nelle la poursuit et l'attaque tant qu'elle est dans son rayon.\n\n"
					+ "Pour déplacer la vue de la carte : utilisez les flèches directionnelles de votre clavier\n";
			JOptionPane.showMessageDialog(null, output, "BIENVENUE", JOptionPane.INFORMATION_MESSAGE);
		}
	}

}

