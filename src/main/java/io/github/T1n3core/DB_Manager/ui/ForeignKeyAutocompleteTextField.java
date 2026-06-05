package io.github.T1n3core.DB_Manager.ui;

import java.awt.Dimension;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import javax.swing.DefaultListModel;
import javax.swing.InputMap;
import javax.swing.ActionMap;
import javax.swing.AbstractAction;
import javax.swing.JList;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/*
 * Class is deprecated, not enough time to finish it, do not use.
 */
public class ForeignKeyAutocompleteTextField<T> extends JTextField {
	private final Function<T, String> displayFunction;
	private final Map<String, T> lookup = new LinkedHashMap<>();
	private final DefaultListModel<String> listModel = new DefaultListModel<>();
	private final JList<String> suggestionList = new JList<>(listModel);
	private final JPopupMenu popup = new JPopupMenu();
	private boolean updatingProgrammatically;
	private T selectedValue;

	public ForeignKeyAutocompleteTextField(List<T> values, Function<T, String> displayFunction) {
		super(20);

		this.displayFunction = displayFunction;

		for (T value : values) {
			lookup.put(displayFunction.apply(value), value);
		}

		initPopup();
		initListeners();
		initKeyBindings();

		setFocusTraversalKeysEnabled(false);
	}

	private void initPopup() {
		suggestionList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		JScrollPane scrollPane = new JScrollPane(suggestionList);

		scrollPane.setPreferredSize(new Dimension(300, 150));

		popup.add(scrollPane);
	}

	private void initListeners() {
		getDocument().addDocumentListener(new DocumentListener() {

			@Override
			public void insertUpdate(DocumentEvent e) {
				SwingUtilities.invokeLater(() -> updateSuggestions());
			}

			@Override
			public void removeUpdate(DocumentEvent e) {
				SwingUtilities.invokeLater(() -> updateSuggestions());
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
				SwingUtilities.invokeLater(() -> updateSuggestions());
			}
		});

		suggestionList.addListSelectionListener(e -> {
			if (e.getValueIsAdjusting())
				return;

			String selected = suggestionList.getSelectedValue();

			if (selected != null) {
				selectedValue = lookup.get(selected);
			}
		});

		suggestionList.addMouseListener(
				new java.awt.event.MouseAdapter() {
					@Override
					public void mouseClicked(java.awt.event.MouseEvent e) {
						if (suggestionList.getSelectedValue() != null) {
							acceptSelection();
						}
					}
				});

		addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				SwingUtilities.invokeLater(() -> {
					if (!suggestionList.hasFocus()) {
						popup.setVisible(false);
					}
				});
			}
		});
	}

	private void initKeyBindings() {
		InputMap im = getInputMap();
		ActionMap am = getActionMap();

		im.put(KeyStroke.getKeyStroke("DOWN"), "nextSuggestion");
		im.put(KeyStroke.getKeyStroke("UP"), "prevSuggestion");
		im.put(KeyStroke.getKeyStroke("ENTER"), "acceptSuggestion");
		im.put(KeyStroke.getKeyStroke("TAB"), "acceptSuggestion");
		im.put(KeyStroke.getKeyStroke("ESCAPE"), "hidePopup");

		am.put(
				"nextSuggestion",
				new AbstractAction() {
					@Override
					public void actionPerformed(
							java.awt.event.ActionEvent e) {

						if (!popup.isVisible())
							return;

						int index = suggestionList.getSelectedIndex();

						if (index < listModel.size() - 1) {
							suggestionList.setSelectedIndex(index + 1);
						}
					}
				});

		am.put(
				"prevSuggestion",
				new AbstractAction() {
					@Override
					public void actionPerformed(
							java.awt.event.ActionEvent e) {

						if (!popup.isVisible())
							return;

						int index = suggestionList.getSelectedIndex();

						if (index > 0) {
							suggestionList.setSelectedIndex(index - 1);
						}
					}
				});

		am.put(
				"acceptSuggestion",
				new AbstractAction() {
					@Override
					public void actionPerformed(
							java.awt.event.ActionEvent e) {

						if (popup.isVisible()) {
							acceptSelection();
						} else {
							transferFocus();
						}
					}
				});

		am.put(
				"hidePopup",
				new AbstractAction() {
					@Override
					public void actionPerformed(
							java.awt.event.ActionEvent e) {

						popup.setVisible(false);
					}
				});
	}

	private void updateSuggestions() {
		if (updatingProgrammatically)
			return;

		selectedValue = null;

		String text = getText().trim().toLowerCase();

		listModel.clear();

		if (text.isEmpty()) {
			popup.setVisible(false);
			return;
		}

		lookup.keySet().stream().filter(s -> s.toLowerCase().contains(text)).limit(20).forEach(listModel::addElement);

		if (listModel.isEmpty()) {
			popup.setVisible(false);
			return;
		}

		suggestionList.setSelectedIndex(0);

		SwingUtilities.invokeLater(() -> {
			if (!isShowing())
				return;

			if (!listModel.isEmpty()) {
				popup.setVisible(false);
				popup.show(this, 0, getHeight());
				requestFocusInWindow();
			} else {
				popup.setVisible(false);
			}
		});
	}

	private void acceptSelection() {
		String selectedText = suggestionList.getSelectedValue();

		if (selectedText == null)
			return;

		selectedValue = lookup.get(selectedText);

		updatingProgrammatically = true;

		setText(selectedText);

		updatingProgrammatically = false;

		popup.setVisible(false);
	}

	public T getSelectedValue() {
		if (selectedValue != null)
			return selectedValue;

		return lookup.get(getText());
	}

	public boolean hasValidSelection() {
		return getSelectedValue() != null;
	}

	public void setSelectedValue(T value) {
		selectedValue = value;

		updatingProgrammatically = true;

		if (value == null) {
			setText("");
		} else {
			setText(displayFunction.apply(value));
		}

		updatingProgrammatically = false;
	}
}
