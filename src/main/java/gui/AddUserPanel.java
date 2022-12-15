package gui;

import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;
import java.util.*;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.tree.DefaultMutableTreeNode;

import app.GroupUser;
import app.Observer;
import app.SingleUser;
import app.User;

// Class for text fields and buttons to add users and user groups

public class AddUserPanel extends ControlPanel {

    private JPanel treePanel;
    private Map<String, Observer> allUsers;

    private JButton addUserButton;
    private JButton addGroupButton;
    private JButton addVerButton;
    private JButton addUpdateButton;
    private JTextField userId;
    private JTextField groupId;

    // Create panel method

    public AddUserPanel(JPanel treePanel, Map<String, Observer> allUsers) {
        super();
        this.treePanel = treePanel;
        this.allUsers = allUsers;

        initializeComponents();
        addComponents();
    }

    private void addComponents() {
        addComponent(this, userId, 0, 0, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH);
        addComponent(this, addUserButton, 1, 0, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH);
        addComponent(this, groupId, 0, 1, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH);
        addComponent(this, addGroupButton, 1, 1, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH);
        addComponent(this, addVerButton, 0, 2, 2, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH);
        addComponent(this, addUpdateButton, 0, 3, 2, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH);
        

    }

    private void initializeComponents() {
        userId = new JTextField();
        groupId = new JTextField();

        addUserButton = new JButton("Add User");
        initializeAddUserButtonActionListener();

        addGroupButton = new JButton("Add Group");
        initializeAddGroupButtonActionListener();

        addVerButton = new JButton("Verify Users/Groups");
        initializeAddVerButtonActionListener();

        addUpdateButton = new JButton("Check Last Updated User");
        initializeAddUpdateButtonActionListener();
    }

    // Action listen methods

    private void initializeAddUserButtonActionListener() {
        addUserButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent arg0) {
                // check if user ID already exists
                if (allUsers.containsKey(userId.getText())) {
                    InfoDialogBox dialogBox = new InfoDialogBox("Error!",
                            "User already exists!\nPlease choose a different user name.",
                            JOptionPane.ERROR_MESSAGE);
                } else {
                    Observer child = new SingleUser(userId.getText());

                    allUsers.put(((User) child).getID(), child);
                    ((TreePanel) treePanel).addSingleUser((DefaultMutableTreeNode) child);
                }
            }
        });
    }

    private void initializeAddGroupButtonActionListener() {
        addGroupButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                // check if user ID already exists
                if (allUsers.containsKey(groupId.getText())) {
                    InfoDialogBox dialogBox = new InfoDialogBox("Error!",
                            "User already exists!\nPlease choose a different user name.",
                            JOptionPane.ERROR_MESSAGE);
                } else {
                    Observer child = new GroupUser(groupId.getText());

                    allUsers.put(((User) child).getID(), child);
                    ((TreePanel) treePanel).addGroupUser((DefaultMutableTreeNode) child);
                }
            }
        });
    }

    private void initializeAddVerButtonActionListener() {
        addVerButton.addActionListener(new ActionListener() {


            @Override
            public void actionPerformed(ActionEvent e) {

            Map<String, Observer> temp = new HashMap<String, Observer>(allUsers);
            Set<String> values = new HashSet<>();
            temp.remove("Root");
            boolean message = true;

            for (Map.Entry<String,Observer> entry : temp.entrySet()) {
                String value = entry.getKey();
                if (values.contains(value) && temp.size() > 1 || value.contains(" ")) {
                    message = false;
                }
                else {
                    values.add(value);
                }
            }
            if(message) {
                InfoDialogBox dialogBox = new InfoDialogBox("User Verification",
                            "User verification succeeded \n No duplicate users or user ID with spaces exist!",
                            JOptionPane.ERROR_MESSAGE);
            }
            else {
                InfoDialogBox dialogBox = new InfoDialogBox("User Verification",
                            "User verification failed \n A duplicate ID or an ID with a space exists!  ",
                            JOptionPane.ERROR_MESSAGE);
            }

            }
        });
    }

    private void initializeAddUpdateButtonActionListener() {
        addVerButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                Map<String, Observer> temp = new HashMap<String, Observer>(allUsers);



            }


        });
    }


}


