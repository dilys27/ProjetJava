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
					+ "Le but du jeu est de r�colter le plus de pi�ces d'or possible avant qu'il n'y en ait plus sur le terrain !\n"
					+ "Joueur :\n"
					+ "Veuillez s�lectionner une de vos unit�s avec le clic gauche de votre souris puis utilisez ce m�me bouton pour effectuer le d�placement.\n"
					+ "M�me bouton pour la d�selection.\n\n"
					+ "Vous pouvez �galement cr�er de nouvelles unit�s pour 50 pi�ces d'or et changer le r�le d'une unit� � n'importe quel moment.\n"
					+ "Vous avez le choix entre 3 r�les d'unit� en plus de celle d'origine :\n"
					+ "- R�colteur (Gratuit) : choix d'une pile de pi�ce et allers-retours de l'unit� pour ramener les pi�ces 10 par 10 entre la pile et la maison-m�re.\n"
					+ "- Sentinelle (20 pi�ces) : choix de deux positions, allers-retours entre les 2 positions et si une unit� adverse se trouve dans un rayon de 3 cases,\nl'unit� attaque � distance (1 pv / seconde) et reprend son d�placement quand plus d'ennemi dans son rayon d'attaque.\n"
					+ "- Attaquant (30 pi�ces) : choix d'une unit� et d'une position, va et reste sur cette position et si une unit� adverse se trouve dans un rayon de 3 cases,\nelle la poursuit et l'attaque tant qu'elle est dans son rayon.\n\n"
					+ "Pour d�placer la vue de la carte : utilisez les fl�ches directionnelles de votre clavier\n";
			JOptionPane.showMessageDialog(null, output, "BIENVENUE", JOptionPane.INFORMATION_MESSAGE);
		}
	}

}

