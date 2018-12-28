package info.ditrapani.termgrid;

/**
 * Represents the terminal as a 2D grid with 256 colors.
 *
 * <p>
 * Typical usage migth have a setup, a main loop, and a shutdown.
 * </p>
 * <p>
 * The setup would call clear() to clean up the screen.
 * </p>
 * Example main loop:
 *     <ul>
 *     <li>User input/other event</li>
 *     <li>Business logic</li>
 *     <li>Update grid with several calls to set() and/or text() methods</li>
 *     <li>Call draw() to display the new grid state on the terminal</li>
 *     <li>Repeat</li>
 *     </ul>
 *
 * <p>
 * Shutdown:  call reset() to return the terminal to normal.
 * </p>
 *
 * <p>
 * A color byte is an index into one of the 256 ANSI Xterm colors
 * <a href="https://jonasjacek.github.io/colors/">https://jonasjacek.github.io/colors/</a></p>
 */
public interface TermGrid {
    /**
     * Clears the screen with the current background color.
     * Literrally prints "\\u001b[2J".
     */
    public void clear();

    /**
     * Draw the current state of the grid to the terminal.
     */
    public void draw();

    /**
     * Reset colors and re-enable the cursor.
     * Literrally prints "\\u001b[0m\\u001B[?25h"
     */
    public void reset();

    /**
     * Set a cell in the grid.
     * You must call draw() to see the change in the terminal.
     * @param y 0-based row index into grid
     * @param x 0-based column index into grid
     * @param c character to set in cell
     * @param fg foreground color to set in cell
     * @param bg background color to set in cell
     */
    public void set(int y, int x, char c, byte fg, byte bg);

    /**
     * Set a sequence of cells of a row in the grid.
     * Effects n cells where n is the length of text.
     * You must call draw() to see the change in the terminal.
     * @param y 0-based row index into grid
     * @param x 0-based column index into grid
     * @param text text to write in row y starting in column x
     * @param fg foreground color to set to each cell for the text
     * @param bg background color to set to each cell in under text
     */
    public void text(int y, int x, String text, byte fg, byte bg);
}
