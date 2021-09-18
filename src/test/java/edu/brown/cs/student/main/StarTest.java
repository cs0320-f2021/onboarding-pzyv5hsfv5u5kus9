package edu.brown.cs.student.main;

import static org.junit.Assert.*;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

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
    s.storeStars("data/stars/ten-star.csv");
    boolean test = Arrays.equals(new double[]{0.0, 0.0, 0.0}, s.getPosition("Sol"));
    assertTrue(test);
  }

  @Test
  public void testKNN0() {
    Stars s = new Stars();
    s.storeStars("data/stars/one-star.csv");
    List<Integer> output = s.getNeighborsFromStar(0,"Lonely Star");
    assertEquals(0, output.size(),0.0);
  }

  @Test
  public void testKNN1() {
    Stars s = new Stars();
    s.storeStars("data/stars/one-star.csv");
    double[] pos = new double[]{0.01, 0.01, 0.01};
    List<Integer> knn = s.getNeighborsFromPosition(1, pos);
    Collections.sort(knn);
    List<Integer> answer = new ArrayList<>();
    answer.add(0);
    assertEquals(knn, answer);
  }
}
