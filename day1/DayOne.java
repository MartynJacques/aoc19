import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

public class DayOne {
    private static int calculateFuel(int mass) {
        return mass / 3 - 2;
    }

    private int partOne() throws FileNotFoundException {
        Scanner scanner = new Scanner(new File("input.txt"));
        int sum = 0;
        while(scanner.hasNextInt()){
           sum += calculateFuel(scanner.nextInt());
        }

        return sum;
    }

    private static int partTwo() throws FileNotFoundException {
        Scanner scanner = new Scanner(new File("input.txt"));
        int sum = 0;
        while(scanner.hasNextInt()) {
            int fuel = calculateFuel(scanner.nextInt());
            sum += fuel;
            while(fuel > 0) {
                fuel = calculateFuel(fuel);
                if (fuel > 0)
                    sum += fuel;
            }
        }

        return sum;
    }

    public static void main(String[] args) throws FileNotFoundException {
        System.out.println(partTwo());
    }
}
