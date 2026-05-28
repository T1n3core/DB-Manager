package io.github.T1n3core.DB_Manager.main;

import java.awt.BorderLayout;
import java.awt.CardLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import io.github.T1n3core.DB_Manager.ui.LoginPanel;

/**
 * Main window of the application.
 * Sets up all UI components in the constructor.
 */
public class MainFrame extends JFrame {
	private JMenuBar menuBar;
	private JButton settingsButton;
	private JButton helpButton;
	private JButton creditsButton;
	private JPanel containerPanel;
	private LoginPanel loginPanel;

	public MainFrame() {
		setName("DB Manager");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(900, 600);

		initComponents();
		initLayout();
		initActions();

		setVisible(true);
	}

	private void initComponents() {
		menuBar = new JMenuBar();
		settingsButton = new JButton("nastavitve");
		helpButton = new JButton("pomoč");
		creditsButton = new JButton("vizitka");
		containerPanel = new JPanel(new CardLayout());
		loginPanel = new LoginPanel();
	}

	private void initLayout() {
		setLayout(new BorderLayout());

		menuBar.add(settingsButton);
		menuBar.add(helpButton);
		menuBar.add(creditsButton);
		setJMenuBar(menuBar);

		containerPanel.add(loginPanel);
		add(containerPanel, BorderLayout.CENTER);
	}

	private void initActions() {
		settingsButton.addActionListener(e -> onSettings());
		helpButton.addActionListener(e -> onHelp());
		creditsButton.addActionListener(e -> onCredits());
	}

	private void onSettings() {

	}

	private void onHelp() {

	}

	private void onCredits() {

	}
}
