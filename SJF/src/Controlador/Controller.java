package Controlador;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

import Modelo.Model;
import Vista.Window;

public class Controller {

	private Model model;
	private Window view;

	public Controller(Model model, Window view) {
		this.model = model;
		this.view = view;
	}

	public void initController(String[] columnName) {
		setTableColumName(columnName);
		view.getPanelAction().getBtnInit().addActionListener(e -> initAction());
		view.getPanelAction().getBtnPoll().addActionListener(e -> pollAction());
		view.getPanelAction().getBtnAdd().addActionListener(e -> addAction());
		view.getPanelAction().getBtnLock().addActionListener(e -> lockAction());
		view.getPanelAction().getBtnUnLock().addActionListener(e -> unLockAction());
		view.getPanelAction().getBtnRestart().addActionListener(e -> restartAction());
		view.getPanelAction().getBtnExit().addActionListener(e -> exitAction());
		view.setVisible(true);
	}

	private void setTableColumName(String[] columnName) {
		if (view.getPanelTable().getTableModel() == null)
			view.getPanelTable().setTableModel(new DefaultTableModel(columnName, 0));
			view.getPanelTableGantt().setTableModel(new DefaultTableModel(new Object[] { "PID" }, 0));
	}

	private void initAction() {
		if (model.getQueueReady().getSerialId() == 0) {
			for (int i = 1; i <= 5; i++)
				view.getPanelTableReadyQueue().getTableModel().addRow(model.getQueueReady().addProcess());
		} else {
			JOptionPane.showMessageDialog(null, "¡No se puede inciar más de una vez!", "Iniciar",
					JOptionPane.WARNING_MESSAGE);
		}
	}

	private void pollAction() {
		try {
			if (!model.getQueueReady().isQueueEmpty()) {
				Object[] data = model.getQueueReady().getDataProcess(model.getQueueReady().pollProcessByPriority());
				view.getPanelTableReadyQueue().removeRow(0, String.valueOf(data[0]));
				view.getPanelTable().getTableModel().addRow(data);
				view.getPanelTableGantt().paintProcess(data);

			} else {
				JOptionPane.showMessageDialog(null, "¡No hay ningún procesos por atender!", "Atender",
						JOptionPane.WARNING_MESSAGE);
			}

		} catch (Exception e) {
			System.out.println("algo");
		}

	}

	private void addAction() {
		view.getPanelTableReadyQueue().getTableModel().addRow(model.getQueueReady().addProcess());
	}

	private void lockAction() {
		if (!model.getQueueReady().isQueueEmpty()) {
			Object[] data = model.getQueueLock().appendProcess((model.getQueueReady().pollProcessByPriority()));
			view.getPanelTableReadyQueue().removeRow(0, String.valueOf(data[0]));
			view.getPanelTableLockQueue().getTableModel().addRow(data);
		} else {
			JOptionPane.showMessageDialog(null, "¡No hay ningún procesos para bloquear!", "Bloquear",
					JOptionPane.WARNING_MESSAGE);
		}
	}

	private void unLockAction() {
		if (!model.getQueueLock().isQueueEmpty()) {
			view.getPanelTableLockQueue().getTableModel().removeRow(0);
			view.getPanelTableReadyQueue().getTableModel()
					.addRow(model.getQueueReady().appendProcess((model.getQueueLock().pollProcess())));
		} else {
			JOptionPane.showMessageDialog(null, "¡No hay ningún procesos por desbloquear!", "Desbloquear",
					JOptionPane.WARNING_MESSAGE);
		}
	}

	private void restartAction() {
		int resp = JOptionPane.showConfirmDialog(null, "¿Está seguro que quiere reiniciar del programa?", "Reiniciar",
				JOptionPane.YES_NO_OPTION);
		if (resp == JOptionPane.YES_OPTION) {
			try {
				model.finalizeQueues();
				model = new Model();
				view.getPanelTable().getTableModel().setNumRows(0);
				view.getPanelTableReadyQueue().getTableModel().setNumRows(0);
				view.getPanelTableLockQueue().getTableModel().setNumRows(0);
				view.getPanelTableGantt().getTableModel().setNumRows(0);
				view.getPanelTableGantt().getTableModel().setColumnCount(1);
			} catch (Throwable e) {
				e.printStackTrace();
			}
		}
	}

	private void exitAction() {
		int resp = JOptionPane.showConfirmDialog(null, "¿Está seguro que quiere salir del programa?", "Salir",
				JOptionPane.YES_NO_OPTION);
		if (resp == JOptionPane.YES_OPTION)
			try {
				model.finalizeQueues();
				System.exit(0);
			} catch (Throwable e) {
				e.printStackTrace();
			}
	}
}
