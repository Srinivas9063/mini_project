package applications;

public class GoLogo {

    static final int WIDTH = 80;

   
    static final String RESET  = "\u001B[0m";
    static final String YELLOW = "\u001B[33m";
    static final String GREEN  = "\u001B[32m";
    static final String CYAN   = "\u001B[36m";
    static final String BLUE   = "\u001B[34m";
    static void loadingBar(String message, int steps, int delay) {
        center(message + " ");

        for (int i = 0; i <= steps; i++) {
            System.out.print(" ");
            try {
                Thread.sleep(delay);
            } catch (InterruptedException e) {}
        }
        System.out.println();
    }

    static void logo() {
        loadingBar("Opening Go", 100, 30);
        String[] LOGO = {
            YELLOW + " ██████╗  ██████╗ " + RESET,
            GREEN  + "██╔════╝ ██╔═══██╗" + RESET,
            CYAN   + "██║  ███╗██║   ██║" + RESET,
            BLUE   + "██║   ██║██║   ██║" + RESET,
            YELLOW + "╚██████╔╝╚██████╔╝" + RESET,
            GREEN  + " ╚═════╝  ╚═════╝ " + RESET,
            "",
            CYAN + "======================================" + RESET,
            CYAN + "Bike  |  Car  |  Parcel" + RESET,
            CYAN + "======================================" + RESET
        };

        for (String line : LOGO) {
            center(line);
        }
    }

   
    static void center(String text) {
        int pad = (WIDTH - visibleLength(text)) / 2;
        if (pad < 0) pad = 0;
        System.out.println(" ".repeat(pad) + text);
    }

    static int visibleLength(String text) {
        return text.replaceAll("\u001B\\[[;\\d]*m", "").length();
    }
}
