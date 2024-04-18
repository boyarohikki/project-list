package org.example.ui.view_pages;

import org.example.Database;
import org.example.base.BaseForm;
import org.example.base.ExtendedTableModel;
import org.example.entities.Client;
import org.example.ui.MainPage;
import org.example.ui.add_pages.AddClientPage;
import org.example.ui.edit_pages.EditClientPage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ClientList extends BaseForm {
    private JTable table;
    private JButton backButton;
    private JPanel mainPanel;
    private JButton addClientButton;
    private final ExtendedTableModel<Client> model;

    public ClientList() throws HeadlessException {
        super(1000, 400);
        setContentPane(mainPanel);
        String[] columns = {"Номер клиента", "Имя клиента", "Проекты"};

        var clients = Database.fetch_all(Client.class);

        model = new ExtendedTableModel<>(Client.class, columns);
        model.setAllRows(clients);
        table.setModel(model);
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2 && e.getButton() == MouseEvent.BUTTON1) {
                    var row = table.rowAtPoint(e.getPoint());
                    if (row != -1) {
                        dispose();
                        new EditClientPage(model.getFilteredRows().get(row));
                    }
                }
            }
        });

        addClientButton.addActionListener(actionEvent -> {
            if (actionEvent.getSource() == addClientButton) {
                dispose();
                new AddClientPage();
            }
        });
        backButton.addActionListener(actionEvent -> {
            if (actionEvent.getSource() == backButton) {
                dispose();
                new MainPage();
            }
        });

        setVisible(true);
    }
}
