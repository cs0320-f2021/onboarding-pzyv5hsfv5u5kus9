package edu.brown.cs.student.main;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;



public class Stars {
  private List<List<String>> dataset = new ArrayList<>();
  private HashMap<String, double[]> positions = new HashMap<>();

  public List<List<String>> getDataset() {
    return dataset;
  }

  /**
   * Reads a csv file and stores it into both dataset and positions.
   * @param file - csv file to read into the database
   */
  public void storeStars(String file) {
    try {
      BufferedReader buffer = new BufferedReader(new FileReader(file));
      String l = buffer.readLine();
      while ((l = buffer.readLine()) != null) {
        String[] star = l.split(",");
        if (star.length == 5 ) {
          this.dataset.add(Arrays.asList(star));
        } else {
          throw new Exception("ERROR: incorrectly formatted data");
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
      System.out.println("ERROR: " + e);
    }

    for (List<String> star : this.dataset) {
      try {
        this.positions.put(star.get(1), new double[] {Double.parseDouble(star.get(2)),
            Double.parseDouble(star.get(2)),
            Double.parseDouble(star.get(2))});
      } catch (Exception e) {
        e.printStackTrace();
        System.out.println("ERROR: star position not a valid number");
      }
    }
  }

  /**
   * gets Position array from star name.
   * @param name - name of star (string format)
   * @return double array of xyz coordinates
   */
  public double[] getPosition(String name) {
    try {
      return this.positions.get(name);
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
  public List<Integer> getNeighborsFromPosition(int k, double[] pos) {
    List<Integer> neighbors = new ArrayList<>();
    List<List<Double>> distances = new ArrayList<>();
    for (List<String> star : this.dataset) {
      List<Double> pair = new ArrayList<>();
      pair.add(Double.parseDouble(star.get(0)));
      double distance = getEuclideanDistance(pos, getPosition(star.get(1)));
      pair.add(distance);
      distances.add(pair);
    }
    Comparator<List<Double>> comparator = (l1, l2) -> {
      if (l1.get(1) > l2.get(1)) {
        return 1;
      } else {
        return 0;
      }
    };
    distances.sort(comparator);
    for (int i = 0; i < k; i++) {
      neighbors.add(distances.get(i).get(1).intValue());
    }
    return neighbors;
  }

  /**
   * naive KNN implementation given starting star.
   * @param k - number of stars to return
   * @param name - name of star to search around
   * @return - list of k star IDs that are closest to the given star
   */
  public List<Integer> getNeighborsFromStar(int k, String name) {
    double[] pos = getPosition(name);
    return getNeighborsFromPosition(k, pos);
  }

  public double getEuclideanDistance(double[] a, double[] b) {
    return Math.sqrt(Math.pow((a[0] - b[0]), 2)
        + Math.pow((a[1] - b[1]), 2)
        + Math.pow((a[2] - b[2]), 2));
  }

}
