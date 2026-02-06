package minios;

import java.util.Scanner;

public class Installer {

    static User1 systemUser = new User1();
    static final int WIDTH = 80;

    static final String RESET  = "\u001B[0m";
    static final String CYAN   = "\u001B[36m";
    static final String BLUE   = "\u001B[34m";
    static final String GREEN  = "\u001B[32m";
    static final String YELLOW = "\u001B[33m";
    static final String WHITE  = "\u001B[37m";
    static final String RED    = "\u001B[31m";

    
    public static void install(Scanner sc) {

        int count1 = 0;
        int count2 = 0;

        System.out.println();
        center(CYAN  + "════════════════════════════════════════" + RESET);
        center(WHITE + "MiniOS Installation Wizard" + RESET);
        center(CYAN  + "════════════════════════════════════════" + RESET);
        System.out.println();

        center(RED + "You need to sign up to install this OS" + RESET);
        System.out.println();

        String u = "", p = "";

        
        while (count1 < 6) {

            if (count1 == 5) {
                System.out.println();
                center(RED + "Too many failed attempts!" + RESET);
                center(RED + "Installation terminated." + RESET);
                System.exit(0);
            }

            centerInline(YELLOW + "Create Username : " + RESET);
            count1++;

            u = sc.next();
            sc.nextLine();
            System.out.println();

            if (isValidUsername(u)) {
                break;
            }

            center(RED + "Invalid Username!" + RESET);
            center(WHITE + "Rules:" + RESET);
            center(WHITE + "- Minimum 4 characters" + RESET);
            center(WHITE + "- Only letters and digits" + RESET);
            center(WHITE + "- No spaces allowed" + RESET);
            System.out.println();
        }

        
        while (count2 < 6) {

            if (count2 == 5) {
                System.out.println();
                center(RED + "Too many failed attempts!" + RESET);
                center(RED + "Installation terminated." + RESET);
                System.exit(0);
            }

            centerInline(YELLOW + "Create Password : " + RESET);
            count2++;

            p = sc.next();
            sc.nextLine();
            System.out.println();

            if (isValidPassword(p)) {
                break;
            }

            center(RED + "Invalid Password!" + RESET);
            center(WHITE + "Rules:" + RESET);
            center(WHITE + "- Minimum 6 characters" + RESET);
            center(WHITE + "- At least 1 uppercase letter" + RESET);
            center(WHITE + "- At least 1 digit" + RESET);
            center(WHITE + "- No spaces allowed" + RESET);
            System.out.println();
        }

        systemUser.setCredentials(u, p);

        System.out.println();
        center(GREEN + "Installation Completed Successfully " + RESET);
        center(GREEN + "Welcome to MiniOS!" + RESET);
        System.out.println();
    }

    

    static boolean isValidUsername(String username) {
        if (username.length() < 4) return false;
        if (username.contains(" ")) return false;

        for (char ch : username.toCharArray()) {
            if (!Character.isLetterOrDigit(ch)) return false;
        }
        return true;
    }

    static boolean isValidPassword(String password) {
        if (password.length() < 6) return false;
        if (password.contains(" ")) return false;

        boolean hasUpper = false;
        boolean hasDigit = false;

        for (char ch : password.toCharArray()) {
            if (Character.isUpperCase(ch)) hasUpper = true;
            if (Character.isDigit(ch)) hasDigit = true;
        }
        return hasUpper && hasDigit;
    }

    

    static void center(String text) {
        int visibleLength =
                text.replaceAll("\u001B\\[[;\\d]*m", "").length();
        int padding = (WIDTH - visibleLength) / 2;
        System.out.println(" ".repeat(Math.max(0, padding)) + text);
    }

    static void centerInline(String text) {
        int visibleLength =
                text.replaceAll("\u001B\\[[;\\d]*m", "").length();
        int padding = (WIDTH - visibleLength) / 2;
        System.out.print(" ".repeat(Math.max(0, padding)) + text);
    }
}
