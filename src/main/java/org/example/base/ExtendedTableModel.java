package org.example.base;

import lombok.Getter;
import org.example.entities.Client;
import org.example.entities.Project;
import org.example.entities.Task;
import org.example.entities.Worker;

import javax.swing.table.AbstractTableModel;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.function.Predicate;

@Getter
public class ExtendedTableModel<T> extends AbstractTableModel {
    public ExtendedTableModel(Class<T> cls, String[] columnNames) {
        this.cls = cls;
        this.columnNames = columnNames;
    }

    private final Class<T> cls;
    private final String[] columnNames;
    private List<T> allRows;
    private List<T> filteredRows;
    private Comparator<T> sorter = null;
    private Predicate<T>[] filters = new Predicate[10];

    @Override
    public String getColumnName(int column) {
        return column < columnNames.length ? columnNames[column] : cls.getDeclaredFields()[column].getName();
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return cls.getDeclaredFields()[columnIndex].getType();
    }

    @Override
    public int getRowCount() {
        return filteredRows.size();
    }

    @Override
    public int getColumnCount() {
        return cls.getDeclaredFields().length;
    }

    static <T> String valueToString(T value) {
        if (value instanceof List) {
            if (((List<?>) value).isEmpty()) {
                return "";
            }
            return String.join(", ", ((List<Object>) value).stream().map(ExtendedTableModel::valueToString).toList());
        } else if (value instanceof Client) {
            return ((Client) value).getName();
        } else if (value instanceof Worker) {
            return ((Worker) value).getName();
        } else if (value instanceof Task) {
            return ((Task) value).getData();
        } else if (value instanceof Project) {
            return ((Project) value).getData();
        } else if (value instanceof Optional) {
            if (((Optional<?>) value).isEmpty()) {
                return "";
            } else {
                return valueToString(((Optional<?>) value).get());
            }
        } else if (value instanceof Calendar) {
            return Config.formatDate(((Calendar) value));
        } else if (value == null) {
            return "";
        } else{
            return value.toString();
        }
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        var field = cls.getDeclaredFields()[columnIndex];
        field.setAccessible(true);
        try {
            return valueToString(field.get(filteredRows.get(rowIndex)));
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            return "ERROR";
        }
    }

    public void updateFilteredRows() {
        filteredRows = new ArrayList<>(allRows);
        for (var filter : filters) {
            if (filter != null) {
                filteredRows.removeIf(element -> !filter.test(element));
            }
        }

        if (sorter != null) {
            filteredRows.sort(sorter);
        }

        fireTableDataChanged();
        onRowsUpdate();
    }

    public void onRowsUpdate() {
    }

    public void setAllRows(List<T> allRows) {
        this.allRows = allRows;
        updateFilteredRows();
    }

    public void setSorter(Comparator<T> sorter) {
        this.sorter = sorter;
        updateFilteredRows();
    }
}
