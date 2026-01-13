package com.azs.cashdesk.client;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class AzsManagerForm extends JFrame {
    private DefaultListModel<String> listModel = new DefaultListModel<>();
    private JList<String> dataList = new JList<>(listModel);
    private String authHeader;
    private String currentCategory = "fuels"; // "fuels" или "products"

    private JTextField nameField = new JTextField(10);
    private JTextField priceField = new JTextField(5);

    public AzsManagerForm(String authHeader) {
        this.authHeader = authHeader;
        setTitle("АЗС+ Панель управления (Swing)");
        setSize(650, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // --- ВЕРХ: КНОПКИ КАТЕГОРИЙ И ОБНОВЛЕНИЯ ---
        JPanel topPanel = new JPanel();
        JButton fuelBtn = new JButton("⛽ Топливо");
        JButton productBtn = new JButton("🛒 Товары");
        JButton refreshBtn = new JButton("🔄 ОБНОВИТЬ");

        refreshBtn.setBackground(new Color(191, 219, 254)); // Светло-голубой
        topPanel.add(fuelBtn);
        topPanel.add(productBtn);
        topPanel.add(Box.createHorizontalStrut(20));
        topPanel.add(refreshBtn);
        add(topPanel, BorderLayout.NORTH);

        // --- ЦЕНТР: СПИСОК ДАННЫХ ---
        dataList.setFont(new Font("Monospaced", Font.PLAIN, 14));
        add(new JScrollPane(dataList), BorderLayout.CENTER);

        // --- НИЗ: ПАНЕЛЬ ДОБАВЛЕНИЯ И УДАЛЕНИЯ ---
        JPanel bottomPanel = new JPanel(new GridLayout(2, 1));

        JPanel inputPanel = new JPanel();
        inputPanel.add(new JLabel("Название/Тип:"));
        inputPanel.add(nameField);
        inputPanel.add(new JLabel("Цена:"));
        inputPanel.add(priceField);

        JPanel actionPanel = new JPanel();
        JButton addBtn = new JButton("➕ ДОБАВИТЬ");
        JButton delBtn = new JButton("❌ УДАЛИТЬ ВЫБРАННОЕ");

        addBtn.setBackground(new Color(34, 197, 94));
        addBtn.setForeground(Color.WHITE);
        delBtn.setBackground(new Color(239, 68, 68));
        delBtn.setForeground(Color.WHITE);

        actionPanel.add(addBtn);
        actionPanel.add(delBtn);

        bottomPanel.add(inputPanel);
        bottomPanel.add(actionPanel);
        add(bottomPanel, BorderLayout.SOUTH);

        // --- ОБРАБОТЧИКИ СОБЫТИЙ ---
        fuelBtn.addActionListener(e -> { currentCategory = "fuels"; loadData(); });
        productBtn.addActionListener(e -> { currentCategory = "products"; loadData(); });
        refreshBtn.addActionListener(e -> loadData());

        addBtn.addActionListener(e -> sendRequest("add"));
        delBtn.addActionListener(e -> {
            if (dataList.getSelectedValue() == null) {
                JOptionPane.showMessageDialog(this, "Сначала выберите строку в списке!");
                return;
            }
            sendRequest("delete");
        });

        loadData(); // Загрузка данных при открытии
    }

    private void loadData() {
        try {
            URL url = new URL("http://localhost:8080/api/" + currentCategory);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestProperty("Authorization", "Basic " + authHeader);

            if (conn.getResponseCode() == 200) {
                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8));
                StringBuilder res = new StringBuilder();
                String line;
                while ((line = in.readLine()) != null) res.append(line);
                in.close();
                parseAndDisplay(res.toString());
            } else {
                JOptionPane.showMessageDialog(this, "Ошибка авторизации или доступа: " + conn.getResponseCode());
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Ошибка сети: " + e.getMessage());
        }
    }

    private void sendRequest(String mode) {
        try {
            String path;
            String params;

            if (mode.equals("add")) {
                path = currentCategory.equals("fuels") ? "/admin/add-fuel" : "/admin/add-product";
                String key = currentCategory.equals("fuels") ? "type" : "name";
                params = key + "=" + nameField.getText() + "&price=" + priceField.getText();
            } else {
                path = currentCategory.equals("fuels") ? "/admin/delete-fuel" : "/admin/delete-product";
                String selected = dataList.getSelectedValue();
                // Извлекаем ID: берем всё между "ID:" и первым "|"
                String idPart = selected.split("ID:")[1].split("\\|")[0].trim();
                params = "id=" + idPart;
            }

            URL url = new URL("http://localhost:8080" + path);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Authorization", "Basic " + authHeader);
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

            // Важно: отключаем редиректы, чтобы не терять заголовок Auth и не получать 401
            conn.setInstanceFollowRedirects(false);
            conn.setDoOutput(true);

            try (OutputStream os = conn.getOutputStream()) {
                os.write(params.getBytes(StandardCharsets.UTF_8));
            }

            int code = conn.getResponseCode();

            // Коды 200-299 (успех) или 302 (редирект после успеха) считаем победой
            if (code >= 200 && code <= 302) {
                nameField.setText("");
                priceField.setText("");
                loadData();
                if (mode.equals("delete")) JOptionPane.showMessageDialog(this, "Удалено успешно!");
            } else {
                JOptionPane.showMessageDialog(this, "Ошибка выполнения (Код " + code + ")");
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Ошибка запроса: " + e.getMessage());
        }
    }

    private void parseAndDisplay(String json) {
        listModel.clear();
        if (json == null || json.length() < 3) return;

        // Удаляем [ ] в начале и конце
        json = json.trim();
        if (json.startsWith("[")) json = json.substring(1);
        if (json.endsWith("]")) json = json.substring(0, json.length() - 1);

        if (json.isEmpty()) return;

        // Разделяем на объекты по шаблону }, {
        String[] objects = json.split("\\},\\{");

        for (String obj : objects) {
            obj = obj.replace("{", "").replace("}", "").replace("\"", "");

            String id = extract(obj, "id");
            String name = extract(obj, currentCategory.equals("fuels") ? "type" : "name");
            String price = extract(obj, "price");

            listModel.addElement(String.format("ID: %-4s | %-15s | Цена: %s руб.", id, name, price));
        }
    }

    private String extract(String row, String key) {
        String[] parts = row.split(",");
        for (String part : parts) {
            String[] pair = part.split(":");
            if (pair.length > 1 && pair[0].trim().equalsIgnoreCase(key)) {
                return pair[1].trim();
            }
        }
        return "0";
    }
}