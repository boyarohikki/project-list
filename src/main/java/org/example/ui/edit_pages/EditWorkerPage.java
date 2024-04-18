package org.example.ui.edit_pages;

import org.example.entities.Worker;
import org.example.ui.add_pages.AddWorkerProject;
import org.example.ui.view_pages.WorkerList;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class EditWorkerPage extends BaseEditPage<Worker> {
    private JPanel mainPanel;
    private JTextField nameTextField;
    private JButton confirmButton;
    private JButton cancelButton;
    private JButton deleteButton;
    private JPanel projectsPanel;
    private JPanel tasksPanel;
    private JButton addWorkerProjectButton;
    private List<JButton> deleteProjectButtons = new ArrayList<JButton>();

    public EditWorkerPage(Worker entity) throws HeadlessException {
        super(entity);
        this._cancelButton = cancelButton;
        this._deleteButton = deleteButton;
        this._confirmButton = confirmButton;
        setContentPane(mainPanel);
        nameTextField.setText(entity.getName());
        refreshProjectsPanel(entity);
        var tasksLayout = new GridLayout(entity.getTasks().size(), 2);
        tasksPanel.setLayout(tasksLayout);
        for (var task : entity.getTasks()) {
            tasksPanel.add(new JLabel(String.valueOf(task.getId())));
            tasksPanel.add(new JLabel(task.getData()));
        }
        initButtons();
        setVisible(true);
    }

    private void refreshProjectsPanel(Worker entity) {
        deleteProjectButtons.clear();
        projectsPanel.removeAll();
        projectsPanel.setLayout(new GridLayout(entity.getProjects().size(), 2));
        for (var project : entity.getProjects()) {
            projectsPanel.add(new JLabel(project.getData()));
            /*
            var deleteProjectButton = new JButton();
            deleteProjectButtons.add(deleteProjectButton);
            deleteProjectButton.setText("Удалить");
            deleteProjectButton.addActionListener(actionEvent -> {
                for (int i = 0; i < deleteProjectButtons.size(); i++) {
                    var button = deleteProjectButtons.get(i);
                    if(actionEvent.getSource() == button) {
                        int finalI = i;
                        Database.execute(entityManager -> {
                            entityManager.getTransaction().begin();
                            var db_entity = entityManager.find(entity.getClass(), getIdentity(entity));
                            db_entity.getProjects().remove(finalI);
                            entityManager.persist(db_entity);
                            refreshProjectsPanel(db_entity);
                            entityManager.getTransaction().commit();
                            return null;
                        });
                    }
                }
            });
            projectsPanel.add(deleteProjectButtons.get(deleteProjectButtons.size() - 1));
            */
        }
        projectsPanel.repaint();
    }

    @Override
    protected void initButtons() {
        super.initButtons();
        addWorkerProjectButton.addActionListener(actionEvent ->
        {
            if (actionEvent.getSource() == addWorkerProjectButton) {
                dispose();
                new AddWorkerProject(entity);
            }
        });
    }

    @Override
    protected void createParentForm() {
        new WorkerList();
    }

    @Override
    protected Integer getIdentity(Worker entity) {
        return entity.getId();
    }

    @Override
    protected String validateForm() {
        return nameTextField.getText().isBlank() ? "Поле имя не может быть пустым" : "";
    }

    @Override
    protected void fillEntity() {
        entity.setName(nameTextField.getText());
    }
}
