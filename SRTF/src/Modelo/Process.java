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

	public Process(int id, int arrivalTime, int burstTime) {
		this.id = "P" + id;
		this.arrivalTime = arrivalTimeHead = arrivalTime;
		this.burstTime = burstTime;
		startTime = 0;
		finalTime = 0;
		returnTime = 0;
		waitTime = 0;
		burstTimeExecuted = 0;
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
		this.waitTime = waitTime;
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

	public Object[] resume() {
		return new Object[] { id, arrivalTimeHead, burstTime, startTime, finalTime, returnTime, waitTime };
	}

	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}
}
