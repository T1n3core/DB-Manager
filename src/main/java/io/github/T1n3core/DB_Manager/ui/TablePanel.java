package io.github.T1n3core.DB_Manager.ui;

import java.awt.BorderLayout;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

/**
 * Displays the current table and exposes CRUD buttons, as well as lets you
 * switch active table.
 */
public class TablePanel extends JPanel {
	private JTable currentTableDisplay; // TODO: replace this with AbstractTableModel subclass.
	private JComboBox<String> currentTable;
	private JButton insertButton;
	private JButton updateButton;
	private JButton deleteButton;
	private JButton queryButton;

	public TablePanel() {
		initComponents();
		initLayout();
		initActions();
	}

	private void initComponents() {
		currentTable = new JComboBox<>();
		queryButton = new JButton("query");
		insertButton = new JButton("new");
		updateButton = new JButton("modify");
		deleteButton = new JButton("delete");
		currentTableDisplay = new JTable();
	}

	private void initLayout() {
		setLayout(new BorderLayout());

		JPanel controlsPanel = new JPanel();
		controlsPanel.setLayout(new BoxLayout(controlsPanel, BoxLayout.Y_AXIS));
		controlsPanel.add(currentTable);
		controlsPanel.add(Box.createVerticalStrut(30));
		controlsPanel.add(queryButton);
		controlsPanel.add(Box.createVerticalStrut(10));
		controlsPanel.add(insertButton);
		controlsPanel.add(Box.createVerticalStrut(10));
		controlsPanel.add(updateButton);
		controlsPanel.add(Box.createVerticalStrut(10));
		controlsPanel.add(deleteButton);
		add(controlsPanel, BorderLayout.WEST);

		JPanel tableContainer = new JPanel(new BorderLayout());
		tableContainer.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
		tableContainer.add(new JScrollPane(currentTableDisplay), BorderLayout.CENTER);
		add(tableContainer, BorderLayout.EAST);
	}

	private void initActions() {
		currentTable.addActionListener(e -> onTableChange());
		queryButton.addActionListener(e -> onQuery());
		insertButton.addActionListener(e -> onInsert());
		updateButton.addActionListener(e -> onUpdate());
		deleteButton.addActionListener(e -> onDelete());
	}

	private void onTableChange() {

	}

	private void onQuery() {

	}

	private void onInsert() {

	}

	private void onUpdate() {

	}

	private void onDelete() {

	}
}
