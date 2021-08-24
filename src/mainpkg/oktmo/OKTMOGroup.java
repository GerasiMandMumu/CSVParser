package mainpkg.oktmo;

import mainpkg.settlement.OKTMOLevel;
import mainpkg.settlement.Place;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

public class OKTMOGroup {

    private OKTMOLevel level;
    private String name;
    private long code;

    private List<OKTMOGroup> descendants = new ArrayList<>();
    private TreeMap<Long, Place> places = new TreeMap<>();

    public OKTMOGroup(String name, long code, OKTMOLevel level){
        this.code = code;
        this.name = name;
        this.level = level;
    }

    public void addPlaces(long code, Place place){
        this.places.put(code, place);
    }

    public TreeMap<Long, Place> getPlaces(){
        return this.places;
    }

    public void addDescendants(OKTMOGroup elem){
        this.descendants.add(elem);
    }

    public List<OKTMOGroup> getDescendants(){
        return this.descendants;
    }

    public String getName() {
        return this.name;
    }

    public long getCode() {
        return this.code;
    }

    public OKTMOLevel getLevel() {
        return this.level;
    }

    @Override
    public String toString(){
        return " Имя: " + this.name + " Уровень: " + this.level;
    }

}
