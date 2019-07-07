package Controlador;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

import Modelo.Model;
import Modelo.Process;
import Modelo.Scheduler;
import Vista.Window;

public class Controller {

	private Model model;
	private Window view;
	private Thread criticalSection;
	private boolean isAvalible;

	public Controller(Model model, Window view) {
		this.model = model;
		this.view = view;
		this.isAvalible = true;
	}

	private void setTablesModel() {
		model.setTableModelRR(view.getPanelTableRRQueue().getTableModel());
		model.setTableModelSJF(view.getPanelTablePriorityQueue().getTableModel());
		model.setTableModelFCFS(view.getPanelTableFCFSQueue().getTableModel());
		model.setTableModelLock(view.getPanelTableLockQueue().getTableModel());
	}

	public void initController() {
		setTablesModel();
		view.getPanelAction().getBtnInit().addActionListener(e -> initAction());
		view.getPanelAction().getBtnPoll().addActionListener(e -> pollAction());
		view.getPanelAction().getBtnAdd().addActionListener(e -> addAction());
		view.getPanelAction().getBtnLock().addActionListener(e -> lockAction());
		view.getPanelAction().getBtnUnLock().addActionListener(e -> unLockAction());
		view.getPanelAction().getBtnRestart().addActionListener(e -> restartAction());
		view.getPanelAction().getBtnExit().addActionListener(e -> exitAction());
		view.setVisible(true);
	}

	private void initAction() {
		if (model.getSerialId() == 0) {
			for (int i = 1; i <= 5; i++) {
				int random = model.random(1, 3);
				if (random == 1) {
					view.getPanelTableRRQueue().getTableModel()
							.addRow(model.getQueueReadyRR().createProcess().resume());
				} else if (random == 2) {
					view.getPanelTableFCFSQueue().getTableModel()
							.addRow(model.getQueueReadyFCFS().createProcess().resume());
				} else {
					view.getPanelTablePriorityQueue().getTableModel()
							.addRow(model.getQueueReadySJF().createProcess().resume());
				}
			}
		} else {
			JOptionPane.showMessageDialog(null, "¡No se puede inciar más de una vez!", "Iniciar",
					JOptionPane.WARNING_MESSAGE);
		}
	}

	private void pollAction() {
		if (isAvalible) {
			isAvalible = false;
			Scheduler scheduler;
			Process process;
			if (!model.getQueueReadyRR().isQueueEmpty()) {
				scheduler = model.getQueueReadyRR();
				process = scheduler.pollProcess();
				view.getPanelTableRRQueue().getTableModel().removeRow(0);
			} else if (!model.getQueueReadyFCFS().isQueueEmpty()) {
				scheduler = model.getQueueReadyFCFS();
				process = scheduler.pollProcess();
				view.getPanelTableFCFSQueue().getTableModel().removeRow(0);
			} else if (!model.getQueueReadySJF().isQueueEmpty()) {
				scheduler = model.getQueueReadySJF();
				process = scheduler.pollProcess();
				view.getPanelTablePriorityQueue().getTableModel().removeRow(0);
			} else {
				JOptionPane.showMessageDialog(null, "¡No hay ningún procesos por atender!", "Atender",
						JOptionPane.WARNING_MESSAGE);
				return;
			}

			if (scheduler != null) {
				paintProcess(process, scheduler);
			}
		}
	}

	private void addAction() {
		int random = model.random(1, 3);
		if (random == 1) {
			view.getPanelTableRRQueue().getTableModel().addRow(model.getQueueReadyRR().createProcess().resume());
		} else if (random == 2) {
			view.getPanelTableFCFSQueue().getTableModel().addRow(model.getQueueReadyFCFS().createProcess().resume());
		} else {
			view.getPanelTablePriorityQueue().getTableModel().addRow(model.getQueueReadySJF().createProcess().resume());
		}
	}

	private void lockAction() {
		if (view.getPanelAction().getBtnLock().isEnabled()) {
			criticalSection.interrupt();
		} else {
			JOptionPane.showMessageDialog(null, "¡No hay ningún procesos para bloquear!", "Bloquear",
					JOptionPane.WARNING_MESSAGE);
		}
	}

	private void unLockAction() {
		if (!model.getQueueLock().isQueueEmpty()) {
			Process process = model.getQueueLock().pollProcess();
			Scheduler scheduler = process.getScheduler();
			if (scheduler.getName().equals("RR")) {
				view.getPanelTableRRQueue().getTableModel().addRow(process.resume());
			} else if (scheduler.getName().equals("FCFS")) {
				view.getPanelTableFCFSQueue().getTableModel().addRow(process.resume());
			} else if (scheduler.getName().equals("SJF")) {
				view.getPanelTablePriorityQueue().getTableModel().addRow(process.resume());
			}
			scheduler.addProcess(process);
			process.aging();
			view.getPanelTableLockQueue().getTableModel().removeRow(0);
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
				setTablesModel();
				view.getPanelTable().getTableModel().setNumRows(0);
				view.getPanelTableRRQueue().getTableModel().setNumRows(0);
				view.getPanelTablePriorityQueue().getTableModel().setNumRows(0);
				view.getPanelTableFCFSQueue().getTableModel().setNumRows(0);
				view.getPanelTableLockQueue().getTableModel().setNumRows(0);
				view.getPanelTableGantt().getTableModel().setNumRows(0);
				view.getPanelTableGantt().setInitialColumns(100);
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

	private void paintProcess(Process process, Scheduler scheduler) {
		scheduler.calcuteTime(process);
		DefaultTableModel tableModelGantt = view.getPanelTableGantt().getTableModel();
		int row = getProcessRow(process.getId());
		criticalSection = new Thread(new Runnable() {
			@Override
			public void run() {
				view.getPanelAction().getBtnPoll().setEnabled(false);
				for (int i = process.getArrivalTime(); i < process.getStartTime(); i++) {
					tableModelGantt.setValueAt("  ", row, i + 1);
					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
					}
				}
				view.getPanelAction().getBtnPoll().setEnabled(true);
				view.getPanelAction().getBtnLock().setEnabled(true);
				for (int i = process.getStartTime(); i < process.getFinalTime(); i++) {
					process.setBurstTimeExecuted(process.getBurstTimeExecuted() + 1);
					tableModelGantt.setValueAt(" ", row, i + 1);
					tableModelGantt.fireTableDataChanged();
					try {
						Thread.sleep(400);
					} catch (InterruptedException e) {
						view.getPanelAction().getBtnLock().setEnabled(false);
						try {
							Process aux = (Process) process.clone();
							process.setBurstTime(process.getBurstTimeExecuted());
							if (process.getBurstTime() > 0) {
								scheduler.recalcuteTime(process);
								model.createBlockProcess(aux);
								view.getPanelTableLockQueue().getTableModel().addRow(aux.resume());
								view.getPanelTable().getTableModel().addRow(process.resume());
							}
							isAvalible = true;
						} catch (CloneNotSupportedException e1) {
							System.err.println("Error al clonar - " + e1);
						}
						Thread.currentThread().stop();
					}
				}
				isAvalible = true;
				scheduler.recalcuteTime(process);
				view.getPanelAction().getBtnLock().setEnabled(false);
				view.getPanelTable().getTableModel().addRow(process.resume());
			}			
		});
		criticalSection.start();
	}

	private int getProcessRow(String id) {
		int max = id.indexOf("*");
		int row = view.getPanelTableGantt().getTableModel().getRowCount();
		if (max != -1) {
			row = view.getPanelTableGantt().searchRow(0, id.substring(0, max));
		} else {
			view.getPanelTableGantt().getTableModel().addRow(new Object[] { id });
		}
		return row;
	}
}
