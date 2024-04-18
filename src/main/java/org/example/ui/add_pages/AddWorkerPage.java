package org.example.ui.add_pages;

import org.example.Database;
import org.example.base.BaseForm;
import org.example.base.DialogUtil;
import org.example.entities.Worker;
import org.example.ui.view_pages.WorkerList;

import javax.swing.*;
import java.awt.*;

public class AddWorkerPage extends BaseForm {
    private JPanel mainPanel;
    private JTextField nameTextField;
    private JButton confirmButton;
    private JButton cancelButton;

    public AddWorkerPage() throws HeadlessException {
        super(1000, 400);
        setContentPane(mainPanel);
        var page = this;
        confirmButton.addActionListener(actionEvent -> {
            if (actionEvent.getSource() == confirmButton) {
                var name = nameTextField.getText();
                if (name.isBlank()) {
                    DialogUtil.showError(page, "Поле имя не может быть пустым");
                } else {
                    Database.execute((entityManager) -> {
                        var worker = new Worker();
                        worker.setName(name);
                        entityManager.getTransaction().begin();
                        entityManager.persist(worker);
                        entityManager.getTransaction().commit();
                        return null;
                    });
                    dispose();
                    new WorkerList();
                }
            }
        });

        cancelButton.addActionListener(actionEvent ->

        {
            if (actionEvent.getSource() == cancelButton) {
                dispose();
                new WorkerList();
            }
        });

        setVisible(true);
    }
}
