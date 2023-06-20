package com.techelevator;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class VendingMachine {
    //fields
    private final int MAX_PRODUCT_AMOUNT = 5;
    private double balance;
    private final int QUARTER = 25;
    private final int DIME = 10;
    private final int NICKEL = 5;
    private final int TWELFTH_HOUR = 12;
    private Map<Product,String> productToQuantityMap;

    //constructor method

    public VendingMachine() {
        balance = 0.00;
        productToQuantityMap = new LinkedHashMap<Product,String>();
    }

    //getter methods
    public double getBalance() {
        return balance;
    }

    public Map<Product, String> getProductToQuantityMap() {
        return productToQuantityMap;
    }


    //instance methods
    public void start(){
        String currentDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("MM/dd/yyyy"));
        String currentDateWithoutSlash = TimeDateFormat.getCurrentDateWithoutSlash(currentDate);
        Transaction transaction = new Transaction(currentDate);
        File file = new File(currentDateWithoutSlash + ".txt");
        transaction.createLogFileForToday(file);
        try(Scanner fileReader = new Scanner(new File("vendingmachine.csv"))){
            while(fileReader.hasNextLine()){
                String[] lineArr = fileReader.nextLine().split("\\|");
                String slotCode = lineArr[0];
                String name = lineArr[1];
                double price = Double.parseDouble(lineArr[2]);
                String type = lineArr[3];
                Product product = null;
                switch(type){
                    case "Drink":
                        product = new Drink(slotCode,name,price,type);
                        break;
                    case "Gum":
                        product = new Gum(slotCode,name,price,type);
                        break;
                    case "Candy":
                        product = new Candy(slotCode,name,price,type);
                        break;
                    case "Chip":
                        product = new Chip(slotCode,name,price,type);
                }
                productToQuantityMap.put(product,String.valueOf(MAX_PRODUCT_AMOUNT));
            }
        }catch(Exception ex){
            System.out.println("Something went wrong when reading in file.");
        }
    }
    public void displayItems(Map<Product,String> productToQuantityMap){
        for(Product product : productToQuantityMap.keySet()){
            System.out.println(product + "|Quantity:" + productToQuantityMap.get(product));
            System.out.println();
        }
    }
    public boolean processChoiceFromPurchaseMenu(String choice){
        String currentDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("MM/dd/yyyy"));
        String twelfthHourFormatTime = TimeDateFormat.convertTimeToTwelveHourFormat(LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss a")));
        switch(choice){
            case "Feed Money":
                int amount = promptUserForMoney(new Scanner(System.in));
                feedMoney(amount);
                formatBalanceTwoDecimals();
                Transaction transaction = new Transaction(currentDate);
                transaction.log(choice.toUpperCase(),amount,balance,twelfthHourFormatTime);
                break;
            case "Select Product":
                displayItems(productToQuantityMap);
                Product dispensedProduct = findProductBySlotCode(promptUserForSlotCode(new Scanner(System.in)),productToQuantityMap);
                if(dispensedProduct == null){
                    System.out.println("That product does not exist!");
                }else if(productToQuantityMap.get(dispensedProduct).equals("SOLD OUT")){
                    System.out.println(dispensedProduct.getName() + " is currently OUT OF STOCK!");
                } else if(balance < dispensedProduct.getPrice()){
                    System.out.println("You did not enter enough money to purchase " + dispensedProduct.getName());
                }else{
                    int dispensedProductCurrentQuantity = Integer.parseInt(productToQuantityMap.get(dispensedProduct));
                    String newQuantity = (dispensedProductCurrentQuantity-1) > 0 ? String.valueOf(dispensedProductCurrentQuantity-1) : "SOLD OUT";
                    productToQuantityMap.put(dispensedProduct,newQuantity);
                    balance -= dispensedProduct.getPrice();
                    formatBalanceTwoDecimals();
                    displayDispensedMessage(dispensedProduct,balance);
                    dispensedProduct.playSound();
                    transaction = new Transaction(currentDate);
                    transaction.log(dispensedProduct.getName(),String.valueOf(dispensedProduct.getPrice()),balance,twelfthHourFormatTime);
                }
                return false;
            case "Finish Transaction":
                displayChange(balance);
                transaction = new Transaction(currentDate);
                transaction.log("GIVE CHANGE",balance,twelfthHourFormatTime);
                balance = 0.00;
        }
        return true;
    }
    private int promptUserForMoney(Scanner scanner){
        while(true){
            System.out.println("Enter a whole dollar amount: ");
            try{
                return scanner.nextInt();
            }catch(InputMismatchException ex){
                System.out.println("You did not enter a valid whole number. Please try again.");
                scanner.nextLine();
            }
        }
    }
    private void feedMoney(int amount){
        if(amount > 0){
            this.balance += amount;
        }else{
            System.out.println("You cannot enter a zero or negative amount.");
        }

    }
    public void formatBalanceTwoDecimals(){
        balance = Double.parseDouble(String.format("%.2f", balance));
    }
    private String promptUserForSlotCode(Scanner scanner){
        System.out.println("Enter slot code: ");
        return scanner.nextLine();
    }
    private Product findProductBySlotCode(String slotCode, Map<Product, String> productToQuantityMap){
        for(Product product : productToQuantityMap.keySet()){
            if(product.getSlotCode().equals(slotCode)){
                return product;
            }
        }
        return null;
    }
    private void displayDispensedMessage(Product product, double balance){
        System.out.println(product.getName() + " | " + "price: " + product.getPrice() + " | " + "balance remaining: " + balance);
    }
    private void displayChange(double change){
            int remainingPennies = (int)Math.round(change * 100);
            int quarters = 0;
            int dimes = 0;
            int nickels = 0;
            if(remainingPennies >= QUARTER) {
                quarters = remainingPennies / QUARTER;
                remainingPennies %= QUARTER;
            }
            if(remainingPennies >= DIME){
                dimes = remainingPennies / DIME;
                remainingPennies %= DIME;
            }
            if(remainingPennies >=NICKEL){
                nickels = remainingPennies / NICKEL;
            }

            System.out.println("Thank you for choosing Vendo-Matic 800. Your CHANGE is: " + balance);
            System.out.println();
            System.out.println("Quarters: " + quarters);
            System.out.println("--------------------------");
            System.out.println("Dimes: " + dimes);
            System.out.println("--------------------------");
            System.out.println("Nickels: " + nickels);
            System.out.println("--------------------------");
    }


}
