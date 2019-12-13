import java.util.Arrays;
import java.util.HashMap;

//    // Initialise grid
//    char[][] grid = new char[5][5];
//    for (int i = 0; i < grid.length; i++) {
//      for (int j = 0; j < grid[i].length; j++) {
//        grid[i][j] = '.';
//      }
//    }

//    testMap.entrySet().forEach(entry -> {
//      System.out.println(entry.getKey() + " " + entry.getValue());
//    });

public class DayEleven {

  public static final long BLACK = 0;
  public static final long WHITE = 1;
  public static final long TURN_LEFT = 0;
  public static final long TURN_RIGHT = 1;

  public enum Direction {
    UP, RIGHT, LEFT, DOWN;

    public static Direction turnRight(Direction curDir) {
      switch (curDir) {
        case UP:
          return RIGHT;
        case RIGHT:
          return DOWN;
        case DOWN:
          return LEFT;
        case LEFT:
          return UP;
        default:
          System.err.println("Unknown direction " + curDir);
          System.exit(1);
      }
      return curDir;
    }

    public static Direction turnLeft(Direction curDir) {
      switch (curDir) {
        case UP:
          return LEFT;
        case RIGHT:
          return UP;
        case DOWN:
          return RIGHT;
        case LEFT:
          return DOWN;
        default:
          System.err.println("Unknown direction " + curDir);
          System.exit(1);
      }
      return curDir;
    }
  }

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

    public void move(Direction d) {
      switch (d) {
        case UP:
          this.x -= 1;
          break;
        case RIGHT:
          this.y += 1;
          break;
        case DOWN:
          this.x += 1;
          break;
        case LEFT:
          this.y -= 1;
          break;
        default:
          System.err.println("Unknown direction " + d);
          System.exit(1);
      }
    }
  }

  /*
   * For each axis (x and y), we find the min and the max. Assuming the min is negative,
   * taking the absolute gives us the translation required to get the origin. The origin
   * is 0,0. The length of each axis is max + abs(min), max - min? 
   * Create the grid using these deminsions, then fill each panel with black (default).
   * Given the HashMap of colours position : colour. Loop through the hash map, translate 
   * the position by the min x and min y, and set the colour. Then print.
   */
  public static void printGrid(HashMap<Position, Long> panels) {
    int maxX = Integer.MIN_VALUE;
    int minX = Integer.MAX_VALUE;
    int maxY = Integer.MIN_VALUE;
    int minY = Integer.MAX_VALUE;
    for (Position p : panels.keySet()) {
      if (p.x > maxX) {
        maxX = p.x;
      }
      if (p.x < minX) {
        minX = p.x;
      }
      if (p.y > maxY) {
        maxY = p.y;
      }
      if (p.y < minY) {
        minY = p.y;
      }
    }
    int xOrigin = Math.abs(minX);
    int yOrigin = Math.abs(minY);
    char[][] grid = new char[xOrigin + maxX + 1][yOrigin + maxY + 1];
    // Fill each row with black 
    for (char[] row : grid)
      Arrays.fill(row, '.');

    for (Position p : panels.keySet()) {
      char colour = panels.get(p) == 0 ? '.' : '#';
      grid[p.x + xOrigin][p.y + yOrigin] = colour;
    }

    for (char[] ca : grid) {
      for (char c : ca) {
        System.out.print(c);
      }
      System.out.println();
    }
  }

  public static void partTwo(long[] program) {
    HashMap<Position, Long> panels = new HashMap<>();

    // Initialise, start at 0,0 facing UP
    Position p = new Position(0, 0);
    Direction d = Direction.UP;
    IntCode robot = new IntCode(program);
    panels.put(new Position(0, 0), (long) 1);
    while (true) {
      // Get colour of current panel, default is black, white = 1, black = 0
      long currCol = panels.containsKey(p) ? panels.get(p) : BLACK;

      // Get the colour the panel should be painted with
      robot.inputs.add(currCol);
      robot.run();
      if (robot.halted)
        break; // Halt signal, exit the loop

      // Put a new position in the map, to avoid a pointer to the same object
      panels.put(new Position(p.x, p.y), robot.output);

      // Get the next rotation, right = 1, left = 0
      robot.inputs.add(currCol);
      robot.run();
      if (robot.halted)
        break; // Halt signal, exit the loop
      d = robot.output == TURN_LEFT ? Direction.turnLeft(d) : Direction.turnRight(d);

      // Move to next panel, i.e. move forward by one in new direction
      p.move(d);
    }
    printGrid(panels);
  }

  public static void partOne(long[] program) {
    HashMap<Position, Long> panels = new HashMap<>();

    // Initialise, start at 0,0 facing UP
    Position p = new Position(0, 0);
    Direction d = Direction.UP;
    IntCode robot = new IntCode(program);
    while (true) {
      // Get colour of current panel, default is black, white = 1, black = 0
      long currCol = panels.containsKey(p) ? panels.get(p) : BLACK;

      // Get the colour the panel should be painted with
      robot.inputs.add(currCol);
      robot.run();
      if (robot.halted)
        break; // Halt signal, exit the loop

      // Put a new position in the map, to avoid a pointer to the same object
      panels.put(new Position(p.x, p.y), robot.output);

      // Get the next rotation, right = 1, left = 0
      robot.inputs.add(currCol);
      robot.run();
      if (robot.halted)
        break; // Halt signal, exit the loop
      d = robot.output == TURN_LEFT ? Direction.turnLeft(d) : Direction.turnRight(d);

      // Move to next panel, i.e. move forward by one in new direction
      p.move(d);
    }
    System.out.println(panels.size());
  }

  public static void main(String[] args) {
    String[] input =
        "3,8,1005,8,330,1106,0,11,0,0,0,104,1,104,0,3,8,102,-1,8,10,101,1,10,10,4,10,1008,8,0,10,4,10,102,1,8,29,2,9,4,10,1006,0,10,1,1103,17,10,3,8,102,-1,8,10,101,1,10,10,4,10,108,0,8,10,4,10,101,0,8,61,1006,0,21,1006,0,51,3,8,1002,8,-1,10,101,1,10,10,4,10,108,1,8,10,4,10,1001,8,0,89,1,102,19,10,1,1107,17,10,1006,0,18,3,8,1002,8,-1,10,1001,10,1,10,4,10,1008,8,1,10,4,10,1001,8,0,123,1,9,2,10,2,1105,10,10,2,103,9,10,2,1105,15,10,3,8,102,-1,8,10,1001,10,1,10,4,10,1008,8,0,10,4,10,102,1,8,161,3,8,102,-1,8,10,101,1,10,10,4,10,108,1,8,10,4,10,101,0,8,182,3,8,1002,8,-1,10,101,1,10,10,4,10,1008,8,0,10,4,10,101,0,8,205,2,1102,6,10,1006,0,38,2,1007,20,10,2,1105,17,10,3,8,102,-1,8,10,1001,10,1,10,4,10,108,1,8,10,4,10,1001,8,0,241,3,8,102,-1,8,10,101,1,10,10,4,10,108,1,8,10,4,10,101,0,8,263,1006,0,93,2,5,2,10,2,6,7,10,3,8,102,-1,8,10,101,1,10,10,4,10,108,0,8,10,4,10,1001,8,0,296,1006,0,81,1006,0,68,1006,0,76,2,4,4,10,101,1,9,9,1007,9,1010,10,1005,10,15,99,109,652,104,0,104,1,21102,825594262284,1,1,21102,347,1,0,1105,1,451,21101,0,932855939852,1,21101,358,0,0,1106,0,451,3,10,104,0,104,1,3,10,104,0,104,0,3,10,104,0,104,1,3,10,104,0,104,1,3,10,104,0,104,0,3,10,104,0,104,1,21102,1,235152649255,1,21101,405,0,0,1105,1,451,21102,235350879235,1,1,21102,416,1,0,1106,0,451,3,10,104,0,104,0,3,10,104,0,104,0,21102,988757512972,1,1,21101,439,0,0,1106,0,451,21102,1,988669698828,1,21101,0,450,0,1106,0,451,99,109,2,22101,0,-1,1,21102,40,1,2,21102,1,482,3,21102,472,1,0,1106,0,515,109,-2,2105,1,0,0,1,0,0,1,109,2,3,10,204,-1,1001,477,478,493,4,0,1001,477,1,477,108,4,477,10,1006,10,509,1101,0,0,477,109,-2,2106,0,0,0,109,4,1202,-1,1,514,1207,-3,0,10,1006,10,532,21102,1,0,-3,21202,-3,1,1,21202,-2,1,2,21102,1,1,3,21102,1,551,0,1106,0,556,109,-4,2105,1,0,109,5,1207,-3,1,10,1006,10,579,2207,-4,-2,10,1006,10,579,22101,0,-4,-4,1105,1,647,21201,-4,0,1,21201,-3,-1,2,21202,-2,2,3,21102,598,1,0,1105,1,556,21202,1,1,-4,21101,0,1,-1,2207,-4,-2,10,1006,10,617,21102,1,0,-1,22202,-2,-1,-2,2107,0,-3,10,1006,10,639,21202,-1,1,1,21102,1,639,0,105,1,514,21202,-2,-1,-2,22201,-4,-2,-4,109,-5,2105,1,0"
            .split(",");
    long[] program = Arrays.stream(input).mapToLong(Long::parseLong).toArray();
    //    partOne(program);
    partTwo(program);
  }
}
