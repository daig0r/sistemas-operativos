package Modelo;

public class SJF extends Scheduler {

	public SJF(String name, Scheduler nexScheduler, Model model) {
		super(name, nexScheduler, model);
		time = 9000;
	}

	@Override
	public Process createProcess() {
		Process process = super.createProcess();
		process.setPriority(model.random(1, 4));
		return process;
	}

	public Process pollProcessByPriority() {
		Process data = null;
		for (int i = 1; i <= 4; i++) {
			for (int j = 1; j <= queue.getSize(); j++) {
				data = queue.getData(j);
				if (data.getPriority() == i) {
					if (data.getArrivalTime() <= model.getCurrentFinalTime()) {
						queue.remove(j);
						return data;
					}
				}
			}
		}
		return data;
	}
}