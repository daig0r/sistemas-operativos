package Controlador;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

import Modelo.Model;
import Modelo.Process;
import Vista.Window;

public class Controller {

	private Model model;
	private Window view;
	private Thread criticalSection;

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
		if (view.getPanelTable().getTableModel() == null) {
			view.getPanelTable().setTableModel(new DefaultTableModel(columnName, 0));
			view.getPanelTableGantt().setInitialColumns(100);
		}
	}

	private void initAction() {
		if (model.getQueueReady().getSerialId() == 0) {
			for (int i = 1; i <= 5; i++) {
				view.getPanelTableReadyQueue().getTableModel().addRow(model.getQueueReady().addProcess().resume());
			}
		} else {
			JOptionPane.showMessageDialog(null, "¡No se puede inciar más de una vez!", "Iniciar",
					JOptionPane.WARNING_MESSAGE);
		}
	}

	private void pollAction() {
		if (!model.getQueueReady().isQueueEmpty()) {
			Process process = model.getQueueReady().pollProcess();
			paintProcess(process);
			view.getPanelTableReadyQueue().getTableModel().removeRow(0);

		} else {
			JOptionPane.showMessageDialog(null, "¡No hay ningún procesos por atender!", "Atender",
					JOptionPane.WARNING_MESSAGE);
		}
	}

	private void addAction() {
		view.getPanelTableReadyQueue().getTableModel().addRow(model.getQueueReady().addProcess().resume());
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
			model.getQueueReady().appendProcess(process);
			view.getPanelTableLockQueue().getTableModel().removeRow(0);
			view.getPanelTableReadyQueue().getTableModel().addRow(process.resume());
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

	private synchronized void paintProcess(Process process) {
		DefaultTableModel tableModelGantt = view.getPanelTableGantt().getTableModel();
		int row = getProcessRow(process.getId());
		criticalSection = new Thread(new Runnable() {
			@Override
			public void run() {
				for (int i = process.getArrivalTime(); i < process.getStartTime(); i++) {
					tableModelGantt.setValueAt("  ", row, i + 1);
					try {
						Thread.sleep(200);
					} catch (InterruptedException e) {
					}
				}
				view.getPanelAction().getBtnLock().setEnabled(true);
				for (int i = process.getStartTime(); i < process.getFinalTime(); i++) {
					process.setBurstTimeExecuted(process.getBurstTimeExecuted() + 1);
//					tableModelGantt.addColumn(i);
					tableModelGantt.setValueAt(" ", row, i + 1);
					try {
						Thread.sleep(500);
					} catch (InterruptedException e) {
						view.getPanelAction().getBtnLock().setEnabled(false);
						try {
							Process aux = (Process) process.clone();
							process.setBurstTime(process.getBurstTimeExecuted());
							if (process.getBurstTime() > 0) {
								model.getQueueReady().recalcuteTime(process);
								model.getQueueReady().createBlockProcess(aux);
								model.getQueueLock().appendProcess(aux);
								view.getPanelTableLockQueue().getTableModel().addRow(aux.resume());
								view.getPanelTable().getTableModel().addRow(process.resume());
							}
						} catch (CloneNotSupportedException e1) {
							System.err.println("Error al clonar - " + e1);
						}
						Thread.currentThread().stop();
					}
				}
				model.getQueueReady().recalcuteTime(process);
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
