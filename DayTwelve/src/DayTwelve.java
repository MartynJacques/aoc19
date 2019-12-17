import java.util.ArrayList;

public class DayTwelve {

  public static void partOne(ArrayList<MoonPair> pairs, ArrayList<Moon> moons) {
    for (int i = 0; i < 1000; i++) {
      for (MoonPair pair : pairs) {
        pair.applyGravity();
      }
      for (Moon moon : moons) {
        moon.applyVelocity();
      }
    }

    // For each moon, add total energy
    int totalEnergy = 0;
    for (Moon moon : moons) {
      totalEnergy += moon.totalEnergy();
    }
    System.out.println(totalEnergy);
  }

  public static long getXSteps(ArrayList<MoonPair> pairs, ArrayList<Moon> moons) {
    /*
    * we do that until all planet x components have their initial values and all 
    * vel_x are 0. We get the steps needed for the x-cycle 
    */

    // Keep a copy of original values
    Moon aOrig =
        new Moon(moons.get(0).position.x, moons.get(0).position.y, moons.get(0).position.z);
    Moon bOrig =
        new Moon(moons.get(1).position.x, moons.get(1).position.y, moons.get(1).position.z);
    Moon cOrig =
        new Moon(moons.get(2).position.x, moons.get(2).position.y, moons.get(2).position.z);
    Moon dOrig =
        new Moon(moons.get(3).position.x, moons.get(3).position.y, moons.get(3).position.z);
    long i = 1;
    long xCycleSteps;
    while (true) {
      for (MoonPair pair : pairs) {
        pair.applyGravity();
      }
      for (Moon moon : moons) {
        moon.applyVelocity();
      }

      if (moons.get(0).position.x == aOrig.position.x && moons.get(0).velocity.x == 0
          && moons.get(1).position.x == bOrig.position.x && moons.get(1).velocity.x == 0
          && moons.get(2).position.x == cOrig.position.x && moons.get(2).velocity.x == 0
          && moons.get(3).position.x == dOrig.position.x && moons.get(3).velocity.x == 0) {
        xCycleSteps = i;
        break;
      }
      i++;
    }
    return xCycleSteps;
  }

  public static long getYSteps(ArrayList<MoonPair> pairs, ArrayList<Moon> moons) {
    /*
    * we do that until all planet x components have their initial values and all 
    * vel_x are 0. We get the steps needed for the x-cycle 
    */

    // Keep a copy of original values
    Moon aOrig =
        new Moon(moons.get(0).position.x, moons.get(0).position.y, moons.get(0).position.z);
    Moon bOrig =
        new Moon(moons.get(1).position.x, moons.get(1).position.y, moons.get(1).position.z);
    Moon cOrig =
        new Moon(moons.get(2).position.x, moons.get(2).position.y, moons.get(2).position.z);
    Moon dOrig =
        new Moon(moons.get(3).position.x, moons.get(3).position.y, moons.get(3).position.z);
    long i = 1;
    long xCycleSteps;
    while (true) {
      for (MoonPair pair : pairs) {
        pair.applyGravity();
      }
      for (Moon moon : moons) {
        moon.applyVelocity();
      }

      if (moons.get(0).position.y == aOrig.position.y && moons.get(0).velocity.y == 0
          && moons.get(1).position.y == bOrig.position.y && moons.get(1).velocity.y == 0
          && moons.get(2).position.y == cOrig.position.y && moons.get(2).velocity.y == 0
          && moons.get(3).position.y == dOrig.position.y && moons.get(3).velocity.y == 0) {
        xCycleSteps = i;
        break;
      }
      i++;
    }
    return xCycleSteps;
  }

  public static long getZSteps(ArrayList<MoonPair> pairs, ArrayList<Moon> moons) {
    /*
    * we do that until all planet x components have their initial values and all 
    * vel_x are 0. We get the steps needed for the x-cycle 
    */

    // Keep a copy of original values
    Moon aOrig =
        new Moon(moons.get(0).position.x, moons.get(0).position.y, moons.get(0).position.z);
    Moon bOrig =
        new Moon(moons.get(1).position.x, moons.get(1).position.y, moons.get(1).position.z);
    Moon cOrig =
        new Moon(moons.get(2).position.x, moons.get(2).position.y, moons.get(2).position.z);
    Moon dOrig =
        new Moon(moons.get(3).position.x, moons.get(3).position.y, moons.get(3).position.z);
    long i = 1;
    long xCycleSteps;
    while (true) {
      for (MoonPair pair : pairs) {
        pair.applyGravity();
      }
      for (Moon moon : moons) {
        moon.applyVelocity();
      }

      if (moons.get(0).position.z == aOrig.position.z && moons.get(0).velocity.z == 0
          && moons.get(1).position.z == bOrig.position.z && moons.get(1).velocity.z == 0
          && moons.get(2).position.z == cOrig.position.z && moons.get(2).velocity.z == 0
          && moons.get(3).position.z == dOrig.position.z && moons.get(3).velocity.z == 0) {
        xCycleSteps = i;
        break;
      }
      i++;
    }
    return xCycleSteps;
  }

  /*
   * Shamelessly aided with https://www.reddit.com/r/adventofcode/comments/e9jxh2/help_2019_day_12_part_2_what_am_i_not_seeing/
   */
  public static void partTwo() {
    // Actual 
    Moon a = new Moon(-17, 9, -5);
    Moon b = new Moon(-1, 7, 13);
    Moon c = new Moon(-19, 12, 5);
    Moon d = new Moon(-6, -6, -4);
    ArrayList<Moon> moons = new ArrayList<>();
    moons.add(a);
    moons.add(b);
    moons.add(c);
    moons.add(d);

    // All pairs of moons
    ArrayList<MoonPair> pairs = new ArrayList<>();
    pairs.add(new MoonPair(a, b));
    pairs.add(new MoonPair(a, c));
    pairs.add(new MoonPair(a, d));
    pairs.add(new MoonPair(b, c));
    pairs.add(new MoonPair(b, d));
    pairs.add(new MoonPair(c, d));
    long xCycleSteps = getXSteps(pairs, moons);

    // Actual 
    a = new Moon(-17, 9, -5);
    b = new Moon(-1, 7, 13);
    c = new Moon(-19, 12, 5);
    d = new Moon(-6, -6, -4);
    moons = new ArrayList<>();
    moons.add(a);
    moons.add(b);
    moons.add(c);
    moons.add(d);

    // All pairs of moons
    pairs = new ArrayList<>();
    pairs.add(new MoonPair(a, b));
    pairs.add(new MoonPair(a, c));
    pairs.add(new MoonPair(a, d));
    pairs.add(new MoonPair(b, c));
    pairs.add(new MoonPair(b, d));
    pairs.add(new MoonPair(c, d));
    long yCycleSteps = getYSteps(pairs, moons);

    // Actual 
    a = new Moon(-17, 9, -5);
    b = new Moon(-1, 7, 13);
    c = new Moon(-19, 12, 5);
    d = new Moon(-6, -6, -4);
    moons = new ArrayList<>();
    moons.add(a);
    moons.add(b);
    moons.add(c);
    moons.add(d);

    // All pairs of moons
    pairs = new ArrayList<>();
    pairs.add(new MoonPair(a, b));
    pairs.add(new MoonPair(a, c));
    pairs.add(new MoonPair(a, d));
    pairs.add(new MoonPair(b, c));
    pairs.add(new MoonPair(b, d));
    pairs.add(new MoonPair(c, d));
    long zCycleSteps = getZSteps(pairs, moons);

    long lcm = lcm(lcm(xCycleSteps, yCycleSteps), zCycleSteps);
    System.out.println(lcm);
  }

  // Recursive method to return gcd of a and b 
  static long gcd(long a, long b) {
    if (a == 0)
      return b;
    return gcd(b % a, a);
  }

  // method to return LCM of two numbers 
  static long lcm(long a, long b) {
    return (a * b) / gcd(a, b);
  }

  public static void main(String[] args) {
    // Actual 
    Moon a = new Moon(-17, 9, -5);
    Moon b = new Moon(-1, 7, 13);
    Moon c = new Moon(-19, 12, 5);
    Moon d = new Moon(-6, -6, -4);
    ArrayList<Moon> moons = new ArrayList<>();
    moons.add(a);
    moons.add(b);
    moons.add(c);
    moons.add(d);
   
    // All pairs of moons
    ArrayList<MoonPair> pairs = new ArrayList<>();
    pairs.add(new MoonPair(a, b));
    pairs.add(new MoonPair(a, c));
    pairs.add(new MoonPair(a, d));
    pairs.add(new MoonPair(b, c));
    pairs.add(new MoonPair(b, d));
    pairs.add(new MoonPair(c, d));

    //    partOne(pairs, moons);
    partTwo();
  }
}
