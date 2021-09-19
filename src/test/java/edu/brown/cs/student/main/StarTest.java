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
  public void testKNN0() throws Exception {
    Stars s = new Stars();
    s.storeStars("data/stars/one-star.csv");
    List<Integer> output = s.getNeighborsFromStar(0,"Lonely Star");
    assertEquals(0, output.size());
  }

  @Test
  public void testKNNOneMax() {
    Stars s = new Stars();
    s.storeStars("data/stars/one-star.csv");
    List<Integer> output = s.getNeighborsFromPosition(1, new double[] {12.0025, 1.0257, -105.5236});
    assertEquals(1, output.size());
  }

  @Test
  public void testKNN1() {
    Stars s = new Stars();
    s.storeStars("data/stars/ten-star.csv");
    double[] pos = new double[]{0.01, 0.01, 0.01};
    List<Integer> knn = s.getNeighborsFromPosition(1, pos);
    Collections.sort(knn);
    List<Integer> answer = new ArrayList<>();
    answer.add(0);
    assertEquals(answer, knn);
  }

  @Test
  public void testKNN() {
    Stars s = new Stars();
    s.storeStars("data/stars/three-star.csv");
    double[] pos = new double[]{0.01, 0.01, 0.01};
    List<Integer> knn = s.getNeighborsFromPosition(3, pos);
    Collections.sort(knn);
    List<Integer> answer = new ArrayList<>(Arrays.asList(1,2,3));
    assertEquals(answer, knn);
  }

  @Test
  public void testKNN2() {
    Stars s = new Stars();
    s.storeStars("data/stars/three-star.csv");
    double[] pos = new double[]{0.01, 0.01, 0.01};
    List<Integer> knn = s.getNeighborsFromPosition(2, pos);
    Collections.sort(knn);
    List<Integer> answer = new ArrayList<>(Arrays.asList(1,2));
    assertEquals(answer, knn);
  }

  @Test
  public void testKNNAlt() throws Exception {
    Stars s = new Stars();
    s.storeStars("data/stars/three-star.csv");
    List<Integer> knn = s.getNeighborsFromStar(2, "Star One");
    List<Integer> answer = new ArrayList<>(Arrays.asList(2,3));
    assertEquals(answer, knn);
  }

  @Test
  public void testStoreStarsOverwrite() {
    Stars s = new Stars();
    s.storeStars("data/stars/three-star.csv");
    s.storeStars("data/stars/one-star.csv");
    int size = s.getDataset().size();
    assertEquals(1, size);
  }

  @Test
  public void testHashMapFill() {
    Stars s = new Stars();
    s.storeStars("data/stars/three-star.csv");
    int size = s.getPositions().size();
    assertEquals(3, size);
  }
}
