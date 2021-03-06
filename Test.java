package info.ditrapani.termgridtest;

import info.ditrapani.termgrid.Color;
import info.ditrapani.termgrid.TermGrid;
import info.ditrapani.termgrid.TermGridImpl;

public class Test {
  public static void main(String[] args) throws InterruptedException {
    TermGrid termGrid = new TermGridImpl(10, 20);
    termGrid.clear();
    termGrid.draw();
    Thread.sleep(400);
    termGrid.set(2, 5, '-', Color.Grey, Color.Aqua);
    termGrid.set(3, 5, '-', Color.Grey, Color.Aqua);
    termGrid.set(4, 5, '-', Color.Grey, Color.Aqua);
    termGrid.draw();
    Thread.sleep(400);
    termGrid.set(2, 5, '.', Color.Green, Color.Grey);
    termGrid.set(3, 4, '-', Color.Grey, Color.Aqua);
    termGrid.set(3, 6, '-', Color.Grey, Color.Aqua);
    termGrid.draw();
    Thread.sleep(400);
    termGrid.set(8, 8, '%', Color.White, Color.Blue3);
    termGrid.set(7, 8, '%', Color.White, Color.Blue3);
    termGrid.set(8, 7, '%', Color.White, Color.Blue3);
    termGrid.set(7, 7, '%', Color.White, Color.Blue3);
    termGrid.draw();
    Thread.sleep(400);
    termGrid.text(6, 5, "Hello, world!", Color.NavyBlue, Color.Silver);
    termGrid.draw();
    Thread.sleep(400);

    termGrid.reset();
    termGrid.clear();
    termGrid = new TermGridImpl(40, 80);
    for (int i = 1; i < 20; i++) {
      Thread.sleep(70);
      termGrid.text(i - 1, 1, ".............", Color.Green, Color.Grey);
      termGrid.text(i, 1, "Hello, world!", Color.NavyBlue, Color.Silver);
      termGrid.set(20, i - 1, '.', Color.Green, Color.Grey);
      termGrid.text(20, i, "<>", Color.HotPink3, Color.Grey);
      termGrid.set(20, i + 4, '.', Color.Green, Color.Grey);
      termGrid.text(20, i + 5, "[]", Color.Grey, Color.Yellow2);
      termGrid.draw();
    }
    termGrid.reset();
  }
}
