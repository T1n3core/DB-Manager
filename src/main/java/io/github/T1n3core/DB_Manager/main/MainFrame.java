package io.github.T1n3core.DB_Manager.main;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.sql.Connection;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import com.github.weisj.darklaf.LafManager;
import com.github.weisj.darklaf.theme.DarculaTheme;
import com.github.weisj.darklaf.theme.IntelliJTheme;
import io.github.T1n3core.DB_Manager.ui.LoginPanel;
import io.github.T1n3core.DB_Manager.ui.TablePanel;

public class MainFrame extends JFrame {
	private JMenuBar menuBar;
	private JButton settingsButton;
	private JButton helpButton;
	private JButton creditsButton;
	private JPanel containerPanel;
	private CardLayout cardLayout;
	private LoginPanel loginPanel;
	private TablePanel tablePanel;
	private boolean lightTheme = false;
	private static final String LOGIN_CARD = "login";
	private static final String TABLE_CARD = "table";

	public MainFrame() {
		super("DB Manager");

		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(900, 600);
		setLocationRelativeTo(null);

		initComponents();
		initLayout();
		initActions();

		setVisible(true);
	}

	private void initComponents() {
		menuBar = new JMenuBar();

		settingsButton = new JButton("tema");
		helpButton = new JButton("pomoč");
		creditsButton = new JButton("vizitka");

		cardLayout = new CardLayout();
		containerPanel = new JPanel(cardLayout);

		loginPanel = new LoginPanel();
		tablePanel = new TablePanel();
	}

	private void initLayout() {
		setLayout(new BorderLayout());

		menuBar.add(settingsButton);
		menuBar.add(helpButton);
		menuBar.add(creditsButton);
		setJMenuBar(menuBar);

		containerPanel.add(loginPanel, LOGIN_CARD);
		containerPanel.add(tablePanel, TABLE_CARD);

		add(containerPanel, BorderLayout.CENTER);

		cardLayout.show(containerPanel, LOGIN_CARD);
	}

	private void initActions() {
		settingsButton.addActionListener(e -> onSettings());
		helpButton.addActionListener(e -> onHelp());
		creditsButton.addActionListener(e -> onCredits());

		loginPanel.setOnLoginSuccess(this::onLoginSuccess);
	}

	private void onLoginSuccess(Connection conn) {
		cardLayout.show(containerPanel, TABLE_CARD);
	}

	private void onSettings() {
		lightTheme = !lightTheme;

		if (lightTheme) {
			LafManager.install(new IntelliJTheme());
		} else {
			LafManager.install(new DarculaTheme());
		}

		SwingUtilities.updateComponentTreeUI(this);
	}

	private void onHelp() {
		JOptionPane.showMessageDialog(
				this,
				"Uporaba aplikacije:\n\n" +
						"1. Vpiši DB uporabniško ime in geslo\n" +
						"2. Klikni Login\n" +
						"3. Uporabi tabelo za operacije na bazi\n",
				"Pomoč",
				JOptionPane.INFORMATION_MESSAGE);
	}

	private void onCredits() {
		JOptionPane.showMessageDialog(
				this,
				"DB Manager\nAvtor: T1n3core\nJava Swing + JDBC aplikacija",
				"Vizitka",
				JOptionPane.INFORMATION_MESSAGE);
	}
}
