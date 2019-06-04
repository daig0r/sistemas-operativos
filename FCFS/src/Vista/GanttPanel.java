package Vista;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class GanttPanel extends JPanel {
	
	int x = 0;
	int y = 0;
	int pantallaX;
	int pantallaY;
	private int rafaga = 8;

	public GanttPanel(JFrame window) {
		setBorder(BorderFactory.createTitledBorder("D. Gantt"));
		setPreferredSize(new Dimension(window.getSize().width,window.getSize().height/2));
		setVisible(true);
	}
	
	private void graficarProceso() {
		x = x + 1;
	}

	@Override
	public void paint(Graphics g) {
		//super.paint(g);
		Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		//g2d.setColor(Color.RED);
		g2d.fillRect(x, y, 30, 30);
	}
	
	/**
	 * Solo es un ejemplo, perdi el tiempo con eso de JFreeChart
	 * @param args
	 * @throws InterruptedException
	 */

	public static void main(String[] args) throws InterruptedException {

		GanttPanel game = new GanttPanel();
		game.pintarProceso(10,10);//por cada proceso que se cree se le suma la posicion
	}
	
	public void pintarProceso(int pantallaX, int pantallaY) {
		setPantallaX(pantallaX);
		setPantallaY(pantallaY);
		for (int i = 0; i < getRafaga()*10; i++) {
			graficarProceso();
			repaint();
			Thread.sleep(10);
		}
		
	}
	
	public int getRafaga() {
		return rafaga;
	}

	public int getPantallaX() {
		return pantallaX;
	}

	public void setPantallaX(int pantallaX) {
		this.pantallaX = pantallaX;
	}

	public int getPantallaY() {
		return pantallaY;
	}

	public void setPantallaY(int pantallaY) {
		this.pantallaY = pantallaY;
	}
	
	
}
