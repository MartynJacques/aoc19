import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Day2 {

    public static int executeOperation(int opcode, int a, int b) {
        switch(opcode) {
            case 1:
                return a + b;
            case 2:
                return a * b;
            case 99:
                return -1;
            default:
                System.err.println("ERROR");
                System.exit(1);
        }
        return -1;
    }

    public static void partTwo() {
        String line;
        BufferedReader br = null;
        int[] instructionComponent;
        int[] originalInstructionComponent;
        try {
            br = new BufferedReader(new FileReader("input.txt"));
            while ((line = br.readLine()) != null) {
                String[] splitLines = line.split(",");

                // Convert to int array
                instructionComponent = new int[splitLines.length];
                originalInstructionComponent = new int[splitLines.length];
                for(int i = 0; i < splitLines.length; i++) {
                    instructionComponent[i] = Integer.parseInt(splitLines[i]);
                    originalInstructionComponent[i] = Integer.parseInt(splitLines[i]);
                }


                for(int inputA = 0; inputA < 99; inputA++) {
                    for(int inputB = 0; inputB < 99; inputB++) {
                        instructionComponent[1] = inputA;
                        instructionComponent[2] = inputB;

                        int result = 0;
                        for(int i = 0; i < splitLines.length; i += 4) {
                            result = executeOperation(
                                instructionComponent[i],
                                instructionComponent[instructionComponent[i+1]],
                                instructionComponent[instructionComponent[i+2]]);
                            if (result == -1) {
                                result = instructionComponent[0];
                                break;
                            } else {
                                instructionComponent[instructionComponent[i+3]] = result;
                            }
                        }

                        if (result == 19690720) {
                            System.out.println(inputA);
                            System.out.println(inputB);
                            System.out.println(100 * inputA + inputB);
                            System.exit(0);
                        }
                        for(int i = 0; i < splitLines.length; i++) {
                            instructionComponent[i] = originalInstructionComponent[i];
                        }
                    }
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void partOne() {
        String line;
        BufferedReader br = null;
        int[] instructionComponent;
        try {
            br = new BufferedReader(new FileReader("input.txt"));
            while ((line = br.readLine()) != null) {
                String[] splitLines = line.split(",");

                // Convert to int array
                instructionComponent = new int[splitLines.length];
                for(int i = 0; i < splitLines.length; i++) {
                    instructionComponent[i] = Integer.parseInt(splitLines[i]);
                }

                instructionComponent[1] = 12;
                instructionComponent[2] = 2;

                for(int i = 0; i < splitLines.length; i += 4) {
                    int result = executeOperation(
                        instructionComponent[i],
                        instructionComponent[instructionComponent[i+1]],
                        instructionComponent[instructionComponent[i+2]]);
                    if (result == -1) {
                        System.out.println(instructionComponent[0]);
                        System.exit(0);
                    }
                    instructionComponent[instructionComponent[i+3]] = result;
                }

                System.out.println(instructionComponent[0]);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) {
        partTwo();
    }
}
