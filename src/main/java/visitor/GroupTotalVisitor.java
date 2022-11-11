package visitor;

import app.GroupUser;
import app.SingleUser;
import app.User;


/*
 * Obtains total number of GroupUsers under the specified user
 * Implements the Visitor design pattern
*/ 

public class GroupTotalVisitor implements Visitor {

    @Override
    public int visitUser(User user) {
        int count = 0;

        if (user.getClass() == SingleUser.class) {
            count += visitSingleUser(user);
        } else if (user.getClass() == GroupUser.class) {
            count += visitGroupUser(user);
        }

        return count;
    }

    @Override
    public int visitSingleUser(User user) {
        return 0;
    }

    @Override
    public int visitGroupUser(User user) {
        int count = 0;

        for (User u : ((GroupUser) user).getGroupUsers().values()) {
            if (u.getClass() == GroupUser.class) {
                ++count;
            }
            count += visitUser(u);
        }

        return count;
    }

}
