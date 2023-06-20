package com.techelevator;

public class Gum extends Product{
    //constructor method


    public Gum(String slotCode, String name, double price, String type) {
        super(slotCode, name, price, type);
    }

    @Override
    public void playSound() {
        System.out.println("Chew Chew, Yum!");
    }
}
