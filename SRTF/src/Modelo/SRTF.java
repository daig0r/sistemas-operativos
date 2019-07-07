package Modelo;

public class SRTF {

	private Queue<Process> queue;
	private Queue<Memento> savedStates;
	private int currentFinalTime;
	private int serialId;

	public SRTF() {
		queue = new Queue<Process>();
		savedStates = new Queue<Memento>();
		currentFinalTime = 0;
		serialId = 0;
		savedStates.add(saveToMemento());
	}

	private int random(int min, int max) {
		return (int) (Math.random() * max + min);
	}

	public void calcuteTime(Process process) {
		process.setStartTime(currentFinalTime);
		process.setFinalTime(process.getBurstTime() + process.getStartTime());
		process.setReturnTime(process.getFinalTime() - process.getArrivalTimeHead());
		process.setWaitTime(process.getReturnTime() - process.getBurstTimeExecuted());
		currentFinalTime = process.getFinalTime();
		savedStates.add(saveToMemento());
	}

	public void recalcuteTime(Process process) {
		retoreFromMemento(savedStates.getData(savedStates.getSize() - 1));
		calcuteTime(process);
	}

	public Process addProcess() {
		Process process = new Process(serialId + 1, serialId, random(1, 12));
		queue.add(process);
		serialId++;
		return process;
	}

	public void appendProcess(Process process) {
		queue.add(process);
	}

	public Process pollProcess() {
		return queue.poll();
	}

	public void createBlockProcess(Process process) {
		process.setId(process.getId() + "*");
		process.setBurstTime(process.getBurstTime() - process.getBurstTimeExecuted());
		process.setArrivalTime(currentFinalTime);
	}

	private Memento saveToMemento() {
		return new Memento(currentFinalTime);
	}

	private void retoreFromMemento(Memento memento) {
		currentFinalTime = memento.getSavedState();
	}

	public int getSerialId() {
		return serialId;
	}

	public boolean isQueueEmpty() {
		return queue.isEmpty();
	}

	public void finalizeQueue() throws Throwable {
		queue.finalize();
		savedStates.finalize();
	}
}