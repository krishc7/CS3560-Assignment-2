package gui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;

import app.*;
import gui.ControlPanel;

// User view panel interface

public class UserViewPanel extends ControlPanel {

    private static JFrame frame;
    private GridBagConstraints constraints;

    private JTextField toFollowTextField;

    private JTextArea tweetMessageTextArea;
    private JTextArea currentFollowingTextArea;
    private JTextArea newsFeedTextArea;
    private JTextArea timeTextArea;

    private JScrollPane tweetMessageScrollPane;
    private JScrollPane currentFollowingScrollPane;
    private JScrollPane newsFeedScrollPane;

    private JButton followUserButton;
    private JButton postTweetButton;

    private Subject user;
    private Map<String, Observer> allUsers;
    private Map<String, JPanel> openPanels;

    /**
     * Create the panel.
     */
    public UserViewPanel(Map<String, Observer> allUsers, Map<String, JPanel> allPanels, DefaultMutableTreeNode user) {
        super();

        this.user = (Subject) user;
        this.allUsers = allUsers;
        this.openPanels = allPanels;
        initializeComponents();
        addComponents();
    }

    /*
     * Private methods
     */

    private void addComponents() {
        addComponent(frame, toFollowTextField, 0, 0, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH);
        addComponent(frame, followUserButton, 1, 0, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH);
        addComponent(frame, currentFollowingTextArea, 0, 1, 2, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH);
        addComponent(frame, tweetMessageScrollPane, 0, 2, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH);
        addComponent(frame, postTweetButton, 1, 2, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH);
        addComponent(frame, newsFeedScrollPane, 0, 3, 2, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH);
        addComponent(frame, timeTextArea, 0, 4, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH);
    }

    private void initializeComponents() {
        frame = new JFrame("User View");
        formatFrame();

        constraints = new GridBagConstraints();
        constraints.ipady = 100;

        toFollowTextField = new JTextField();
        followUserButton = new JButton("Follow User");
        initializeFollowUserButtonActionListener();

        currentFollowingTextArea = new JTextArea("Current Following: ");
        formatTextArea(currentFollowingTextArea);
        currentFollowingScrollPane = new JScrollPane(currentFollowingTextArea);
        currentFollowingScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);

        tweetMessageTextArea = new JTextArea();
        tweetMessageScrollPane = new JScrollPane(tweetMessageTextArea);
        tweetMessageScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);

        postTweetButton = new JButton("Post Tweet");
        initializePostTweetButtonActionListener();

        newsFeedTextArea = new JTextArea("News Feed: ");
        formatTextArea(newsFeedTextArea);
        newsFeedScrollPane = new JScrollPane(newsFeedTextArea);
        newsFeedScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        timeTextArea = new JTextArea("User Creation Time:");
        formatTextArea(timeTextArea);
        updateTimeTextArea();

        // current following and news feed lists reflect most recent state of UserViewPanel
        updateCurrentFollowingTextArea();

        // news feed is updated even while UserViewPanel is closed
        updateNewsFeedTextArea();
    }

    private void formatTextArea(JTextArea textArea) {
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setRows(8);
        textArea.setEditable(false);
    }

    private void formatFrame() {
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLayout(new GridBagLayout());
        frame.setSize(800, 400);
        frame.setVisible(true);
        frame.setTitle(((User) user).getID());

        // allows UserViewPanel to be reopened after it has been closed
        frame.addWindowListener(new WindowAdapter() {

            public void windowClosing(WindowEvent e) {
                openPanels.remove(((User) user).getID());
            }
        });
    }

    private void updateTimeTextArea() {
        long newTime = ((SingleUser) user).getCreationTime();

        long updTime = ((SingleUser) user).getLastUpdateTime();
        //creating Date from millisecond
       Date currentDate = new Date(newTime);

       Date updateTime = new Date(updTime);
    
       DateFormat df = new SimpleDateFormat("HH:mm:ss");
     
        timeTextArea.setText("Creation time: " + df.format(currentDate) +
        "\n\nLast update time: " + df.format(updateTime));
    }

    /**
     * Updates the news feed display.
     */
    private void updateNewsFeedTextArea() {
        String list = "News Feed: \n";

        for (String news : ((SingleUser) user).getNewsFeed()) {
            list += " - " + news + "\n";
        }

        // show most recent message at top of news feed
        newsFeedTextArea.setText(list);
        newsFeedTextArea.setCaretPosition(0);
        ((SingleUser) user).setLastUpdateTime();
    }

    /**
     * Updates the current following display.
     */
    private void updateCurrentFollowingTextArea() {
        String list = "Current Following: \n";
        for (String following : ((SingleUser) user).getFollowing().keySet()) {
            list += " - " + following + "\n";
        }
        currentFollowingTextArea.setText(list);
        currentFollowingTextArea.setCaretPosition(0);
    }

    /*
     * Action Listeners
     */

    /**
     * Initializes action listener for PostTweetButton.  Sends
     * specified message to the news feeds of the followers of
     * this User.
     */
    private void initializePostTweetButtonActionListener() {
        postTweetButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                ((SingleUser) user).sendMessage(tweetMessageTextArea.getText());

                for (JPanel panel : openPanels.values()) {
                    ((UserViewPanel) panel).updateNewsFeedTextArea();
                }
            }
        });
    }

    /**
     * Initializes action listener for FollowUserButton.  Adds this
     * User as a follower of the specified SingleUser.  Cannot follow
     * a GroupUser.
     */
    private void initializeFollowUserButtonActionListener() {
        followUserButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                User toFollow = (User) allUsers.get(toFollowTextField.getText());

                if (!allUsers.containsKey(toFollowTextField.getText())) {
                    InfoDialogBox dialogBox = new InfoDialogBox("Error!",
                            "User does not exist!",
                            JOptionPane.ERROR_MESSAGE);

                } else if (toFollow.getClass() == GroupUser.class) {
                    InfoDialogBox dialogBox = new InfoDialogBox("Error!",
                            "Cannot follow a group!",
                            JOptionPane.ERROR_MESSAGE);
                } else if (allUsers.containsKey(toFollowTextField.getText())) {
                    ((Subject) toFollow).attach((Observer) user);
                }

                // show current following as list
                updateCurrentFollowingTextArea();
            }
        });
    }

}
