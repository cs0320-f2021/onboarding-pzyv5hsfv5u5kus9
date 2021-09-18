package edu.brown.cs.student.main;

import static org.junit.Assert.*;

import org.junit.Test;

import java.util.Arrays;

public class StarTest {

  @Test
  public void testEuclideanDistance() {
    Stars s = new Stars();
    double output = s.getEuclideanDistance(new double[]{1.0,2.0,3.0},
                                            new double[]{1.0,2.0,3.0});
    assertEquals(0.0, output, 0.01);
    output = s.getEuclideanDistance(new double[]{0.0,0.0,0.0},
        new double[]{1.0,2.0,2.0});
    assertEquals(3.0, output, 0.01);
  }

  @Test
  public void testStoreStars() {
    Stars s = new Stars();
    s.storeStars("data/stars/ten-star.csv");
    assertEquals(10,s.getDataset().size(), 0.01);
  }

  @Test
  public void testGetPosition() {
    Stars s = new Stars();
    s.storeStars("/data/stars/ten-star.csv");
    System.out.println(s.getPosition("Sol"));
    boolean test = Arrays.equals(new double[]{0.0, 0.0, 0.0}, s.getPosition("Sol"));
    assertTrue(test);
  }

  @Test
  public void testKNN1() {

  }


}
