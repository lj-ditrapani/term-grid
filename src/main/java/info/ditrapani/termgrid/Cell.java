package info.ditrapani.termgrid;

class Cell {
    public byte fg;
    public byte bg;
    public char c;

    public Cell(char c, byte fg, byte bg) {
        this.c = c;
        this.fg = fg;
        this.bg = bg;
    }
}
