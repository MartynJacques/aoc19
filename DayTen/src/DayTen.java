import java.io.BufferedReader;
import java.io.FileReader;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class DayTen {

  public static class Position {
    public int x;
    public int y;

    public Position(int x, int y) {
      this.x = x;
      this.y = y;
    }

    @Override
    public int hashCode() {
      int tmp = (y + ((x + 1) / 2));
      return x + (tmp * tmp);
    }

    @Override
    public boolean equals(Object obj) {
      if (this == obj)
        return true;
      if (obj == null)
        return false;
      if (getClass() != obj.getClass())
        return false;
      Position other = (Position) obj;
      return this.x == other.x && this.y == other.y;
    }

    public BigDecimal angleFromXClockwise(Position p) {
      Position c = new Position(this.x, p.y);
      double angle;
      if (p.x == this.x) {
        if (this.y < p.y) {
          angle = 180;
        } else {
          angle = 360;
        }
      } else if (p.y == this.y) {
        if (this.x < p.x) {
          angle = 270;
        } else {
          angle = 90;
        }
      } else {
        double hd = Math.sqrt(Math.pow(p.x - this.x, 2) + Math.pow(p.y - this.y, 2));
        double od = Math.sqrt(Math.pow(c.x - p.x, 2) + Math.pow(c.y - p.y, 2));
        angle = Math.toDegrees(Math.asin(od / hd));
        if (p.x > this.x && p.y > this.y) {
          angle = 180 + angle;
        }
        if (p.x > this.x && p.y < this.y) {
          angle = 270 + angle;
        }
        if (p.x < this.x && p.y > this.y) {
          angle = 90 + angle;
        }
      }
      return new BigDecimal(angle).setScale(2, BigDecimal.ROUND_HALF_UP);
    }

    @Override
    public String toString() {
      return "(" + x + ", " + y + ")";
    }
  }

  public static char[][] getGridFromInput() throws Exception {
    BufferedReader br = new BufferedReader(
        new FileReader("/Users/marjac01/Development/AdventOfCode/DayTen/src/input.txt"));
    ArrayList<String> lines = new ArrayList<String>();
    String line = null;
    while ((line = br.readLine()) != null) {
      lines.add(line);
    }
    br.close();

    char[][] grid = new char[lines.size()][lines.get(0).length()];
    for (int i = 0; i < lines.size(); i++) {
      for (int j = 0; j < lines.get(0).length(); j++) {
        grid[i][j] = lines.get(i).charAt(j);
      }
    }
    return grid;
  }

  public static void printGrid(char[][] grid) {
    for (char[] ca : grid) {
      for (char c : ca) {
        System.out.print(c);
      }
      System.out.println();
    }
  }

  public static ArrayList<Position> getSatellitePositions(char[][] grid) {
    ArrayList<Position> s = new ArrayList<>();
    for (int i = 0; i < grid.length; i++) {
      for (int j = 0; j < grid[0].length; j++) {
        if (grid[i][j] == '#') {
          s.add(new Position(i, j));
        }
      }
    }
    return s;
  }

  /*
   * For each satelite, find the angle between itself and every other satelite 
   * any duplicate angles, those asteroids can be ignored, so we use a set to 
   * add angles to, and return the size of the set
   * i.e. num satellites == num of unique angles
   * find the max 
   */
  public static int findMaxSatellite(ArrayList<Position> s) {
    int max = Integer.MIN_VALUE;
    for (Position p : s) {
      Set<BigDecimal> gradients = new HashSet<>();
      for (Position otherP : s) {
        if (!p.equals(otherP)) {
          BigDecimal gradient = p.angleFromXClockwise(otherP);
          gradients.add(gradient);
        }
      }
      if (gradients.size() > max) {
        max = gradients.size();
      }
    }
    return max;
  }

  public static Position findBestSatellite(ArrayList<Position> s) {
    int max = Integer.MIN_VALUE;
    Position bestP = null;
    for (Position p : s) {
      Set<BigDecimal> gradients = new HashSet<>();
      for (Position otherP : s) {
        if (!p.equals(otherP)) {
          BigDecimal gradient = p.angleFromXClockwise(otherP);
          gradients.add(gradient);
        }
      }
      if (gradients.size() > max) {
        max = gradients.size();
        bestP = p;
      }
    }
    return bestP;
  }

  public static Position findClosestPosition(Position origin, ArrayList<Position> positions) {
    double min = Double.MAX_VALUE;
    Position closest = null;
    for (Position p : positions) {
      double dist = Math.sqrt(Math.pow(origin.x - p.x, 2) + Math.pow(origin.y - p.y, 2));
      if (dist < min) {
        min = dist;
        closest = p;
      }
    }
    return closest;
  }

  public static Position find200Vapourised(Position p, ArrayList<Position> other) {
    HashMap<BigDecimal, ArrayList<Position>> gradientPositions = new HashMap<>();
    for (Position otherP : other) {
      if (!p.equals(otherP)) {
        BigDecimal gradient = p.angleFromXClockwise(otherP);
        if (gradientPositions.containsKey(gradient)) {
          gradientPositions.get(gradient).add(otherP);
        } else {
          ArrayList<Position> pList = new ArrayList<>();
          pList.add(otherP);
          gradientPositions.put(gradient, pList);
        }
      }
    }

    //    gradientPositions.entrySet().forEach(entry -> {
    //      System.out.println(entry.getKey() + " " + entry.getValue());
    //    });
    ArrayList<BigDecimal> gList = new ArrayList<>();
    for (BigDecimal bd : gradientPositions.keySet()) {
      gList.add(bd);
    }
    Collections.sort(gList);

    int index = 0;
    for (int i = 0; i < gList.size(); i++) {
      if (gList.get(i).doubleValue() == 90) {
        index = i;
      }
    }
    int removed = 0;
    int i = index;
    while (!gradientPositions.isEmpty()) {
      if (gradientPositions.containsKey(gList.get(i))) {
        ArrayList<Position> positionsAtAngle = gradientPositions.get(gList.get(i));
        if (positionsAtAngle.isEmpty()) {
          continue;
        }
        Position closest = findClosestPosition(p, positionsAtAngle);
        positionsAtAngle.remove(closest);
        removed++;
        if (removed == 200) {
          return closest;
        }
        if (positionsAtAngle.isEmpty()) {
          gradientPositions.remove(gList.get(i));
        }
      }
      i++;
      if (i == gList.size()) {
        i = 0;
      }
    }
    return null;
  }

  public static void partOne() throws Exception {
    char[][] grid = getGridFromInput();
    ArrayList<Position> s = getSatellitePositions(grid);
    //    System.out.println(Arrays.toString(s.toArray()));
    int maxAsteroidsDetectable = findMaxSatellite(s);
    Position bestP = findBestSatellite(s);
    System.out.println(maxAsteroidsDetectable);
    System.out.println(bestP);
  }

  public static void partTwo() throws Exception {
    char[][] grid = getGridFromInput();
    ArrayList<Position> s = getSatellitePositions(grid);
    Position bestP = findBestSatellite(s);
    Position vaporised200th = find200Vapourised(bestP, s);
    System.out.println((100 * vaporised200th.x) + vaporised200th.y);
  }

  public static void main(String[] args) throws Exception {
    //    partOne();
    partTwo();
  }
}
