/** Represents a social network. The network has users, who follow other uesrs.
 *  Each user is an instance of the User class. */
public class Network {

    // Fields
    private User[] users;  // the users in this network (an array of User objects)
    private int userCount; // actual number of users in this network

    /** Creates a network with a given maximum number of users. */
    public Network(int maxUserCount) {
        this.users = new User[maxUserCount];
        this.userCount = 0;
    }

    /** Creates a network  with some users. The only purpose of this constructor is 
     *  to allow testing the toString and getUser methods, before implementing other methods. */
    public Network(int maxUserCount, boolean gettingStarted) {
        this(maxUserCount);
        users[0] = new User("Foo");
        users[1] = new User("Bar");
        users[2] = new User("Baz");
        userCount = 3;
    }

    public int getUserCount() {
        return this.userCount;
    }
    /** Finds in this network, and returns, the user that has the given name.
     *  If there is no such user, returns null.
     *  Notice that the method receives a String, and returns a User object. */
    public User getUser(String name) {
        if (name == null) {
            return null;
        }
        name = name.toLowerCase();
        for(int i = 0; i < userCount; i++) {
            if (name.equals(users[i].getName().toLowerCase())) {
                return users[i];
            }
        }
        return null;
    }

    /** Adds a new user with the given name to this network.
    *  If ths network is full, does nothing and returns false;
    *  If the given name is already a user in this network, does nothing and returns false;
    *  Otherwise, creates a new user with the given name, adds the user to this network, and returns true. */
    public boolean addUser(String name) {
        if (getUser(name) != null) {
            return false;
        }
        for(int i = 0; i < users.length; i++) {
            if (users[i] == null) {
                users[i] = new User(name);
                userCount++;
                return true;
            }
        }
        return false;
    }

    /** Makes the user with name1 follow the user with name2. If successful, returns true.
     *  If any of the two names is not a user in this network,
     *  or if the "follows" addition failed for some reason, returns false. */
    public boolean addFollowee(String name1, String name2) {
        if (getUser(name1) == null || getUser(name2) == null || getUser(name1).follows(name2) || name1.equals(name2)
        || name1 == null || name2 == null) {
            return false;
        }
        User user = getUser(name1);
        user.addFollowee(name2);
        return true;
    }
    
    /** For the user with the given name, recommends another user to follow. The recommended user is
     *  the user that has the maximal mutual number of followees as the user with the given name. */
    public String recommendWhoToFollow(String name) {
        User user = getUser(name);
        if (user == null) {
            return null;
        }
        int index = -1;
        int countMutuals = -1;
        for(int i = 0; i < userCount; i++) {
            User currUser = users[i];
            if (user == currUser) {
                continue;
            }
            if (user.follows(currUser.getName())) {
                continue;
            }
            if (user.countMutual(currUser) > countMutuals) {
                countMutuals = user.countMutual(currUser);
                index = i;
            }

        }
        return users[index].getName();
    }

    /** Computes and returns the name of the most popular user in this network: 
     *  The user who appears the most in the follow lists of all the users. */
    public String mostPopularUser() {
        if (userCount == 0) {
            return null;
        }
        int max = followeeCount(users[0].getName());
        int index = 0;
        for (int i = 1; i < userCount; i++) {
            int curr = followeeCount(users[i].getName());
            if (curr > max) {
                max = curr;
                index = i;
            }
        }
        return users[index].getName();
    }

    /** Returns the number of times that the given name appears in the follows lists of all
     *  the users in this network. Note: A name can appear 0 or 1 times in each list. */
    private int followeeCount(String name) {
        int counter = 0;
        for(int i = 0; i < userCount; i++) {
            if (users[i].follows(name)) {
                counter++;
            }
        }
        return counter;
    }

    // Returns a textual description of all the users in this network, and who they follow.
    public String toString() {
       String ans = "Network:";
       for (int i = 0; i < userCount; i++) {
            ans += "\n"+ users[i].toString();
       }
       return ans;
    }
}
