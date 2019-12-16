public class Triple {
  int x;
  int y;
  int z;

  public Triple(int x, int y, int z) {
    this.x = x;
    this.y = y;
    this.z = z;
  }
  
  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    Triple other = (Triple) obj;
    return this.x == other.x && this.y == other.y && this.z == other.z;
  }

  public void add(Triple other) {
    this.x += other.x;
    this.y += other.y;
    this.z += other.z;
  }

  @Override
  public String toString() {
    return "(" + x + "," + y + "," + z + ")";
  }

  @Override
  public int hashCode() {
    return x ^ y * 137 ^ z * 11317;
  }
}
