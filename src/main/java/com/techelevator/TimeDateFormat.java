package com.techelevator;

public class TimeDateFormat {
    //static fields
    private static final int TWELFTH_HOUR = 12;
    //static methods

    public static String convertTimeToTwelveHourFormat(String currentTime){
        int currentHour = Integer.parseInt(currentTime.substring(0,2));
        return (currentHour > TWELFTH_HOUR) ? (currentHour - TWELFTH_HOUR) + currentTime.substring(2) : currentTime;
    }
    public static String convertDateTimeToTwelveHourFormat(String currentDateTime){
        String currentDate = currentDateTime.substring(0,10);
        String currentTime = currentDateTime.substring(11);
        String twelveHourFormatTime = convertTimeToTwelveHourFormat(currentTime);
        return currentDate + " " +  twelveHourFormatTime;
    }

    public static String getCurrentDateTimeWithoutSlash(String currentDateTime){
        return getCurrentDateWithoutSlash(currentDateTime.substring(0,10)) + getCurrentTimeWithoutColon(currentDateTime.substring(11));
    }
    public static String getCurrentDateWithoutSlash(String currentDate){
        String[] currentDateArr = currentDate.split("/");
        return currentDateArr[0] + currentDateArr[1] + currentDateArr[2];
    }

    public static String getCurrentTimeWithoutColon(String currentTime){
        String[] currentTimeArr = currentTime.split(":");
        currentTime = currentTimeArr[0] + currentTimeArr[1];
        currentTimeArr = currentTimeArr[2].split(" ");
        return currentTime += currentTimeArr[0] + currentTimeArr[1];

    }
}
