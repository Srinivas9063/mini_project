package applications;

import java.util.Random;
import java.util.Scanner;


class User {
    private String name;
    private String email;
    private String mobile;
    private Feedback feedback;

    public User(String name, String email, String mobile) {
        this.name = name;
        this.email = email;
        this.mobile = mobile;
    }

    public void setFeedback(Feedback feedback) {
        this.feedback = feedback;
    }
}


class Feedback {
    private int rating;
    private String comment;

    public Feedback(int rating, String comment) {
        this.rating = rating;
        this.comment = comment;
    }
}


class Validator {

    static boolean validName(String name) {
        return name != null && name.trim().length() >= 3;
    }

    static boolean validEmail(String email) {
        return email.contains("@") && email.contains(".");
    }

    static boolean validMobile(String mobile) {
        return mobile.matches("\\d{10}");
    }

    static boolean validAmount(double amt) {
        return amt > 0;
    }

    static boolean validRating(int r) {
        return r >= 1 && r <= 5;
    }
}


class Wallet {
    private double balance;
    static final char CURRENCY = '\u20B9';

    public void addMoney(double amount) {
        if (!Validator.validAmount(amount)) {
            UI.center(UI.RED + "Invalid amount!" + UI.RESET);
            return;
        }
        balance += amount;
        UI.center(UI.GREEN + CURRENCY + amount + " added to wallet" + UI.RESET);
    }

    public boolean deduct(double amount) {
        if (balance >= amount) {
            balance -= amount;
            return true;
        }
        return false;
    }

    public double getBalance() {
        return balance;
    }
}


interface Ride {
    double calculateFare(int distance);
    void startRide();
    void endRide();
}

class BikeRide implements Ride {
    public double calculateFare(int d) { return d * 10; }
    public void startRide() { UI.center("Bike ride started"); }
    public void endRide() { UI.center("Bike ride completed"); }
}

class CarRide implements Ride {
    public double calculateFare(int d) { return d * 15; }
    public void startRide() { UI.center("Car ride started"); }
    public void endRide() { UI.center("Car ride completed"); }
}

class ParcelRide implements Ride {
    public double calculateFare(int d) { return d * 20; }
    public void startRide() { UI.center("Parcel delivery started"); }
    public void endRide() { UI.center("Parcel delivery completed"); }
}


class OTPUtil {
    public static int generateOTP() {
        return new Random().nextInt(9000) + 1000;
    }
}


class UI {

    static final int WIDTH = 80;

    static final String RESET  = "\u001B[0m";
    static final String CYAN   = "\u001B[36m";
    static final String BLUE   = "\u001B[34m";
    static final String GREEN  = "\u001B[32m";
    static final String YELLOW = "\u001B[33m";
    static final String RED    = "\u001B[31m";

    static void center(String text) {
        int len = text.replaceAll("\u001B\\[[;\\d]*m", "").length();
        int pad = (WIDTH - len) / 2;
        System.out.println(" ".repeat(Math.max(0, pad)) + text);
    }

    static void centerInline(String text) {
        int len = text.replaceAll("\u001B\\[[;\\d]*m", "").length();
        int pad = (WIDTH - len) / 2;
        System.out.print(" ".repeat(Math.max(0, pad)) + text);
    }
}


public class RapidoApp {

    public static void rapidoApp(Scanner sc) {
        GoLogo.logo();

        Wallet wallet = new Wallet();
        User user = null;

        boolean isSignedUp = false;
        boolean isLoggedIn = false;
        String registeredMobile = null;

        
        while (!isLoggedIn) {

            UI.center("1. Signup");
            UI.center("2. Signin");
            UI.center("0. Exit");
            System.out.println();

            UI.centerInline(UI.YELLOW + "Choose option : " + UI.RESET);
            int choice = sc.nextInt();
            sc.nextLine();

            if (choice == 1) {

                UI.center(UI.BLUE + "Signup" + UI.RESET);

                UI.centerInline("Name   : ");
                String name = sc.nextLine();
                if (!Validator.validName(name)) continue;

                UI.centerInline("Email  : ");
                String email = sc.nextLine();
                if (!Validator.validEmail(email)) continue;

                UI.centerInline("Mobile : ");
                String mobile = sc.nextLine();
                if (!Validator.validMobile(mobile)) continue;

                int otp = OTPUtil.generateOTP();
                UI.center(UI.YELLOW + "OTP sent : " + otp + UI.RESET);

                UI.centerInline("Enter OTP : ");
                if (sc.nextInt() != otp) continue;

                user = new User(name, email, mobile);
                registeredMobile = mobile;
                isSignedUp = true;

                UI.center(UI.GREEN + "Signup successful!" + UI.RESET);
            }

            else if (choice == 2) {

                if (!isSignedUp) continue;

                UI.centerInline("Mobile : ");
                String mobile = sc.nextLine();
                if (!mobile.equals(registeredMobile)) continue;

                int otp = OTPUtil.generateOTP();
                UI.center(UI.YELLOW + "OTP sent : " + otp + UI.RESET);

                UI.centerInline("Enter OTP : ");
                if (sc.nextInt() != otp) continue;

                isLoggedIn = true;
                UI.center(UI.GREEN + "Login successful!" + UI.RESET);
            }

            else {
                MainApp.call();
                return;
            }
        }

        
        UI.centerInline("Add money to wallet : ");
        wallet.addMoney(sc.nextDouble());
        sc.nextLine();

        
        UI.centerInline("Pickup Location : ");
        String pickup = sc.nextLine();

        UI.centerInline("Drop Location   : ");
        String drop = sc.nextLine();

       
        UI.center("1. Bike");
        UI.center("2. Car");
        UI.center("3. Parcel");

        UI.centerInline("Choice : ");
        int r = sc.nextInt();

        Ride ride =
                (r == 1) ? new BikeRide() :
                (r == 2) ? new CarRide() :
                (r == 3) ? new ParcelRide() : null;

        if (ride == null) return;

        UI.centerInline("Distance (km) : ");
        int distance = sc.nextInt();

        double fare = ride.calculateFare(distance);
        UI.center(UI.GREEN + "Total Fare : ₹" + fare + UI.RESET);

       
        UI.center("Select Payment Method");
        UI.center("1. Wallet");
        UI.center("2. UPI");
        UI.center("3. Cash");

        UI.centerInline("Choice : ");
        int pay = sc.nextInt();

        boolean paymentSuccess = false;

        switch (pay) {
            case 1:
                paymentSuccess = wallet.deduct(fare);
                if (!paymentSuccess)
                    UI.center(UI.RED + "Insufficient wallet balance!" + UI.RESET);
                break;

            case 2:
                UI.center(UI.GREEN + "UPI payment successful!" + UI.RESET);
                paymentSuccess = true;
                break;

            case 3:
                UI.center(UI.GREEN + "Pay cash to driver!" + UI.RESET);
                paymentSuccess = true;
                break;
        }

        if (!paymentSuccess) return;

        ride.startRide();
        ride.endRide();

        UI.center(UI.CYAN + "Pickup Location : " + pickup + UI.RESET);
        UI.center(UI.CYAN + "Drop Location   : " + drop + UI.RESET);

        
        UI.centerInline("Rate your ride (1-5)");
        int rating = sc.nextInt();
        sc.nextLine();

        if (Validator.validRating(rating)) {
            UI.centerInline("Feedback : ");
            String comment = sc.nextLine();
            user.setFeedback(new Feedback(rating, comment));
        }

        UI.center("Remaining Wallet Balance : ₹" + wallet.getBalance());
        UI.center(UI.GREEN + "Thanks for riding with Go!" + UI.RESET);

        MainApp.call();
    }
}
