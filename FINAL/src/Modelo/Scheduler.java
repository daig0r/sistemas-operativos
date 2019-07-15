package Modelo;

import javax.swing.table.DefaultTableModel;

public abstract class Scheduler {

	protected String name;
	protected Model model;
	protected Queue<Process> queue;
	protected Scheduler nextScheduler;
	protected DefaultTableModel tableModel;
	protected int time;

	public Scheduler(String name, Scheduler nexScheduler, Model model) {
		this.name = name;
		this.nextScheduler = nexScheduler;
		this.model = model;
		queue = new Queue<Process>();
	}

	public void calcuteTime(Process process) {
		process.setStartTime(model.getCurrentFinalTime());
		process.setFinalTime(process.getBurstTime() + process.getStartTime());
		process.setReturnTime(process.getFinalTime() - process.getArrivalTimeHead());
		process.setWaitTime(process.getReturnTime() - process.getBurstTimeExecuted());
		model.setCurrentFinalTime(process.getFinalTime());
		model.getSavedStates().add(model.saveToMemento());
	}

	public void recalcuteTime(Process process) {
		model.retoreFromMemento(model.getSavedStates().getData(model.getSavedStates().getSize() - 1));
		calcuteTime(process);
	}

	public Process createProcess() {
		Process process = new Process(model.getSerialId() + 1, model.getSerialId(), model.random(1, 10), this);
		addProcess(process);
		model.increaseSerialId();
		return process;
	}

	public void addProcess(Process process) {
		queue.add(process);
	}

	public Process pollProcess() {
		Process process = queue.poll();
		process.getTimeOut().interrupt();
		return process;
	}

	public void moveProcess() {
		if (nextScheduler != null) {
			if (!isQueueEmpty()) {
				Process process = pollProcess();
				process.setScheduler(nextScheduler);
				nextScheduler.addProcess(process);
				if (nextScheduler.nextScheduler != null) {
					process.aging();			
				}
				tableModel.removeRow(0);
				nextScheduler.tableModel.addRow(process.resume());
			}
		}
	}

	public boolean isQueueEmpty() {
		return queue.isEmpty();
	}

	public String getName() {
		return name;
	}

	public void finalizeQueue() throws Throwable {
		queue.finalize();
	}
}
