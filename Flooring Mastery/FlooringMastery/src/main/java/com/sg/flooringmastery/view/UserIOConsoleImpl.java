/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmastery.view;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;
import org.springframework.stereotype.Component;

/**
 *
 * @author benrickel
 */
@Component
public class UserIOConsoleImpl implements UserIO {

    Scanner sc = new Scanner(System.in);

    @Override
    public void print(String message) {
        System.out.println(message);
    }

    @Override
    public double readDouble(String prompt) {
        print(prompt);
        double userNumber = sc.nextDouble();
        return userNumber;
    }

    @Override
    public double readDouble(String prompt, double min, double max) {
        print(prompt);
        double userNumber = sc.nextDouble();
        while (userNumber < min || userNumber > max) {
            print(prompt);
            userNumber = sc.nextDouble();
        }
        return userNumber;
    }

    @Override
    public float readFloat(String prompt) {
        print(prompt);
        float userNumber = sc.nextFloat();
        return userNumber;

    }

    @Override
    public float readFloat(String prompt, float min, float max) {
        print(prompt);
        float userNumber = sc.nextFloat();
        while (userNumber < min || userNumber > max) {
            print(prompt);
            userNumber = sc.nextFloat();
        }
        return userNumber;
    }

    @Override
    public int readInt(String prompt) {
        do {
            try {
                print(prompt);
                int value1 = sc.nextInt();
                return value1;
            } catch (Exception e) {
                System.out.println("That is not a valid option. Please try again.");
            }
        } while (true);
    }

    @Override
    public int readInt(String prompt, int min, int max) {

        do {
            try {
                print(prompt);
                Scanner sc2 = new Scanner(System.in);
                int userNumber = sc2.nextInt();
                return userNumber;
            } catch (Exception e) {
                System.out.println("That is not a valid option. Please try again.");
            }

        } while (true);
    }

    @Override
    public long readLong(String prompt) {
        do {
            try {
                long userNumber = sc.nextLong();
                return userNumber;
            } catch (Exception e) {
                System.out.println("That is not a valid option. Please try again.");
            }
        } while (true);

    }

    @Override
    public long readLong(String prompt, long min, long max) {
        print(prompt);
        long userNumber = sc.nextLong();
        while (userNumber < min || userNumber > max) {
            print(prompt);
            userNumber = sc.nextLong();
        }
        return userNumber;

    }

    @Override
    public String readString(String prompt) {
        Scanner sc = new Scanner(System.in);
        print(prompt);
        String userNumber = sc.nextLine();
        return userNumber;

    }

    @Override
    public BigDecimal readBigDecimal(String prompt) {
        Scanner sc = new Scanner(System.in);
        print(prompt);
        BigDecimal userNumber = sc.nextBigDecimal();
        return userNumber;
    }

    @Override
    public BigDecimal readBigDecimal(String prompt, BigDecimal min, BigDecimal max) {
        Scanner sc = new Scanner(System.in);
        print(prompt);
        BigDecimal userNumber = sc.nextBigDecimal();
        while (userNumber.compareTo(max) == 1) {
            print(prompt);
            userNumber = sc.nextBigDecimal();
        }

        while (userNumber.compareTo(min) == -1) {
            print(prompt);
            userNumber = sc.nextBigDecimal();
        }
        return userNumber;
    }

    @Override
    public LocalDate readLocalDate(String prompt) {
        Scanner sc = new Scanner(System.in);
        print(prompt);
        String userDate = sc.nextLine();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd-yyyy");
        LocalDate ld = LocalDate.parse(userDate, formatter);

        return ld;
    }
    
    
    @Override
    public boolean readBoolean(String prompt) {
        Scanner sc = new Scanner(System.in);
        print(prompt);
        boolean confirmation = sc.nextBoolean();
        return confirmation;
    }

}
