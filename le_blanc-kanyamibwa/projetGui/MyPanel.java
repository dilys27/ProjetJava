package projetGui;

import java.awt.Graphics;

import javax.swing.JPanel;

public abstract class MyPanel extends JPanel {

	protected static final long serialVersionUID = 1L;
	protected int l = 0;
	protected int c = 0;
	protected int view = 15;
	protected Map m;
	public static int W = 600;
	public static int H = 600;
	protected KListener press;
	protected MListener clk;

	public MyPanel(Map m) {
		this.m = m;
		this.press = new KListener(this);
		setSize(W, H);
		addKeyListener(press);
	}

	public Map getM() {
		return m;
	}

	public void setL(int i) {
		int res = this.l + i;
		if (res >= 0 && res < m.map.length - (view - 1))
			this.l = res;
	}

	public void setC(int j) {
		int res = this.c + j;
		if (res >= 0 && res < m.map.length - (view - 1))
			this.c = res;
	}

	public int getL() {
		return l;
	}

	public int getC() {
		return c;
	}

	public int getW() {
		return W;
	}

	public int getH() {
		return H;
	}
	
	protected void drawVLines(Graphics g, int width, int height, int j) {
		for (int i = 1; i < j; i++) {
			g.drawLine(width * i / j, 0, width * i / j, height);
		}
	}

	protected void drawHLines(Graphics g, int width, int height, int j) {
		for (int i = 1; i < j; i++) {
			g.drawLine(0, height * i / j, width, height * i / j);
		}
	}

	public int position(int p, int size) {
		return p / (size / view - 1);

	}

	public int mouseCord(int p, int size) {
		return p * (size / view - 1);
	}

	public int positionY(int p, int size) {
		int cell_size = size / view - 1;
		return c + (p / cell_size);

	}

	public int positionX(int p, int size) {
		int cell_size = size / view - 1;
		return l + (p / cell_size);
	}

}
