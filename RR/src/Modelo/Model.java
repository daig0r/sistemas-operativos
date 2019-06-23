package Modelo;

public class Model {

	private RR queueReady;
	private RR queueLock;
	
	public Model() {
		queueReady = new RR();
		queueLock = new RR();
	}

	public RR getQueueReady() {
		return queueReady;
	}

	public void setQueueReady(RR queueReady) {
		this.queueReady = queueReady;
	}

	public RR getQueueLock() {
		return queueLock;
	}

	public void setQueueLock(RR queueLock) {
		this.queueLock = queueLock;
	}

	public void finalizeQueues() throws Throwable {
		queueReady.finalizeQueue();
		queueLock.finalizeQueue();
	}
}
