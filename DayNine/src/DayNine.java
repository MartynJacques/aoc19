import java.util.Arrays;
import java.util.Scanner;

/*
 *  Add support for relative mode, 2
 *  Add support for opcode 9, which adjusts the relative base
 *  Increase total memory available to intcode
 *  Support large numbers (long)
 */

public class DayNine {
  public static final int POS = 0; // Position mode
  public static final int IMM = 1; // Immediate mode
  public static final int REL = 2; // Relative mode

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

  public static void run(long[] program) {
    int pc = 0;
    long[] memory = new long[program.length * 100];
    for (int i = 0; i < program.length; i++) {
      memory[i] = program[i];
    }
    program = memory;
    int rb = 0;
    while (pc < program.length) {
      Instruction in = new Instruction(Long.toString(program[pc]));
      String opcode = in.opcode;
      if (opcode.contentEquals("99")) {
        //        System.out.println("Instruction 99, halting.");
        return;
      }
      int[] modes = in.modes;
      int[] addresses = getAddresses(modes, pc, program, rb);
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
        pc += 4;
      } else if (in.getNumOperands() == 2) {
        // Two operands
        int firstParamAdd = addresses[0];
        int secondParamAdd = addresses[1];
        if (opcode.contentEquals("05")) {
          if (program[firstParamAdd] != 0) {
            pc = (int) program[secondParamAdd];
          } else {
            pc += 3;
          }
        } else if (opcode.contentEquals("06")) {
          if (program[firstParamAdd] == 0) {
            pc = (int) program[secondParamAdd];
          } else {
            pc += 3;
          }
        }
      } else if (in.getNumOperands() == 1) {
        // One operand
        if (opcode.contentEquals("03")) {
          // Input
          Scanner myInput = new Scanner(System.in);
          System.out.print("Enter input: ");
          int userInput = myInput.nextInt();
          program[addresses[0]] = userInput;
          pc += 2;
          myInput.close();
        } else if (opcode.contentEquals("04")) {
          // Output 
          System.out.println(program[addresses[0]]);
          pc += 2;
        } else if (opcode.contentEquals("09")) {
          // Output 
          rb += program[addresses[0]];
          pc += 2; 
        }
      } else {
        // Error
        System.out.println("Unknown instruction " + opcode + ". Exiting.");
        System.exit(1);
      }
    }
  }

  public static void main(String[] args) {
    String[] input =
        "1102,34463338,34463338,63,1007,63,34463338,63,1005,63,53,1102,1,3,1000,109,988,209,12,9,1000,209,6,209,3,203,0,1008,1000,1,63,1005,63,65,1008,1000,2,63,1005,63,902,1008,1000,0,63,1005,63,58,4,25,104,0,99,4,0,104,0,99,4,17,104,0,99,0,0,1102,1,37,1007,1102,24,1,1006,1102,26,1,1012,1101,528,0,1023,1102,256,1,1027,1102,466,1,1029,1102,1,629,1024,1101,0,620,1025,1101,0,0,1020,1102,1,30,1004,1101,39,0,1003,1102,36,1,1005,1102,531,1,1022,1102,32,1,1019,1101,0,27,1000,1101,0,28,1016,1101,1,0,1021,1101,23,0,1013,1102,1,25,1015,1102,1,21,1008,1102,1,22,1018,1102,1,34,1014,1102,475,1,1028,1101,33,0,1002,1101,0,35,1011,1102,1,20,1009,1102,38,1,1017,1101,259,0,1026,1101,31,0,1010,1101,0,29,1001,109,8,21102,40,1,10,1008,1018,40,63,1005,63,203,4,187,1105,1,207,1001,64,1,64,1002,64,2,64,109,7,21108,41,41,0,1005,1015,225,4,213,1106,0,229,1001,64,1,64,1002,64,2,64,109,1,1205,5,247,4,235,1001,64,1,64,1105,1,247,1002,64,2,64,109,20,2106,0,-9,1105,1,265,4,253,1001,64,1,64,1002,64,2,64,109,-38,1202,4,1,63,1008,63,33,63,1005,63,291,4,271,1001,64,1,64,1106,0,291,1002,64,2,64,109,6,2102,1,0,63,1008,63,29,63,1005,63,315,1001,64,1,64,1106,0,317,4,297,1002,64,2,64,109,10,21102,42,1,5,1008,1019,40,63,1005,63,341,1001,64,1,64,1105,1,343,4,323,1002,64,2,64,109,-13,2101,0,5,63,1008,63,24,63,1005,63,365,4,349,1105,1,369,1001,64,1,64,1002,64,2,64,109,7,1202,-6,1,63,1008,63,36,63,1005,63,389,1105,1,395,4,375,1001,64,1,64,1002,64,2,64,109,1,2107,31,-5,63,1005,63,411,1106,0,417,4,401,1001,64,1,64,1002,64,2,64,109,3,1206,8,431,4,423,1105,1,435,1001,64,1,64,1002,64,2,64,109,-8,2108,31,0,63,1005,63,451,1105,1,457,4,441,1001,64,1,64,1002,64,2,64,109,26,2106,0,-2,4,463,1001,64,1,64,1106,0,475,1002,64,2,64,109,-33,1207,6,38,63,1005,63,491,1106,0,497,4,481,1001,64,1,64,1002,64,2,64,109,3,2108,27,0,63,1005,63,515,4,503,1105,1,519,1001,64,1,64,1002,64,2,64,109,23,2105,1,0,1106,0,537,4,525,1001,64,1,64,1002,64,2,64,109,-30,1207,7,28,63,1005,63,559,4,543,1001,64,1,64,1106,0,559,1002,64,2,64,109,20,21101,43,0,0,1008,1013,43,63,1005,63,581,4,565,1105,1,585,1001,64,1,64,1002,64,2,64,109,-14,2102,1,1,63,1008,63,27,63,1005,63,611,4,591,1001,64,1,64,1105,1,611,1002,64,2,64,109,18,2105,1,7,4,617,1001,64,1,64,1106,0,629,1002,64,2,64,109,13,1206,-9,641,1105,1,647,4,635,1001,64,1,64,1002,64,2,64,109,-18,21107,44,45,-1,1005,1011,665,4,653,1105,1,669,1001,64,1,64,1002,64,2,64,109,-2,2107,28,-9,63,1005,63,687,4,675,1106,0,691,1001,64,1,64,1002,64,2,64,1205,10,701,1106,0,707,4,695,1001,64,1,64,1002,64,2,64,109,-6,1201,2,0,63,1008,63,21,63,1005,63,731,1001,64,1,64,1106,0,733,4,713,1002,64,2,64,109,-5,1208,7,23,63,1005,63,753,1001,64,1,64,1105,1,755,4,739,1002,64,2,64,109,16,1208,-8,37,63,1005,63,777,4,761,1001,64,1,64,1106,0,777,1002,64,2,64,109,3,21107,45,44,-8,1005,1010,797,1001,64,1,64,1105,1,799,4,783,1002,64,2,64,109,-8,1201,-5,0,63,1008,63,36,63,1005,63,821,4,805,1106,0,825,1001,64,1,64,1002,64,2,64,109,-9,2101,0,1,63,1008,63,31,63,1005,63,845,1105,1,851,4,831,1001,64,1,64,1002,64,2,64,109,6,21108,46,49,3,1005,1010,867,1106,0,873,4,857,1001,64,1,64,1002,64,2,64,109,5,21101,47,0,7,1008,1019,44,63,1005,63,897,1001,64,1,64,1106,0,899,4,879,4,64,99,21101,27,0,1,21102,913,1,0,1106,0,920,21201,1,30449,1,204,1,99,109,3,1207,-2,3,63,1005,63,962,21201,-2,-1,1,21101,940,0,0,1105,1,920,21202,1,1,-1,21201,-2,-3,1,21102,1,955,0,1106,0,920,22201,1,-1,-2,1105,1,966,22102,1,-2,-2,109,-3,2105,1,0"
            .split(",");
    long[] program = Arrays.stream(input).mapToLong(Long::parseLong).toArray();
    run(program);
  }
}
