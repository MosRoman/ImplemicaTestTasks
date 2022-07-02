import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

//Task2
public class CitiesList {
    public int numberTest;
    public int countCities;
    public ArrayList<City> cities = new ArrayList<>();
    public int countRoad;
    public ArrayList<String> roadToCity = new ArrayList<>();

    @Override
    public String toString() {
        return "CitiesList{" +
                "numberTest=" + numberTest +
                ", countCities=" + countCities +
                ", cities=" + cities +
                ", countRoad=" + countRoad +
                ", roadToCity=" + roadToCity +
                '}';
    }

    public CitiesList() {
    }

    public int getNumberTest() {
        return numberTest;
    }

    public void setNumberTest(int numberTest) {
        this.numberTest = numberTest;
    }

    public int getCountCities() {
        return countCities;
    }

    public void setCountCities(int countCities) {
        this.countCities = countCities;
    }

    public ArrayList<City> getCities() {
        return cities;
    }

    public void setCities(ArrayList<City> cities) {
        this.cities = cities;
    }

    public int getCountRoad() {
        return countRoad;
    }

    public void setCountRoad(int countRoad) {
        this.countRoad = countRoad;
    }

    public ArrayList<String> getRoadToCity() {
        return roadToCity;
    }

    public void setRoadToCity(ArrayList<String> roadToCity) {
        this.roadToCity = roadToCity;
    }

    public void addCity(City city) {
        cities.add(city);
    }

    public void addPathCities(String s) {
        roadToCity.add(s);
    }

    public static CitiesList parseFile() throws IOException {
        File file = new File("src/resources/test_data.txt");
        BufferedReader br = new BufferedReader(new FileReader(file.getPath()));
        CitiesList citiesList = new CitiesList();
        citiesList.setNumberTest(Integer.parseInt(br.readLine()));
        citiesList.setCountCities(Integer.parseInt(br.readLine()));
        for (int i = 0; i < citiesList.getCountCities(); i++) {
            City city = new City();
            city.setName(br.readLine());
            city.setNeighbors(Integer.parseInt(br.readLine()));
            for (int j = 0; j < city.getNeighbors(); j++) {
                String[] l = br.readLine().split(" ");
                city.getCostToCity().put(Integer.parseInt(l[0]), Integer.parseInt(l[1]));
            }
            citiesList.addCity(city);
        }
        citiesList.setCountRoad(Integer.parseInt(br.readLine()));
        for (int i = 0; i < citiesList.getCountRoad(); i++) {
            citiesList.addPathCities(br.readLine());
        }
        br.close();
        return citiesList;
    }

    //print min cost  from start to end city
    public static void printMinCostForRoades(CitiesList citiesList) {
        for (int i = 0; i < citiesList.getRoadToCity().size(); i++) {
            System.out.println(minCostRoad(citiesList, citiesList.getRoadToCity().get(i)));
        }
    }

    //find min price for road
    private static int minCostRoad(CitiesList citiesList, String road) {
        String[] myWay = road.split(" ");
        List<Path> pathList = findAllPathes(citiesList);
        List<String> cities = findAllCities(citiesList);
        List<List<Integer>> matrixPathes = getMatrixPathes(citiesList);

        int indexFrom = cities.indexOf(myWay[0]);
        int indexTo = cities.indexOf(myWay[1]);

        List<Integer> listPrice = findAllCostForRoades(myWay[0], pathList, cities, matrixPathes, indexFrom, indexTo);
        Collections.sort(listPrice);

        return listPrice.get(0);
    }

    private static List<Integer> findAllCostForRoades(String firstCity, List<Path> pathList, List<String> cities,
                                                      List<List<Integer>> matrixPathes, int indexFrom, int indexTo) {
        List<Path> step = new ArrayList<>();
        List<String> citiesWhereBeen = new ArrayList<>();
        citiesWhereBeen.add(firstCity);
        List<Integer> listPrice = new ArrayList<>();
        while (!waysEnd(matrixPathes.get(cities.indexOf(firstCity)), (cities.indexOf(firstCity) + 1))) {

            for (int j = 0; j < matrixPathes.get(indexFrom).size(); j++) {
                if (!citiesWhereBeen.contains(cities.get(matrixPathes.get(indexFrom).get(j) - 1))) {
                    citiesWhereBeen.add(cities.get(matrixPathes.get(indexFrom).get(j) - 1));
                    step.add(new Path(indexFrom, j));
                    if (matrixPathes.get(indexFrom).get(j) == indexTo + 1) {
                        int price = 0;
                        price = getPrice(pathList, step, matrixPathes);
                        listPrice.add(price);
                        if (step.size() == 1) {
                            Path path = step.get(step.size() - 1);
                            matrixPathes.get(path.from).set(path.to, cities.indexOf(firstCity) + 1);
                            step.clear();
                            citiesWhereBeen.clear();
                            citiesWhereBeen.add(firstCity);
                            indexFrom = cities.indexOf(firstCity);
                            break;
                        }
                        if (step.size() == 2 && j != matrixPathes.get(indexFrom).size() - 1) {

                            Path path = step.get(step.size() - 1);
                            Path path2 = step.get(step.size()-2);
                            matrixPathes.get(path.from).set(path.to, matrixPathes.get(path2.from).get(path2.to));
                            step.clear();
                            citiesWhereBeen.clear();
                            citiesWhereBeen.add(firstCity);
                            indexFrom = cities.indexOf(firstCity);
                            break;
                        }

                        Path path = step.get(step.size() - 2);
                        matrixPathes.get(path.from).set(path.to, cities.indexOf(firstCity) + 1);
                        step.clear();
                        citiesWhereBeen.clear();
                        citiesWhereBeen.add(firstCity);
                        indexFrom = cities.indexOf(firstCity);
                        break;
                    }
                    indexFrom = matrixPathes.get(indexFrom).get(j) - 1;
                    break;
                }
            }
            if (waysEnd(matrixPathes.get(indexFrom), (cities.indexOf(firstCity) + 1)) && indexFrom != cities.indexOf(firstCity)) {
                Path path = step.get(step.size() - 1);
                matrixPathes.get(path.from).set(path.to, cities.indexOf(firstCity) + 1);
                indexFrom = cities.indexOf(firstCity);
                step.clear();
                citiesWhereBeen.clear();
                citiesWhereBeen.add(firstCity);
            }
        }
        return listPrice;
    }

    private static List<List<Integer>> getMatrixPathes(CitiesList citiesList) {
        List<List<Integer>> matrixPathes = new ArrayList<>();
        for (int i = 0; i < citiesList.getCities().size(); i++) {
            matrixPathes.add(new ArrayList<>(citiesList.getCities().get(i).getCostToCity().keySet()));
        }
        return matrixPathes;
    }

    private static List<Path> findAllPathes(CitiesList citiesList) {
        List<Path> pathList = new ArrayList<>();
        for (int i = 0; i < citiesList.getCities().size(); i++) {
            Map<Integer, Integer> price = citiesList.getCities().get(i).getCostToCity();
            for (Map.Entry<Integer, Integer> entry : price.entrySet()) {
                pathList.add(new Path(i + 1, entry.getKey(), entry.getValue()));
            }
        }
        return pathList;
    }

    private static List<String> findAllCities(CitiesList citiesList) {
        List<String> cities = new ArrayList<>();
        for (City city : citiesList.getCities()) {
            cities.add(city.getName());
        }
        return cities;
    }

    private static int getPrice(List<Path> pathList, List<Path> road, List<List<Integer>> listPath) {
        int price = 0;
        for (Path r : road) {
            for (Path p : pathList) {
                if (new Path(r.from + 1, listPath.get(r.getFrom()).get(r.getTo())).equals(p)) {
                    price += p.price;
                }
            }
        }
        return price;
    }

    private static boolean waysEnd(List<Integer> path, int from) {
        for (int a : path) {
            if (a != from) {
                return false;
            }
        }
        return true;
    }

    private static void printListList(List<List<Integer>> listList) {
        for (List a : listList) {
            System.out.println(a);
        }
    }
}

