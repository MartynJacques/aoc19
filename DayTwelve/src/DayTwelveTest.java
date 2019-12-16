import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;

class DayTwelveTest {

  @Test
  void testApplyGravity() {
    Moon ganymede = new Moon(3, 3, 3);
    Moon callisto = new Moon(5, 5, 5);
    MoonPair mp = new MoonPair(ganymede, callisto);
    mp.applyGravity();
    assertEquals(ganymede.velocity, new Triple(1, 1, 1));
    assertEquals(callisto.velocity, new Triple(-1, -1, -1));
  }

  @Test
  void testTripleEqual() {
    Triple a = new Triple(1, 1, 1);
    Triple b = new Triple(1, 1, 1);
    assertEquals(a, b);
  }

  @Test
  void testTripleNotEqual() {
    Triple a = new Triple(1, 1, 1);
    Triple b = new Triple(1, 2, 1);
    assertNotEquals(a, b);
  }
}
