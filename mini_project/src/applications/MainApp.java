package applications;

import java.util.Scanner;

import minios.Desktop;


public class MainApp {

    
    static final int WIDTH = 80;

    static final String RESET  = "\u001B[0m";
    static final String CYAN   = "\u001B[36m";
    static final String BLUE   = "\u001B[34m";
    static final String GREEN  = "\u001B[32m";
    static final String YELLOW = "\u001B[33m";
    static final String RED    = "\u001B[31m";
    static final String WHITE  = "\u001B[37m";

   
    public static void call() {

        Scanner sc = new Scanner(System.in);

        System.out.println();
        center(CYAN + "================================" + RESET);
        center(BLUE + "        MiniOS Applications     " + RESET);
        center(CYAN + "================================" + RESET);
        System.out.println();

        center(GREEN + "1. Go Travel" + RESET);
        center(GREEN+"2. Address book"+ RESET);
        center(GREEN+"3.  Food App"+RESET);
        center(GREEN + "0. Exit" + RESET);
        System.out.println(); 

        centerInline(YELLOW + "Select an option: " + RESET);

        int choice;
        try {
            choice = sc.nextInt();
        } catch (Exception e) {
            sc.nextLine();
            System.out.println();
            center(RED + "Invalid Input! Numbers only." + RESET);
            return;
        }

        System.out.println();

        switch (choice) {
            case 1:
                
                System.out.println();
                RapidoApp.rapidoApp(sc);
                break;

            case 2:
                
                ContactApp1.Con(sc);
                System.out.println();

            case 3:
                
                Res.rs();

            case 0:
                
                    Desktop.open(sc);

            default:
                center(RED + "Invalid Option Selected!" + RESET);
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
