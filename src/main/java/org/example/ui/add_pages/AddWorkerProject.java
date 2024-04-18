package org.example.ui.add_pages;

import org.example.Database;
import org.example.base.BaseForm;
import org.example.base.DialogUtil;
import org.example.entities.Project;
import org.example.entities.Worker;
import org.example.ui.edit_pages.EditWorkerPage;

import javax.swing.*;
import java.awt.*;

public class AddWorkerProject extends BaseForm {
    private Worker entity;
    private JComboBox projectSelector;
    private JButton confirmButton;
    private JButton cancelButton;
    private JPanel mainPanel;

    public AddWorkerProject(Worker entity) throws HeadlessException {
        super(1000, 400);
        this.entity = entity;
        setContentPane(mainPanel);
        var page = this;
        var projects =
                Database.fetch_all(Project.class).stream().filter(project -> !entity.getProjects().stream().map(Project::getId).toList().contains(project.getId())).toList();
        projectSelector.addItem("");
        for (var project : projects) {
            projectSelector.addItem(project.getData());
        }
        confirmButton.addActionListener(actionEvent -> {
            if (actionEvent.getSource() == confirmButton) {
                var error = "";
                Project project;
                if (projectSelector.getSelectedIndex() == 0) {
                    project = null;
                    error += "Проект не выбран\n";
                } else {
                    project =
                            projects.get(projectSelector.getSelectedIndex() - 1);
                }
                if (error.isEmpty()) {
                    final Worker[] db_entity = new Worker[1];
                    Database.execute((entityManager) -> {
                        entityManager.getTransaction().begin();
                        db_entity[0] = entityManager.find(entity.getClass(),
                                entity.getId());
                        db_entity[0].getProjects().add(project);
                        entityManager.persist(db_entity[0]);
                        entityManager.getTransaction().commit();
                        return null;
                    });
                    dispose();
                    new EditWorkerPage(db_entity[0]);
                } else {
                    DialogUtil.showError(page, error);
                }
            }
        });

        cancelButton.addActionListener(actionEvent ->

        {
            if (actionEvent.getSource() == cancelButton) {
                dispose();
                new EditWorkerPage(entity);
            }
        });

        setVisible(true);
    }
}
