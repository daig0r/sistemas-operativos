package Vista;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

public class GanttPanel extends TablePanel {

	private TitledBorder titledBorder;
	private TableCellRedendererColor cellRenderer;

	public GanttPanel(JFrame window, String title) {
		super(window, title);
		titledBorder = (TitledBorder) getBorder();
		titledBorder.setTitleColor(Color.green);
		getTable().setShowGrid(false);
		getTable().setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		setTableModel(new DefaultTableModel(new Object[] { "PID" }, 0));
		cellRenderer = new TableCellRedendererColor();
		cellRenderer.setHorizontalAlignment(SwingConstants.CENTER);
		getTable().setDefaultRenderer(Object.class, cellRenderer);
		setInitialColumns(100);
	}

	public void setInitialColumns(int max) {
		for (int i = 1; i <= max; i++) {
			getTableModel().addColumn(i);
		}
	}

	public void updateTitleColor(Boolean isAvalible) {
		if (isAvalible) {
			titledBorder.setTitleColor(Color.green);
			repaint();
		} else {
			titledBorder.setTitleColor(Color.red);
			repaint();

		}
	}
}
