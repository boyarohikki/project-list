package org.example.ui.edit_pages;

import org.example.Database;
import org.example.base.Config;
import org.example.entities.Client;
import org.example.entities.Project;
import org.example.ui.view_pages.ProjectList;

import javax.swing.*;
import java.awt.*;
import java.text.ParseException;
import java.util.Calendar;
import java.util.List;

public class EditProjectPage extends BaseEditPage<Project> {
    private final List<Client> clients;
    private JTextField dataTextField;
    private JTextField statusTextField;
    private JTextField dateTextField;
    private JPanel mainPanel;
    private JComboBox<String> clientSelector;
    private JButton confirmButton;
    private JButton cancelButton;
    private JButton deleteButton;

    public EditProjectPage(Project entity) throws HeadlessException {
        super(entity);
        this._cancelButton = cancelButton;
        this._confirmButton = confirmButton;
        this._deleteButton = deleteButton;
        setContentPane(mainPanel);
        clients = Database.fetch_all(Client.class);
        clientSelector.addItem("");
        for (var client : clients) {
            clientSelector.addItem(client.getName());
        }
        dataTextField.setText(entity.getData());
        statusTextField.setText(entity.getStatus());
        dateTextField.setText(Config.formatDate(entity.getDate()));
        clientSelector.setSelectedIndex(clients.stream().map(Client::getId).toList().indexOf(entity.getClient().getId()) + 1);

        initButtons();
        setVisible(true);
    }

    @Override
    protected void createParentForm() {
        new ProjectList();
    }

    @Override
    protected Integer getIdentity(Project entity) {
        return entity.getId();
    }

    @Override
    protected String validateForm() {
        var data = dataTextField.getText();
        var status = statusTextField.getText();
        var dateText = dateTextField.getText();
        var clientIndex = clientSelector.getSelectedIndex();
        String error = "";
        if (data.isBlank()) {
            error += "Поле имя не может быть пустым\n";
        }
        if (status.isBlank()) {
            error += "Поле статус не может быть пустым\n";
        }
        try {
            Config.parseDate(dateText);
        } catch (ParseException e) {
            error += "Неверный формат даты\n";
        }
        if (clientIndex == 0) {
            error += "Не выбран клиент\n";
        }
        return error;
    }

    @Override
    protected void fillEntity() {
        entity.setData(dataTextField.getText());
        entity.setClient(clients.get(clientSelector.getSelectedIndex() - 1));
        entity.setStatus(statusTextField.getText());
        try {
            var calendar = Calendar.getInstance();
            calendar.setTime(Config.parseDate(dateTextField.getText()));
            entity.setDate(calendar);
        } catch (ParseException ignored) {
        }
    }
}
