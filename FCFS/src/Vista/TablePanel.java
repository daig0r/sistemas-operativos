package Vista;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class TablePanel extends JPanel {

	private JTable table;
	private DefaultTableModel tableModel;

	public TablePanel(JFrame window, String title) {
		setBorder(BorderFactory.createTitledBorder(title));
		setLayout(new BorderLayout());

		table = new JTable();
		JScrollPane scrollJTable = new JScrollPane(table);
		table.setFillsViewportHeight(true);
	
		add(table.getTableHeader(), BorderLayout.PAGE_START);
		add(scrollJTable, BorderLayout.CENTER);
		

		setVisible(true);
	}

	protected void initTableStatusPanel(JFrame window) {
		setPreferredSize(new Dimension(window.getSize().width / 6, (int) (window.getSize().height * 0.42)));
		String[] columnName = { "Proceso", "T. Llegada", "T. Rafaga" };
		setTableModel(new DefaultTableModel(columnName, 0));
	}

	public JTable getTable() {
		return table;
	}

	public void setTable(JTable table) {
		this.table = table;
	}

	public DefaultTableModel getTableModel() {
		return tableModel;
	}

	public void setTableModel(DefaultTableModel tableModel) {
		this.tableModel = tableModel;
		table.setModel(tableModel);
	}
}
