package com.example.ashht.mentormanager;

public class userProfile {
    public String userName;
    public String userEmail;
    public String userBranch;
    public String userPassword;

    public userProfile(){

    }


    public userProfile(String userName, String userEmail, String userBranch, String userPassword ){
        this.userName = userName;
        this.userPassword = userPassword;
        this.userEmail = userEmail;
        this.userBranch = userBranch;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }


    public String getUserBranch() {
        return userBranch;
    }

    public void setUserBranch(String userBranch) {
        this.userBranch = userBranch;
    }
}
