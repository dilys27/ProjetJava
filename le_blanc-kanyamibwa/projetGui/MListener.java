package projetGui;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public abstract class MListener extends MouseAdapter implements MouseListener, Runnable {
	protected MyPanel p;
	protected Map m;
	protected MouseEvent e;

	public MListener(MyPanel p, Map m) {
		super();
		this.p = p;
		this.m = m;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		super.mouseClicked(e);
		this.e = e;
		Thread t = new Thread(this);
		t.start();
	}

}
