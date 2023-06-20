package com.techelevator;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.LinkedHashMap;
import java.util.Map;



public class Report {

    //fields
    private final int MAX_PRODUCT_AMOUNT = 5;


    public void generateSalesReport(Map<Product, String> productToQuantityMap, String currentDateTime){
        currentDateTime = TimeDateFormat.convertDateTimeToTwelveHourFormat(currentDateTime);
        try(PrintWriter fileWriter = new PrintWriter(new FileOutputStream(new File(TimeDateFormat.getCurrentDateTimeWithoutSlash(currentDateTime) + ".txt"),true))){
            fileWriter.println(currentDateTime);
            LinkedHashMap<Product,Integer> productToQuantitySoldMap = determineQuantitySoldEachProduct(productToQuantityMap);
            for(Product product : productToQuantitySoldMap.keySet()){
                fileWriter.println(product.getName() + "|" + productToQuantitySoldMap.get(product));
            }
            fileWriter.println();
            fileWriter.println("**TOTAL SALES** " + "$" + calculateTotalSales(productToQuantitySoldMap));

        }catch(Exception ex){
            System.out.println("Something went wrong writing to the file.");

        }


    }

    private LinkedHashMap<Product, Integer> determineQuantitySoldEachProduct(Map<Product,String> productToQuantityMap){
        LinkedHashMap<Product,Integer> productToQuantitySoldMap = new LinkedHashMap<Product, Integer>();
        for(Product product : productToQuantityMap.keySet()){
            String quantityLeftStr = productToQuantityMap.get(product);
            int quantityLeftInt = quantityLeftStr.equals("SOLD OUT") ? 0 : Integer.parseInt(quantityLeftStr);
            productToQuantitySoldMap.put(product, MAX_PRODUCT_AMOUNT - quantityLeftInt);
        }
        return productToQuantitySoldMap;
    }
    private String calculateTotalSales(LinkedHashMap<Product,Integer> productToQuantitySoldMap){
        double totalSales = 0.00;
        for(Product product : productToQuantitySoldMap.keySet()){
            totalSales += (product.getPrice() * productToQuantitySoldMap.get(product));
        }
        return String.format("%.2f",totalSales);

    }
}
