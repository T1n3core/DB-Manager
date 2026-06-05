package io.github.T1n3core.DB_Manager.ui;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import javax.swing.JComboBox;

/**
 * Simple FK selector based on JComboBox.
 * Stores real object as value, display text is only for UI.
 */
public class ForeignKeyComboBox<T> extends JComboBox<String> {
	private final Map<String, T> displayToValue = new LinkedHashMap<>();
	private final Map<T, String> valueToDisplay = new LinkedHashMap<>();
	private T selectedValue;

	public ForeignKeyComboBox(List<T> values, Function<T, String> displayFn) {
		super();

		for (T value : values) {
			String display = displayFn.apply(value);

			displayToValue.put(display, value);
			valueToDisplay.put(value, display);

			addItem(display);
		}

		addActionListener(e -> {
			String selected = (String) getSelectedItem();
			selectedValue = displayToValue.get(selected);
		});
	}

	public T getSelectedValue() {
		return selectedValue;
	}

	public void setSelectedValue(T value) {
		selectedValue = value;

		if (value == null) {
			setSelectedItem(null);
			return;
		}

		String display = valueToDisplay.get(value);
		if (display != null) {
			setSelectedItem(display);
		}
	}

	public boolean hasValidSelection() {
		return selectedValue != null;
	}
}
