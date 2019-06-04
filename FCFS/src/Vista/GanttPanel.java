package Vista;

import java.awt.SystemColor;

import javax.swing.JFrame;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class GanttPanel extends TablePanel {

	private final String[] HEADER = { "PID" };

	public GanttPanel(JFrame window, String title) {
		super(window, title);
		getTable().setShowGrid(false);
		getTable().setBackground(SystemColor.window);
		getTable().setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		setTableModel(new DefaultTableModel(HEADER, 0));
	}

	// TODO: Nada elegante este metodo
	public void paintProcess(Object[] process) {
		int row = getTableModel().getRowCount();
		getTableModel().addRow(HEADER);
		getTableModel().setValueAt(process[0], row, 0);
		paintWaitTime(row, (int) process[1], (int) process[3]);
		paintBurstTime(row, (int) process[3], (int) process[4]);
	}
	
	private void graficarProceso() {
		x = x + 1;
	}

	private void paintWaitTime(int row, int arrivalTime, int startTime){
		for (int i = arrivalTime; i < startTime; i++) {
			getTableModel().setValueAt("---", row, i + 1);			
		}
	}
	
	private void paintBurstTime(int row, int startTime, int finalTime){
		for (int i = startTime; i < finalTime; i++) {
			getTableModel().addColumn(String.valueOf(i));
			getTableModel().setValueAt("**", row, i + 1);
		}
	}
	
	
}
