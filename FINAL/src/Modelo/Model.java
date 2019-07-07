package Modelo;

import javax.swing.table.DefaultTableModel;

public class Model {

	private RR queueReadyRR;
	private FCFS queueReadyFCFS;
	private SJF queueReadySJF;
	private FCFS queueLock;
	private Queue<Memento> savedStates;
	private int serialId;
	private int currentFinalTime;

	public Model() {
		queueLock = new FCFS("Lock", null, this);
		queueReadyRR = new RR("RR", queueLock, this);
		queueReadyFCFS = new FCFS("FCFS", queueReadyRR, this);
		queueReadySJF = new SJF("SJF", queueReadyFCFS, this);
		savedStates = new Queue<Memento>();
		serialId = 0;
		currentFinalTime = 0;
		savedStates.add(saveToMemento());
	}
	
	public int random(int min, int max) {
		return (int) (Math.random() * max + min);
	}

	public void createBlockProcess(Process process) {
		process.setId(process.getId() + "*");
		process.setBurstTime(process.getBurstTime() - process.getBurstTimeExecuted());
		process.setArrivalTime(currentFinalTime);
		queueLock.addProcess(process);
	}

	public RR getQueueReadyRR() {
		return queueReadyRR;
	}

	public void setQueueReadyRR(RR queueReadyRR) {
		this.queueReadyRR = queueReadyRR;
	}

	public void setTableModelRR(DefaultTableModel tableModel) {
		queueReadyRR.tableModel = tableModel;
	}

	public SJF getQueueReadySJF() {
		return queueReadySJF;
	}

	public void setQueueReadySJF(SJF queueReadySJF) {
		this.queueReadySJF = queueReadySJF;
	}

	public void setTableModelSJF(DefaultTableModel tableModel) {
		queueReadySJF.tableModel = tableModel;
	}

	public FCFS getQueueReadyFCFS() {
		return queueReadyFCFS;
	}

	public void setQueueReadyFCFS(FCFS queueReadyFCFS) {
		this.queueReadyFCFS = queueReadyFCFS;
	}

	public void setTableModelFCFS(DefaultTableModel tableModel) {
		queueReadyFCFS.tableModel = tableModel;
	}

	public FCFS getQueueLock() {
		return queueLock;
	}

	public void setQueueLock(FCFS queueLock) {
		this.queueLock = queueLock;
	}

	public void setTableModelLock(DefaultTableModel tableModel) {
		queueLock.tableModel = tableModel;
	}

	public Queue<Memento> getSavedStates() {
		return savedStates;
	}

	public int getSerialId() {
		return serialId;
	}

	public void increaseSerialId() {
		serialId++;
	}

	public int getCurrentFinalTime() {
		return currentFinalTime;
	}

	public void setCurrentFinalTime(int currentFinalTime) {
		this.currentFinalTime = currentFinalTime;
	}

	public Memento saveToMemento() {
		return new Memento(currentFinalTime);
	}

	public void retoreFromMemento(Memento memento) {
		currentFinalTime = memento.getSavedState();
	}

	public void finalizeQueues() throws Throwable {
		queueReadyRR.finalizeQueue();
		queueReadySJF.finalizeQueue();
		queueReadyFCFS.finalizeQueue();
		queueLock.finalizeQueue();
		savedStates.finalize();
	}
}
