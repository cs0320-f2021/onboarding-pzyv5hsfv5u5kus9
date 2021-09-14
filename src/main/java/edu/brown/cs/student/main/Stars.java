package edu.brown.cs.student.main;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class Stars {
  private List<List<String>> dataset;
  private HashMap<String, double[]> positions;

  /**
   * Reads a csv file and stores it into both dataset and positions.
   * @param file - csv file to read into the database
   */
  public void storeStars(String file) {
    // TODO: fill in database/positions initialization/data ingression
    try {
      BufferedReader buffer = new BufferedReader(new FileReader(file));
      String l;
      while ((l = buffer.readLine()) != null) {
        String[] star = l.split(",");
        dataset.add(Arrays.asList(star));
      }
    } catch (Exception e) {
      e.printStackTrace();
      System.out.println("ERROR: file ingestion error");
    }
  }

  /**
   * gets Position array from star name.
   * @param name - name of star (string format)
   * @return double array of xyz coordinates
   */
  public double[] getPosition(String name) {
    try {
      return positions.get(name);
    } catch (Exception e) {
      e.printStackTrace();
      System.out.print("ERROR: index key not in positions hashmap");
    }
    return null;
  }

  /**
   * naive KNN implementation given starting position.
   * @param k - number of stars to return
   * @param pos - starting position
   * @return list of k star IDs that are closest to position
   */
  public int[] getNeighborsFromPosition(int k, double[] pos) {
    // TODO: fill out KNN
    return null;
  }

  /**
   * naive KNN implementation given starting star.
   * @param k - number of stars to return
   * @param name - name of star to search around
   * @return - list of k star IDs that are closest to the given star
   */
  public int[] getNeighborsFromStar(int k, String name) {
    double[] pos = getPosition(name);
    return getNeighborsFromPosition(k, pos);
  }
}
