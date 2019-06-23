package Vista;

import javax.swing.JFrame;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

public class GanttPanel extends TablePanel {

	TableCellRedendererColor cellRenderer;
//	private DefaultTableModel tableModel;

	public GanttPanel(JFrame window, String title) {
		super(window, title);
		getTable().setShowGrid(false);
		getTable().setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		setTableModel(new DefaultTableModel(new Object[] { "PID" }, 0));
		cellRenderer = new TableCellRedendererColor();
		cellRenderer.setHorizontalAlignment(SwingConstants.CENTER);
		getTable().setDefaultRenderer(Object.class, cellRenderer);
		System.out.println("nuevo");
	}

	public void paintProcess(Object[] process) throws Exception {
		int row = getTableModel().getRowCount();
		getTableModel().addRow(new Object[] { process[0] });
		for (int i = (int) process[4]; i < (int) process[5]; i++) {
			getTableModel().addColumn(String.valueOf(i));
		}
		Thread thread = new Thread(new Runnable() {
			@Override
			public void run() {
				for (int i = (int) process[2]; i < (int) process[4]; i++) {
					getTableModel().setValueAt("  ", row, i + 1);
					try {
						Thread.sleep(200);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				for (int i = (int) process[4]; i < (int) process[5]; i++) {
					getTableModel().setValueAt(" ", row, i + 1);
					try {
						Thread.sleep(200);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		});
		thread.start();
	}
}
