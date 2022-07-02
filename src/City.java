import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class City {

     public String name;
     public int neighbors;
     public Map<Integer, Integer> costToCity = new HashMap<>();

    public City() {
    }

    public City(String name, int neighbors, Map<Integer, Integer> costToCity) {
        this.name = name;
        this.neighbors = neighbors;
        this.costToCity = costToCity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNeighbors() {
        return neighbors;
    }

    public void setNeighbors(int neighbors) {
        this.neighbors = neighbors;
    }

    public Map<Integer, Integer> getCostToCity() {
        return costToCity;
    }

    public void setCostToCity(Map<Integer, Integer> costToCity) {
        this.costToCity = costToCity;
    }

    @Override
    public String toString() {
        return "City{" +
                "name='" + name + '\'' +
                ", neighbors=" + neighbors +
                ", costToCity=" + costToCity +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        City city = (City) o;
        return Objects.equals(name, city.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
