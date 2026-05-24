package io.github.T1n3core.DB_Manager.main;

import javax.swing.JFrame;

/**
 * Main window of the application.
 * Sets up all UI components in the constructor.
 */
public class MainFrame extends JFrame {
	public MainFrame() {
		setName("DB Manager");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(900, 600);
		setVisible(true);
	}
}
