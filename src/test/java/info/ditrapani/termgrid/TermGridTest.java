package info.ditrapani.termgrid;

import org.junit.Test;
import static org.junit.Assert.*;

public class TermGridTest {
    @Test public void testVisualTest() {
        System.out.println("hello!!!");
        TermGrid termGrid = new TermGrid(10, 10);
        termGrid.clear();
        termGrid.draw();
        termGrid.draw();
        termGrid.reset();
        assertTrue(true);
    }
}
