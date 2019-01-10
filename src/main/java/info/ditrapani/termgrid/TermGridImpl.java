package info.ditrapani.termgrid;

public class TermGridImpl implements TermGrid {
  private final Cell[][] grid;
  private final Printer printer;
  private final int height;
  private final int width;
  private static String clear = "\u001b[2J";
  private static String init = "\u001B[?25l\u001b[0;0H";
  private static String reset = "\u001b[0m\u001B[?25h";

  TermGridImpl(int height, int width, Printer printer) {
    require(height >= 1, "Height must be positive.");
    require(width >= 1, "Width must be positive.");
    this.height = height;
    this.width = width;
    this.printer = printer;
    this.grid = new Cell[height][width];
    for (Cell[] row : grid) {
      for (int i = 0; i < row.length; i++) {
        row[i] = new Cell('.', Color.Green, Color.Grey);
      }
    }
  }

  public TermGridImpl(int height, int width) {
    this(height, width, new PrinterImpl());
  }

  public void clear() {
    printer.print(clear);
  }

  public void draw() {
    StringBuilder sb = new StringBuilder(10000);
    sb.append(init);
    for (Cell[] row : grid) {
      for (Cell cell : row) {
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

  public void reset() {
    printer.print(reset);
  }

  public void set(int y, int x, char c, byte fg, byte bg) {
    checkBounds(y, x);
    Cell cell = grid[y][x];
    cell.c = c;
    cell.fg = fg;
    cell.bg = bg;
  }

  public void text(int y, int x, String text, byte fg, byte bg) {
    checkBounds(y, x);
    require(x + text.length() <= width, "x + text.length must be <= grid width");
    int currX = x;
    for (int i = 0; i < text.length(); i++) {
      char c = text.charAt(i);
      set(y, currX, c, fg, bg);
      ++currX;
    }
  }

  private void checkBounds(int y, int x) {
    require(y >= 0 && y < height, "y index must by >= 0 and < grid height");
    require(x >= 0 && x < width, "x index must by >= 0 and < grid width");
  }

  private void require(boolean b, String message) {
    if (!b) {
      throw new IllegalArgumentException(message);
    }
  }
}
