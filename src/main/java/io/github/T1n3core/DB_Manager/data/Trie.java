package io.github.T1n3core.DB_Manager.data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.LinkedHashSet;
import java.util.Locale;

/**
 * Generic trie data structure implementation.
 * Used for autocomplete fields.
 */
public class Trie<T> {
	private static class Node<T> {
		Map<Character, Node<T>> children = new HashMap<>();
		Set<AutocompleteItem<T>> items = new LinkedHashSet<>();
		Set<AutocompleteItem<T>> cachedAll = null;
		boolean dirty = true;
	}

	private final Node<T> root = new Node<>();

	public void insert(String key, AutocompleteItem<T> item) {
		Node<T> current = root;

		key = key.toLowerCase(Locale.ROOT);

		for (char c : key.toCharArray()) {
			current = current.children.computeIfAbsent(c, _ -> new Node<>());
			current.dirty = true;
		}

		boolean added = current.items.add(item);
		if (added) {
			invalidateUpwards(root, key, 0);
		}
	}

	public List<AutocompleteItem<T>> searchPrefix(String prefix) {
		Node<T> current = root;

		prefix = prefix.toLowerCase(Locale.ROOT);

		for (char c : prefix.toCharArray()) {
			current = current.children.get(c);
			if (current == null)
				return Collections.emptyList();
		}

		return new ArrayList<>(collectCached(current));
	}

	private Set<AutocompleteItem<T>> collectCached(Node<T> node) {
		if (!node.dirty && node.cachedAll != null) {
			return node.cachedAll;
		}

		LinkedHashSet<AutocompleteItem<T>> result = new LinkedHashSet<>();
		collect(node, result);

		node.cachedAll = result;
		node.dirty = false;

		return result;
	}

	private void collect(Node<T> node, Set<AutocompleteItem<T>> result) {
		result.addAll(node.items);

		for (Node<T> child : node.children.values()) {
			collect(child, result);
		}
	}

	private void invalidateUpwards(Node<T> node, String key, int index) {
		node.dirty = true;

		if (index == key.length())
			return;

		Node<T> child = node.children.get(key.charAt(index));
		if (child != null) {
			invalidateUpwards(child, key, index + 1);
		}
	}
}
