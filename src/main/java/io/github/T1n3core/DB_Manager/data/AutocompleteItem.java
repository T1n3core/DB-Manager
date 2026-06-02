package io.github.T1n3core.DB_Manager.data;

/**
 * Pairs a value to a string for
 * {@linkplain io.github.T1n3core.DB_Manager.data.Trie trie} autocomplete
 * indexing.
 */
public record AutocompleteItem<T>(String displayText, T value) {
	@Override
	public final String toString() {
		return displayText;
	}
}
