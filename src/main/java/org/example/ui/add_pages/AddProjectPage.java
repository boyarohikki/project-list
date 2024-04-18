package org.example.ui.add_pages;

import org.example.Database;
import org.example.base.BaseForm;
import org.example.base.DialogUtil;
import org.example.entities.Client;
import org.example.entities.Project;
import org.example.ui.view_pages.ProjectList;

import javax.swing.*;
import java.awt.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class AddProjectPage extends BaseForm {
    private JTextField dataTextField;
    private JTextField statusTextField;
    private JTextField dateTextField;
    private JPanel mainPanel;
    private JComboBox<String> clientSelector;
    private JButton confirmButton;
    private JButton cancelButton;

    public AddProjectPage() throws HeadlessException {
        super(1000, 400);
        setContentPane(mainPanel);
        var page = this;
        var clients = Database.fetch_all(Client.class);
        clientSelector.addItem("");
        for (var client : clients) {
            clientSelector.addItem(client.getName());
        }
        confirmButton.addActionListener(actionEvent -> {
            if (actionEvent.getSource() == confirmButton) {
                var data = dataTextField.getText();
                var status = statusTextField.getText();
                var dateText = dateTextField.getText();
                var clientIndex = clientSelector.getSelectedIndex();
                Client client;
                Calendar date = Calendar.getInstance();
                String error = "";
                if (data.isBlank()) {
                    error += "Поле имя не может быть пустым\n";
                }
                if (status.isBlank()) {
                    error += "Поле статус не может быть пустым\n";
                }
                try {
                    var parsed = new SimpleDateFormat("yyyy-MM-dd").parse(dateText);
                    date.setTime(parsed);
                } catch (ParseException e) {
                    error += "Неверный формат даты\n";
                }
                if (clientIndex == 0) {
                    client = null;
                    error += "Не выбран клиент\n";
                } else {
                    client = clients.get(clientIndex - 1);
                }
                if (error.isEmpty()) {
                    Database.execute((entityManager) -> {
                        var project = new Project();
                        project.setData(data);
                        project.setDate(date);
                        project.setClient(client);
                        project.setStatus(status);
                        entityManager.getTransaction().begin();
                        entityManager.persist(project);
                        entityManager.getTransaction().commit();
                        return null;
                    });
                    dispose();
                    new ProjectList();
                } else {
                    DialogUtil.showError(page, error);
                }
            }
        });

        cancelButton.addActionListener(actionEvent ->

        {
            if (actionEvent.getSource() == cancelButton) {
                dispose();
                new ProjectList();
            }
        });

        setVisible(true);
    }
}
