package Vista;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.table.DefaultTableModel;

public class Window extends JFrame {

	private final static String[] COLUMN_NAME = { "Proceso", "T. Llegada", "T. Rafaga", "Prioridad", "T. Comienzo",
			"T. Final", "T. Retorno", "T. Espera", "P.P" };

	private GanttPanel panelTableGantt;
	private TablePanel panelTableRRQueue;
	private TablePanel panelTablePriorityQueue;
	private TablePanel panelTableFCFSQueue;
	private TablePanel panelTableLockQueue;
	private TablePanel panelTable;
	private ActionPanel panelAction;

	public Window(String title) {
		setTitle(title);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new BorderLayout());
		setSize((int) (Toolkit.getDefaultToolkit().getScreenSize().width * 1),
				(int) (Toolkit.getDefaultToolkit().getScreenSize().height * 0.84));
		setLocationRelativeTo(null);

		panelTableGantt = new GanttPanel(this, "D. Gantt");
		add(panelTableGantt, BorderLayout.NORTH);

		JPanel panelStatus = new JPanel();

		panelTableRRQueue = new TablePanel(this, "C. RR");
		initTableStatusPanel(panelTableRRQueue, new String[] { "Proceso", "T. Llegada", "T. Rafaga" });
		panelStatus.add(panelTableRRQueue);

		panelTableFCFSQueue = new TablePanel(this, "C. FCFS");
		initTableStatusPanel(panelTableFCFSQueue, new String[] { "Proceso", "T. Llegada", "T. Rafaga" });
		panelStatus.add(panelTableFCFSQueue);

		panelTablePriorityQueue = new TablePanel(this, "C. Priority");
		initTableStatusPanel(panelTablePriorityQueue,
				new String[] { "Proceso", "T. Llegada", "T. Rafaga", "Prioridad" });
		panelStatus.add(panelTablePriorityQueue);

		panelTableLockQueue = new TablePanel(this, "C. Bloqueados");
		initTableStatusPanel(panelTableLockQueue, new String[] { "Proceso", "T. Llegada", "T. Rafaga" });
		panelStatus.add(panelTableLockQueue);

		add(panelStatus, BorderLayout.WEST);

		panelTable = new TablePanel(this, "Table");
		panelTable.setTableModel(new DefaultTableModel(COLUMN_NAME, 0));
		add(panelTable, BorderLayout.CENTER);

		panelAction = new ActionPanel(this);
		add(panelAction, BorderLayout.SOUTH);

		setResizable(false);
	}

	private void initTableStatusPanel(TablePanel table, String[] columnName) {
		table.setPreferredSize(new Dimension((int) (getSize().width / 6.5), (int) (getSize().height * 0.42)));
		table.setTableModel(new DefaultTableModel(columnName, 0));
	}

	public GanttPanel getPanelTableGantt() {
		return panelTableGantt;
	}

	public void setPanelTableGantt(GanttPanel panelTableGantt) {
		this.panelTableGantt = panelTableGantt;
	}

	public TablePanel getPanelTableRRQueue() {
		return panelTableRRQueue;
	}

	public void setPanelTableRRQueue(TablePanel panelTableRRQueue) {
		this.panelTableRRQueue = panelTableRRQueue;
	}

	public TablePanel getPanelTablePriorityQueue() {
		return panelTablePriorityQueue;
	}

	public void setPanelTablePriorityQueue(TablePanel panelTablePriorityQueue) {
		this.panelTablePriorityQueue = panelTablePriorityQueue;
	}

	public TablePanel getPanelTableFCFSQueue() {
		return panelTableFCFSQueue;
	}

	public void setPanelTableFCFSQueue(TablePanel panelTableFCFSQueue) {
		this.panelTableFCFSQueue = panelTableFCFSQueue;
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
