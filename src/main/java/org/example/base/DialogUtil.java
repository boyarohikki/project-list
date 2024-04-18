package org.example.base;

import javax.swing.*;
import java.awt.*;

public class DialogUtil {
    public static void showError(Component parent, String error) {
        JOptionPane.showMessageDialog(parent, error, "Ошибка", JOptionPane.ERROR_MESSAGE);
    }

    public static void showInfo(Component parent, String message) {
        JOptionPane.showMessageDialog(parent, message, "Информация", JOptionPane.INFORMATION_MESSAGE);
    }

    public static boolean showConfirmationDialog(Component parent, String prompt) {
        return 0 == JOptionPane.showConfirmDialog(parent, prompt, "", JOptionPane.YES_NO_OPTION);
    }
}
