package Modelo;

public class Memento {
	
	private final int state;

	public Memento(int state) {
		this.state = state;
	}

	public int getSavedState() {
		return state;
	}
}