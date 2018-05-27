package main;

import java.io.FileNotFoundException;
import java.util.Scanner;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.swing.JFrame;

public class CommandLineController {

	private static final String SET_KEY_COMMAND = "setkey";
	private static final String ENCRYPT_COMMAND = "enc";
	private static final String ENCRYPT_FILE_COMMAND = "encf";
	private static final String DECRYPT_COMMAND = "dec";
	private static final String DECRYPT_FILE_COMMAND = "decf";
	private static final String EXIT_COMMAND = "exit";
	private static final String QUIT_COMMAND = "quit";
	private static final String OPEN_GUI_COMMAND = "gui";

	public void handleCommandLineInput() {
    	final Scanner in = new Scanner(System.in);
    	
    	System.out.println("Ready");
    	
        boolean exit = false;
        AES cipher = AES.getInstance();
        
        while(!exit) {
        	final String input = in.nextLine();
        	final String[] params = input.split(" ");
        	final String command = params[0];
        	
        	switch (command) {
        		case SET_KEY_COMMAND: {
        			String key = "";
        			if(params.length > 1) {
        				key = params[1];
        			} else {
        				System.out.print("Enter key: ");
        				key = new String(System.console().readPassword());
        			}
        			cipher.setKey(key);
        			System.out.println("Key initialized"); 
        			break;
        		}
        		
        		case ENCRYPT_COMMAND: {
        			if(cipher.isKeyInitialized()) {
        				System.out.println(cipher.encrypt(params[1]));
        			} else {
        				System.out.println("Key not initialized");
        			}
        			break;
        		}
        		
        		case ENCRYPT_FILE_COMMAND: {
        			if(cipher.isKeyInitialized()) {
        				String[] vett = input.split(" ");
        				
        				if(vett.length < 2) {
        					System.out.println("Invalid input! The command is: " + ENCRYPT_FILE_COMMAND + " <source> <destination>");
        				} else {
        					String source = vett[1];
            				String dest = source;
            				
            				if(vett.length == 3) {
            					dest = vett[2];
            				}
            				
            				try {
								FileManager.writeToFile(dest, cipher.encrypt(FileManager.readFromFile(source)));
								System.out.println("Encryption ok");
							} catch (FileNotFoundException e) {
								System.err.println(e.getMessage());
							}	
        				}
        			} else {
        				System.out.println("Key not initialized");
        			}
        			break;
        		}
        		
        		case DECRYPT_COMMAND: {
        			if(cipher.isKeyInitialized()) {
        				try {
							System.out.println(cipher.decrypt(params[1]));
						} catch (IllegalBlockSizeException | BadPaddingException e) {
							System.err.println("Decrypton failed: " + e.getMessage());
						}
        			} else {
        				System.out.println("Key not initialized");
        			}
        			break;
        		}
        		
        		case DECRYPT_FILE_COMMAND: {
        			if(cipher.isKeyInitialized()) {
        				String[] vett = input.split(" ");
        				
        				if(vett.length < 2) {
        					System.out.println("Invalid input! The command is: " + ENCRYPT_FILE_COMMAND + " <source> <destination>");
        				} else {
        					String source = vett[1];
        					String dest = source;
        					if(vett.length == 3) {
        						dest = vett[2];
        					}
        					
            				try {
            					final String ciphertext = FileManager.readFromFile(source);
            					final String plaintext = cipher.decrypt(ciphertext);
    							FileManager.writeToFile(dest, plaintext);
    							System.out.println("Decryption ok");
    						} catch (FileNotFoundException | IllegalBlockSizeException | BadPaddingException e) {
								System.err.println("Decryption failed: " + e.getMessage());
							}
        				}
        			} else {
        				System.out.println("Key not initialized");
        			}
        			break;
        		}
        		
        		case EXIT_COMMAND: exit = true; break;
        		case QUIT_COMMAND: exit = true; break;
        		case OPEN_GUI_COMMAND: {
        			MyFrame frame = new MyFrame(JFrame.DISPOSE_ON_CLOSE);
        			Controller controller = new Controller(frame);
        			frame.setController(controller);
        			break;
        		}
        		default: System.out.println("Command not found"); break;
			}
        }
        
        in.close();
	}
}