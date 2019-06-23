package Vista;

import javax.swing.JFrame;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

public class GanttPanel extends TablePanel {

	public GanttPanel(JFrame window, String title) {
		super(window, title);
		getTable().setShowGrid(false);
		getTable().setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		setTableModel(new DefaultTableModel(new Object[] { "PID" }, 0));
		TableCellRedendererColor cellRenderer = new TableCellRedendererColor();
		cellRenderer.setHorizontalAlignment(SwingConstants.CENTER);
		getTable().setDefaultRenderer(Object.class, cellRenderer);
	}

	public void paintProcess(Object[] process) {
		int row = getRow(process);
		for (int i = (int) process[3]; i < (int) process[4]; i++) {
			getTableModel().addColumn(String.valueOf(i));
		}
		Thread thread = new Thread(new Runnable() {
			@Override
			public void run() {
				for (int i = (int) process[1]; i < (int) process[3]; i++) {
					getTableModel().setValueAt("  ", row, i + 1);
					try {
						Thread.sleep(400);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				for (int i = (int) process[3]; i < (int) process[4]; i++) {
					getTableModel().setValueAt(" ", row, i + 1);
					try {
						Thread.sleep(400);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		});
		thread.start();
	}

	public int getRow(Object[] process) {
		int max = String.valueOf(process[0]).indexOf("*");
		int row = getTableModel().getRowCount();
		if (max != -1) {
			row = searchRow(0, String.valueOf(process[0]).substring(0, max));
		} else {
			getTableModel().addRow(new Object[] { process[0] });
		}
		return row;
	}
}
