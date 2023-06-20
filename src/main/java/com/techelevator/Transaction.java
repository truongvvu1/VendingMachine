package com.techelevator;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;


public class Transaction {
    //field
    String currentDate;

    public Transaction(String currentDate){
        this.currentDate = currentDate;
    }

    //instance methods
    public void createLogFileForToday(File file){
        if(!file.exists()){
            try(PrintWriter fileWriter = new PrintWriter(file)){
                fileWriter.println(currentDate);
            }catch(Exception ex){
                System.out.println("Something went wrong writing to the file.");
            }
        }

    }

    public void log(String userChoice, double depositedAmount, double newBalance, String currentTime){ //feed money
        String fileName = TimeDateFormat.getCurrentDateWithoutSlash(currentDate) + ".txt";
        try(PrintWriter fileWriter = new PrintWriter(new FileOutputStream(new File(fileName),true))){
            fileWriter.println( currentTime + " " + userChoice +":" + " " + "$" + depositedAmount  + " " + "$" + newBalance);
        }catch(Exception ex){
            System.out.println("Something went wrong when writing to file.");
        }
    }
    public void log(String productName,String productPrice,double newBalance, String currentTime){ //select product
        String fileName = TimeDateFormat.getCurrentDateWithoutSlash(currentDate) + ".txt";
        try(PrintWriter fileWriter = new PrintWriter(new FileOutputStream(new File(fileName),true))){
            fileWriter.println( currentTime + " " + productName +":" + " " + "$" + productPrice  + " " + "$" + newBalance);
        }catch(Exception ex){
            System.out.println("Something went wrong when writing to file.");
        }

    }

    public void log(String userChoice,double changeLeft, String currentTime){ //finish transaction
        String fileName = TimeDateFormat.getCurrentDateWithoutSlash(currentDate) + ".txt";
        try(PrintWriter fileWriter = new PrintWriter(new FileOutputStream(new File(fileName),true))){
            fileWriter.println( currentTime + " " + userChoice +":" + " " + "$" + changeLeft  + " " + "$" + 0.00);
        }catch(Exception ex){
            System.out.println("Something went wrong when writing to file.");
        }
    }

}
