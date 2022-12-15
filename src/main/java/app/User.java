package app;

import javax.swing.tree.DefaultMutableTreeNode;

import visitor.Visitor;

// Users make up user groups, which are treated as one object, implementing the Composite design architecture

public abstract class User extends DefaultMutableTreeNode implements Observer {

    private String userID;
    private int messageCount;
    private long creationTime;
    private long lastUpdateTime;

    public abstract boolean contains(String id);
    public abstract int getSingleUserCount();
    public abstract int getGroupUserCount();

    public User(String id) {
        super(id);
        this.userID = id;
        this.setMessageCount(0);
        this.creationTime = setCreationTime();
        this.lastUpdateTime = 0;
    }

    // Return this user's user ID

    public String getID() {
        return userID;
    }

    // Return total number of messages sent by this user

    public int getMessageCount() {
        return messageCount;
    }

    // Set the number of messages sent by this user

    public void setMessageCount(int count) {
        this.messageCount = count;
    }
    // Returns the current time in milliseconds
    public long getCreationTime() {
        return creationTime;
    }

    // Returns the current time in milliseconds
    public long setCreationTime() {
        return System.currentTimeMillis();
    }

    public void setLastUpdateTime() {
        this.lastUpdateTime = System.currentTimeMillis();
    }

    public long getLastUpdateTime() {
        return lastUpdateTime;
    }




    // Accept method for Visitors as part of the Visitor design pattern

    public abstract void accept(Visitor visitor);

}
