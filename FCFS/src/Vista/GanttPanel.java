package Vista;

import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class GanttPanel extends JPanel {

	public GanttPanel(JFrame window) {
		setBorder(BorderFactory.createTitledBorder("D. Gantt"));
		setPreferredSize(new Dimension(window.getSize().width,window.getSize().height/2));
		setVisible(true);
	}
}
