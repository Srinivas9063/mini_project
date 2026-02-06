package applications;
public class FoodAppLogo {

    static final int WIDTH = 80;

   
    static final String RESET  = "\u001B[0m";
    static final String RED    = "\u001B[31m";
    static final String YELLOW = "\u001B[33m";
    static final String GREEN  = "\u001B[32m";
    static final String CYAN   = "\u001B[36m";

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
        loadingBar("FOOD", 100, 30);
        String[] LOGO = {
            RED    + "███████╗ ██████╗  ██████╗ ██████╗" + RESET,
            YELLOW + "██╔════╝██╔═══██╗██╔═══██╗██╔══██╗" + RESET,
            GREEN  + "█████╗  ██║   ██║██║   ██║██║  ██║" + RESET,
            CYAN   + "██╔══╝  ██║   ██║██║   ██║██║  ██║" + RESET,
            RED    + "██║     ╚██████╔╝╚██████╔╝██████╔╝" + RESET,
            YELLOW + "╚═╝      ╚═════╝  ╚═════╝ ╚═════╝" + RESET,
            "",
            GREEN + "Order  |  Wallet  |  Fast Delivery" + RESET,
            
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
