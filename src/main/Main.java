package main;

public class Main {

	public static void main(String[] args) {
		new Thread(() -> new CommandLineController().handleCommandLineInput()).start();

		if(args.length == 0 || !args[0].equals("nogui")) {
			final MyFrame frame = new MyFrame();
			final Controller controller = new Controller(frame);
			frame.setController(controller);
		}
	}
}
