package Controlador;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

import Modelo.Model;
import Modelo.Process;
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
		if (this.view.getPanelTable().getTableModel() == null)
			this.view.getPanelTable().setTableModel(new DefaultTableModel(columnName, 0));
	}

	private void initAction() {
		if (model.getQueueReady().getSerialId() == 0) {
			for (int i = 1; i <= 5; i++) {
				view.getPanelTableReadyQueue().getTableModel().addRow(model.getQueueReady().addProcess().tableResume());
			}
		} else {
			JOptionPane.showMessageDialog(null, "¡No se puede inciar más de una vez!", "Iniciar",
					JOptionPane.WARNING_MESSAGE);
		}
	}

	private void pollAction() {
		if (!model.getQueueReady().isQueueEmpty()) {
			view.getPanelTableReadyQueue().getTableModel().removeRow(0);
			Process process = model.getQueueReady().pollProcess();
			if (process.getBurstTime() > 4) {
				Object[] child = model.getQueueReady().pollByQuantum(process).tableResume();
				view.getPanelTableReadyQueue().getTableModel().addRow(child);
			} else {
				process.setBurstTimeExecuted(process.getBurstTimeExecuted() + process.getBurstTime());
				model.getQueueReady().calcuteTime(process);
			}
			view.getPanelTable().getTableModel().addRow(process.tableResume());
			view.getPanelTableGantt().paintProcess(process.resume());
		} else {
			JOptionPane.showMessageDialog(null, "¡No hay ningún procesos por atender!", "Atender",
					JOptionPane.WARNING_MESSAGE);
		}
	}

	private void addAction() {
		view.getPanelTableReadyQueue().getTableModel().addRow(model.getQueueReady().addProcess().tableResume());
	}

	private void lockAction() {
		if (!model.getQueueReady().isQueueEmpty()) {
			Process process = model.getQueueReady().pollProcess();
			model.getQueueLock().appendProcess(process);
			view.getPanelTableReadyQueue().getTableModel().removeRow(0);
			view.getPanelTableLockQueue().getTableModel().addRow(process.tableResume());
		} else {
			JOptionPane.showMessageDialog(null, "¡No hay ningún procesos para bloquear!", "Bloquear",
					JOptionPane.WARNING_MESSAGE);
		}
	}

	private void unLockAction() {
		if (!model.getQueueLock().isQueueEmpty()) {
			Process process = model.getQueueLock().pollProcess();
			model.getQueueReady().appendProcess(process);
			view.getPanelTableLockQueue().getTableModel().removeRow(0);
			view.getPanelTableReadyQueue().getTableModel().addRow(process.tableResume());
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
