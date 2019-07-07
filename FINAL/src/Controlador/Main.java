package Controlador;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import Modelo.Model;
import Vista.Window;

public class Main {

	public static void main(String[] args) {

		// Unifica la interfaz para Mac y para Windows.
		try {
			UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
		Controller controller = new Controller(new Model(), new Window("Multicolas"));
		controller.initController();
	}

}
