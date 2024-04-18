package org.example.ui.edit_pages;

import org.example.Database;
import org.example.base.DialogUtil;
import org.example.entities.Client;
import org.example.ui.view_pages.ClientList;

import javax.swing.*;
import java.awt.*;

public class EditClientPage extends BaseEditPage<Client> {
    private JPanel mainPanel;
    private JTextField nameTextField;

    private JButton confirmButton;
    protected JButton cancelButton;
    protected JButton deleteButton;

    private final Client entity;

    public EditClientPage(Client entity) throws HeadlessException {
        super(entity);
        this._cancelButton = cancelButton;
        this._deleteButton = deleteButton;
        this.entity = entity;
        assert entity != null;
        nameTextField.setText(entity.getName());
        setContentPane(mainPanel);
        initButtons();
        setVisible(true);
    }

    @Override
    protected void createParentForm() {
        new ClientList();
    }

    @Override
    protected Integer getIdentity(Client entity) {
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
