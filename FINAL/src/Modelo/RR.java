package Modelo;

public class RR extends Scheduler {

	private final int QUANTUM = 4;
	public Process child;

	public RR(String name, Scheduler nexScheduler, Model model) {
		super(name, nexScheduler, model);
		time = 0;
	}

	public Process pollByQuantum() {
		child = null;
		Process process = pollProcess();
		if (process.getBurstTime() > 4) {
			try {
				child = (Process) process.clone();
				child.setId(process.getId() + "*");
				child.setBurstTime(process.getBurstTime() - QUANTUM);
				child.setBurstTimeExecuted(process.getBurstTimeExecuted() + QUANTUM);
				process.setBurstTime(QUANTUM);
				child.setArrivalTime(process.getBurstTime() + model.getCurrentFinalTime());
			} catch (CloneNotSupportedException e) {
				e.printStackTrace();
			}
		}
		return process;
	}

	public void updateChildProcess(Process process) {
		child.setBurstTime(child.getBurstTime() + process.getBurstTime() - process.getBurstTimeExecuted());
		child.setBurstTimeExecuted(process.getBurstTimeExecuted());
		child.setArrivalTime(model.getCurrentFinalTime());
	}

	public void addChildProcess() {
		if (child != null) {
			queue.add(child);
			tableModel.addRow(child.resume());
		}
	}
}