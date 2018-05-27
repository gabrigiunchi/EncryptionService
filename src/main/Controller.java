package main;

public class Controller {
	
	private MyFrame frame;
	private final AES cipher;
	
	public Controller() {
		this.cipher = AES.getInstance();
	}
	
	public Controller(final MyFrame frame) {
		this();
		this.frame = frame;
	}
	
	public void setView(final MyFrame frame) {
		this.frame = frame;
	}

	public void encrypt() {
		new Thread(() -> {
			try {
				this.cipher.setKey(this.frame.getKey());
				this.frame.setOutput(this.cipher.encrypt(this.frame.getInputText()));
			} catch (Exception e) {
				this.frame.showError(e.getMessage());
			}
		}).start();
		
	}
	
	public void decrypt() {
		new Thread(() -> {
			try {
				this.cipher.setKey(this.frame.getKey());
				this.frame.setOutput(this.cipher.decrypt(this.frame.getInputText()));
			} catch (Exception e) {
				this.frame.showError(e.getMessage());
			}
		}).start();
	}

}
