package org.example.ui.add_pages;

import org.example.Database;
import org.example.base.BaseForm;
import org.example.base.DialogUtil;
import org.example.entities.Project;
import org.example.entities.Task;
import org.example.entities.Worker;
import org.example.ui.view_pages.TaskList;

import javax.swing.*;
import java.awt.*;

public class AddTaskPage extends BaseForm {
    private JTextField dataTextField;
    private JComboBox projectSelector;
    private JComboBox workerSelector;
    private JButton confirmButton;
    private JButton cancelButton;
    private JPanel mainPanel;

    public AddTaskPage() throws HeadlessException {
        super(1000, 400);
        setContentPane(mainPanel);
        var page = this;
        var projects = Database.fetch_all(Project.class);
        var workers = Database.fetch_all(Worker.class);
        projectSelector.addItem("");
        for (var project : projects) {
            projectSelector.addItem(project.getData());
        }
        workerSelector.addItem("");
        for (var worker : workers) {
            workerSelector.addItem(worker.getName());
        }

        confirmButton.addActionListener(actionEvent -> {
            if (actionEvent.getSource() == confirmButton) {
                var data = dataTextField.getText();
                var error = "";
                Project project;
                Worker worker;
                if (data.isBlank()) {
                    error += "Поле название не может быть пустым\n";
                }
                if (projectSelector.getSelectedIndex() == 0) {
                    project = null;
                    error += "Проект не выбран\n";
                } else {
                    project = projects.get(projectSelector.getSelectedIndex() - 1);
                }
                if (workerSelector.getSelectedIndex() == 0) {
                    worker = null;
                    error += "Исполнитель не выбран\n";
                } else  {
                    worker = workers.get(workerSelector.getSelectedIndex() - 1);
                }
                if (error.isEmpty()) {
                    Database.execute((entityManager) -> {
                        entityManager.getTransaction().begin();
                        var task = new Task();
                        task.setData(data);
                        task.setProject(project);
                        task.setWorker(worker);
                        System.out.println(task);
                        entityManager.persist(task);
                        entityManager.getTransaction().commit();
                        return null;
                    });
                    dispose();
                    new TaskList();
                } else {
                    DialogUtil.showError(page, error);
                }
            }
        });

        cancelButton.addActionListener(actionEvent ->

        {
            if (actionEvent.getSource() == cancelButton) {
                dispose();
                new TaskList();
            }
        });

        setVisible(true);
    }
}
