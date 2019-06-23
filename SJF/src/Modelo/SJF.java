package Modelo;

public class SJF {

	private Queue<Process> queue;
	private int currentFinalTime;
	private int serialId;

	public SJF() {
		queue = new Queue<Process>();
		currentFinalTime = 0;
		serialId = 0;
	}

	private int random(int min, int max) {
		return (int) (Math.random() * max + min);
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
		Process process = new Process(serialId + 1, serialId, random(1, 7), random(1, 4));
		queue.add(process);
		serialId++;
		return process.resume();
	}

	public Object[] appendProcess(Process process) {
		queue.add(process);
		return process.resume();
	}

	public Process pollProcess() {
		return queue.poll();
	}

	public Process pollProcessByPriority() {
		Process data = null;
		for (int i = 1; i <= 4; i++) {
			for (int j = 1; j <= queue.getSize(); j++) {
				data = queue.getData(j);
				if (data.getPriority() == i) {
					if (data.getArrivalTime() <= currentFinalTime) {
						queue.remove(j);
						return data;
					}
				}
			}
		}
		return data;
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