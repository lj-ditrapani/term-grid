package info.ditrapani.termgrid;

import static org.junit.Assert.*;

import org.junit.Test;

public class TermGridImplTest {
  @Test
  public void testVisualTest() {
    System.out.println("hello!!!");
    TermGrid termGrid = new TermGridImpl(10, 10);
    termGrid.clear();
    termGrid.draw();
    termGrid.draw();
    termGrid.reset();
    assertTrue(true);
  }
}
