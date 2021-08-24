package mainpkg.oktmo;

import jdk.nashorn.api.tree.Tree;
import mainpkg.settlement.Place;
import mainpkg.settlement.PlaceComparator;

import java.util.*;

public class OKTMOData {

    private ArrayList<Place> listOfPlaces = new ArrayList<>();
    private ArrayList<Place> sortedPlace;
    private HashSet<String> allStatuses = new HashSet<>();

    private TreeMap<Long, OKTMOGroup> regionGroups = new TreeMap<>();
    private TreeMap<Long, OKTMOGroup> districtGroups = new TreeMap<>();
    private TreeMap<Long, OKTMOGroup> municipalityGroups = new TreeMap<>();

    private TreeMap<Long, Place> places = new TreeMap<>();

    public TreeMap<Long, OKTMOGroup> getRegionGroups(){
        return this.regionGroups;
    }

    public TreeMap<Long, OKTMOGroup> getDistrictGroups(){
        return this.districtGroups;
    }

    public TreeMap<Long, OKTMOGroup> getMunicipalityGroups(){
        return this.municipalityGroups;
    }

    public void addRegionGroups(OKTMOGroup group){
        this.regionGroups.put(group.getCode(), group);
    }

    public void addDistrictGroups(OKTMOGroup group){
        this.districtGroups.put(group.getCode(), group);
    }

    public void addMunicipalityGroups(OKTMOGroup group){
        this.municipalityGroups.put(group.getCode(), group);
    }

    public void printGroups(){
        for (Map.Entry<Long, OKTMOGroup> entry : this.regionGroups.entrySet()) {
            System.out.println(entry);
        }
    }

    public void printStatuses(){
        allStatuses.forEach(s-> System.out.print(s + "; "));
        //allStatuses.forEach(System.out::print);
        //System.out.println(allStatuses);
    }

    public long getLength(){
        return this.listOfPlaces.size();
    }

    public ArrayList<Place> getPlaces(){
        return this.listOfPlaces;
    }

    public void sortPlaces(){
        Comparator speedComparator = new PlaceComparator();
        Collections.sort(sortedPlace, speedComparator);
    }

    public void printPlaces(){
        listOfPlaces.forEach(System.out::println);
    }

    public void printSortedPlaces(){
        sortedPlace.forEach(System.out::println);
    }

    public void copyPlaces(){
        if(!listOfPlaces.isEmpty()){
            sortedPlace = new ArrayList<>(listOfPlaces);
        }
    }

    public void addPlace(Place place){
        listOfPlaces.add(place);
    }

    public void addPlace(Place place, long code){
        places.put(code, place);
    }

    public void addStatus(String status){
        allStatuses.add(status);
    }

}
