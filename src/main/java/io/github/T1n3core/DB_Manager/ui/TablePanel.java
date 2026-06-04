package io.github.T1n3core.DB_Manager.ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import io.github.T1n3core.DB_Manager.dao.DAOFactory;
import io.github.T1n3core.DB_Manager.dao.GenericDAO;
import io.github.T1n3core.DB_Manager.model.*;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

/**
 * Displays the current table and exposes CRUD buttons, as well as lets you
 * switch active table.
 */
public class TablePanel extends JPanel {
	private JTable currentTableDisplay;
	private JComboBox<String> currentTable;
	private JButton insertButton;
	private JButton updateButton;
	private JButton deleteButton;
	private final Map<String, Class<?>> entityMap = Map.of(
			"Zaposleni", Zaposleni.class,
			"StrojnaOprema", StrojnaOprema.class,
			"ProgramskaOprema", ProgramskaOprema.class,
			"Servis", Servis.class,
			"Namestitev", Namestitev.class,
			"Licenca", Licenca.class,
			"Dodelitev", Dodelitev.class);

	public TablePanel() {
		initComponents();
		initLayout();
		initActions();
	}

	private void initComponents() {
		currentTable = new JComboBox<>();
		entityMap.keySet().forEach(currentTable::addItem);
		insertButton = new JButton("new");
		updateButton = new JButton("modify");
		updateButton.setEnabled(false);
		deleteButton = new JButton("delete");
		deleteButton.setEnabled(false);
		currentTableDisplay = new JTable();
	}

	private void initLayout() {
		setLayout(new BorderLayout());

		JPanel controlsPanel = new JPanel();
		controlsPanel.setLayout(new BoxLayout(controlsPanel, BoxLayout.Y_AXIS));

		currentTable.setMaximumSize(new Dimension(100, 50));
		insertButton.setAlignmentX(CENTER_ALIGNMENT);
		updateButton.setAlignmentX(CENTER_ALIGNMENT);
		deleteButton.setAlignmentX(CENTER_ALIGNMENT);

		controlsPanel.add(Box.createVerticalStrut(50));
		controlsPanel.add(currentTable);
		controlsPanel.add(Box.createVerticalStrut(100));
		controlsPanel.add(insertButton);
		controlsPanel.add(Box.createVerticalStrut(20));
		controlsPanel.add(updateButton);
		controlsPanel.add(Box.createVerticalStrut(20));
		controlsPanel.add(deleteButton);
		add(controlsPanel, BorderLayout.WEST);

		JPanel tableContainer = new JPanel(new BorderLayout());
		tableContainer.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
		tableContainer.add(new JScrollPane(currentTableDisplay), BorderLayout.CENTER);
		add(tableContainer, BorderLayout.CENTER);
	}

	private void initActions() {
		insertButton.addActionListener(e -> onInsert());
		updateButton.addActionListener(e -> onUpdate());
		deleteButton.addActionListener(e -> onDelete());

		currentTable.addActionListener(e -> reloadCurrentTable());

		currentTableDisplay.getSelectionModel().addListSelectionListener(e -> {
			boolean selected = currentTableDisplay.getSelectedRow() != -1;
			updateButton.setEnabled(selected);
			deleteButton.setEnabled(selected);
		});
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void onInsert() {
		Class<?> entityClass = entityMap.get(currentTable.getSelectedItem());

		InsertDialog dialog = new InsertDialog(entityClass);

		dialog.setVisible(true);

		if (!dialog.isConfirmed())
			return;

		Object entity = dialog.buildEntity();

		GenericDAO dao = DAOFactory.getDAO(entityClass);

		dao.insert(entity);

		reloadCurrentTable();
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void onUpdate() {
		int row = currentTableDisplay.getSelectedRow();

		if (row == -1)
			return;

		ReflectionTableModel model = (ReflectionTableModel) currentTableDisplay.getModel();

		Object entity = model.getEntityAt(row);

		InsertDialog dialog = new InsertDialog(entity.getClass(), entity);

		dialog.setVisible(true);

		if (!dialog.isConfirmed())
			return;

		dialog.updateEntity(entity);

		GenericDAO dao = DAOFactory.getDAO(entity.getClass());

		dao.update(entity);

		reloadCurrentTable();
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void onDelete() {
		try {
			int row = currentTableDisplay.getSelectedRow();

			if (row == -1)
				return;

			ReflectionTableModel model = (ReflectionTableModel) currentTableDisplay.getModel();

			Object entity = model.getEntityAt(row);

			int result = JOptionPane.showConfirmDialog(this, "Delete selected row?");

			if (result != JOptionPane.YES_OPTION)
				return;

			GenericDAO dao = DAOFactory.getDAO(entity.getClass());

			Field idField = entity.getClass().getDeclaredFields()[0];

			idField.setAccessible(true);

			Object id = idField.get(entity);

			dao.delete(id);

			reloadCurrentTable();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(
					this,
					e.getMessage(),
					"Error",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	private void reloadCurrentTable() {
		Class<?> entityClass = entityMap.get(currentTable.getSelectedItem());

		GenericDAO<?, ?> dao = DAOFactory.getDAO(entityClass);

		List<?> rows = dao.findAll();

		currentTableDisplay.setModel(ReflectionTableModel.from(rows));
	}
}
