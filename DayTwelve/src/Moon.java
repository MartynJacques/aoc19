
public class Moon {

  public Triple position;
  public Triple velocity;

  public Moon(int x, int y, int z) {
    this.position = new Triple(x, y, z);
    velocity = new Triple(0, 0, 0);
  }

  public void applyVelocity() {
    position.add(velocity);
  }

  @Override
  public String toString() {
    return "(" + position.toString() + "," + velocity.toString() + ")";
  }

  @Override
  public int hashCode() {
    int tmp = (velocity.hashCode() + ((position.hashCode() + 1) / 2));
    return position.hashCode() + (tmp * tmp);
  }

  public int totalEnergy() {
    int pE = Math.abs(position.x) + Math.abs(position.y) + Math.abs(position.z);
    int kE = Math.abs(velocity.x) + Math.abs(velocity.y) + Math.abs(velocity.z);
    return pE * kE;
  }
}
