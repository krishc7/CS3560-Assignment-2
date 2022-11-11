package gui;

import javax.swing.JOptionPane;

// Message dialogbox for displaying messages and errors

public class InfoDialogBox {

    // Create dialogbox with specified title and message 
    
    public InfoDialogBox(String title, String message, int messageType) {
        JOptionPane.showMessageDialog(null, message, title, messageType);
    }

}
