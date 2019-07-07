package Vista;

import javax.swing.JFrame;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

public class GanttPanel extends TablePanel {

	TableCellRedendererColor cellRenderer;

	public GanttPanel(JFrame window, String title) {
		super(window, title);
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
}
