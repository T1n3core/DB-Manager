package io.github.T1n3core.DB_Manager.ui;

import java.awt.BorderLayout;
import java.lang.reflect.Field;
import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.Map;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

/*
 * Popup that allows inserting into the database.
 * Autogenerates the input fields based on entity field types.
 */
public class InsertDialog extends JDialog {
	private final Class<?> entityClass;
	private final Map<Field, JComponent> editors;

	public InsertDialog(Class<?> entityClass) {
		this.entityClass = entityClass;
		editors = new LinkedHashMap<>();

		initLayout();
		pack();
		setLocationRelativeTo(null);
		setModal(true);
	}

	private void initLayout() {
		setTitle("Insert " + entityClass.getSimpleName());

		JPanel formPanel = new JPanel();
		formPanel.setLayout(
				new BoxLayout(formPanel, BoxLayout.Y_AXIS));

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

		add(new JScrollPane(formPanel),
				BorderLayout.CENTER);

		// TODO add save/cancel buttons
	}

	private JComponent createEditorFor(Field field) {

		Class<?> type = field.getType();

		if (type == String.class) {

			// TODO HistoryTextField
			return new javax.swing.JTextField(20);
		}

		if (type == int.class
				|| type == Integer.class) {

			// TODO NumberField
			return new javax.swing.JTextField("0", 20);
		}

		if (type == LocalDate.class) {

			// TODO DatePickerComponent
			return new JLabel(
					"[DATE PICKER PLACEHOLDER]");
		}

		if (isEntityReference(type)) {

			// TODO ForeignKeyAutocompleteField
			//
			// should display:
			// "Jane Doe"
			//
			// internally return:
			// Zaposleni object
			//
			// or selected id

			return new JLabel(
					"[FK AUTOCOMPLETE PLACEHOLDER]");
		}

		throw new IllegalArgumentException(
				"Unsupported field type: "
						+ type.getName());
	}

	private boolean isPrimaryKey(Field field) {
		return field.getName().endsWith("Id");
	}

	private boolean isEntityReference(Class<?> type) {

		Package p = type.getPackage();

		if (p == null)
			return false;

		return p.getName()
				.equals(
						"io.github.T1n3core.DB_Manager.model");
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

	public Map<Field, Object> getValues() {

		Map<Field, Object> values = new LinkedHashMap<>();

		for (Map.Entry<Field, JComponent> entry : editors.entrySet()) {

			Field field = entry.getKey();
			JComponent editor = entry.getValue();

			values.put(field,
					extractValue(field, editor));
		}

		return values;
	}

	private Object extractValue(
			Field field,
			JComponent editor) {

		Class<?> type = field.getType();

		if (editor instanceof javax.swing.JTextField textField) {

			if (type == String.class)
				return textField.getText();

			if (type == int.class
					|| type == Integer.class)
				return Integer.parseInt(
						textField.getText());
		}

		// TODO date picker extraction

		// TODO fk extraction

		return null;
	}
}
