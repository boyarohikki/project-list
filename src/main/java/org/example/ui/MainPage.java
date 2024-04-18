package org.example.ui;

import org.example.base.BaseForm;
import org.example.ui.view_pages.ClientList;
import org.example.ui.view_pages.ProjectList;
import org.example.ui.view_pages.TaskList;
import org.example.ui.view_pages.WorkerList;

import javax.swing.*;
import java.awt.*;

public class MainPage extends BaseForm {
    private JPanel main_panel;
    private JButton projectsButton;
    private JButton clientsButton;
    private JButton workersButton;
    private JButton tasksButton;

    public MainPage() throws HeadlessException {
        super(1000, 400);
        setContentPane(main_panel);
        projectsButton.addActionListener(actionEvent -> {
            if (actionEvent.getSource() == projectsButton) {
                dispose();
                new ProjectList();
            }
        });

        clientsButton.addActionListener(actionEvent -> {
            if (actionEvent.getSource() == clientsButton) {
                dispose();
                new ClientList();
            }
        });

        workersButton.addActionListener(actionEvent -> {
            if (actionEvent.getSource() == workersButton) {
                dispose();
                new WorkerList();
            }
        });
        tasksButton.addActionListener(actionEvent -> {
            if (actionEvent.getSource() == tasksButton) {
                dispose();
                new TaskList();
            }
        });
        setVisible(true);
    }
}
