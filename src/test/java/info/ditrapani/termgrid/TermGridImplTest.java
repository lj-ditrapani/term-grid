package info.ditrapani.termgrid;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Test;

public class TermGridImplTest {
  @Test
  public void constructor_whenHeightIs0_throws() {
    Printer printer = mock(Printer.class);
    assertThrows(IllegalArgumentException.class, () -> new TermGridImpl(0, 5, printer));
  }

  @Test
  public void constructor_whenWidthIs0_throws() {
    Printer printer = mock(Printer.class);
    assertThrows(IllegalArgumentException.class, () -> new TermGridImpl(5, 0, printer));
  }

  @Test
  public void clear_printsClearCode() {
    Printer printer = mock(Printer.class);
    final TermGrid termGrid = new TermGridImpl(6, 4, printer);
    termGrid.clear();
    verify(printer).print("\u001b[2J");
  }

  @Test
  public void reset_printsResetCode() {
    Printer printer = mock(Printer.class);
    final TermGrid termGrid = new TermGridImpl(6, 4, printer);
    termGrid.reset();
    verify(printer).print("\u001b[0m\u001B[?25h");
  }

  @Test
  public void set_setsTheValuesOfTheGivenCell() {
    Printer printer = mock(Printer.class);
    final TermGrid termGrid = new TermGridImpl(2, 1, printer);
    termGrid.set(0, 0, '^', (byte) 12, (byte) 34);
    termGrid.set(1, 0, 'v', (byte) 56, (byte) 78);
    termGrid.draw();
    verify(printer)
        .print(
            "\u001B[?25l\u001b[0;0H"
                + "\u001b[38;5;12m\u001b[48;5;34m^"
                + "\n"
                + "\u001b[38;5;56m\u001b[48;5;78mv"
                + "\n");
  }

  @Test
  public void set_whenYisNegative_throws() {
    Printer printer = mock(Printer.class);
    final TermGrid termGrid = new TermGridImpl(5, 5, printer);
    assertThrows(
        IllegalArgumentException.class, () -> termGrid.set(-1, 4, 'x', (byte) 0, (byte) 0));
  }

  @Test
  public void set_whenYisTooLarge_throws() {
    Printer printer = mock(Printer.class);
    final TermGrid termGrid = new TermGridImpl(5, 10, printer);
    assertThrows(IllegalArgumentException.class, () -> termGrid.set(5, 4, 'x', (byte) 0, (byte) 0));
  }

  @Test
  public void set_whenXisNegative_throws() {
    Printer printer = mock(Printer.class);
    final TermGrid termGrid = new TermGridImpl(5, 5, printer);
    assertThrows(
        IllegalArgumentException.class, () -> termGrid.set(4, -1, 'x', (byte) 0, (byte) 0));
  }

  @Test
  public void set_whenXisTooLarge_throws() {
    Printer printer = mock(Printer.class);
    final TermGrid termGrid = new TermGridImpl(10, 5, printer);
    assertThrows(IllegalArgumentException.class, () -> termGrid.set(4, 5, 'x', (byte) 0, (byte) 0));
  }

  @Test
  public void text_whenYisNegative_throws() {
    Printer printer = mock(Printer.class);
    final TermGrid termGrid = new TermGridImpl(5, 5, printer);
    assertThrows(
        IllegalArgumentException.class, () -> termGrid.text(-1, 4, "x", (byte) 0, (byte) 0));
  }

  @Test
  public void text_whenYisTooLarge_throws() {
    Printer printer = mock(Printer.class);
    final TermGrid termGrid = new TermGridImpl(5, 10, printer);
    assertThrows(
        IllegalArgumentException.class, () -> termGrid.text(5, 4, "x", (byte) 0, (byte) 0));
  }

  @Test
  public void text_whenXisNegative_throws() {
    Printer printer = mock(Printer.class);
    final TermGrid termGrid = new TermGridImpl(5, 5, printer);
    assertThrows(
        IllegalArgumentException.class, () -> termGrid.text(4, -1, "x", (byte) 0, (byte) 0));
  }

  @Test
  public void text_whenXisTooLarge_throws() {
    Printer printer = mock(Printer.class);
    final TermGrid termGrid = new TermGridImpl(10, 5, printer);
    assertThrows(
        IllegalArgumentException.class, () -> termGrid.text(4, 5, "x", (byte) 0, (byte) 0));
  }

  @Test
  public void text_whenTextOverflowsWidth_throws() {
    Printer printer = mock(Printer.class);
    final TermGrid termGrid = new TermGridImpl(10, 3, printer);
    assertThrows(
        IllegalArgumentException.class, () -> termGrid.text(4, 1, "123", (byte) 0, (byte) 0));
  }

  @Test
  public void text_whenTextFitsInTheGrid_doesNotThrow() {
    Printer printer = mock(Printer.class);
    final TermGrid termGrid = new TermGridImpl(10, 3, printer);
    termGrid.text(4, 1, "12", (byte) 0, (byte) 0);
  }

  @Test
  public void constructor_usesDefaultPrinterIfOmitted() {
    new TermGridImpl(2, 3);
  }
}
