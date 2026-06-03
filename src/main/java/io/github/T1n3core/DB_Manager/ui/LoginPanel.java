package io.github.T1n3core.DB_Manager.ui;

import io.github.T1n3core.DB_Manager.db.DatabaseConfig;
import io.github.T1n3core.DB_Manager.db.DatabaseConnection;
import java.awt.Color;
import javax.swing.BoxLayout;
import javax.swing.Box;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.function.Consumer;

/**
 * Simple login panel with DB validation.
 */
public class LoginPanel extends JPanel {
	private JTextField userTextField;
	private JPasswordField passwdTextField;
	private JLabel errorLabel;
	private JButton loginButton;
	private Consumer<Connection> onLoginSuccess;

	public LoginPanel() {
		initComponents();
		initLayout();
		initActions();
	}

	private void initComponents() {
		userTextField = new JTextField(15);
		passwdTextField = new JPasswordField(15);

		errorLabel = new JLabel(" ");
		errorLabel.setForeground(Color.RED);

		loginButton = new JButton("Login");
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

		add(Box.createVerticalStrut(8));
		add(loginButton);

		add(Box.createVerticalStrut(8));
		add(errorLabel);
	}

	private void initActions() {
		loginButton.addActionListener(e -> attemptLogin());
	}

	private void attemptLogin() {
		errorLabel.setText(" ");

		String user = getUsername();
		char[] pass = getPassword();

		try {
			DatabaseConfig.setCredentials(user, pass);

			Connection conn = DatabaseConnection.getConnection();

			if (onLoginSuccess != null) {
				onLoginSuccess.accept(conn);
			}
		} catch (SQLException ex) {
			showError("Napačno uporabniško ime ali geslo.");

			clearFields();
			DatabaseConfig.clearCredentials();
		}
	}

	private void clearFields() {
		userTextField.setText("");
		passwdTextField.setText("");
		passwdTextField.requestFocus();
	}

	private void showError(String msg) {
		errorLabel.setText(msg);
	}

	public String getUsername() {
		return userTextField.getText().trim();
	}

	public char[] getPassword() {
		return passwdTextField.getPassword();
	}

	public void setOnLoginSuccess(Consumer<Connection> callback) {
		this.onLoginSuccess = callback;
	}
}
