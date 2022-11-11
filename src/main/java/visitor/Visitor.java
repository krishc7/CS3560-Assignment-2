package visitor;

import app.User;

// Class implements visitor design pattern

public interface Visitor {

    public int visitUser(User user);
    public int visitSingleUser(User user);
    public int visitGroupUser(User user);

}
