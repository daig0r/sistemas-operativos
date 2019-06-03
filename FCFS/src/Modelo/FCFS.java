package Modelo;

public class FCFS {

	private Queue<Process> queue;
	private int currentFinalTime = 0;

	public FCFS() {
		queue = new Queue<Process>();
	}

	private int randomBurst() {
		return (int) (Math.random() * 10 + 1);
	}

	private void calcuteTime(Process process) {
		process.setStartTime(currentFinalTime);
		process.setFinalTime(process.getBurstTime() + process.getStartTime());
		process.setReturnTime(process.getFinalTime() - process.getArrivalTime());
		process.setWaitTime(process.getReturnTime() - process.getBurstTime());
		currentFinalTime = process.getFinalTime();
	}

	public Object[] getDataProcess(Process process) {
		calcuteTime(process);
		return process.resume();
	}

	public Object[] addProcess() {
		Process process = new Process(queue.getSerialId() + 1, queue.getSerialId(), randomBurst());
		queue.add(process);
		return process.resume();
	}

	public Object[] appendProcess(Process process) {
		queue.append(process);
		return process.resume();
	}

	public Process pollProcess() {
		return queue.poll();
	}

	public int getQueueSerialId() {
		return queue.getSerialId();
	}

	public boolean isQueueEmpty() {
		return queue.isEmpty();
	}

	public void finalizeQueue() throws Throwable {
		queue.finalize();
	}
}