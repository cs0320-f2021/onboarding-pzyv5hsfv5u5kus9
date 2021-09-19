package edu.brown.cs.student.main;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;



public class Stars {
  private List<List<String>> dataset = new ArrayList<>();
  private HashMap<String, double[]> positions = new HashMap<>();

  public List<List<String>> getDataset() {
    return this.dataset;
  }

  public HashMap<String, double[]> getPositions() {
    return this.positions;
  }

  /**
   * Reads a csv file and stores it into both dataset and positions.
   * @param file - csv file to read into the database
   */
  public void storeStars(String file) {
    try {
      BufferedReader buffer = new BufferedReader(new FileReader(file));
      buffer.readLine();
      String l;
      List<List<String>> temp = new ArrayList<>();
      while ((l = buffer.readLine()) != null) {
        String[] star = l.split(",");
        if (star[1].equals("")) {
          star[1] = star[0] + "_star";
        }
        if (star.length == 5) {
          temp.add(Arrays.asList(star));
        } else {
          throw new Exception("ERROR: incorrectly formatted data");
        }
      }
      this.dataset = temp;
    } catch (Exception e) {
      e.printStackTrace();
      System.out.println("ERROR: " + e);
    }

    for (List<String> star : this.dataset) {
      try {
        this.positions.put(star.get(1), new double[]{Double.parseDouble(star.get(2)),
            Double.parseDouble(star.get(3)),
            Double.parseDouble(star.get(4))});
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
    return this.positions.getOrDefault(name, null);
  }

  /**
   * naive KNN implementation given starting position.
   * @param k - number of stars to return
   * @param pos - starting position
   * @return list of k star IDs that are closest to position
   */
  public List<Integer> getNeighborsFromPosition(int k, double[] pos) {
    if (k > dataset.size()) {
      return getNeighborsFromPosition(dataset.size(), pos);
    }
    List<Integer> neighbors = new ArrayList<>();
    if (k == 0) {
      return neighbors;
    }
    List<List<Double>> distances = new ArrayList<>();
    for (List<String> star : this.dataset) {
      if (!Arrays.equals(getPosition(star.get(1)), pos)) {
        List<Double> pair = new ArrayList<>();
        pair.add(Double.parseDouble(star.get(0)));
        double distance = getEuclideanDistance(pos, getPosition(star.get(1)));
        pair.add(distance);
        distances.add(pair);
      }
    }


    distances.sort((o1, o2) -> {
      if (o1.get(1) > o2.get(1)) {
        return 1;
      }
      if (o1.get(1) < o2.get(1)) {
        return -1;
      }
      if (o1.get(1).equals(o2.get(1))) {
        return 0;
      }
      return 0;
    });
    for (int i = 0; i < k; i++) {
      int id = distances.get(i).get(0).intValue();
      neighbors.add(id);
    }
    return neighbors;
  }

  /**
   * naive KNN implementation given starting star.
   * @param k - number of stars to return
   * @param name - name of star to search around
   * @return - list of k star IDs that are closest to the given star
   */
  public List<Integer> getNeighborsFromStar(int k, String name) throws Exception {
    name = name.replaceAll("^\"|\"$", "");
    double[] pos = getPosition(name);
    if (pos == null) {
      throw new Exception("star name not in database");
    } else {
      return getNeighborsFromPosition(k, pos);
    }
  }

  public double getEuclideanDistance(double[] a, double[] b) {
    return Math.sqrt(Math.pow((a[0] - b[0]), 2)
        + Math.pow((a[1] - b[1]), 2)
        + Math.pow((a[2] - b[2]), 2));
  }
}
