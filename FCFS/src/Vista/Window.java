package Vista;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.table.DefaultTableModel;

public class Window extends JFrame {

	private final String[] COLUMN_NAME = { "Proceso", "T. Llegada", "T. Rafaga" };

	private GanttPanel panelGantt;
	private TablePanel panelTableReadyQueue;
	private TablePanel panelTableLockQueue;
	private TablePanel panelTable;
	private ActionPanel panelAction;

	public Window(String title) {
		setTitle(title);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new BorderLayout());
		setSize((int) (Toolkit.getDefaultToolkit().getScreenSize().width * 0.9),
				(int) (Toolkit.getDefaultToolkit().getScreenSize().height * 0.95));
		setLocationRelativeTo(null);

		panelGantt = new GanttPanel(this);
		add(panelGantt, BorderLayout.NORTH);

		JPanel panelStatus = new JPanel();

		panelTableReadyQueue = new TablePanel(this, "C. Listos");
		initTableStatusPanel(panelTableReadyQueue, COLUMN_NAME);
		panelStatus.add(panelTableReadyQueue);

		panelTableLockQueue = new TablePanel(this, "C. Bloqueados");
		initTableStatusPanel(panelTableLockQueue, COLUMN_NAME);
		panelStatus.add(panelTableLockQueue);

		add(panelStatus, BorderLayout.WEST);

		panelTable = new TablePanel(this, "Table");
		add(panelTable, BorderLayout.CENTER);

		panelAction = new ActionPanel(this);
		add(panelAction, BorderLayout.SOUTH);

		setResizable(false);
	}

	private void initTableStatusPanel(TablePanel table, String[] columnName) {
		table.setPreferredSize(new Dimension(getSize().width / 6, (int) (getSize().height * 0.42)));
		table.setTableModel(new DefaultTableModel(columnName, 0));
	}

	public GanttPanel getPanelGantt() {
		return panelGantt;
	}

	public void setPanelGantt(GanttPanel panelGantt) {
		this.panelGantt = panelGantt;
	}

	public TablePanel getPanelTableReadyQueue() {
		return panelTableReadyQueue;
	}

	public void setPanelTableReadyQueue(TablePanel panelTableReadyQueue) {
		this.panelTableReadyQueue = panelTableReadyQueue;
	}

	public TablePanel getPanelTableLockQueue() {
		return panelTableLockQueue;
	}

	public void setPanelTableLockQueue(TablePanel panelTableLockQueue) {
		this.panelTableLockQueue = panelTableLockQueue;
	}

	public TablePanel getPanelTable() {
		return panelTable;
	}

	public void setPanelTable(TablePanel panelTable) {
		this.panelTable = panelTable;
	}

	public ActionPanel getPanelAction() {
		return panelAction;
	}

	public void setPanelAction(ActionPanel panelAction) {
		this.panelAction = panelAction;
	}
}
