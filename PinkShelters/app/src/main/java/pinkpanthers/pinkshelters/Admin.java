package pinkpanthers.pinkshelters;
import pinkpanthers.pinkshelters.Account;

public class Admin extends Account {

        private String AdminId;

        public Admin (String userName,
                         String password,
                         String name,
                         String accountState,
                         String email,
                         String ShelterId){
            super(userName, password, name, accountState, email, ShelterId );
            this.AdminId =AdminId;
        }

        //getter for AdminId
        public String getAdminId() {
            return this.getAdminId();
        }

        //setter for AdminId
        public void setAdminId(String AdminId) {
            this.AdminId = AdminId;
        }
    }






