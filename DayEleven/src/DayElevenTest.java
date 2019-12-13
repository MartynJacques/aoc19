import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class DayElevenTest {

  @Test
  public void testUpTurn() {
    DayEleven.Direction d = DayEleven.Direction.UP;
    d = DayEleven.Direction.turnRight(d);
    assertEquals(d, DayEleven.Direction.RIGHT);

    d = DayEleven.Direction.UP;
    d = DayEleven.Direction.turnLeft(d);
    assertEquals(d, DayEleven.Direction.LEFT);
  }

  @Test
  public void testDownTurn() {
    DayEleven.Direction d = DayEleven.Direction.DOWN;
    d = DayEleven.Direction.turnRight(d);
    assertEquals(d, DayEleven.Direction.LEFT);

    d = DayEleven.Direction.DOWN;
    d = DayEleven.Direction.turnLeft(d);
    assertEquals(d, DayEleven.Direction.RIGHT);
  }

  @Test
  public void testLeftTurn() {
    DayEleven.Direction d = DayEleven.Direction.LEFT;
    d = DayEleven.Direction.turnRight(d);
    assertEquals(d, DayEleven.Direction.UP);

    d = DayEleven.Direction.LEFT;
    d = DayEleven.Direction.turnLeft(d);
    assertEquals(d, DayEleven.Direction.DOWN);
  }

  @Test
  public void testRightTurn() {
    DayEleven.Direction d = DayEleven.Direction.RIGHT;
    d = DayEleven.Direction.turnRight(d);
    assertEquals(d, DayEleven.Direction.DOWN);

    d = DayEleven.Direction.RIGHT;
    d = DayEleven.Direction.turnLeft(d);
    assertEquals(d, DayEleven.Direction.UP);
  }

  @Test
  public void testMovement() {
    DayEleven.Direction d = DayEleven.Direction.UP;
    DayEleven.Position p = new DayEleven.Position(0, 0);
    p.move(d);
    assertEquals(p, new DayEleven.Position(-1, 0));

    d = DayEleven.Direction.RIGHT;
    p = new DayEleven.Position(0, 0);
    p.move(d);
    assertEquals(p, new DayEleven.Position(0, 1));

    d = DayEleven.Direction.DOWN;
    p = new DayEleven.Position(0, 0);
    p.move(d);
    assertEquals(p, new DayEleven.Position(1, 0));

    d = DayEleven.Direction.LEFT;
    p = new DayEleven.Position(0, 0);
    p.move(d);
    assertEquals(p, new DayEleven.Position(0, -1));
  }

}
