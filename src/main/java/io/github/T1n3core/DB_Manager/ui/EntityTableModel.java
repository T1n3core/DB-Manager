package io.github.T1n3core.DB_Manager.ui;

import javax.swing.table.AbstractTableModel;
import java.util.List;

public class EntityTableModel<T> extends AbstractTableModel {
	private final List<T> data;
	private final String[] columns;

	public EntityTableModel(List<T> data, String[] columns) {
		this.data = data;
		this.columns = columns;
	}

	public T getEntityAt(int row) {
		return data.get(row);
	}

	@Override
	public int getRowCount() {
		return data.size();
	}

	@Override
	public int getColumnCount() {
		return columns.length;
	}

	@Override
	public String getColumnName(int col) {
		return columns[col];
	}

	@Override
	public Object getValueAt(int row, int col) {
		return data.get(row).toString(); // TODO: temporary, fix later
	}
}
