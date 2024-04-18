package org.example.ui.view_pages;

import org.example.Database;
import org.example.base.BaseForm;
import org.example.base.ExtendedTableModel;
import org.example.entities.Client;
import org.example.entities.Project;
import org.example.ui.MainPage;
import org.example.ui.add_pages.AddProjectPage;
import org.example.ui.edit_pages.EditProjectPage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Objects;

public class ProjectList extends BaseForm {
    private JPanel mainPanel;
    private JTable table;
    private JButton backButton;
    private JComboBox<String> clientSelector;
    private JCheckBox expiredCheckBox;
    private JButton addProjectButton;
    private final ExtendedTableModel<Project> model;

    public ProjectList() throws HeadlessException {
        super(1000, 400);
        setContentPane(mainPanel);
        var columns = new String[]{"Номер проекта", "Проект", "Статус проекта", "Дата сдачи проекта",
                "Заказчик", "Исполнители", "Задачи"};

        var projects = Database.fetch_all(Project.class);

        model = new ExtendedTableModel<>(Project.class, columns);
        model.setAllRows(projects);
        table.setModel(model);
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2 && e.getButton() == MouseEvent.BUTTON1) {
                    var row = table.rowAtPoint(e.getPoint());
                    if (row != -1) {
                        dispose();
                        new EditProjectPage(model.getFilteredRows().get(row));
                    }
                }
            }
        });

        addProjectButton.addActionListener(actionEvent -> {
            if (actionEvent.getSource() == addProjectButton) {
                dispose();
                new AddProjectPage();
            }
        });
        backButton.addActionListener(actionEvent -> {
            if (actionEvent.getSource() == backButton) {
                dispose();
                new MainPage();
            }
        });

        clientSelector.addItem("");
        var clients = Database.fetch_all(Client.class);
        for (var client : clients) {
            clientSelector.addItem(client.getName());
        }

        initFilters(clients);

        clientSelector.addItemListener(e -> model.updateFilteredRows());
        expiredCheckBox.addChangeListener(e -> model.updateFilteredRows());


        setVisible(true);
    }

    private void initFilters(java.util.List<Client> clients) {
        model.getFilters()[0] = (Project project) -> {
            int index = clientSelector.getSelectedIndex();
            if (index == 0) {
                return true;
            }
            return Objects.equals(clients.get(index - 1).getId(), project.getClient().getId());
        };
        model.getFilters()[1] = (Project project) -> {
            if (!expiredCheckBox.isSelected()) {
                return true;
            }
            return LocalDate.now().isAfter(LocalDate.of(
                    project.getDate().get(Calendar.YEAR),
                    project.getDate().get(Calendar.MONTH) + 1,
                    project.getDate().get(Calendar.DATE) + 1));
        };
    }
}
