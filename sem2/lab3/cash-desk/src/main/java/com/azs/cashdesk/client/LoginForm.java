package com.azs.cashdesk.client;

import javax.swing.*;
import java.awt.*;
import java.util.Base64;

public class LoginForm extends JFrame {
    private JTextField userField = new JTextField("admin");
    private JPasswordField passField = new JPasswordField("admin123");

    public LoginForm() {
        setTitle("АЗС+ Вход");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(4, 1, 10, 10));

        add(new JLabel("  Логин:"));
        add(userField);
        add(new JLabel("  Пароль:"));
        add(passField);

        JButton loginBtn = new JButton("ВОЙТИ");
        add(loginBtn);

        loginBtn.addActionListener(e -> {
            String username = userField.getText();
            String password = new String(passField.getPassword());

            // Кодируем "логин:пароль" в Base64 для заголовка Authorization
            String auth = username + ":" + password;
            String encodedAuth = Base64.getEncoder().encodeToString(auth.getBytes());

            new AzsManagerForm(encodedAuth).setVisible(true);
            this.dispose();
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LoginForm().setVisible(true));
    }
}