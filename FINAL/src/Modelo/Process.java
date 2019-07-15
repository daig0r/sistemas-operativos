package Modelo;

public class Process implements Cloneable {

	private String id;
	private int arrivalTime;
	private int burstTime;
	private int startTime;
	private int finalTime;
	private int returnTime;
	private int waitTime;
	private int arrivalTimeHead;
	private int burstTimeExecuted;
	private int priority;
	private Thread timeOut;
	private Scheduler scheduler;

	public Process(int id, int arrivalTime, int burstTime, Scheduler scheduler) {
		this.id = "P" + id;
		this.arrivalTime = arrivalTimeHead = arrivalTime;
		this.burstTime = burstTime;
		this.scheduler = scheduler;
		startTime = 0;
		finalTime = 0;
		returnTime = 0;
		waitTime = 0;
		burstTimeExecuted = 0;
		priority = 0;
		aging();
	}

	public void aging() {
		timeOut = new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					Thread.sleep(scheduler.time + scheduler.queue.getSize() * 1500);
				} catch (InterruptedException e) {
					Thread.currentThread().stop();
				} finally {
					scheduler.moveProcess();
				}
			}
		});
		timeOut.start();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getArrivalTime() {
		return arrivalTime;
	}

	public void setArrivalTime(int arrivalTime) {
		this.arrivalTime = arrivalTime;
	}

	public int getBurstTime() {
		return burstTime;
	}

	public void setBurstTime(int burstTime) {
		this.burstTime = burstTime;
	}

	public int getStartTime() {
		return startTime;
	}

	public void setStartTime(int startTime) {
		this.startTime = startTime;
	}

	public int getFinalTime() {
		return finalTime;
	}

	public void setFinalTime(int finalTime) {
		this.finalTime = finalTime;
	}

	public int getReturnTime() {
		return returnTime;
	}

	public void setReturnTime(int returnTime) {
		this.returnTime = returnTime;
	}

	public int getWaitTime() {
		return waitTime;
	}

	public void setWaitTime(int waitTime) {
		if (waitTime < 0) {
			this.waitTime = 0;
		} else {
			this.waitTime = waitTime;
		}
	}

	public int getArrivalTimeHead() {
		return arrivalTimeHead;
	}

	public void setArrivalTimeHead(int arrivalTimeHead) {
		this.arrivalTimeHead = arrivalTimeHead;
	}

	public int getBurstTimeExecuted() {
		return burstTimeExecuted;
	}

	public void setBurstTimeExecuted(int burstTimeExecuted) {
		this.burstTimeExecuted = burstTimeExecuted;
	}

	public int getPriority() {
		return priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}

	public Thread getTimeOut() {
		return timeOut;
	}

	public void setTimeOut(Thread timeOut) {
		this.timeOut = timeOut;
	}

	public Scheduler getScheduler() {
		return scheduler;
	}

	public void setScheduler(Scheduler scheduler) {
		this.scheduler = scheduler;
	}

	public Object[] resume() {
		return new Object[] { id, arrivalTimeHead, burstTime, priority, startTime, finalTime, returnTime, waitTime,
				scheduler.name };
	}

	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}
}
