import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

public class DaySix {

  /* Adjacency matrix representation */
  public static class Graph {
    public Map<String, ArrayList<String>> satelites;

    Graph() {
      satelites = new HashMap<>();
    }

    public void add(String planet, String satellite) {
      satelites.putIfAbsent(planet, new ArrayList<>());
      satelites.putIfAbsent(satellite, new ArrayList<>());

      // Satellite orbits planet
      satelites.get(planet).add(satellite);
    }

    public void addCyclic(String planet, String satellite) {
      satelites.putIfAbsent(planet, new ArrayList<>());
      satelites.putIfAbsent(satellite, new ArrayList<>());

      // Satellite orbits planet
      satelites.get(planet).add(satellite);
      satelites.get(satellite).add(planet);
    }

    int getNumChildren(String root) {
      Set<String> visited = new LinkedHashSet<String>();
      Stack<String> stack = new Stack<String>();
      stack.push(root);
      while (!stack.isEmpty()) {
        String vertex = stack.pop();
        if (!visited.contains(vertex)) {
          visited.add(vertex);
          for (String s : this.satelites.get(vertex)) {
            stack.push(s);
          }
        }
      }
      return visited.size() - 1;
    }

    int findShortestRoute(String start, String end) {
      Map<String, Boolean> vis = new HashMap<String, Boolean>();
      Map<String, String> prev = new HashMap<String, String>();
      LinkedList<String> directions = new LinkedList<>();
      LinkedList<String> q = new LinkedList<>();
      String current = start;
      q.add(current);
      vis.put(current, true);
      while (!q.isEmpty()) {
        current = q.remove();
        if (current.equals(end)) {
          break;
        } else {
          for (String node : this.satelites.get(current)) {
            if (!vis.keySet().contains(node)) {
              q.add(node);
              vis.put(node, true);
              prev.put(node, current);
            }
          }
        }
      }
      if (!current.equals(end)) {
        System.out.println("can't reach destination");
      }
      for (String node = end; node != null; node = prev.get(node)) {
        directions.add(node);
      }
      System.out.println(Arrays.deepToString(directions.toArray()));
      return directions.size() - 3;
    }
  }

  public static void partOne() throws Exception {
    BufferedReader br = new BufferedReader(
        new FileReader("/Users/marjac01/Development/AdventOfCode/DaySix/src/input.txt"));
    String line;
    Graph orbitGraph = new Graph();
    while ((line = br.readLine()) != null) {
      String[] planets = line.split("\\)");
      String planet = planets[0];
      String satelite = planets[1];
      orbitGraph.add(planet, satelite);
    }
    br.close();

    int total = 0;
    for (Map.Entry<String, ArrayList<String>> entry : orbitGraph.satelites.entrySet()) {
      System.out.println(entry.getKey() + ": " + Arrays.toString(entry.getValue().toArray()));
      total += orbitGraph.getNumChildren(entry.getKey());
    }
    System.out.println(total);
  }

  public static void partTwo() throws Exception {
    // Part two
    //    String start = "NWW";
    //    String end = "TS7";
    String start = "YOU";
    String end = "SAN";
    BufferedReader br = new BufferedReader(
        new FileReader("/Users/marjac01/Development/AdventOfCode/DaySix/src/input.txt"));
    String line;
    Graph orbitGraph = new Graph();
    while ((line = br.readLine()) != null) {
      String[] planets = line.split("\\)");
      String planet = planets[0];
      String satelite = planets[1];
      orbitGraph.addCyclic(planet, satelite);
    }
    br.close();

    System.out.println(orbitGraph.findShortestRoute(start, end));
  }

  public static void main(String[] args) throws Exception {
    partTwo();
  }
}
