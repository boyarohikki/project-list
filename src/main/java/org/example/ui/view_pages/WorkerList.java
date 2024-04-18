package org.example.ui.view_pages;

import org.example.Database;
import org.example.base.BaseForm;
import org.example.base.ExtendedTableModel;
import org.example.entities.Project;
import org.example.entities.Worker;
import org.example.ui.MainPage;
import org.example.ui.add_pages.AddWorkerPage;
import org.example.ui.edit_pages.EditWorkerPage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Objects;

public class WorkerList extends BaseForm {
    private JTable table;
    private JButton backButton;
    private JPanel mainPanel;
    private JComboBox<String> projectSelector;
    private JButton addWorkerButton;
    private final ExtendedTableModel<Worker> model;

    public WorkerList() throws HeadlessException {
        super(1000, 400);
        setContentPane(mainPanel);

        String[] columns = {"Номер работника", "Имя работника", "Задачи", "Проекты"};

        var workers = Database.fetch_all(Worker.class);

        model = new ExtendedTableModel<>(Worker.class, columns);
        model.setAllRows(workers);
        table.setModel(model);

        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1 && e.getClickCount() == 2) {
                    var row = table.rowAtPoint(e.getPoint());
                    if (row != -1) {
                        dispose();
                        new EditWorkerPage(model.getFilteredRows().get(row));
                    }
                }
            }
        });

        var projects = Database.fetch_all(Project.class);

        initFilters(projects);

        projectSelector.addItem("");
        for (var project : projects) {
            projectSelector.addItem(project.getData());
        }
        projectSelector.addItemListener(itemEvent -> model.updateFilteredRows());

        backButton.addActionListener(actionEvent -> {
            if (actionEvent.getSource() == backButton) {
                dispose();
                new MainPage();
            }
        });
        addWorkerButton.addActionListener(actionEvent -> {
            if (actionEvent.getSource() == addWorkerButton) {
                dispose();
                new AddWorkerPage();
            }
        });

        setVisible(true);
    }

    private void initFilters(java.util.List<Project> projects) {
        model.getFilters()[0] = worker -> {
            var index = projectSelector.getSelectedIndex();
            if (index == 0) {
                return true;
            }
            var projectId = projects.get(index - 1).getId();

            return worker.getProjects().stream().map(Project::getId).anyMatch(id -> Objects.equals(id, projectId));
        };
    }
}
