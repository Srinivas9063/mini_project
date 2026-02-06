package applications;

import java.util.Scanner;
import java.util.Random;

public class Res {

    static final int WIDTH = 80;
    static final Scanner sc = new Scanner(System.in);
    static final Random r = new Random();

    static final String RESET  = "\u001B[0m";
    static final String CYAN   = "\u001B[36m";
    static final String BLUE   = "\u001B[34m";
    static final String GREEN  = "\u001B[32m";
    static final String YELLOW = "\u001B[33m";
    static final String RED    = "\u001B[31m";

    /* ================== UI HELPERS ================== */

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

    static void line() {
        center(CYAN + "========================================" + RESET);
    }

    /* ================== COMMON CONTINUE / EXIT ================== */

    static boolean continueOrExit() {
        center("");
        centerInline(YELLOW + "Press 1 to Continue | Press 0 to Exit : " + RESET);
        int c = sc.nextInt();
        return c == 1;
    }

    /* ================== PHONE VALIDATION ================== */

    static boolean isValidPhone(long phone) {
        String p = String.valueOf(phone);
        return p.matches("[6-9][0-9]{9}");
    }

    /* ================== ACCOUNT ================== */

    static class Account {
        long phone;
        double wallet = 0;

        Account(long phone) {
            this.phone = phone;
        }

        void showWallet() {
            center(GREEN + " Wallet Balance : ₹" + wallet + RESET);
        }

        void addMoney() {
            centerInline(CYAN + "Enter amount to add: " + RESET);
            double amt = sc.nextDouble();

            if (amt <= 0) {
                center(RED + "Invalid amount!" + RESET);
                return;
            }
            wallet += amt;
            center(GREEN + "Money added successfully!" + RESET);
            showWallet();
        }

        boolean pay(double amt) {
            if (wallet >= amt) {
                wallet -= amt;
                return true;
            }
            return false;
        }
    }

    /* ================== FOOD APP ================== */

    static class FoodApp {

        Account acc;
        double total;

        FoodApp(Account acc) {
            this.acc = acc;
        }

        void orderFood() {

            total = 0;

            while (true) {
                line();
                center(BLUE + "FOOD MENU" + RESET);
                line();

                center("1. Veg Biryani     ₹199");
                center("2. Chicken Biryani ₹299");
                center("3. Mutton Biryani  ₹399");
                center("0. Finish Order");

                centerInline(CYAN + "Choose item: " + RESET);
                int c = sc.nextInt();

                if (c == 0) break;

                centerInline(CYAN + "Enter quantity: " + RESET);
                int q = sc.nextInt();

                if (q <= 0) continue;

                switch (c) {
                    case 1 -> total += q * 199;
                    case 2 -> total += q * 299;
                    case 3 -> total += q * 399;
                    default -> center(RED + "Invalid item!" + RESET);
                }
            }

            if (total == 0) {
                center(RED + "No items ordered!" + RESET);
                return;
            }

            double gst = total * 0.05;
            double payable = total + gst + 30 + 1;

            line();
            center("Order Amount : ₹" + total);
            center("GST          : ₹" + String.format("%.2f", gst));
            center("Delivery     : ₹30");
            center("Platform Fee : ₹1");
            center(GREEN + "Payable      : ₹" + payable + RESET);

            payment(payable);
        }

        void payment(double amt) {

            line();
            center(BLUE + "PAYMENT METHOD" + RESET);
            line();

            center("1. UPI");
            center("2. Cash on Delivery");
            center("3. Wallet");

            centerInline(CYAN + "Select option: " + RESET);
            int p = sc.nextInt();

            switch (p) {
                case 1 -> center(GREEN + "UPI Payment Successful!" + RESET);
                case 2 -> center(GREEN + "Pay on Delivery Selected!" + RESET);
                case 3 -> {
                    acc.showWallet();
                    if (!acc.pay(amt)) {
                        center(RED + "Insufficient Wallet Balance!" + RESET);
                        return;
                    }
                    center(GREEN + "Paid using Wallet!" + RESET);
                    acc.showWallet();
                }
                default -> center(RED + "Invalid option!" + RESET);
            }
        }
    }

    /* ================== LOGIN ================== */

    static Account login() {

        center(YELLOW + "LOGIN" + RESET);

        long phone;
        while (true) {
            centerInline(CYAN + "Enter phone number: " + RESET);
            phone = sc.nextLong();

            if (isValidPhone(phone)) break;

            center(RED + "Invalid Phone Number! Must be 10 digits & start with 6-9." + RESET);
            if (!continueOrExit()) return null;
        }

        int otp = 1000 + r.nextInt(9000);
        center(YELLOW + "OTP : " + otp + RESET);

        for (int i = 1; i <= 3; i++) {
            centerInline(CYAN + "Enter OTP (Attempt " + i + "): " + RESET);
            if (sc.nextInt() == otp) {
                center(GREEN + "Login Successful!" + RESET);
                return new Account(phone);
            }
            center(RED + "Wrong OTP!" + RESET);
        }

        center(RED + "Login Failed!" + RESET);
        return null;
    }

    /* ================== ENTRY ================== */

    public static void rs() {

        FoodAppLogo.logo();
        Account acc = login();
        if (acc == null) return;

        FoodApp app = new FoodApp(acc);

        while (true) {
            line();
            center(BLUE + "FOOD APP HOME" + RESET);
            line();

            center("1. Order Food");
            center("2. Wallet Balance");
            center("3. Add Money");
            center("0. Exit");

            centerInline(CYAN + "Choose option: " + RESET);
            int c = sc.nextInt();

            switch (c) {
                case 1 -> {
                    app.orderFood();
                    if (!continueOrExit()) return;
                }
                case 2 -> {
                    acc.showWallet();
                    if (!continueOrExit()) continue;
                }
                case 3 -> {
                    acc.addMoney();
                    if (!continueOrExit()) continue;
                }
                case 0 -> {
                    MainApp.call();
                    return;
                }
                default -> center(RED + "Invalid option!" + RESET);
            }
        }
    }
}
