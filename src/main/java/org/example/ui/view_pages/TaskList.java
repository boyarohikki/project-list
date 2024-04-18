package org.example.ui.view_pages;

import org.example.Database;
import org.example.base.BaseForm;
import org.example.base.ExtendedTableModel;
import org.example.entities.Task;
import org.example.ui.MainPage;
import org.example.ui.add_pages.AddTaskPage;
import org.example.ui.edit_pages.EditClientPage;
import org.example.ui.edit_pages.EditTaskPage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class TaskList extends BaseForm {
    private JPanel mainPanel;
    private JTable table;
    private JButton backButton;
    private JButton addTaskButton;

    private ExtendedTableModel<Task> model;

    public TaskList() throws HeadlessException {
        super(1000, 400);
        setContentPane(mainPanel);
        String[] columns = {"Номер задачи", "Срок выполнения", "Проект", "Исполнители"};

        var tasks = Database.fetch_all(Task.class);

        model = new ExtendedTableModel<>(Task.class, columns);
        model.setAllRows(tasks);
        table.setModel(model);
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2 && e.getButton() == MouseEvent.BUTTON1) {
                    var row = table.rowAtPoint(e.getPoint());
                    if (row != -1) {
                        dispose();
                        new EditTaskPage(model.getFilteredRows().get(row));
                    }
                }
            }
        });

        addTaskButton.addActionListener(actionEvent -> {
            if (actionEvent.getSource() == addTaskButton) {
                dispose();
                new AddTaskPage();
            }
        });
        backButton.addActionListener(actionEvent -> {
            if (actionEvent.getSource() == backButton) {
                dispose();
                new MainPage();
            }
        });

        setVisible(true);
    }
}
