public class Block {
  private String value;

  public Block(String value) {
    this.value = value;
  }

  public String getVal() {
    return this.value;
  }

  public String toString() {
    return "[" + this.value + "]";
  }
}
