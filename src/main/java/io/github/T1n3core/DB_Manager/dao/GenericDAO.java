package io.github.T1n3core.DB_Manager.dao;

import java.util.List;
import java.util.Optional;

/**
 * Generic Data Access Object is an interface which defines the basic CRUD
 * operations (Create, Read, Update, Delete).
 */
public interface GenericDAO<T, ID> {
	List<T> findAll();

	Optional<T> findById(ID id);

	void insert(T entity);

	void update(T entity);

	void delete(ID id);
}
