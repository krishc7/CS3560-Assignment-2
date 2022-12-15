package app;

import java.util.*;

import visitor.Visitor;

/**
 * SingleUser is the leaf in the Composite design pattern.
 * SingleUser represents both Subject and Observer of Observer design pattern.
 * SingleUser accepts visitors of Visitor design pattern.
 */

public class SingleUser extends User implements Subject {

    private static final List<String> POSITIVE_BANK = Arrays.asList("good", "great", "excellent", "awesome");

    private Map<String, Observer> followers;
    private Map<String, Subject> following;
    private List<String> newsFeed;

    private String latestMessage;
    private int positiveMessageCount;
    private long creationTime;

    public SingleUser(String id) {
        super(id);
        followers = new HashMap<String, Observer>();
        followers.put(this.getID(), this);
        following = new HashMap<String, Subject>();
        newsFeed = new ArrayList<String>();
        this.creationTime = setCreationTime();
    }

    // Returns map of users which follow this user

    public Map<String, Observer> getFollowers() {
        return followers;
    }

    // Returns the users this user is following

    public Map<String, Subject> getFollowing() {
        return following;
    }

    // Return newsfeed of this user

    public List<String> getNewsFeed() {
        return newsFeed;
    }

    // Send a message to the newsfeed of the users which follow this user

    public void sendMessage(String message) {
        this.latestMessage = message;
        this.setMessageCount(this.getMessageCount() + 1);

        // Check if message is positive, if so increment positive message count
        if (isPositiveMessage(message)) {
            ++positiveMessageCount;
        }

        notifyObservers();
    }

    // Returns the most recent message sent by this user

    public String getLatestMessage() {
        return this.latestMessage;
    }

    // Return number of positive tweets sent by this user

    public int getPositiveMessageCount() {
        return positiveMessageCount;
    }

    /*
     * Composite methods
     */

    
    // Return true if user id matches this user's 
     
    @Override
    public boolean contains(String id) {
        return this.getID().equals(id);
    }

    // Return the number of groups this user is in

    @Override
    public int getGroupUserCount() {
        return 0;
    }

    // Return number of single users in this group

    @Override
    public int getSingleUserCount() {
        return 1;
    }

    /*
     * Observer methods
     */

    // Update this user's newsfeed

    @Override
    public void update(Subject subject) {
        newsFeed.add(0, (((SingleUser) subject).getID() + ": " + ((SingleUser) subject).getLatestMessage()));
    }

    /*
     * Subject methods
     */

    // User passed into this function follows this user

    @Override
    public void attach(Observer observer) {
        addFollower(observer);
    }

    // Updates users which follow this user

    @Override
    public void notifyObservers() {
        for (Observer obs : followers.values()) {
            obs.update(this);
        }
    }

    // Visitor method

    @Override
    public void accept(Visitor visitor) {
        visitor.visitSingleUser(this);
    }

    /*
     * Private methods
     */

    // Adds the user as a follower of this user

    private void addFollower(Observer user) {
        this.getFollowers().put(((User) user).getID(), user);
        ((SingleUser) user).addUserToFollow(this);
    }

    // This user follows the user passed into this function

    private void addUserToFollow(Subject toFollow){
        if (toFollow.getClass() == SingleUser.class) {
            getFollowing().put(((User) toFollow).getID(), toFollow);
        }
    }

    // Return true if string contains a positive word

    private boolean isPositiveMessage(String message) {
        boolean positive = false;
        message = message.toLowerCase();
        for (String word : POSITIVE_BANK) {
            if (message.contains(word)) {
                positive = true;
            }
        }
        return positive;
    }

}
