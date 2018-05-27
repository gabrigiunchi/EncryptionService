package main;

import javax.swing.JFrame;
import java.awt.GridLayout;
import javax.swing.JScrollPane;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JTextArea;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.border.EmptyBorder;
import javax.swing.ScrollPaneConstants;
import java.awt.Component;
import javax.swing.Box;
import java.awt.FlowLayout;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.JPasswordField;

public class MyFrame {
	
	private static final char DEFAULT_ECHO = new JPasswordField().getEchoChar();

	private JFrame frame;
	private JTextArea inputTextArea;
	private JTextArea outputTextArea;
	private JPasswordField keyField;
	private JButton toggleKeyField;
	private Controller controller;

	public MyFrame() {
		this(JFrame.EXIT_ON_CLOSE);
	}
	
	public MyFrame(final int closeOperation) {
		this.initialize(closeOperation);
	}
	
	public void setController(final Controller controller) {
		this.controller = controller;
	}
	
	private void initialize(final int closeOperation) {
		frame = new JFrame();
		frame.setBounds(100, 100, 650, 500);
		frame.setDefaultCloseOperation(closeOperation);
		frame.getContentPane().setLayout(new GridLayout(3, 1, 0, 0));
		
		final JPanel commands = new JPanel();
		FlowLayout fl_commands = (FlowLayout) commands.getLayout();
		fl_commands.setAlignment(FlowLayout.LEFT);
		frame.getContentPane().add(commands);
		
		final Component horizontalStrut_1 = Box.createHorizontalStrut(10);
		commands.add(horizontalStrut_1);
		
		final JLabel labelKey = new JLabel("Key");
		commands.add(labelKey);
		
		toggleKeyField = new JButton("Show key");
		toggleKeyField.addActionListener(e -> toggleKeyField());
		
		keyField = new JPasswordField();
		keyField.setColumns(15);
		commands.add(keyField);
		commands.add(toggleKeyField);
		
		final Component horizontalStrut = Box.createHorizontalStrut(100);
		commands.add(horizontalStrut);
		
		final JButton encryptButton = new JButton("Encrypt");
		encryptButton.addActionListener(e -> controller.encrypt());
		commands.add(encryptButton);
		
		final JButton decryptButton = new JButton("Decrypt");
		decryptButton.addActionListener(e -> controller.decrypt());
		commands.add(decryptButton);
		
		final JScrollPane input = new JScrollPane();
		input.setToolTipText("");
		input.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		frame.getContentPane().add(input);
		
		inputTextArea = new JTextArea();
		inputTextArea.setBorder(new EmptyBorder(4, 4, 4, 4));
		inputTextArea.setBackground(Color.WHITE);
		inputTextArea.setLineWrap(true);
		input.setViewportView(inputTextArea);
		
		final JLabel labelInput = new JLabel("Input");
		labelInput.setHorizontalAlignment(SwingConstants.CENTER);
		input.setColumnHeaderView(labelInput);
		
		final JScrollPane output = new JScrollPane();
		output.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		frame.getContentPane().add(output);
		
		outputTextArea = new JTextArea();
		outputTextArea.setBorder(new EmptyBorder(4, 4, 4, 4));
		outputTextArea.setTabSize(10);
		outputTextArea.setLineWrap(true);
		output.setViewportView(outputTextArea);
		
		final JLabel labelOutput = new JLabel("Output");
		labelOutput.setHorizontalAlignment(SwingConstants.CENTER);
		output.setColumnHeaderView(labelOutput);
		
		this.frame.setVisible(true);
	}

	private void toggleKeyField() {
		final boolean isKeyVisible = this.keyField.getEchoChar() == 0;
		this.keyField.setEchoChar(isKeyVisible? DEFAULT_ECHO : (char)0);
		this.toggleKeyField.setText(isKeyVisible? "Show key" : "Hide key");
	}

	public String getKey() {
		return new String(this.keyField.getPassword());
	}
	
	public String getInputText() {
		return this.inputTextArea.getText();
	}
	
	public String getOutputText() {
		return this.outputTextArea.getText();
	}
	
	public void setOutput(final String s) {
		SwingUtilities.invokeLater(() -> this.outputTextArea.setText(s));
	}
	
	public void showError(final String error) {
		SwingUtilities.invokeLater(() -> {
			JOptionPane.showMessageDialog(frame,
				    error,
				    "Error",
				    JOptionPane.ERROR_MESSAGE);
		});	
	}
	
}
