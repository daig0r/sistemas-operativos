package Modelo;

public class Model {

	private SJF queueReady;
	private SJF queueLock;
	
	public Model() {
		queueReady = new SJF();
		queueLock = new SJF();
	}

	public SJF getQueueReady() {
		return queueReady;
	}

	public void setQueueReady(SJF queueReady) {
		this.queueReady = queueReady;
	}

	public SJF getQueueLock() {
		return queueLock;
	}

	public void setQueueLock(SJF queueLock) {
		this.queueLock = queueLock;
	}

	public void finalizeQueues() throws Throwable {
		queueReady.finalizeQueue();
		queueLock.finalizeQueue();
	}
}
