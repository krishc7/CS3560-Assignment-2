package app;

import java.util.HashMap;
import java.util.Map;

import visitor.Visitor;

 /** The GroupUser class implements multiple design patterns
  * It is the Observor in the Observer design pattern
  * It accepts visitors as part of the Visitor design pattern
  */

public class GroupUser extends User {

    private Map<String,User> groupUsers;
    long creationTime;

    public GroupUser(String id) {
        super(id);
        groupUsers = new HashMap<String,User>();
        this.creationTime = getCreationTime();
    }

    public Map<String,User> getGroupUsers() {
        return groupUsers;
    }

    // Add user to group if it is not already a part of the group

    public User addUserInGroup(User user) {
        if (!this.contains(user.getID())) {
            this.groupUsers.put(user.getID(), user);
        }
        return this;
    }

    // Check if GroupUser contains a user id

    @Override
    public boolean contains(String id) {
        boolean contains = false;
        for (User user : groupUsers.values()) {
            if (user.contains(id)) {
                contains = true;
            }
        }
        return contains;
    }

    // Get count of users in group

    @Override
    public int getSingleUserCount() {
        int count = 0;
        for (User user : this.groupUsers.values()) {
            count += user.getSingleUserCount();
        }
        return count;
    }

    // Get count of group users

    @Override
    public int getGroupUserCount() {
        int count = 0;
        for (User user : this.groupUsers.values()) {
            if (user.getClass() == GroupUser.class) {
                ++count;
                count += user.getGroupUserCount();
            }
        }
        return count;
    }

    // Get count of messages sent by users in this group

    @Override
    public int getMessageCount() {
        int msgCount = 0;
        for (User user : this.groupUsers.values()) {
            msgCount += user.getMessageCount();
        }
        return msgCount;
    }

    

    // Update news feed of users in this group
    @Override
    public void update(Subject message) {
        for (User user : groupUsers.values()) {
            ((Observer) user).update(message);
        }
    }

    @Override
    public void accept(Visitor y) {
        for (User user : groupUsers.values()) {
            user.accept(y);
        }
        y.visitGroupUser(this);
    }

    // Return true if this GroupUser contains a GroupUser

    private boolean containsGroupUser() {
        boolean x = false;
        for (User user : this.groupUsers.values()) {
            if (user.getClass() == GroupUser.class) {
                x = true;
            }
        }
        return x;
    }

    // Returns the current time in milliseconds
    public long getCreationTime() {
        return System.currentTimeMillis();
    }


}
