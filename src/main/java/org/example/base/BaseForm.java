package org.example.base;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class BaseForm extends JFrame {
    private static String APP_TITLE = "Менеджер проектов";
    /*private static Image APP_ICON = null;
    static {
        try {
            APP_ICON = ImageIO.read(BaseForm.class.getClassLoader().getResource("icon.png"));
        } catch (IOException e) {
            DialogUtil.showError(null, "Не удалось загрузить иконку приложения");
            e.printStackTrace();
        }
    }*/


    public BaseForm(int width, int height) throws HeadlessException {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(width, height));
        setLocation(
                (Toolkit.getDefaultToolkit().getScreenSize().width - width) / 2,
                (Toolkit.getDefaultToolkit().getScreenSize().height - height) / 2
        );
        setTitle(APP_TITLE);
        /*if (APP_ICON != null) {
            setIconImage(APP_ICON);
        }*/
    }
}
