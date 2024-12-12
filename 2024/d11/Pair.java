import java.util.Objects;

public class Pair<T, S> {
  private T first;
  private S second;

  public Pair(T first, S second) {
    this.first = first;
    this.second = second;
  }

  public T first() {
    return this.first;
  }

  public S second() {
    return this.second;
  }

  @Override
  public boolean equals(Object obj) {
    if(!(obj instanceof Pair)) {
      return false;
    }
    Pair<?, ?> p = (Pair<?, ?>) obj;
    return this.first.equals(p.first()) && this.second.equals(p.second());
  }

  @Override
  public int hashCode() {
    return Objects.hash(first, second);
  }
}
