import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
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

    @Override
    public String toString() {
      return "(" + x + ", " + y + ")";
    }
  }

  public static double gradient(Position a, Position b) {
    int yDiff = a.y - b.y;
    int xDiff = a.x - b.x;
    return (double) yDiff / xDiff;
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
   * max = int.min
   * bestSatelite = null
   * For each satelite, s
   *    for every other satelite, s'
   *        line = line between s and s' 
   *        find gradient of line
   *        add to hash map (gradient : sateliteList)
   *    numVis = number of visible satelites is size(hashmap)
   *    because only one satelite is visible that has the same gradient
   *    because one satelite is blocking the rest
   *    this will be the one closest to s, but we don't need to know that
   *    at least for part one
   *    if numVis > max
   *        max = numVis
   *        bestSatelite = s
   *        
   *    The number of visible satelites is the number of unique gradients 
   *    between current satelite and other satelites.
   */
  public static int findBestSatellite(ArrayList<Position> s) {
    int max = Integer.MIN_VALUE;
    for (Position p : s) {
      Set<Double> gradients = new HashSet<>();
      for (Position otherP : s) {
        if (p.equals(otherP))
          break;
        double gradient = gradient(p, otherP);
        gradients.add(gradient);
      }
      if (gradients.size() > max) {
        max = gradients.size();
      }
    }
    return max;
  }

  public static void partOne() throws Exception {
    char[][] grid = getGridFromInput();
    ArrayList<Position> s = getSatellitePositions(grid);
    System.out.println(Arrays.toString(s.toArray()));
    int maxAsteroidsDetectable = findBestSatellite(s);
    System.out.println(maxAsteroidsDetectable);
  }

  public static void main(String[] args) throws Exception {
    partOne();
  }
}
