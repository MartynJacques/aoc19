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

  public static void partTwo(ArrayList<MoonPair> pairs, ArrayList<Moon> moons) {
    /*
    * we do that until all planet x components have their initial values and all 
    * vel_x are 0. We get the steps needed for the x-cycle 
    */
    while (true) {
      for (MoonPair pair : pairs) {
        pair.applyGravity();
      }
      for (Moon moon : moons) {
        moon.applyVelocity();
      }
    }


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
    partTwo(pairs, moons);
  }
}
