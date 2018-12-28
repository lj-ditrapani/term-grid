package info.ditrapani.termgrid;

public class Test {
    public static void main(String[] args) throws InterruptedException {
        TermGrid termGrid = new TermGrid(10, 20);
        termGrid.clear();
        termGrid.draw();
        Thread.sleep(400);
        termGrid.set(2, 5, '-', Color.Green, Color.Maroon);
        termGrid.set(3, 5, '-', Color.Green, Color.Maroon);
        termGrid.set(4, 5, '-', Color.Green, Color.Maroon);
        termGrid.draw();
        Thread.sleep(400);
        termGrid.set(2, 5, '%', Color.Maroon, Color.Green);
        termGrid.set(3, 4, '-', Color.Green, Color.Maroon);
        termGrid.set(3, 6, '-', Color.Green, Color.Maroon);
        termGrid.draw();
        Thread.sleep(400);
        termGrid.set(8, 8, '.', Color.White, Color.Blue3);
        termGrid.set(7, 8, '.', Color.White, Color.Blue3);
        termGrid.set(8, 7, '.', Color.White, Color.Blue3);
        termGrid.set(7, 7, '.', Color.White, Color.Blue3);
        termGrid.draw();
        Thread.sleep(400);
        termGrid.reset();
    }
}
