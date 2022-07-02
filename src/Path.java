import java.util.Objects;

public class Path {
    public int from;
    public  int to;
    public int price;

    public Path(int from, int to) {
        this.from = from;
        this.to = to;
    }

    public Path(int from, int to, int price) {
        this.from = from;
        this.to = to;
        this.price = price;
    }

    public int getFrom() {
        return from;
    }

    public void setFrom(int from) {
        this.from = from;
    }

    public int getTo() {
        return to;
    }

    public void setTo(int to) {
        this.to = to;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Path path = (Path) o;
        return from == path.from &&
                to == path.to;
    }

    @Override
    public int hashCode() {
        return Objects.hash(from, to);
    }

    @Override
    public String toString() {
        return "Path{" +
                "from=" + from +
                ", to=" + to +
                ", price=" + price +
                '}';
    }
}
