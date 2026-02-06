package minios;

import java.util.Scanner;
import applications.MainApp;
import settings.AllInOneApp;

public class Desktop {

    static final int WIDTH = 80;

    static final String RESET  = "\u001B[0m";
    static final String CYAN   = "\u001B[36m";
    static final String BLUE   = "\u001B[34m";
    static final String GREEN  = "\u001B[32m";
    static final String YELLOW = "\u001B[33m";
    static final String RED    = "\u001B[31m";
    static final String WHITE  = "\u001B[37m";

    // 🔥 ACCESSIBLE FROM MainOs
    public static void open(Scanner sc) {

        while (true) {

            // ---------- HEADER ----------
            System.out.println();
            center(CYAN  + "════════════════════════════════════════" + RESET);
            center(BLUE  + "Welcome, " + Installer.systemUser.getUsername() + RESET);
            center(WHITE + "MiniOS Desktop Environment" + RESET);
            center(CYAN  + "════════════════════════════════════════" + RESET);
            System.out.println();

            // ---------- MENU ----------
            center(GREEN + "1. Applications" + RESET);
            center(GREEN + "2. System Applications"+ RESET);
            center(GREEN + "3. Shutdown OS"  + RESET);
            System.out.println();

            // ---------- INPUT ----------
            centerInline(YELLOW + "Select Option : " + RESET);

            int choice;
            try {
                choice = sc.nextInt();
            } catch (Exception e) {
                sc.nextLine();
                center(RED + "Invalid Input" + RESET);
                continue;
            }
            sc.nextLine();
            System.out.println();

            // ---------- ACTION ----------
            switch (choice) {

                case 1:
                    MainApp.call();     // Applications
                    break;

                case 2:
                    AllInOneApp.All();  // Settings (GUI)
                    break;

                case 3:
                    System.out.println();
                    center(RED + "Shutting down MiniOS..." + RESET);
                    System.exit(0);
                    return;

                default:
                    center(RED + "Invalid Option" + RESET);
            }
        }
    }

    // ---------- CENTER HELPERS (ANSI SAFE) ----------

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
