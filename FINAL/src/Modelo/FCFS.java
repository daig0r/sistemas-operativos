package Modelo;

public class FCFS extends Scheduler {

	public FCFS(String name, Scheduler nexScheduler, Model model) {
		super(name, nexScheduler, model);
		time = 7000;
	}
}