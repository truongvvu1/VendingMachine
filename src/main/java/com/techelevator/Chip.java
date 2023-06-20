package com.techelevator;

public class Chip extends Product {
    //constructor method


    public Chip(String slotCode, String name, double price, String type) {
        super(slotCode, name, price, type);
    }

    @Override
    public void playSound() {
        System.out.println("Crunch Crunch, Yum!");
    }
}
