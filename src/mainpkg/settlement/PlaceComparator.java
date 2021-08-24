package mainpkg.settlement;

import java.util.Comparator;

public class PlaceComparator implements Comparator<Place> {
    @Override
    public int compare(Place place1, Place place2){
        return (place1.getName()).compareTo(place2.getName());
    }
}
