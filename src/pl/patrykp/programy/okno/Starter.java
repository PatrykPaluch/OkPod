package pl.patrykp.programy.okno;

public class Starter {

	public static void main(String[] args) {
		AppManager test = AppManager.getInstance();
		test.start();
		//Tests...
		test.createWindow().getJFrame().setLocationRelativeTo(null);
	}

}
