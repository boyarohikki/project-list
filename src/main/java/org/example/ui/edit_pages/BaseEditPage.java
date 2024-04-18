package org.example.ui.edit_pages;

import org.example.Database;
import org.example.base.BaseForm;
import org.example.base.DialogUtil;
import org.example.ui.view_pages.ClientList;

import javax.swing.*;
import java.awt.*;
import java.util.function.Function;
import java.util.function.Supplier;

public abstract class BaseEditPage<T> extends BaseForm {
    protected JButton _deleteButton;
    protected JButton _cancelButton;
    protected JButton _confirmButton;
    protected T entity;

    public BaseEditPage(T entity) throws HeadlessException {
        super(1000, 400);
        this.entity = entity;
        assert entity != null;
    }

    protected void initButtons() {
        assert _deleteButton != null;
        assert _cancelButton != null;
        assert _confirmButton != null;
        var page = this;

        _confirmButton.addActionListener(actionEvent -> {
            if (actionEvent.getSource() == _confirmButton) {
                var error = validateForm();
                if (error.isBlank()) {
                    dispose();
                    Database.execute((entityManager) -> {
                        entityManager.getTransaction().begin();
                        entity = (T) entityManager.find(entity.getClass(), getIdentity(entity));
                        fillEntity();
                        entityManager.persist(entity);
                        entityManager.getTransaction().commit();
                        return null;
                    });
                    createParentForm();
                } else {
                    DialogUtil.showError(page, error);
                }
            }
        });

        _deleteButton.addActionListener(actionEvent -> {
            if (actionEvent.getSource() == _deleteButton) {
                if (DialogUtil.showConfirmationDialog(page, "Вы уверены?")) {
                    Database.execute(entityManager -> {
                        dispose();
                        entityManager.getTransaction().begin();
                        var to_delete = entityManager.find(entity.getClass(), getIdentity(entity));
                        entityManager.remove(to_delete);
                        entityManager.getTransaction().commit();
                        createParentForm();
                        return null;
                    });
                }
            }
        });

        _cancelButton.addActionListener(actionEvent -> {
            if (actionEvent.getSource() == _cancelButton) {
                dispose();
                createParentForm();
            }
        });
    }

    protected abstract void createParentForm();
    protected abstract Integer getIdentity(T entity);
    protected abstract String validateForm();
    protected abstract void fillEntity();
}
