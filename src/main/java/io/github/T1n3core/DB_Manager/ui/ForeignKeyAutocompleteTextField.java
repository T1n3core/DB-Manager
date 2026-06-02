package io.github.T1n3core.DB_Manager.ui;

import java.awt.Dimension;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.function.Function;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import io.github.T1n3core.DB_Manager.data.AutocompleteItem;
import io.github.T1n3core.DB_Manager.data.Trie;

/**
 * ForeignKeyAutocompleteTextField
 */
public class ForeignKeyAutocompleteTextField<T> extends JTextField {
	private final Trie<T> trie = new Trie<>();
	private final DefaultListModel<AutocompleteItem<T>> listModel = new DefaultListModel<>();
	private final JList<AutocompleteItem<T>> suggestionList = new JList<>(listModel);
	private final JPopupMenu popup = new JPopupMenu();
	private T selectedValue;

	public ForeignKeyAutocompleteTextField(List<T> values, Function<T, String> displayFunction) {
		super(20);

		for (T value : values) {
			String display = displayFunction.apply(value);

			trie.insert(display, new AutocompleteItem<>(display, value));
		}

		initPopup();
		initListeners();
	}

	private void initPopup() {
		suggestionList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		JScrollPane scrollPane = new JScrollPane(suggestionList);
		scrollPane.setPreferredSize(new Dimension(250, 150));
		popup.add(scrollPane);
	}

	private void initListeners() {
		getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void insertUpdate(DocumentEvent e) {
				updateSuggestions();
			}

			@Override
			public void removeUpdate(DocumentEvent e) {
				updateSuggestions();
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
				updateSuggestions();
			}
		});

		suggestionList.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2)
					acceptSelection();
			}
		});

		addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				switch (e.getKeyCode()) {
					case KeyEvent.VK_DOWN -> {
						if (!popup.isVisible())
							return;

						int index = suggestionList.getSelectedIndex();

						if (index < listModel.getSize() - 1)
							suggestionList.setSelectedIndex(index + 1);
					}

					case KeyEvent.VK_UP -> {
						if (!popup.isVisible())
							return;

						int index = suggestionList.getSelectedIndex();

						if (index > 0)
							suggestionList.setSelectedIndex(index - 1);
					}

					case KeyEvent.VK_ENTER -> {
						if (popup.isVisible()) {
							acceptSelection();
							e.consume();
						}
					}

					case KeyEvent.VK_ESCAPE -> popup.setVisible(false);
				}
			}
		});

	}

	private void updateSuggestions() {
		selectedValue = null;

		String text = getText().trim();
		if (text.isEmpty()) {
			popup.setVisible(false);
			return;
		}

		List<AutocompleteItem<T>> matches = trie.searchPrefix(text);

		listModel.clear();

		matches.stream().limit(10).forEach(listModel::addElement);

		if (listModel.isEmpty()) {
			popup.setVisible(false);
			return;
		}

		suggestionList.setSelectedIndex(0);

		popup.show(this, 0, getHeight());
	}

	private void acceptSelection() {
		AutocompleteItem<T> item = suggestionList.getSelectedValue();

		if (item == null)
			return;

		setText(item.displayText());

		selectedValue = item.value();

		popup.setVisible(false);
	}

	public T getSelectedValue() {
		return selectedValue;
	}
}
