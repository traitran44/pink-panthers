package pinkpanthers.pinkshelters.Model;

public class Admin extends Account{
     Admin(String username,
                 String password,
                 String name,
                 String accountState,
                 String email,
                 int userId) {
        super(username, password, name, accountState, email, userId);
    }
}
