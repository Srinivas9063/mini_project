package minios;

import java.util.Scanner;

public class MainOs {

    static final int WIDTH = 80;

    static final String RESET  = "\u001B[0m";
    static final String CYAN   = "\u001B[36m";
    static final String GREEN  = "\u001B[32m";
    static final String RED    = "\u001B[31m";
    static final String YELLOW = "\u001B[33m";

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.println();
        center(CYAN + "════════════════════════════════════════" + RESET);
        center(CYAN + "Welcome to MiniOS" + RESET);
        center(CYAN + "════════════════════════════════════════" + RESET);
        System.out.println();

        center(YELLOW + "Do you want to install this MiniOS?" + RESET);
        System.out.println();

        center(GREEN + "1. Yes" + RESET);
        center(RED   + "2. No"  + RESET);
        System.out.println();

        centerInline(YELLOW + "Select Option : " + RESET);

        int a;
        try {
            a = sc.nextInt();
        } catch (Exception e) {
            center(RED + "Invalid Input. Exiting..." + RESET);
            return;
        }

        System.out.println();

        if (a == 1) {

           
            MainOsLogo.logo();

            
            Installer.install(sc);

            
            Login.login(sc);

            
            Desktop.open(sc);

        } else {

            center(RED + "Installation cancelled." + RESET);
            center(RED + "Goodbye..." + RESET);
            System.exit(0);
        }
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
