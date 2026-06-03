package io.github.T1n3core.DB_Manager.ui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.lang.reflect.Field;
import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.List;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import com.github.lgooddatepicker.components.DatePicker;

import io.github.T1n3core.DB_Manager.dao.DAOFactory;
import io.github.T1n3core.DB_Manager.dao.GenericDAO;

/*
 * Popup that allows inserting into the database.
 * Autogenerates the input fields based on entity field types.
 */
public class InsertDialog extends JDialog {
	private final Class<?> entityClass;
	private final Map<Field, JComponent> editors;
	private final Map<Class<?>, List<?>> fkCache;
	private boolean confirmed = false;

	public InsertDialog(Class<?> entityClass) {
		this.entityClass = entityClass;
		editors = new LinkedHashMap<>();
		fkCache = new LinkedHashMap<>();

		loadForeignKeys();
		initLayout();
		pack();
		setLocationRelativeTo(null);
		setModal(true);
	}

	private void loadForeignKeys() {
		for (Field f : entityClass.getDeclaredFields()) {
			Class<?> type = f.getType();

			if (isEntityReference(type)) {
				GenericDAO<?, ?> dao = DAOFactory.getDAO(type);
				if (dao == null)
					throw new IllegalStateException("No DAO for: " + type.getName() + ".");

				List<?> values = dao.findAll();
				fkCache.put(type, values);
			}
		}
	}

	private void initLayout() {
		setTitle("Insert " + entityClass.getSimpleName());
		setLayout(new BorderLayout());

		JPanel formPanel = new JPanel();
		formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));

		for (Field field : entityClass.getDeclaredFields()) {

			if (isPrimaryKey(field))
				continue;

			JLabel label = new JLabel(toDisplayName(field.getName()));

			JComponent editor = createEditorFor(field);

			editors.put(field, editor);

			formPanel.add(label);
			formPanel.add(editor);
			formPanel.add(Box.createVerticalStrut(8));
		}

		add(new JScrollPane(formPanel), BorderLayout.CENTER);

		add(createButtons(), BorderLayout.SOUTH);
	}

	private JPanel createButtons() {
		JPanel panel = new JPanel(new FlowLayout(FlowLayout.RIGHT));

		JButton cancel = new JButton("Cancel");
		JButton save = new JButton("Save");

		cancel.addActionListener(e -> {
			confirmed = false;
			dispose();
		});

		save.addActionListener(e -> {
			confirmed = true;
			dispose();
		});

		panel.add(cancel);
		panel.add(save);

		return panel;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private JComponent createEditorFor(Field field) {
		Class<?> type = field.getType();

		if (type == String.class)
			return new JTextField(20);

		if (type == int.class || type == Integer.class)
			return new JTextField("0", 20);
		// TODO: implement integer protection

		if (type == LocalDate.class)
			return new DatePicker();

		if (isEntityReference(type)) {
			List<?> values = fkCache.get(type);

			Function<Object, String> displayFn = obj -> {
				try {
					Field idField = obj.getClass().getDeclaredFields()[0];
					idField.setAccessible(true);
					Object id = idField.get(obj);
					return obj.toString() + " (" + id + ")";
				} catch (Exception e) {
					return obj.toString();
				}
			};

			return new ForeignKeyAutocompleteTextField(values, displayFn);
		}

		throw new IllegalArgumentException("Unsupported type: " + type);
	}

	private boolean isPrimaryKey(Field field) {
		return field.getName().endsWith("Id");
	}

	private boolean isEntityReference(Class<?> type) {
		Package p = type.getPackage();

		if (p == null)
			return false;

		return p.getName().equals("io.github.T1n3core.DB_Manager.model");
	}

	private String toDisplayName(String name) {

		StringBuilder result = new StringBuilder();

		for (char c : name.toCharArray()) {

			if (Character.isUpperCase(c))
				result.append(' ');

			result.append(c);
		}

		return result.toString();
	}

	public boolean isConfirmed() {
		return confirmed;
	}

	public Map<Field, Object> getValues() {
		Map<Field, Object> values = new LinkedHashMap<>();

		for (var e : editors.entrySet()) {
			values.put(e.getKey(), extractValue(e.getKey(), e.getValue()));
		}

		return values;
	}

	private Object extractValue(Field field, JComponent editor) {
		Class<?> type = field.getType();

		if (editor instanceof JTextField tf) {
			if (type == String.class)
				return tf.getText();

			if (type == int.class || type == Integer.class)
				return Integer.parseInt(tf.getText());
		}

		if (editor instanceof ForeignKeyAutocompleteTextField<?> fk) {
			return fk.getSelectedValue();
		}

		if (type == LocalDate.class && editor instanceof DatePicker dp) {
			return dp.getDate();
		}

		// TODO: refactor for pattern matching switch

		return null;
	}
}
