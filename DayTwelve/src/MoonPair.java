
public class MoonPair {

  public Moon a;
  public Moon b;

  public MoonPair(Moon a, Moon b) {
    this.a = a;
    this.b = b;
  }

  public void applyGravity() {
    if (a.position.x == b.position.x) {
      ;
    } else if (a.position.x < b.position.x) {
      a.velocity.x += 1;
      b.velocity.x -= 1;
    } else {
      a.velocity.x -= 1;
      b.velocity.x += 1;
    }

    if (a.position.y == b.position.y) {
      ;
    } else if (a.position.y < b.position.y) {
      a.velocity.y += 1;
      b.velocity.y -= 1;
    } else {
      a.velocity.y -= 1;
      b.velocity.y += 1;
    }

    if (a.position.z == b.position.z) {
      ;
    } else if (a.position.z < b.position.z) {
      a.velocity.z += 1;
      b.velocity.z -= 1;
    } else {
      a.velocity.z -= 1;
      b.velocity.z += 1;
    }
  }
}
