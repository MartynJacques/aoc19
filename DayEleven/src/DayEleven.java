import java.util.Arrays;
import java.util.HashMap;

//    // Initialise grid
//    char[][] grid = new char[5][5];
//    for (int i = 0; i < grid.length; i++) {
//      for (int j = 0; j < grid[i].length; j++) {
//        grid[i][j] = '.';
//      }
//    }

public class DayEleven {
  public static final int POS = 0; // Position mode
  public static final int IMM = 1; // Immediate mode
  public static final int REL = 2; // Relative mode
  
  public static final long BLACK = 0;
  public static final long WHITE = 1;
  public static final long TURN_LEFT = 0;
  public static final long TURN_RIGHT = 1;

  public static class IntCodeParams {
    public long result = 0;
    public int pc = 0;
    public int rb = 0;
    public boolean hasHalted = false;
  }

  public static class Instruction {
    public String opcode;
    public int[] modes;

    public Instruction(String instStr) {
      String strModes = null;
      if (instStr.length() == 1) {
        opcode = "0" + instStr;
      } else {
        opcode = instStr.substring(instStr.length() - 2, instStr.length());
        strModes = instStr.substring(0, instStr.length() - 2);
      }
      modes = setModes(opcode, strModes);
    }

    public int[] setModes(String opcode, String modesStr) {
      if (opcode.contentEquals("99")) {
        return null;
      }
      int[] modes = new int[getNumOperands()];
      if (modesStr == null)
        return modes;
      for (int i = modesStr.length() - 1; i >= 0; i--) {
        modes[modesStr.length() - 1 - i] = Character.getNumericValue(modesStr.charAt(i));
      }
      return modes;
    }

    public int getNumOperands() {
      switch (opcode) {
        case "01":
        case "02":
        case "07":
        case "08":
          return 3;
        case "05":
        case "06":
          return 2;
        case "03":
        case "04":
        case "09":
          return 1;
        case "99":
        default:
          System.err.println("Somethings gone wrong in getNumOperands. Opcode: " + opcode);
          return -1;
      }
    }
  }


  public static int[] getAddresses(int[] modes, int pc, long[] program, int rb) {
    int[] addresses = new int[modes.length];
    for (int i = 0; i < modes.length; i++) {
      int mode = modes[i];
      switch (mode) {
        case POS:
          addresses[i] = (int) program[pc + i + 1];
          break;
        case IMM:
          addresses[i] = pc + i + 1;
          break;
        case REL:
          addresses[i] = (int) program[pc + i + 1] + rb;
          break;
        default:
          System.err.println("Somethings gone wrong in getAddresses. Unknown mode: " + mode);
          System.exit(1);
      }
    }
    return addresses;
  }

  public static void run(long[] program, long input, IntCodeParams icr) {
    // Initialise memory
    long[] memory = new long[program.length * 100];
    for (int i = 0; i < program.length; i++) {
      memory[i] = program[i];
    }
    program = memory;
    
    while (icr.pc < memory.length) {
      Instruction in = new Instruction(Long.toString(program[icr.pc]));
      String opcode = in.opcode;
      if (opcode.contentEquals("99")) {
        System.out.println("Instruction 99, halting.");
        icr.hasHalted = true;
        return;
      }
      int[] modes = in.modes;
      int[] addresses = getAddresses(modes, icr.pc, program, icr.rb);
      if (in.getNumOperands() == 3) {
        // Three operands
        int firstParamAdd = addresses[0];
        int secondParamAdd = addresses[1];
        int thirdParamAdd = addresses[2];

        if (opcode.contentEquals("01")) {
          program[thirdParamAdd] = program[firstParamAdd] + program[secondParamAdd];
        } else if (opcode.contentEquals("02")) {
          program[thirdParamAdd] = program[firstParamAdd] * program[secondParamAdd];
        } else if (opcode.contentEquals("07")) {
          if (program[firstParamAdd] < program[secondParamAdd]) {
            program[thirdParamAdd] = 1;
          } else {
            program[thirdParamAdd] = 0;
          }
        } else if (opcode.contentEquals("08")) {
          if (program[firstParamAdd] == program[secondParamAdd]) {
            program[thirdParamAdd] = 1;
          } else {
            program[thirdParamAdd] = 0;
          }
        }
        icr.pc += 4;
      } else if (in.getNumOperands() == 2) {
        // Two operands
        int firstParamAdd = addresses[0];
        int secondParamAdd = addresses[1];
        if (opcode.contentEquals("05")) {
          if (program[firstParamAdd] != 0) {
            icr.pc = (int) program[secondParamAdd];
          } else {
            icr.pc += 3;
          }
        } else if (opcode.contentEquals("06")) {
          if (program[firstParamAdd] == 0) {
            icr.pc = (int) program[secondParamAdd];
          } else {
            icr.pc += 3;
          }
        }
      } else if (in.getNumOperands() == 1) {
        // One operand
        if (opcode.contentEquals("03")) {
          // Input
          program[addresses[0]] = input;
          icr.pc += 2;
        } else if (opcode.contentEquals("04")) {
          // Output 
          icr.result = program[addresses[0]];
          icr.pc += 2;
          return;
        } else if (opcode.contentEquals("09")) {
          // Relative base adjust 
          icr.rb += program[addresses[0]];
          icr.pc += 2;
        }
      } else {
        // Error
        System.out.println("Unsupported number of operands " + in.getNumOperands() + "Opcode is "
            + opcode + ". Exiting.");
        System.exit(1);
      }
    }
  }

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

  public static void printGrid(char[][] grid) {
    for (char[] ca : grid) {
      for (char c : ca) {
        System.out.print(c);
      }
      System.out.println();
    }
  }

  public static void partOne(long[] program) {
    HashMap<Position, Long> panels = new HashMap<>();

    // Initialise, start at 0,0 facing UP
    Position p = new Position(0, 0);
    Direction d = Direction.UP;
    IntCodeParams icr = new IntCodeParams();
    while (true) {
      // Get colour of current panel, default is black, white = 1, black = 0
      long currCol = panels.containsKey(p) ? panels.get(p) : BLACK;

      // Get the colour the panel should be painted with
      run(program, currCol, icr);
      if (icr.hasHalted)
        break; // Halt signal, exit the loop

      // Paint the new colour i.e. store the new colour, white = 1, black = 0
      if (icr.result != BLACK && icr.result != WHITE) {
        System.err.println("Error with intcode output, unknown colour " + icr.result);
        System.exit(1);
      }
      panels.put(p, icr.result);

      // Get the next rotation, right = 1, left = 0
      run(program, currCol, icr);
      if (icr.hasHalted)
        break; // Halt signal, exit the loop
      if (icr.result != TURN_LEFT && icr.result != TURN_RIGHT) {
        System.err.println("Error with intcode output, unknown direction " + icr.result);
        System.exit(1);
      }
      d = icr.result == TURN_LEFT ? Direction.turnLeft(d) : Direction.turnRight(d);

      // Move to next panel, i.e. move forward by one in new direction
      p.move(d);
      //      panels.entrySet().forEach(entry -> {
      //        System.out.println(entry.getKey() + " " + entry.getValue());
      //      });
    }
    System.out.println(icr.result);
    System.out.println(panels.size());
  }


  public static void main(String[] args) {
    String[] input =
        "3,8,1005,8,330,1106,0,11,0,0,0,104,1,104,0,3,8,102,-1,8,10,101,1,10,10,4,10,1008,8,0,10,4,10,102,1,8,29,2,9,4,10,1006,0,10,1,1103,17,10,3,8,102,-1,8,10,101,1,10,10,4,10,108,0,8,10,4,10,101,0,8,61,1006,0,21,1006,0,51,3,8,1002,8,-1,10,101,1,10,10,4,10,108,1,8,10,4,10,1001,8,0,89,1,102,19,10,1,1107,17,10,1006,0,18,3,8,1002,8,-1,10,1001,10,1,10,4,10,1008,8,1,10,4,10,1001,8,0,123,1,9,2,10,2,1105,10,10,2,103,9,10,2,1105,15,10,3,8,102,-1,8,10,1001,10,1,10,4,10,1008,8,0,10,4,10,102,1,8,161,3,8,102,-1,8,10,101,1,10,10,4,10,108,1,8,10,4,10,101,0,8,182,3,8,1002,8,-1,10,101,1,10,10,4,10,1008,8,0,10,4,10,101,0,8,205,2,1102,6,10,1006,0,38,2,1007,20,10,2,1105,17,10,3,8,102,-1,8,10,1001,10,1,10,4,10,108,1,8,10,4,10,1001,8,0,241,3,8,102,-1,8,10,101,1,10,10,4,10,108,1,8,10,4,10,101,0,8,263,1006,0,93,2,5,2,10,2,6,7,10,3,8,102,-1,8,10,101,1,10,10,4,10,108,0,8,10,4,10,1001,8,0,296,1006,0,81,1006,0,68,1006,0,76,2,4,4,10,101,1,9,9,1007,9,1010,10,1005,10,15,99,109,652,104,0,104,1,21102,825594262284,1,1,21102,347,1,0,1105,1,451,21101,0,932855939852,1,21101,358,0,0,1106,0,451,3,10,104,0,104,1,3,10,104,0,104,0,3,10,104,0,104,1,3,10,104,0,104,1,3,10,104,0,104,0,3,10,104,0,104,1,21102,1,235152649255,1,21101,405,0,0,1105,1,451,21102,235350879235,1,1,21102,416,1,0,1106,0,451,3,10,104,0,104,0,3,10,104,0,104,0,21102,988757512972,1,1,21101,439,0,0,1106,0,451,21102,1,988669698828,1,21101,0,450,0,1106,0,451,99,109,2,22101,0,-1,1,21102,40,1,2,21102,1,482,3,21102,472,1,0,1106,0,515,109,-2,2105,1,0,0,1,0,0,1,109,2,3,10,204,-1,1001,477,478,493,4,0,1001,477,1,477,108,4,477,10,1006,10,509,1101,0,0,477,109,-2,2106,0,0,0,109,4,1202,-1,1,514,1207,-3,0,10,1006,10,532,21102,1,0,-3,21202,-3,1,1,21202,-2,1,2,21102,1,1,3,21102,1,551,0,1106,0,556,109,-4,2105,1,0,109,5,1207,-3,1,10,1006,10,579,2207,-4,-2,10,1006,10,579,22101,0,-4,-4,1105,1,647,21201,-4,0,1,21201,-3,-1,2,21202,-2,2,3,21102,598,1,0,1105,1,556,21202,1,1,-4,21101,0,1,-1,2207,-4,-2,10,1006,10,617,21102,1,0,-1,22202,-2,-1,-2,2107,0,-3,10,1006,10,639,21202,-1,1,1,21102,1,639,0,105,1,514,21202,-2,-1,-2,22201,-4,-2,-4,109,-5,2105,1,0"
            .split(",");
    long[] program = Arrays.stream(input).mapToLong(Long::parseLong).toArray();
    partOne(program);
  }
}
