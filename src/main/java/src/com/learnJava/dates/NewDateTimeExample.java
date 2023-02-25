package com.learnJava.dates;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.Period;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;

public class NewDateTimeExample {

    public static void main(String[] args) {

        LocalDate localDate = LocalDate.now();
        System.out.println("localDate : "+ localDate);
        LocalTime localTime = LocalTime.now();
        System.out.println("localTime : "+ localTime);
        LocalDateTime localDateTime = LocalDateTime.now();
        System.out.println("localDateTime : "+ localDateTime);

        try {
            System.out.println(new SimpleDateFormat("MMM yyyy").parse("MAR 2022"));
        } catch (ParseException e) {
//            throw new RuntimeException(e);
        }

        try {
            long diff= printDifference("Mar 2022","Apr 2023");
            System.out.println(diff);
        } catch (ParseException e) {
//            throw new RuntimeException(e);
        }

        LocalDate bday = LocalDate.of(1955, Month.MAY, 19); LocalDate today = LocalDate.now(); Period age = Period.between(bday, today); int years = age.getYears(); int months = age.getMonths();
        int days = age.getDays();

        System.out.println("number of days: " + days);
        System.out.println("number of years: " + years);
        System.out.println("number of months: " + months);
    }

    private static long printDifference(String start, String end) throws ParseException {
        SimpleDateFormat formatter=new SimpleDateFormat("MMM yyyy");
        LocalDate startDate=formatter.parse(start).toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
        LocalDate endDate=formatter.parse(end).toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
        int months = Period.between(startDate, endDate).getYears() * 12+Period.between(startDate, endDate).getMonths();
        System.out.println(startDate);
        System.out.println(endDate);

        return months;
    }
}
