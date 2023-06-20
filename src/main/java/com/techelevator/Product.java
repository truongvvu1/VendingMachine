package com.techelevator;

public abstract class Product {
    //fields
    private String name;
    private String type;
    private String slotCode;
    private double price;

    //constructor method
    public Product(String slotCode, String name, double price, String type) {
        this.slotCode = slotCode;
        this.name = name;
        this.price = price;
        this.type = type;
    }

    //getter methods
    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public String getSlotCode() {
        return slotCode;
    }

    public double getPrice() {
        return price;
    }

    //abstract instance methods
    public abstract void playSound();

    @Override
    public String toString() {
        return slotCode + "|" + name + "|" + price + "|" + type;
    }
}
