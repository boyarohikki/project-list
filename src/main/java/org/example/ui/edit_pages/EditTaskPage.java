package org.example.ui.edit_pages;

import org.example.Database;
import org.example.entities.Project;
import org.example.entities.Task;
import org.example.entities.Worker;
import org.example.ui.view_pages.TaskList;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class EditTaskPage extends BaseEditPage<Task> {
    private JTextField dataTextField;
    private JComboBox<String> projectSelector;
    private JComboBox<String> workerSelector;
    private JButton confirmButton;
    private JButton cancelButton;
    private JPanel mainPanel;
    private JButton deleteButton;
    private final List<Project> projects;
    private final List<Worker> workers;

    public EditTaskPage(Task entity) throws HeadlessException {
        super(entity);
        this._cancelButton = cancelButton;
        this._confirmButton = confirmButton;
        this._deleteButton = deleteButton;
        setContentPane(mainPanel);
        projects = Database.fetch_all(Project.class);
        workers = Database.fetch_all(Worker.class);
        projectSelector.addItem("");
        for (var project : projects) {
            projectSelector.addItem(project.getData());
        }
        projectSelector.setSelectedIndex(projects.stream().map(Project::getId).toList().indexOf(entity.getProject().getId()) + 1);

        workerSelector.addItem("");
        for (var worker : workers) {
            workerSelector.addItem(worker.getName());
        }
        workerSelector.setSelectedIndex(workers.stream().map(Worker::getId).toList().indexOf(entity.getWorker().getId()) + 1);
        dataTextField.setText(entity.getData());

        initButtons();
        setVisible(true);
    }

    @Override
    protected void createParentForm() {
        new TaskList();
    }

    @Override
    protected Integer getIdentity(Task entity) {
        return entity.getId();
    }

    @Override
    protected String validateForm() {
        var error = "";
        if (dataTextField.getText().isBlank()) {
            error += "Поле название не может быть пустым\n";
        }
        if (projectSelector.getSelectedIndex() == 0) {
            error += "Проект не выбран\n";
        }
        if (workerSelector.getSelectedIndex() == 0) {
            error += "Исполнитель не выбран\n";
        }
        return error;
    }

    @Override
    protected void fillEntity() {
        entity.setData(dataTextField.getText());
        entity.setProject(projects.get(projectSelector.getSelectedIndex() - 1));
        entity.setWorker(workers.get(workerSelector.getSelectedIndex() - 1));
    }
}
