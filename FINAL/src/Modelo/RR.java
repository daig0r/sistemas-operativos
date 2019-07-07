package Modelo;

public class RR extends Scheduler {

	private final int QUANTUM = 4;

	public RR(String name, Scheduler nexScheduler, Model model) {
		super(name, nexScheduler, model);
	}

//	public Process pollByQuantum(Process process) {
//		Process aux = null;
//		try {
//			process.setBurstTimeExecuted(process.getBurstTimeExecuted() + QUANTUM);
//			aux = (Process) process.clone();
//			aux.setId(aux.getId() + "*");
//			aux.setBurstTime(process.getBurstTime() - QUANTUM);
//			process.setBurstTime(QUANTUM);
//			calcuteTime(process);
//			aux.setArrivalTime(currentFinalTime);
//			appendProcess(aux);
//		} catch (CloneNotSupportedException e) {
//			e.printStackTrace();
//		}
//		return aux;
//	}
}