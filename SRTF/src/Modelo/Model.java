package Modelo;

public class Model {

	private SRTF queueReady;
	private SRTF queueLock;

	public Model() {
		queueReady = new SRTF();
		queueLock = new SRTF();
	}

	public SRTF getQueueReady() {
		return queueReady;
	}

	public SRTF getQueueLock() {
		return queueLock;
	}

	public void finalizeQueues() throws Throwable {
		queueReady.finalizeQueue();
		queueLock.finalizeQueue();
	}
}
