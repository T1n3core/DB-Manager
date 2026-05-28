package io.github.T1n3core.DB_Manager.ui;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

/**
 * Simple vertical login form.
 */
public class LoginPanel extends JPanel {
	private JTextField userTextField;
	private JPasswordField passwdTextField;

	public LoginPanel() {
		initComponents();
		initLayout();
	}

	private void initComponents() {
		userTextField = new JTextField(15);
		passwdTextField = new JPasswordField(15);
	}

	private void initLayout() {
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		add(new JLabel("VPIS V BAZO PODATKOV"));
		add(Box.createVerticalStrut(8));
		add(new JLabel("uporabniško ime:"));
		add(userTextField);
		add(Box.createVerticalStrut(5));
		add(new JLabel("geslo:"));
		add(passwdTextField);
	}

	public String getUsername() {
		return userTextField.getText().trim();
	}

	public char[] getPassword() {
		return passwdTextField.getPassword();
	}
}
