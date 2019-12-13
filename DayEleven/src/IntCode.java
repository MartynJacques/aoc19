import java.util.Stack;

/*
 * IntCode machine 
 */
public class IntCode {
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

  public static final int POS = 0; // Position mode
  public static final int IMM = 1; // Immediate mode
  public static final int REL = 2; // Relative mode

  public long[] memory;
  public long output;
  public boolean halted;
  public int pc;
  public int rb;
  public Stack<Long> inputs;

  public IntCode(long[] program) {
    // Initialise memory, memory is larger than program size
    this.memory = new long[program.length * 50];
    for (int i = 0; i < program.length; i++) {
      this.memory[i] = program[i];
    }
    this.halted = false;
    this.pc = 0;
    this.rb = 0;
    this.inputs = new Stack<>();
    this.output = 0;
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

  public void run() {
    while (pc < memory.length) {
      Instruction in = new Instruction(Long.toString(memory[pc]));
      String opcode = in.opcode;
      if (opcode.contentEquals("99")) {
        //        System.out.println("Instruction 99, halting.");
        halted = true;
        return;
      }
      int[] modes = in.modes;
      int[] addresses = getAddresses(modes, pc, memory, rb);
      if (in.getNumOperands() == 3) {
        // Three operands
        int firstParamAdd = addresses[0];
        int secondParamAdd = addresses[1];
        int thirdParamAdd = addresses[2];

        if (opcode.contentEquals("01")) {
          memory[thirdParamAdd] = memory[firstParamAdd] + memory[secondParamAdd];
        } else if (opcode.contentEquals("02")) {
          memory[thirdParamAdd] = memory[firstParamAdd] * memory[secondParamAdd];
        } else if (opcode.contentEquals("07")) {
          if (memory[firstParamAdd] < memory[secondParamAdd]) {
            memory[thirdParamAdd] = 1;
          } else {
            memory[thirdParamAdd] = 0;
          }
        } else if (opcode.contentEquals("08")) {
          if (memory[firstParamAdd] == memory[secondParamAdd]) {
            memory[thirdParamAdd] = 1;
          } else {
            memory[thirdParamAdd] = 0;
          }
        }
        pc += 4;
      } else if (in.getNumOperands() == 2) {
        // Two operands
        int firstParamAdd = addresses[0];
        int secondParamAdd = addresses[1];
        if (opcode.contentEquals("05")) {
          if (memory[firstParamAdd] != 0) {
            pc = (int) memory[secondParamAdd];
          } else {
            pc += 3;
          }
        } else if (opcode.contentEquals("06")) {
          if (memory[firstParamAdd] == 0) {
            pc = (int) memory[secondParamAdd];
          } else {
            pc += 3;
          }
        }
      } else if (in.getNumOperands() == 1) {
        // One operand
        if (opcode.contentEquals("03")) {
          // Input
          memory[addresses[0]] = inputs.pop();
          pc += 2;
        } else if (opcode.contentEquals("04")) {
          // Output 
          output = memory[addresses[0]];
          pc += 2;
          return;
        } else if (opcode.contentEquals("09")) {
          // Relative base adjust 
          rb += memory[addresses[0]];
          pc += 2;
        }
      } else {
        // Error
        System.out.println("Unknown instruction " + opcode + ". Exiting.");
        System.exit(1);
      }
    }
  }
}
