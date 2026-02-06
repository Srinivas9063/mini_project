package settings;

import javax.swing.*;
import javax.swing.Timer;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.time.LocalTime;
import java.util.*;



public class AllInOneApp {

    public static void All() {
        SwingUtilities.invokeLater(() -> new Dashboard().setVisible(true));
    }

    /* ================= SOUND HELPERS ================= */
    static void beep() {
        Toolkit.getDefaultToolkit().beep();
    }

    static void successBeep() {
        new Thread(() -> {
            beep(); sleep(80);
            beep(); sleep(80);
            beep();
        }).start();
    }

    static void errorBeep() {
        new Thread(() -> {
            for (int i = 0; i < 3; i++) {
                beep();
                sleep(120);
            }
        }).start();
    }

    static void sleep(int ms) {
        try { Thread.sleep(ms); } catch (Exception ignored) {}
    }

    /* ================= DASHBOARD ================= */
    static class Dashboard extends JFrame {
        Dashboard() {
            setTitle("All-In-One App");
            setSize(900, 600);
            setLocationRelativeTo(null);
            setDefaultCloseOperation(EXIT_ON_CLOSE);
            setLayout(new GridLayout(0, 4, 12, 12));
            getContentPane().setBackground(new Color(30, 30, 50));

            String[] apps = {
                "Analog Clock", "Calculator", "Notepad", "Unit Converter",
                "Tic-Tac-Toe", "Number Guess", "Snake", "Tetris", "Car Racer"
            };

            for (String a : apps) {
                JButton b = new JButton(a);
                b.setFont(new Font("Segoe UI", Font.BOLD, 18));
                b.setBackground(new Color(70, 110, 200));
                b.setForeground(Color.WHITE);
                b.addActionListener(e -> launch(a));
                add(b);
            }
        }

        void launch(String a) {
            switch (a) {
                case "Analog Clock"    -> new AnalogClock().setVisible(true);
                case "Calculator"      -> new Calculator().setVisible(true);
                case "Notepad"         -> new Notepad().setVisible(true);
                case "Unit Converter"  -> new UnitConverter().setVisible(true);
                case "Tic-Tac-Toe"     -> new TicTacToe().setVisible(true);
                case "Number Guess"    -> new NumberGuess().setVisible(true);
                case "Snake"           -> new SnakeGame().setVisible(true);
                case "Tetris"          -> new Tetris().setVisible(true);
                case "Car Racer"       -> new CarRacer().setVisible(true);
            }
        }
    }

    /* ================= ANALOG CLOCK ================= */
    static class AnalogClock extends JFrame {
        AnalogClock() {
            setTitle("Analog Clock");
            setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            add(new ClockPanel());
            pack();
            setLocationRelativeTo(null);
            new Timer(1000, e -> repaint()).start();
        }
    }

    static class ClockPanel extends JPanel {
        ClockPanel() {
            setPreferredSize(new Dimension(400, 400));
            setBackground(new Color(240, 240, 240));
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            int cx = 200, cy = 200, r = 150;
            g2.setColor(new Color(218, 165, 32));
            g2.setStroke(new BasicStroke(12));
            g2.drawOval(50, 50, 300, 300);
            g2.setColor(Color.WHITE);
            g2.fillOval(60, 60, 280, 280);
            g2.setColor(Color.BLACK);
            g2.setStroke(new BasicStroke(4));
            g2.drawOval(60, 60, 280, 280);

            LocalTime t = LocalTime.now();
            double s = t.getSecond(), m = t.getMinute() + s / 60, h = (t.getHour() % 12) + m / 60;

            for (int i = 1; i <= 12; i++) {
                double a = Math.toRadians(90 - i * 30);
                String num = "" + i;
                g2.setFont(new Font("Arial", Font.BOLD, 24));
                int nx = (int) (cx + Math.cos(a) * (r - 40) - g2.getFontMetrics().stringWidth(num) / 2);
                int ny = (int) (cy - Math.sin(a) * (r - 40) + g2.getFontMetrics().getHeight() / 4);
                g2.drawString(num, nx, ny);
            }

            drawHand(g2, cx, cy, h * 30, r * 0.5, 10, Color.BLACK);
            drawHand(g2, cx, cy, m * 6, r * 0.7, 7, Color.BLACK);
            drawHand(g2, cx, cy, s * 6, r * 0.9, 3, Color.RED);
            g2.setColor(Color.BLACK);
            g2.fillOval(cx - 6, cy - 6, 12, 12);
        }

        void drawHand(Graphics2D g2, int cx, int cy, double deg, double len, int thick, Color c) {
            double rad = Math.toRadians(90 - deg);
            int ex = (int) (cx + Math.cos(rad) * len);
            int ey = (int) (cy - Math.sin(rad) * len);
            g2.setColor(c);
            g2.setStroke(new BasicStroke(thick, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
            g2.drawLine(cx, cy, ex, ey);
        }
    }

    /* ================= CALCULATOR (already improved) ================= */
    static class Calculator extends JFrame implements ActionListener, KeyListener {
        private JTextField display = new JTextField("0");
        private String input = "";
        private String operator = "";
        private double result = 0;
        private boolean newInput = true;

        Calculator() {
            setTitle("Calculator");
            setSize(340, 480);
            setLocationRelativeTo(null);
            setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            setLayout(new BorderLayout(10, 10));
            getContentPane().setBackground(new Color(30, 30, 40));

            display.setFont(new Font("Consolas", Font.PLAIN, 42));
            display.setHorizontalAlignment(SwingConstants.RIGHT);
            display.setEditable(false);
            display.setBackground(new Color(20, 20, 30));
            display.setForeground(new Color(220, 255, 220));
            display.setBorder(BorderFactory.createEmptyBorder(20, 15, 20, 15));
            add(display, BorderLayout.NORTH);

            JPanel panel = new JPanel(new GridLayout(5, 4, 8, 8));
            panel.setBackground(new Color(30, 30, 40));

            String[] buttons = {
                "C", "←", "%", "/",
                "7", "8", "9", "*",
                "4", "5", "6", "-",
                "1", "2", "3", "+",
                "0", ".", "±", "="
            };

            for (String btn : buttons) {
                JButton b = new JButton(btn);
                b.setFont(new Font("Segoe UI", Font.BOLD, 28));
                b.setFocusPainted(false);
                b.addActionListener(this);
                if ("0123456789.".contains(btn)) b.setBackground(new Color(60, 60, 80));
                else if (btn.equals("="))         b.setBackground(new Color(40, 160, 40));
                else if ("C←".contains(btn))      b.setBackground(new Color(180, 60, 60));
                else                              b.setBackground(new Color(90, 90, 110));
                b.setForeground(Color.WHITE);
                panel.add(b);
            }

            add(panel, BorderLayout.CENTER);
            addKeyListener(this);
            setFocusable(true);
            requestFocusInWindow();
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            processInput(e.getActionCommand());
        }

        @Override
        public void keyPressed(KeyEvent e) {
            char c = e.getKeyChar();
            int code = e.getKeyCode();

            if (Character.isDigit(c))              processInput("" + c);
            else if (c == '.')                     processInput(".");
            else if (c == '+' || c == '-' || c == '*' || c == '/') processInput("" + c);
            else if (c == '=' || code == KeyEvent.VK_ENTER) processInput("=");
            else if (code == KeyEvent.VK_BACK_SPACE) processInput("←");
            else if (code == KeyEvent.VK_ESCAPE || Character.toLowerCase(c) == 'c') processInput("C");
        }

        private void processInput(String cmd) {
            beep();

            if (cmd.matches("[0-9]")) {
                if (newInput || input.equals("0")) input = "";
                input += cmd;
                newInput = false;
            }
            else if (cmd.equals(".")) {
                if (!input.contains(".")) input += ".";
            }
            else if (cmd.equals("C")) {
                input = ""; operator = ""; result = 0; newInput = true;
            }
            else if (cmd.equals("←")) {
                if (input.length() > 0) input = input.substring(0, input.length() - 1);
                if (input.isEmpty()) input = "0";
            }
            else if (cmd.equals("±")) {
                if (!input.isEmpty() && !input.equals("0")) {
                    input = input.startsWith("-") ? input.substring(1) : "-" + input;
                }
            }
            else if (cmd.equals("%")) {
                if (!input.isEmpty()) {
                    double val = Double.parseDouble(input);
                    input = String.valueOf(val / 100);
                }
            }
            else if ("+-*/".contains(cmd)) {
                if (!input.isEmpty()) {
                    if (!operator.isEmpty()) calculate();
                    result = Double.parseDouble(input);
                    operator = cmd;
                    input = "";
                    newInput = true;
                    display.setText(formatResult(result)); // Show result after operator
                    return;
                }
            }
            else if (cmd.equals("=")) {
                if (!input.isEmpty() && !operator.isEmpty()) {
                    calculate();
                    input = formatResult(result);
                    operator = "";
                    newInput = true;
                    successBeep();
                }
            }

            display.setText(input.isEmpty() ? "0" : input);
        }

        private void calculate() {
            double value = Double.parseDouble(input);
            switch (operator) {
                case "+": result += value; break;
                case "-": result -= value; break;
                case "*": result *= value; break;
                case "/":
                    if (value == 0) {
                        errorBeep();
                        JOptionPane.showMessageDialog(this, "Cannot divide by zero!");
                        input = "Error";
                        return;
                    }
                    result /= value;
                    break;
            }
        }

        private String formatResult(double value) {
            if (value == (long) value) {
                return String.valueOf((long) value);
            }
            String s = String.format("%.8f", value);
            s = s.replaceAll("0+$", "");
            if (s.endsWith(".")) s = s.substring(0, s.length() - 1);
            return s;
        }

        public void keyTyped(KeyEvent e) {}
        public void keyReleased(KeyEvent e) {}
    }

    /* ================= NOTEPAD ================= */
    static class Notepad extends JFrame implements ActionListener {
        JTextArea a = new JTextArea();
        JFileChooser fc = new JFileChooser();

        Notepad() {
            setTitle("Notepad");
            setSize(600, 400);
            setLocationRelativeTo(null);
            setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            add(new JScrollPane(a));
            JMenuBar mb = new JMenuBar();
            JMenu file = new JMenu("File");
            JMenuItem open = new JMenuItem("Open"), save = new JMenuItem("Save");
            open.addActionListener(this);
            save.addActionListener(this);
            file.add(open);
            file.add(save);
            mb.add(file);
            setJMenuBar(mb);
        }

        public void actionPerformed(ActionEvent e) {
            String cmd = e.getActionCommand();
            beep();
            if (cmd.equals("Open")) {
                if (fc.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
                    try (BufferedReader br = new BufferedReader(new FileReader(fc.getSelectedFile()))) {
                        a.read(br, null);
                    } catch (Exception ex) {
                        errorBeep();
                        JOptionPane.showMessageDialog(this, "Error opening file");
                    }
                }
            } else if (cmd.equals("Save")) {
                if (fc.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
                    try (BufferedWriter bw = new BufferedWriter(new FileWriter(fc.getSelectedFile()))) {
                        a.write(bw);
                        successBeep();
                    } catch (Exception ex) {
                        errorBeep();
                        JOptionPane.showMessageDialog(this, "Error saving file");
                    }
                }
            }
        }
    }

    /* ================= UNIT CONVERTER ================= */
    static class UnitConverter extends JFrame implements ActionListener {
        JTextField input = new JTextField();
        JLabel result = new JLabel("0");
        JComboBox<String> cat, from, to;
        String[] cats = {"Length", "Weight"};
        String[][] u = {{"m", "cm", "in"}, {"kg", "g", "lb"}};
        double[][] f = {{1, 100, 39.37}, {1, 1000, 2.205}};

        UnitConverter() {
            setTitle("Unit Converter");
            setSize(400, 200);
            setLocationRelativeTo(null);
            setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            setLayout(new GridLayout(5, 2, 10, 10));
            add(new JLabel("Category"));
            cat = new JComboBox<>(cats);
            cat.addActionListener(this);
            add(cat);
            add(new JLabel("From"));
            from = new JComboBox<>(u[0]);
            add(from);
            add(new JLabel("To"));
            to = new JComboBox<>(u[0]);
            add(to);
            add(new JLabel("Input"));
            add(input);
            add(new JLabel("Result"));
            add(result);
            JButton b = new JButton("Convert");
            b.addActionListener(this);
            add(b);
        }

        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == cat) {
                int i = cat.getSelectedIndex();
                from.removeAllItems();
                to.removeAllItems();
                for (String s : u[i]) {
                    from.addItem(s);
                    to.addItem(s);
                }
                return;
            }
            try {
                double v = Double.parseDouble(input.getText());
                int c = cat.getSelectedIndex(), fi = from.getSelectedIndex(), ti = to.getSelectedIndex();
                double base = v / f[c][fi];
                double res = base * f[c][ti];
                result.setText(String.format("%.4f", res));
                successBeep();
            } catch (Exception ex) {
                errorBeep();
                result.setText("Error");
            }
        }
    }

    /* ================= TIC TAC TOE ================= */
    static class TicTacToe extends JFrame implements ActionListener {
        JButton[][] c = new JButton[3][3];
        boolean x = true;

        TicTacToe() {
            setTitle("Tic-Tac-Toe");
            setSize(300, 300);
            setLocationRelativeTo(null);
            setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            setLayout(new GridLayout(3, 3));
            for (int i = 0; i < 3; i++) for (int j = 0; j < 3; j++) {
                c[i][j] = new JButton("");
                c[i][j].setFont(new Font("Arial", Font.BOLD, 80));
                c[i][j].addActionListener(this);
                add(c[i][j]);
            }
        }

        public void actionPerformed(ActionEvent e) {
            JButton b = (JButton) e.getSource();
            if (!b.getText().isEmpty()) return;
            beep();
            b.setText(x ? "X" : "O");
            if (win()) {
                successBeep();
                JOptionPane.showMessageDialog(this, (x ? "X" : "O") + " wins!");
                reset();
            } else if (full()) {
                JOptionPane.showMessageDialog(this, "Draw!");
                reset();
            }
            x = !x;
        }

        boolean win() {
            for (int i = 0; i < 3; i++) if (same(c[i][0], c[i][1], c[i][2]) || same(c[0][i], c[1][i], c[2][i])) return true;
            return same(c[0][0], c[1][1], c[2][2]) || same(c[0][2], c[1][1], c[2][0]);
        }

        boolean same(JButton a, JButton b, JButton d) {
            String t = a.getText();
            return !t.isEmpty() && t.equals(b.getText()) && t.equals(d.getText());
        }

        boolean full() {
            for (JButton[] r : c) for (JButton b : r) if (b.getText().isEmpty()) return false;
            return true;
        }

        void reset() {
            for (JButton[] r : c) for (JButton b : r) b.setText("");
            x = true;
        }
    }

    /* ================= NUMBER GUESS ================= */
    static class NumberGuess extends JFrame implements ActionListener {
        int n = new Random().nextInt(100) + 1, tries = 0, max = 10;
        JTextField f = new JTextField();
        JLabel l = new JLabel("Guess 1-100! Attempts left: " + (max - tries), SwingConstants.CENTER);

        NumberGuess() {
            setTitle("Number Guess");
            setSize(300, 200);
            setLocationRelativeTo(null);
            setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            setLayout(new GridLayout(3, 1));
            JButton b = new JButton("Guess");
            b.addActionListener(this);
            add(l);
            add(f);
            add(b);
        }

        public void actionPerformed(ActionEvent e) {
            try {
                int g = Integer.parseInt(f.getText());
                tries++;
                beep();
                if (g == n) {
                    l.setText("Correct! Well done!");
                    successBeep();
                    f.setEnabled(false);
                } else if (tries >= max) {
                    l.setText("Game over! It was " + n);
                    errorBeep();
                    f.setEnabled(false);
                } else {
                    l.setText((g < n ? "Too Low!" : "Too High!") + " Attempts left: " + (max - tries));
                }
                f.setText("");
            } catch (Exception ex) {
                errorBeep();
                l.setText("Invalid! Attempts left: " + (max - tries));
            }
        }
    }

    /* ================= SNAKE WITH RESTART BUTTON ================= */
    static class SnakeGame extends JFrame {
        SnakeGame() {
            setTitle("Snake");
            setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            add(new SnakePanel());
            pack();
            setLocationRelativeTo(null);
        }
    }

    static class SnakePanel extends JPanel implements ActionListener, KeyListener {
        static final int T = 20, W = 25, H = 25;
        java.util.List<Point> s = new ArrayList<>();
        Point food;
        int dx = T, dy = 0, score = 0;
        Timer t;
        JLabel scoreL = new JLabel("Score: 0");
        JButton restartBtn;

        SnakePanel() {
            setPreferredSize(new Dimension(W * T, H * T + 50));
            setBackground(Color.BLACK);
            setLayout(new BorderLayout());
            setFocusable(true);
            addKeyListener(this);

            JPanel top = new JPanel();
            top.setBackground(Color.BLACK);
            top.add(scoreL);
            scoreL.setForeground(Color.WHITE);
            add(top, BorderLayout.NORTH);

            restartBtn = new JButton("Play Again");
            restartBtn.setVisible(false);
            restartBtn.addActionListener(e -> resetGame());
            add(restartBtn, BorderLayout.SOUTH);

            resetGame();
        }

        void resetGame() {
            s.clear();
            s.add(new Point(10 * T, 10 * T));
            dx = T; dy = 0;
            score = 0;
            scoreL.setText("Score: 0");
            spawn();
            if (t != null) t.stop();
            t = new Timer(150, this);
            t.start();
            restartBtn.setVisible(false);
            requestFocusInWindow();
            repaint();
        }

        void spawn() {
            food = new Point(new Random().nextInt(W) * T, new Random().nextInt(H) * T);
        }

        public void actionPerformed(ActionEvent e) {
            Point h = new Point(s.get(0).x + dx, s.get(0).y + dy);
            if (h.x < 0 || h.y < 0 || h.x >= W * T || h.y >= H * T || s.contains(h)) {
                t.stop();
                errorBeep();
                JOptionPane.showMessageDialog(this, "Game Over! Score: " + score);
                restartBtn.setVisible(true);
                return;
            }
            s.add(0, h);
            if (h.equals(food)) {
                score++;
                successBeep();
                spawn();
                scoreL.setText("Score: " + score);
            } else s.remove(s.size() - 1);
            repaint();
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.setColor(Color.GREEN);
            for (Point p : s) g.fillRoundRect(p.x, p.y, T, T, 5, 5);
            g.setColor(Color.RED);
            g.fillRoundRect(food.x, food.y, T, T, 5, 5);
        }

        public void keyPressed(KeyEvent e) {
            if (!t.isRunning()) return;
            int k = e.getKeyCode();
            if (k == KeyEvent.VK_UP && dy == 0) { dx = 0; dy = -T; }
            if (k == KeyEvent.VK_DOWN && dy == 0) { dx = 0; dy = T; }
            if (k == KeyEvent.VK_LEFT && dx == 0) { dx = -T; dy = 0; }
            if (k == KeyEvent.VK_RIGHT && dx == 0) { dx = T; dy = 0; }
        }

        public void keyTyped(KeyEvent e) {}
        public void keyReleased(KeyEvent e) {}
    }

    /* ================= TETRIS WITH RESTART BUTTON ================= */
    static class Tetris extends JFrame {
        Tetris() {
            setTitle("Tetris");
            setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            add(new TetrisPanel());
            pack();
            setLocationRelativeTo(null);
        }
    }

    static class TetrisPanel extends JPanel implements ActionListener, KeyListener {
        static final int C = 10, R = 20, S = 25;
        int[][] b = new int[R][C];
        int[][] p;
        int px = C / 2 - 2, py = 0, score = 0;
        Timer t;
        int[][][] sh = {
            {{1,1,1,1}}, {{1,1},{1,1}}, {{0,1,0},{1,1,1}},
            {{0,1,1},{1,1,0}}, {{1,1,0},{0,1,1}},
            {{1,0,0},{1,1,1}}, {{0,0,1},{1,1,1}}
        };
        JButton restartBtn;

        TetrisPanel() {
            setPreferredSize(new Dimension(C * S, R * S + 50));
            setBackground(Color.BLACK);
            setLayout(new BorderLayout());
            setFocusable(true);
            addKeyListener(this);

            restartBtn = new JButton("Play Again");
            restartBtn.setVisible(false);
            restartBtn.addActionListener(e -> resetGame());
            add(restartBtn, BorderLayout.SOUTH);

            resetGame();
        }

        void resetGame() {
            for (int[] row : b) Arrays.fill(row, 0);
            score = 0;
            spawn();
            if (t != null) t.stop();
            t = new Timer(500, this);
            t.start();
            restartBtn.setVisible(false);
            requestFocusInWindow();
            repaint();
        }

        void spawn() {
            int i = new Random().nextInt(sh.length);
            p = sh[i];
            px = C / 2 - p[0].length / 2;
            py = 0;
        }

        public void actionPerformed(ActionEvent e) {
            if (!can(0, 1)) {
                place();
                int cl = clear();
                if (cl > 0) {
                    score += cl * 100;
                    successBeep();
                }
                spawn();
                if (!can(0, 0)) {
                    t.stop();
                    errorBeep();
                    JOptionPane.showMessageDialog(this, "Game Over! Score: " + score);
                    restartBtn.setVisible(true);
                    return;
                }
            } else py++;
            repaint();
        }

        boolean can(int dx, int dy) {
            for (int y = 0; y < p.length; y++) for (int x = 0; x < p[y].length; x++) if (p[y][x] > 0) {
                int nx = px + x + dx, ny = py + y + dy;
                if (nx < 0 || nx >= C || ny >= R || (ny >= 0 && b[ny][nx] > 0)) return false;
            }
            return true;
        }

        void place() {
            for (int y = 0; y < p.length; y++) for (int x = 0; x < p[y].length; x++) if (p[y][x] > 0) b[py + y][px + x] = 1;
        }

        int clear() {
            int cl = 0;
            for (int y = R - 1; y >= 0; y--) {
                boolean full = true;
                for (int x = 0; x < C; x++) if (b[y][x] == 0) { full = false; break; }
                if (full) {
                    cl++;
                    for (int yy = y; yy > 0; yy--) System.arraycopy(b[yy - 1], 0, b[yy], 0, C);
                    y++;
                }
            }
            return cl;
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.setColor(Color.GRAY);
            for (int y = 0; y < R; y++) for (int x = 0; x < C; x++) if (b[y][x] > 0) g.fillRoundRect(x * S, y * S, S - 1, S - 1, 5, 5);
            g.setColor(Color.CYAN);
            for (int y = 0; y < p.length; y++) for (int x = 0; x < p[y].length; x++) if (p[y][x] > 0) g.fillRoundRect((px + x) * S, (py + y) * S, S - 1, S - 1, 5, 5);
            g.setColor(Color.WHITE);
            g.drawString("Score: " + score, 10, 20);
        }

        public void keyPressed(KeyEvent e) {
            if (!t.isRunning()) return;
            int k = e.getKeyCode();
            if (k == KeyEvent.VK_LEFT && can(-1, 0)) px--;
            if (k == KeyEvent.VK_RIGHT && can(1, 0)) px++;
            if (k == KeyEvent.VK_DOWN && can(0, 1)) py++;
            if (k == KeyEvent.VK_UP) {
                int[][] rot = new int[p[0].length][p.length];
                for (int y = 0; y < p.length; y++) for (int x = 0; x < p[y].length; x++) rot[x][p.length - 1 - y] = p[y][x];
                int[][] old = p;
                p = rot;
                if (!can(0, 0)) p = old;
            }
            repaint();
        }

        public void keyTyped(KeyEvent e) {}
        public void keyReleased(KeyEvent e) {}
    }

    /* ================= CAR RACER WITH RESTART BUTTON ================= */
    static class CarRacer extends JFrame {
        CarRacer() {
            setTitle("Car Racer");
            setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            add(new CarPanel());
            pack();
            setLocationRelativeTo(null);
        }
    }

    static class CarPanel extends JPanel implements ActionListener, KeyListener {
        int carX = 175, score = 0;
        ArrayList<Rectangle> obs = new ArrayList<>();
        Timer t;
        Random r = new Random();
        JButton restartBtn;

        CarPanel() {
            setPreferredSize(new Dimension(400, 650));
            setBackground(Color.DARK_GRAY);
            setLayout(new BorderLayout());
            setFocusable(true);
            addKeyListener(this);

            restartBtn = new JButton("Play Again");
            restartBtn.setVisible(false);
            restartBtn.addActionListener(e -> resetGame());
            add(restartBtn, BorderLayout.SOUTH);

            resetGame();
        }

        void resetGame() {
            carX = 175;
            score = 0;
            obs.clear();
            if (t != null) t.stop();
            t = new Timer(25, this);
            t.start();
            restartBtn.setVisible(false);
            requestFocusInWindow();
            repaint();
        }

        public void actionPerformed(ActionEvent e) {
            score++;
            spawn();
            for (Rectangle o : obs) o.y += 6;
            obs.removeIf(o -> o.y > getHeight());

            Rectangle car = new Rectangle(carX, getHeight() - 140, 50, 100);
            for (Rectangle o : obs) {
                if (car.intersects(o)) {
                    t.stop();
                    errorBeep();
                    JOptionPane.showMessageDialog(this, "Crashed! Score: " + score);
                    restartBtn.setVisible(true);
                    return;
                }
            }
            repaint();
        }

        void spawn() {
            if (r.nextInt(70) < 6) {
                int lane = r.nextInt(5);
                int x = 50 + lane * 60;
                obs.add(new Rectangle(x, -100, 50, 100));
            }
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.setColor(Color.GRAY);
            g.fillRect(50, 0, 300, getHeight());
            g.setColor(Color.WHITE);
            for (int i = 0; i < 12; i++) {
                int y = (i * 80 + score * 5) % (getHeight() + 80) - 40;
                g.fillRect(195, y, 10, 50);
            }
            g.setColor(Color.BLUE);
            g.fillRoundRect(carX, getHeight() - 140, 50, 100, 12, 12);
            g.setColor(Color.RED);
            for (Rectangle o : obs) g.fillRoundRect(o.x, o.y, 50, 100, 12, 12);
            g.setColor(Color.WHITE);
            g.setFont(new Font("Arial", Font.BOLD, 24));
            g.drawString("Score: " + score, 20, 40);
        }

        public void keyPressed(KeyEvent e) {
            if (!t.isRunning()) return;
            int k = e.getKeyCode();
            if (k == KeyEvent.VK_LEFT && carX > 55) carX -= 60;
            if (k == KeyEvent.VK_RIGHT && carX < 400 - 50 - 55) carX += 60;
            repaint();
        }

        public void keyTyped(KeyEvent e) {}
        public void keyReleased(KeyEvent e) {}
    }
}