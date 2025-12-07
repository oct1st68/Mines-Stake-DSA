package main;

import controller.GameController;
import ui.GameFrame;
import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        // Chạy GUI trong luồng sự kiện riêng biệt để tránh treo ứng dụng
        SwingUtilities.invokeLater(() -> {
            // 1. Tạo View
            GameFrame view = new GameFrame();

            // 2. Tạo Controller và đưa View vào
            // (Controller sẽ tự khởi tạo Model bên trong khi bấm Start)
            new GameController(view);

            // 3. Hiển thị
            view.setVisible(true);
        });
    }
}