package edu.brown.cs.student.main;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class MathBotTest {

  @Test
  public void testAddition() {
    MathBot matherator9000 = new MathBot();
    double output = matherator9000.add(10.5, 3);
    assertEquals(13.5, output, 0.01);
  }

  @Test
  public void testLargerNumbers() {
    MathBot matherator9001 = new MathBot();
    double output = matherator9001.add(100000, 200303);
    assertEquals(300303, output, 0.01);
  }

  @Test
  public void testSubtraction() {
    MathBot matherator9002 = new MathBot();
    double output = matherator9002.subtract(18, 17);
    assertEquals(1, output, 0.01);
  }

  // TODO: add more unit tests of your own

  @Test
  public void testNegativeAddition() {
    MathBot m = new MathBot();
    double output = m.add(-1, -2);
    assertEquals(-3, output, 0.01);
  }

  @Test
  public void testNegativeSubtraction() {
    MathBot m = new MathBot();
    double output = m.subtract(-1, -2);
    assertEquals(1, output, 0.01);
  }

  @Test
  public void testMoreNegativeSubtraction() {
    MathBot m - new MathBot();
    double o = m.subtract(-5, -1);
    assertEquals( -4, o, 0.01);
  }
}
