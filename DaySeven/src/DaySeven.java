import java.util.Arrays;

public class DaySeven {

  public static void partOne(String[] input, String[] permutations) {
    long max = Long.MIN_VALUE;
    for (String code : permutations) {
      long a = Character.getNumericValue(code.charAt(0));
      long b = Character.getNumericValue(code.charAt(1));
      long c = Character.getNumericValue(code.charAt(2));
      long d = Character.getNumericValue(code.charAt(3));
      long e = Character.getNumericValue(code.charAt(4));
      IntCode ampA = new IntCode(Arrays.stream(input).mapToLong(Long::parseLong).toArray());
      ampA.inputs.add(new Long(0));
      ampA.inputs.add(a);
      ampA.run();

      // Reset input each time
      IntCode ampB = new IntCode(Arrays.stream(input).mapToLong(Long::parseLong).toArray());
      ampB.inputs.add(ampA.output);
      ampB.inputs.add(b);
      ampB.run();

      IntCode ampC = new IntCode(Arrays.stream(input).mapToLong(Long::parseLong).toArray());
      ampC.inputs.add(ampB.output);
      ampC.inputs.add(c);
      ampC.run();

      IntCode ampD = new IntCode(Arrays.stream(input).mapToLong(Long::parseLong).toArray());
      ampD.inputs.add(ampC.output);
      ampD.inputs.add(d);
      ampD.run();

      IntCode ampE = new IntCode(Arrays.stream(input).mapToLong(Long::parseLong).toArray());
      ampE.inputs.add(ampD.output);
      ampE.inputs.add(e);
      ampE.run();
      if (ampE.output > max) {
        max = ampE.output;
      }
    }
    System.out.println(max);
  }

  public static void partTwo(String[] input, String[] permutations) {
    long max = Long.MIN_VALUE;
    for (String code : permutations) {
      long a = Character.getNumericValue(code.charAt(0));
      long b = Character.getNumericValue(code.charAt(1));
      long c = Character.getNumericValue(code.charAt(2));
      long d = Character.getNumericValue(code.charAt(3));
      long e = Character.getNumericValue(code.charAt(4));

      IntCode ampA = new IntCode(Arrays.stream(input).mapToLong(Long::parseLong).toArray());
      IntCode ampB = new IntCode(Arrays.stream(input).mapToLong(Long::parseLong).toArray());
      IntCode ampC = new IntCode(Arrays.stream(input).mapToLong(Long::parseLong).toArray());
      IntCode ampD = new IntCode(Arrays.stream(input).mapToLong(Long::parseLong).toArray());
      IntCode ampE = new IntCode(Arrays.stream(input).mapToLong(Long::parseLong).toArray());

      // Only the first loop takes the phase setting
      ampA.inputs.add(ampE.output);
      ampA.inputs.add(a);
      ampA.run();
      ampB.inputs.add(ampA.output);
      ampB.inputs.add(b);
      ampB.run();
      ampC.inputs.add(ampB.output);
      ampC.inputs.add(c);
      ampC.run();
      ampD.inputs.add(ampC.output);
      ampD.inputs.add(d);
      ampD.run();
      ampE.inputs.add(ampD.output);
      ampE.inputs.add(e);
      ampE.run();

      // Feedback loop
      while (true) {
        ampA.inputs.add(ampE.output);
        ampA.run();
        ampB.inputs.add(ampA.output);
        ampB.run();
        ampC.inputs.add(ampB.output);
        ampC.run();
        ampD.inputs.add(ampC.output);
        ampD.run();
        ampE.inputs.add(ampD.output);
        ampE.run();
        if (ampE.halted) {
          break;
        }
      }

      if (ampE.output > max) {
        max = ampE.output;
      }
    }
    System.out.println(max);
  }

  public static void main(String[] args) {
    String[] input =
        "3,8,1001,8,10,8,105,1,0,0,21,34,59,68,85,102,183,264,345,426,99999,3,9,101,3,9,9,102,3,9,9,4,9,99,3,9,1002,9,4,9,1001,9,2,9,1002,9,2,9,101,5,9,9,102,5,9,9,4,9,99,3,9,1001,9,4,9,4,9,99,3,9,101,3,9,9,1002,9,2,9,1001,9,5,9,4,9,99,3,9,1002,9,3,9,1001,9,5,9,102,3,9,9,4,9,99,3,9,1001,9,1,9,4,9,3,9,1001,9,2,9,4,9,3,9,1002,9,2,9,4,9,3,9,102,2,9,9,4,9,3,9,1001,9,2,9,4,9,3,9,101,2,9,9,4,9,3,9,102,2,9,9,4,9,3,9,102,2,9,9,4,9,3,9,101,1,9,9,4,9,3,9,101,1,9,9,4,9,99,3,9,1002,9,2,9,4,9,3,9,1002,9,2,9,4,9,3,9,1002,9,2,9,4,9,3,9,1001,9,2,9,4,9,3,9,101,1,9,9,4,9,3,9,1001,9,1,9,4,9,3,9,1002,9,2,9,4,9,3,9,101,2,9,9,4,9,3,9,101,1,9,9,4,9,3,9,101,2,9,9,4,9,99,3,9,1001,9,1,9,4,9,3,9,101,1,9,9,4,9,3,9,102,2,9,9,4,9,3,9,1002,9,2,9,4,9,3,9,1002,9,2,9,4,9,3,9,101,2,9,9,4,9,3,9,1001,9,1,9,4,9,3,9,1001,9,1,9,4,9,3,9,102,2,9,9,4,9,3,9,1001,9,1,9,4,9,99,3,9,1002,9,2,9,4,9,3,9,101,1,9,9,4,9,3,9,1001,9,2,9,4,9,3,9,102,2,9,9,4,9,3,9,1002,9,2,9,4,9,3,9,102,2,9,9,4,9,3,9,1001,9,1,9,4,9,3,9,1002,9,2,9,4,9,3,9,1001,9,2,9,4,9,3,9,101,2,9,9,4,9,99,3,9,1001,9,1,9,4,9,3,9,1002,9,2,9,4,9,3,9,101,2,9,9,4,9,3,9,101,1,9,9,4,9,3,9,1002,9,2,9,4,9,3,9,101,1,9,9,4,9,3,9,102,2,9,9,4,9,3,9,101,1,9,9,4,9,3,9,102,2,9,9,4,9,3,9,101,2,9,9,4,9,99"
            .split(",");
    String[] permutations =
        "01234, 01243, 01324, 01342, 01423, 01432, 02143, 02134, 02341, 02314, 02431, 02413, 03124, 03142, 03214, 03241, 03412, 03421, 04132, 04123, 04231, 04213, 04321, 04312, 10423, 10432, 10243, 10234, 10342, 10324, 12430, 12403, 12034, 12043, 12304, 12340, 13402, 13420, 13042, 13024, 13240, 13204, 14320, 14302, 14023, 14032, 14203, 14230, 20341, 20314, 20431, 20413, 20134, 20143, 21304, 21340, 21403, 21430, 21043, 21034, 23140, 23104, 23410, 23401, 23014, 23041, 24103, 24130, 24301, 24310, 24031, 24013, 30124, 30142, 30214, 30241, 30412, 30421, 31042, 31024, 31240, 31204, 31420, 31402, 32014, 32041, 32104, 32140, 32401, 32410, 34021, 34012, 34120, 34102, 34210, 34201, 40312, 40321, 40132, 40123, 40231, 40213, 41320, 41302, 41023, 41032, 41203, 41230, 42301, 42310, 42031, 42013, 42130, 42103, 43210, 43201, 43012, 43021, 43102, 43120"
            .split(", ");
    partOne(input, permutations);


    permutations =
        "56789,56798,56879,56897,56978,56987,57698,57689,57896,57869,57986,57968,58679,58697,58769,58796,58967,58976,59687,59678,59786,59768,59876,59867,65978,65987,65798,65789,65897,65879,67985,67958,67589,67598,67859,67895,68957,68975,68597,68579,68795,68759,69875,69857,69578,69587,69758,69785,75896,75869,75986,75968,75689,75698,76859,76895,76958,76985,76598,76589,78695,78659,78965,78956,78569,78596,79658,79685,79856,79865,79586,79568,85679,85697,85769,85796,85967,85976,86597,86579,86795,86759,86975,86957,87569,87596,87659,87695,87956,87965,89576,89567,89675,89657,89765,89756,95867,95876,95687,95678,95786,95768,96875,96857,96578,96587,96758,96785,97856,97865,97586,97568,97685,97658,98765,98756,98567,98576,98657,98675"
            .split(",");
    partTwo(input, permutations);
  }
}
