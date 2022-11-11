package gui;

import java.awt.EventQueue;

import javax.swing.JFrame;

// Main method launches the application

public class Driver extends JFrame {
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    AdminControlPanel frame = AdminControlPanel.getInstance();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

}
