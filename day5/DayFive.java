import java.util.Arrays;
import java.util.Scanner;

public class DayFive {
  public static boolean[] getImmModes(String opcode, int numParams) {
    if (opcode.length() == 1) return new boolean[numParams];
    boolean[] modes = new boolean[numParams];
    String modeStr = opcode.substring(0, opcode.length() - 2);
    for (int i = modeStr.length() - 1; i >= 0; i--) {
      if (modeStr.charAt(i) == '1') {
        modes[modeStr.length() - 1 - i] = true;
      }
    }
    return modes;
  }

  public static void main(String[] args) {
    String[] input =
        "3,225,1,225,6,6,1100,1,238,225,104,0,1101,33,37,225,101,6,218,224,1001,224,-82,224,4,224,102,8,223,223,101,7,224,224,1,223,224,223,1102,87,62,225,1102,75,65,224,1001,224,-4875,224,4,224,1002,223,8,223,1001,224,5,224,1,224,223,223,1102,49,27,225,1101,6,9,225,2,69,118,224,101,-300,224,224,4,224,102,8,223,223,101,6,224,224,1,224,223,223,1101,76,37,224,1001,224,-113,224,4,224,1002,223,8,223,101,5,224,224,1,224,223,223,1101,47,50,225,102,43,165,224,1001,224,-473,224,4,224,102,8,223,223,1001,224,3,224,1,224,223,223,1002,39,86,224,101,-7482,224,224,4,224,102,8,223,223,1001,224,6,224,1,223,224,223,1102,11,82,225,1,213,65,224,1001,224,-102,224,4,224,1002,223,8,223,1001,224,6,224,1,224,223,223,1001,14,83,224,1001,224,-120,224,4,224,1002,223,8,223,101,1,224,224,1,223,224,223,1102,53,39,225,1101,65,76,225,4,223,99,0,0,0,677,0,0,0,0,0,0,0,0,0,0,0,1105,0,99999,1105,227,247,1105,1,99999,1005,227,99999,1005,0,256,1105,1,99999,1106,227,99999,1106,0,265,1105,1,99999,1006,0,99999,1006,227,274,1105,1,99999,1105,1,280,1105,1,99999,1,225,225,225,1101,294,0,0,105,1,0,1105,1,99999,1106,0,300,1105,1,99999,1,225,225,225,1101,314,0,0,106,0,0,1105,1,99999,1107,677,226,224,1002,223,2,223,1005,224,329,101,1,223,223,8,677,226,224,102,2,223,223,1006,224,344,1001,223,1,223,108,677,677,224,1002,223,2,223,1006,224,359,1001,223,1,223,1108,226,677,224,102,2,223,223,1006,224,374,1001,223,1,223,1008,677,226,224,102,2,223,223,1005,224,389,101,1,223,223,7,226,677,224,102,2,223,223,1005,224,404,1001,223,1,223,1007,677,677,224,1002,223,2,223,1006,224,419,101,1,223,223,107,677,226,224,102,2,223,223,1006,224,434,101,1,223,223,7,677,677,224,1002,223,2,223,1005,224,449,101,1,223,223,108,677,226,224,1002,223,2,223,1006,224,464,101,1,223,223,1008,226,226,224,1002,223,2,223,1006,224,479,101,1,223,223,107,677,677,224,1002,223,2,223,1006,224,494,1001,223,1,223,1108,677,226,224,102,2,223,223,1005,224,509,101,1,223,223,1007,226,677,224,102,2,223,223,1005,224,524,1001,223,1,223,1008,677,677,224,102,2,223,223,1005,224,539,1001,223,1,223,1107,677,677,224,1002,223,2,223,1006,224,554,1001,223,1,223,1007,226,226,224,1002,223,2,223,1005,224,569,1001,223,1,223,7,677,226,224,1002,223,2,223,1006,224,584,1001,223,1,223,108,226,226,224,102,2,223,223,1005,224,599,1001,223,1,223,8,677,677,224,102,2,223,223,1005,224,614,1001,223,1,223,1107,226,677,224,102,2,223,223,1005,224,629,1001,223,1,223,8,226,677,224,102,2,223,223,1006,224,644,1001,223,1,223,1108,226,226,224,1002,223,2,223,1006,224,659,101,1,223,223,107,226,226,224,1002,223,2,223,1006,224,674,1001,223,1,223,4,223,99,226"
            .split(",");
    int[] program = Arrays.stream(input).mapToInt(Integer::parseInt).toArray();

    int pc = 0;
    boolean[] isImm = null;
    while (pc < program.length) {
      String currInstStr = Integer.toString(program[pc]);
      if (currInstStr.charAt(currInstStr.length() - 1) == '1'
          || currInstStr.charAt(currInstStr.length() - 1) == '2') {
        isImm = getImmModes(currInstStr, 3);
        int resAdd = isImm[2] ? pc + 3 : program[pc + 3];
        int aAdd = isImm[0] ? pc + 1 : program[pc + 1];
        int bAdd = isImm[1] ? pc + 2 : program[pc + 2];

        if (currInstStr.charAt(currInstStr.length() - 1) == '1') {
          program[resAdd] = program[aAdd] + program[bAdd];
        } else {
          program[resAdd] = program[aAdd] * program[bAdd];
        }
        pc += 4;
      } else if (currInstStr.charAt(currInstStr.length() - 1) == '3') {
        Scanner myInput = new Scanner(System.in);
        System.out.print("Enter input: ");
        int userInput = myInput.nextInt();
        program[program[pc + 1]] = userInput;
        pc += 2;
      } else if (currInstStr.charAt(currInstStr.length() - 1) == '4') {
        if (currInstStr.length() == 1) {
          System.out.println(program[program[pc + 1]]);
          if (program[program[pc + 1]] == 16209841) System.out.println("Happy Days");
        } else {
          System.out.println(program[pc + 1]);
          if (program[pc + 1] == 16209841) System.out.println("Happy Days");
        }
        pc += 2;
      } else if (currInstStr.charAt(currInstStr.length() - 1) == '5') {
        isImm = getImmModes(currInstStr, 2);
        int firstParamAdd = isImm[0] ? pc + 1 : program[pc + 1];
        int secondParamAdd = isImm[1] ? pc + 2 : program[pc + 2];
        if (program[firstParamAdd] != 0) {
          pc = program[secondParamAdd];
        } else {
          pc += 3;
        }
      } else if (currInstStr.charAt(currInstStr.length() - 1) == '6') {
        isImm = getImmModes(currInstStr, 2);
        int firstParamAdd = isImm[0] ? pc + 1 : program[pc + 1];
        int secondParamAdd = isImm[1] ? pc + 2 : program[pc + 2];
        if (program[firstParamAdd] == 0) {
          pc = program[secondParamAdd];
        } else {
          pc += 3;
        }
      } else if (currInstStr.charAt(currInstStr.length() - 1) == '7') {
        isImm = getImmModes(currInstStr, 3);
        int firstParamAdd = isImm[0] ? pc + 1 : program[pc + 1];
        int secondParamAdd = isImm[1] ? pc + 2 : program[pc + 2];
        int thirdParamAdd = isImm[2] ? pc + 3 : program[pc + 3];
        if (program[firstParamAdd] < program[secondParamAdd]) {
          program[thirdParamAdd] = 1;
        } else {
          program[thirdParamAdd] = 0;
        }
        pc += 4;
      } else if (currInstStr.charAt(currInstStr.length() - 1) == '8') {
        isImm = getImmModes(currInstStr, 3);
        int firstParamAdd = isImm[0] ? pc + 1 : program[pc + 1];
        int secondParamAdd = isImm[1] ? pc + 2 : program[pc + 2];
        int thirdParamAdd = isImm[2] ? pc + 3 : program[pc + 3];
        if (program[firstParamAdd] == program[secondParamAdd]) {
          program[thirdParamAdd] = 1;
        } else {
          program[thirdParamAdd] = 0;
        }
        pc += 4;
      } else {
        System.out.println("Instruction " + program[pc] + " exiting!");
        System.exit(1);
      }
    }
  }
}
