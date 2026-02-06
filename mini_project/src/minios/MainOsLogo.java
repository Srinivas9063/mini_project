package minios;

public class MainOsLogo {

    
    static final String RESET  = "\u001B[0m";
    static final String CYAN   = "\u001B[36m";
    static final String BLUE   = "\u001B[34m";
    static final String GREEN  = "\u001B[32m";

    
    static final int WIDTH = 80;

    
   static void loadingBar(String message, int steps, int delay) {
        center(message + " ");

        for (int i = 0; i <= steps; i++) {
            System.out.print(GREEN+" █"+RESET);
            try {
                Thread.sleep(delay);
            } catch (InterruptedException e) {}
        }
        System.out.println();
    }
 
    public static void logo() {

        loadingBar("Installing MiniOS ", 40, 30);
        System.out.println();

        center(CYAN +
        "███╗   ███╗ ██╗ ███╗   ██╗ ██╗    ██████╗ ███████╗" + RESET);
        center(BLUE +
        "████╗ ████║ ██║ ████╗  ██║ ██║   ██╔═══██╗██╔════╝" + RESET);
        center(BLUE +
        "██╔████╔██║ ██║ ██╔██╗ ██║ ██║   ██║   ██║███████╗" + RESET);
        center(GREEN +
        "██║╚██╔╝██║ ██║ ██║╚██╗██║ ██║   ██║   ██║╚════██║" + RESET);
        center(CYAN +
        "██║ ╚═╝ ██║ ██║ ██║ ╚████║ ██║   ╚██████╔╝███████║" + RESET);
        center(CYAN +
        "╚═╝     ╚═╝ ╚═╝ ╚═╝  ╚═══╝ ╚═╝    ╚═════╝ ╚══════╝" + RESET);

        System.out.println();
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
