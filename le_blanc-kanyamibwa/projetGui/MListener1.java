package projetGui;

import java.awt.event.MouseEvent;

import javax.swing.JOptionPane;

import projetModel.Coordinate;
import projetModel.Player;
import projetModel.Unit;
import projetModel.UnitState;

public class MListener1 extends MListener {

	public MListener1(MyPanel p, Map m) {
		super(p, m);
	}

	@Override
	public void run() {
		int x = 0, y = 0;
		x = e.getX();
		y = e.getY();
		Player p1 = m.getGame().getP1();
		Player p2 = m.getGame().getP2();
		Coordinate click = new Coordinate(p.positionX(y, MyPanel.H), p.positionY(x, MyPanel.W));
		System.out.println(p.positionX(y, MyPanel.H) + " " + p.positionY(x, MyPanel.W));
		
		if (e.getButton() == MouseEvent.BUTTON1) { // LEFT CLICK
			// Si click sur QG du joueur 1 alors créer une nouvelle unité
			for (Coordinate qg : p1.getQG()) {
				if (click.equals(qg)) {
					String output = "Êtes-vous sûr de vouloir créer une nouvelle unité pour 50 pièces ?";
					int q = JOptionPane.showConfirmDialog(p, output, "Création d'une unité", JOptionPane.YES_NO_OPTION);
					if (q == JOptionPane.YES_OPTION
							&& p1.enough_gold(50, "créer une nouvelle unité", p))
						p1.createUnit();
				}
			}
			// Parcourir la liste d'unités du joueur 1
			for (Unit u : p1.getUnit()) {
				if (click.equals(u.getPos()) && u.isSelected() == false) { // SELECTION DE L'UNITE 1
					for (Unit i : p1.getUnit()) {
						if (i.isSelected()) {
							i.setSelected(false);
						}
					}
					if (u.changeState(p, p1) != UnitState.State.NO_ROLE)
						u.setSelected(true);
					if (u.getState() == UnitState.State.ATTACKER)
						u.action(m, p, click);
				} else if (click.equals(u.getPos()) && u.isSelected()) { // DESELECTION DE L'UNITE 1
					u.setSelected(false);

				} else if (u.isSelected()) {
					u.action(m, p, click);
				}
			}
			for (Unit u : p2.getUnit()) {
				if (u.isSelected() == true) {// DESELECTION DE L'UNITE 2
					u.setSelected(false);
				}
			}
		} else if (e.getButton() == MouseEvent.BUTTON3) { // RIGHT CLICK
			p1.infos_joueur(p); // INFOS SUR LE JOUEUR
			for (Unit u : p1.getUnit()) {
				if (u.isSelected() == true) {// DESELECTION DE L'UNITE 2
					u.setSelected(false);
				}
			}
		}
	}

}
