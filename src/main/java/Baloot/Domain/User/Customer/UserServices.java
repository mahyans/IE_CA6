package Baloot.Domain.User.Customer;

import Baloot.Exeptions.BalootDuplicateUsernameError;
import Baloot.Exeptions.UserIdDoesntExistError;
import Baloot.Exeptions.UserPasswordDoesntMatchError;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserServices {
    private Map<String, User> users = new HashMap<>();

    private User onlineClient = null;

    private static UserServices instance;
    private UserServices() throws Exception {
    }
    public static UserServices getInstance() throws Exception {
        if(instance == null)
            instance = new UserServices();
        return instance;
    }
    public User getUser(String userID) throws Exception {
        if (!users.containsKey(userID))
            throw new UserIdDoesntExistError(userID);
        User user = users.get(userID);
        return user;
    }

    public Boolean checkUserExist(String id, String pass) throws Exception {
        if (!users.containsKey(id))
            throw new UserIdDoesntExistError(id);
        if (users.get(id).getPassword().equals(pass))
            return true;
        else
            throw new UserPasswordDoesntMatchError(id);
//        return false;
    }

    public String addUser(UserInit userI_) throws Exception {
        User user_ = new User(userI_.getUsername(), userI_.getPassword(), userI_.getEmail(), userI_.getBirthDate(),
                userI_.getAddress(), userI_.getCredit());
        if (users.containsKey(user_.getUsername()))
            throw new BalootDuplicateUsernameError(String.format(user_.getUsername()));
        users.put(user_.getUsername(), user_);
        return "User successfully added.";
    }


    public User getOnlineClient() throws Exception {
        return onlineClient;
    }

    public void setClientOnline(String username) throws Exception {
        if (!users.containsKey(username))
            throw new UserIdDoesntExistError(username);
        onlineClient = users.get(username);
    }


    public List<User> getUsers() throws Exception {
        List<User> usersList = new ArrayList<User>(users.values());

        return usersList;
    }
}
