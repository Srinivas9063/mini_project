package minios;

import java.util.Scanner;

public class Login {

    static final int WIDTH = 80;

    static final String RESET  = "\u001B[0m";
    static final String CYAN   = "\u001B[36m";
    static final String BLUE   = "\u001B[34m";
    static final String GREEN  = "\u001B[32m";
    static final String YELLOW = "\u001B[33m";
    static final String RED    = "\u001B[31m";

   
    public static void login(Scanner sc) {

        Security security = new LoginSecurity(Installer.systemUser);
        int attempts = 0;

        System.out.println();
        center(CYAN + "════════════════════════════════════════" + RESET);
        center(BLUE + "MiniOS Secure Login" + RESET);
        center(CYAN + "════════════════════════════════════════" + RESET);
        System.out.println();

        while (attempts < 5) {

            centerInline(YELLOW + "Username : " + RESET);
            String u = sc.next();
            sc.nextLine();

            centerInline(YELLOW + "Password : " + RESET);
            String p = sc.next();
            sc.nextLine();

            if (security.authenticate(u, p)) {
                System.out.println();
                center(GREEN + "Login Successful " + RESET);
                System.out.println();
                return; 
            }

            attempts++;
            System.out.println();
            center(RED + "Invalid Credentials   Try Again" + RESET);
            center(RED + "Attempts Left : " + (5 - attempts) + RESET);
            System.out.println();
        }

       
        center(RED + "Too many failed attempts!" + RESET);
        center(RED + "System locked. Re-installation required." + RESET);
        System.out.println();

        Installer.install(sc); 
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
