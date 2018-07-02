package com.example.dhanuja.cpool;

public class ActiveUsers {
    private int numberactive;

    public ActiveUsers(){

    }

    public ActiveUsers(int active){
        this.numberactive = active;
    }

    public int getNumberactive() {
        return numberactive;
    }

    public void setNumberactive(int numberactive) {
        this.numberactive = numberactive;
    }
}
