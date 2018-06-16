package com.example.dhanuja.cpool;

public class UserProfile {
    public String userName;
    public String usermail;
    public String userno;

    public UserProfile(){

    }

    public UserProfile(String userName, String usermail, String userno) {
        this.userName = userName;
        this.usermail = usermail;
        this.userno = userno;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUsermail() {
        return usermail;
    }

    public void setUsermail(String usermail) {
        this.usermail = usermail;
    }

    public String getUserno() {
        return userno;
    }

    public void setUserno(String userno) {
        this.userno = userno;
    }
}
