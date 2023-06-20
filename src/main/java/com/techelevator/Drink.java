package com.techelevator;

public class Drink extends Product {

    //constructor method


    public Drink(String slotCode, String name, double price, String type) {
        super(slotCode, name, price, type);
    }

    @Override
    public void playSound() {
        System.out.println("Glug Glug, Yum!");
    }
}
