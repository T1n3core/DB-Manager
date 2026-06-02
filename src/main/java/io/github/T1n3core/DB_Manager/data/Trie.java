package io.github.T1n3core.DB_Manager.data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Generic trie data structure implementation.
 * Used for autocomplete fields.
 */
public class Trie<T> {
	private static class Node<T> {
		Map<Character, Node<T>> children = new HashMap<>();
		List<AutocompleteItem<T>> items = new ArrayList<>();
	}

	private final Node<T> root = new Node<>();

	public void insert(String key, AutocompleteItem<T> item) {
		Node<T> current = root;

		key = key.toLowerCase();

		for (char c : key.toCharArray()) {
			current = current.children.computeIfAbsent(c, _ -> new Node<>());
		}

		current.items.add(item);
	}

	public List<AutocompleteItem<T>> searchPrefix(String prefix) {
		Node<T> current = root;

		prefix = prefix.toLowerCase();

		for (char c : prefix.toCharArray()) {
			current = current.children.get(c);

			if (current == null)
				return Collections.emptyList();
		}

		List<AutocompleteItem<T>> result = new ArrayList<>();

		collect(current, result);

		return result;
	}

	private void collect(Node<T> node, List<AutocompleteItem<T>> result) {
		result.addAll(node.items);

		for (Node<T> child : node.children.values()) {
			collect(child, result);
		}
	}
}

// TODO: implement O(prefixLength) lookup
