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

	public SJF getQueueLock() {
		return queueLock;
	}

	public void finalizeQueues() throws Throwable {
		queueReady.finalizeQueue();
		queueLock.finalizeQueue();
	}
}
