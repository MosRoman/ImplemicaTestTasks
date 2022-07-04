import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class City {

     private String name;
     private int neighbors;
     private List<Path> paths = new ArrayList<>();

    public City() {
    }

    public City(String name) {
        this.name = name;
    }

    public City(String name, int neighbors, List<Path> paths) {
        this.name = name;
        this.neighbors = neighbors;
        this.paths = paths;
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

    public List<Path> getPaths() {
        return paths;
    }

    public void setPaths(List<Path> paths) {
        this.paths = paths;
    }
     public void addPath(Path path){
        paths.add(path);
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

    @Override
    public String toString() {
        return "City{" +
                "name='" + name + '\'' +
                ", paths=" + paths +
                '}';
    }
}
