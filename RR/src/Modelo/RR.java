package Modelo;

public class RR {

	private Queue<Process> queue;
	private final int QUANTUM = 4;
	private int currentFinalTime = 0;
	private int serialId;

	public RR() {
		queue = new Queue<Process>();
		serialId = 0;
	}

	private int randomBurst() {
		return (int) (Math.random() * 10 + 1);
	}

	public Object[] calcuteTime(Process process) {
		process.setStartTime(currentFinalTime);
		process.setFinalTime(process.getBurstTime() + process.getStartTime());
		process.setReturnTime(process.getFinalTime() - process.getArrivalTimeHead());
		process.setWaitTime(process.getReturnTime() - process.getBurstTimeExecuted());
		currentFinalTime = process.getFinalTime();
		return process.resume();
	}

	public Process pollByQuantum(Process process) {
		Process aux = null;
		try {
			process.setBurstTimeExecuted(process.getBurstTimeExecuted() + QUANTUM);
			aux = (Process) process.clone();
			aux.setId(aux.getId() + "*");
			aux.setBurstTime(process.getBurstTime() - QUANTUM);
			process.setBurstTime(QUANTUM);
			calcuteTime(process);
			aux.setArrivalTime(currentFinalTime);
			appendProcess(aux);
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return aux;
	}

	public Process addProcess() {
		Process process = new Process(serialId + 1, serialId, randomBurst());
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

	public int getSerialId() {
		return serialId;
	}

	public int getCurrentFinalTime() {
		return currentFinalTime;
	}

	public boolean isQueueEmpty() {
		return queue.isEmpty();
	}

	public void finalizeQueue() throws Throwable {
		queue.finalize();
	}
}