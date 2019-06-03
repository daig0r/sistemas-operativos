package Vista;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class GanttPanel extends JPanel {

	public GanttPanel(JFrame window) {
		setBorder(BorderFactory.createTitledBorder("D. Gantt"));
		setPreferredSize(new Dimension(window.getSize().width, window.getSize().height / 2));
		setVisible(true);
	}

	public void paint(Graphics g) {
		super.paint(g);
		g.setColor(Color.red);
		g.fillOval(75, 75, 150, 75);
	}
}
