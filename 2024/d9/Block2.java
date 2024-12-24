public class Block2 {
  private long id;

  public Block2(long id) {
    this.id = id;
  }

  public long id() {
    return this.id;
  }

  public String toString() {
    return "[" + this.id + "]";
  }
}
