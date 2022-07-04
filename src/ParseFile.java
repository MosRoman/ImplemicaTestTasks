import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

//Task2
public class ParseFile {
    public int numberTest;
    public int countCities;
    public ArrayList<City> cities = new ArrayList<>();
    public int countRoad;
    public ArrayList<String> roadToCity = new ArrayList<>();

    @Override
    public String toString() {
        return "ParseFile{" +
                "numberTest=" + numberTest +
                ", countCities=" + countCities +
                ", cities=" + cities +
                ", countRoad=" + countRoad +
                ", roadToCity=" + roadToCity +
                '}';
    }

    public ParseFile() {
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

    //method parse file to object
    public static ParseFile parseFile() throws IOException {
        File file = new File("src/resources/test_data.txt");
        BufferedReader br = new BufferedReader(new FileReader(file.getPath()));
        ParseFile citiesList = new ParseFile();
        citiesList.setNumberTest(Integer.parseInt(br.readLine()));
        citiesList.setCountCities(Integer.parseInt(br.readLine()));
        for (int i = 0; i < citiesList.getCountCities(); i++) {
            City city = new City();
            city.setName(br.readLine());
            city.setNeighbors(Integer.parseInt(br.readLine()));
            for (int j = 0; j < city.getNeighbors(); j++) {
                String[] line = br.readLine().split(" ");
                city.addPath(new Path(i + 1, Integer.parseInt(line[0]), Integer.parseInt(line[1])));
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

    //method get way destination and print min cost this way
    public static void getCostPathDestination(ParseFile parseFile) {
        List<City> cityList = parseFile.getCities();
        for (int i = 0; i < parseFile.getRoadToCity().size(); i++) {
            String[] myWay = parseFile.getRoadToCity().get(i).split(" ");
            String cityFrom = myWay[0];
            String cityTo = myWay[1];
            System.out.println(minCostForDestination(cityList, cityFrom, cityTo));

        }
    }

    //method get min cost by way destination
    public static int minCostForDestination(List<City> cityList, String cityFrom, String cityTo) {
        List<List<Path>> mapRoads = new ArrayList<>();
        List<Integer> costForRoads = new ArrayList<>();
        List<Path> myPath = new ArrayList<>();
        List<City> citiesWhereBeen = new ArrayList<>();
        citiesWhereBeen.add(getCityByName(cityList, cityFrom));

        int index = cityList.indexOf(getCityByName(cityList, cityFrom)) + 1;
        //algorithm build map all roads
        do {
            for (int j = 0; j < citiesWhereBeen.get(citiesWhereBeen.size() - 1).getNeighbors(); j++) {
                List<Path> lastRoad = new ArrayList<>(myPath);
                lastRoad.add(new Path(index, citiesWhereBeen.get(citiesWhereBeen.size() - 1).getNeighbors() - 1));
                City city = getCityByIndex(cityList, citiesWhereBeen.get(citiesWhereBeen.size() - 1).getPaths().get(j).to);
                if (mapRoads.contains(lastRoad)) {
                    mapRoads.add(new ArrayList<>(myPath));
                    myPath.remove(myPath.size() - 1);
                    index = cityList.indexOf(citiesWhereBeen.get(citiesWhereBeen.size() - 2)) + 1;
                    citiesWhereBeen.remove(citiesWhereBeen.size() - 1);
                    break;
                }
                myPath.add(new Path(index, j));
                if (!mapRoads.contains(myPath)) {
                    if (!citiesWhereBeen.contains(city)) {
                        citiesWhereBeen.add(city);
                        index = cityList.indexOf(city) + 1;
                        if (city.equals(getCityByName(cityList, cityTo))) {
                            mapRoads.add(new ArrayList<>(myPath));
                            costForRoads.add(getCoast(myPath, cityList));
                            myPath.remove(myPath.size() - 1);
                            index = cityList.indexOf(citiesWhereBeen.get(citiesWhereBeen.size() - 2)) + 1;
                            citiesWhereBeen.remove(citiesWhereBeen.size() - 1);
                            break;
                        }
                        break;
                    } else {
                        mapRoads.add(new ArrayList<Path>(myPath));
                        myPath.remove(myPath.size() - 1);
                    }
                } else {
                    myPath.remove(myPath.size() - 1);
                }
            }
        } while (haventRoads(mapRoads, getCityByName(cityList, cityFrom)));
        Collections.sort(costForRoads);
        return costForRoads.get(0);
    }

    private static int getCoast(List<Path> paths, List<City> cityList) {
        int cost = 0;
        for (int i = 0; i < paths.size(); i++) {
            cost += cityList.get(paths.get(i).from - 1).getPaths().get(paths.get(i).to).price;
        }
        return cost;
    }

    private static boolean haventRoads(List<List<Path>> lists, City from) {
        int a = 0;
        for (int i = 0; i < lists.size(); i++) {
            if (lists.get(i).size() == 1) {
                a++;
            }
        }
        if (from.getNeighbors() == a) {
            return false;
        }
        return true;
    }

    private static City getCityByName(List<City> cities, String from) {
        for (City c : cities) {
            if (from.equals(c.getName())) {
                return c;
            }
        }
        return null;
    }

    private static City getCityByIndex(List<City> cities, int from) {
        return cities.get(from - 1);
    }

}

