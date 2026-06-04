package io.github.T1n3core.DB_Manager.ui;

import java.util.List;
import javax.swing.table.AbstractTableModel;
import java.lang.reflect.Field;

public class ReflectionTableModel extends AbstractTableModel {
	private final List<?> data;
	private final Field[] fields;

	public ReflectionTableModel(List<?> data) {
		this.data = data;

		if (data.isEmpty()) {
			fields = new Field[0];
		} else {
			fields = data.get(0).getClass().getDeclaredFields();

			for (Field f : fields)
				f.setAccessible(true);
		}
	}

	public Object getEntityAt(int row) {
		return data.get(row);
	}

	@Override
	public int getRowCount() {
		return data.size();
	}

	@Override
	public int getColumnCount() {
		return fields.length;
	}

	@Override
	public String getColumnName(int col) {
		return fields[col].getName();
	}

	@Override
	public Object getValueAt(int row, int col) {
		try {
			return fields[col].get(data.get(row));
		} catch (Exception e) {
			return null;
		}
	}

	public static ReflectionTableModel from(List<?> data) {
		return new ReflectionTableModel(data);
	}
}
