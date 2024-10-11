package org.example.Execution;

public class UserData {

        String firstName;
        String lastName;
        String email;
        String password;
        String confirmPassword;


        public UserData(String firstName, String lastName,String email,String password,String confirmPassword) {
            this.firstName = firstName;
            this.lastName=lastName;
            this.email= email;
            this.password= password;
            this.confirmPassword = confirmPassword;
        }

        public String getFirstName() {
            return firstName;
        }
        public String getLastName() {
            return lastName;
        }
        public String getEmail() {
            return email;
        }
        public String getPassword() {
            return password;
        }
        public String getConfirmPassword() {
            return confirmPassword;
        }

    }



