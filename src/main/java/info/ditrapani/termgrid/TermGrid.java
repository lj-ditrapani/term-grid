package info.ditrapani.termgrid;

public class TermGrid {
    private final Cell[][] grid;
    private final Printer printer;
    private final int height;
    private final int width;
    private int xLoc = 0;
    private int yLoc = 0;
    static private String clear = "\u001b[2J";
    static private String init = "\u001B[?25l\u001b[0;0H";
    static private String reset = "\u001b[0m\u001B[?25h";

    public TermGrid(int height, int width, Printer printer) {
        this.printer = printer;
        this.height = height;
        this.width = width;
        this.grid = new Cell[height][width];
        for (Cell[] row: grid) {
            for (int i = 0; i < row.length; i++) {
                row[i] = new Cell('%', Color.Maroon, Color.Green);
            }
        }
    }

    public TermGrid(int height, int width) {
        this(height, width, new PrinterImpl());
    }

    public void set(int y, int x, char c, byte fg, byte bg) {
        Cell cell = grid[y][x];
        cell.c = c;
        cell.fg = fg;
        cell.bg = bg;
    }

    public void putc(char c, int y, int x, byte fg, byte bg) {
        printer.print(String.format("\u001b[38;5;%dm\u001b[48;5;%dm%c", fg & 0xFF, bg & 0xFF, c));
    }

    public void puts(String s, int y, int x) {
        printer.print(s);
    }

    public void draw() {
        StringBuilder sb = new StringBuilder(10000);
        sb.append(init);
        for (Cell[] row: grid) {
            for (Cell cell: row) {
                sb.append("\u001b[38;5;");
                sb.append(cell.fg & 0xFF);
                sb.append("m\u001b[48;5;");
                sb.append(cell.bg & 0xFF);
                sb.append('m');
                sb.append(cell.c);
            }
            sb.append('\n');
        }
        printer.print(sb.toString());
    }

    public void clear() {
        printer.print(clear);
    }

    public void reset() {
        printer.print(reset);
    }
}
