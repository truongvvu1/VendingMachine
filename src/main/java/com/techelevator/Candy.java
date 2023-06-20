package com.techelevator;

public class Candy extends Product{
    //constructor method


    public Candy(String slotCode, String name, double price, String type) {
        super(slotCode, name, price, type);
    }

    @Override
    public void playSound() {
        System.out.println("Munch munch, Yum!");
    }
}
