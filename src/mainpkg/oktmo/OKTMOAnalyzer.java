package mainpkg.oktmo;

import mainpkg.settlement.OKTMOLevel;
import mainpkg.settlement.Place;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class OKTMOAnalyzer {

    private boolean placeHandler(String name, Pattern p){
        Matcher m = p.matcher(name);
        return m.matches();
    }

    public void searchPlaces(OKTMOData data, String regExp, int flag){
        ArrayList<Place> places = data.getPlaces();
        Pattern p = Pattern.compile(regExp, flag);
        for (Place place:places) {
            if(placeHandler(place.getName(), p)) {
                System.out.println(place);
            }
        }
    }





    public List<Place> findAllPlacesInGroup(OKTMOGroup group){

        List<Place> places = new ArrayList<>();

        switch (group.getLevel()){
            case REGION:
                // из региона извлекаем список районов
                List<OKTMOGroup> districts = group.getDescendants();
                List<OKTMOGroup> municipalities1 = new ArrayList<>();
                // получили список муниципальных образований
                for (OKTMOGroup t : districts) {
                    t.getDescendants().stream().collect(Collectors.toCollection(() -> municipalities1));
                }
                // получаем список городов
                for(OKTMOGroup gr : municipalities1){
                    gr.getPlaces().values().stream().collect(Collectors.toCollection(() -> places));
                }
                break;
            case DISTRICT:
                // из района извлекаем список МО
                List<OKTMOGroup> municipalities2 = group.getDescendants();
                // из МО получаем список городов
                for(OKTMOGroup gr : municipalities2){
                    gr.getPlaces().values().stream().collect(Collectors.toCollection(() -> places));
                }
                break;
            case MUNICIPALITY:
                // из МО извлекаем города
                group.getPlaces().values().stream().collect(Collectors.toCollection(() -> places));
                break;
        }
        return places;
    }

    public String findMostPopularPlaceName(OKTMOData data, String regionName){
        // получение региона по имени
        Stream<OKTMOGroup> region = data.getRegionGroups().values().stream().filter(x -> regionName.equals(x.getName()));
        // выделение группы
        OKTMOGroup group = region.findFirst().get();
        // получение списка населенных пунктов
        List<Place> places = findAllPlacesInGroup(group);
        // подсчитывается количество каждого НП
        Map<String, Long> a = places.stream().collect(Collectors.groupingBy(Place::getName, Collectors.counting()));
        // вывод самого популярного НП
        String place = a.entrySet().stream().max((Comparator.comparingLong(e->e.getValue()))).get().getKey();
        //a.entrySet().stream().sorted(Map.Entry.<String, Long>comparingByValue().reversed());
        return place;
    }

    public Map<String, Long> printStatusTableForRegion(OKTMOData data, String region){
        // получение региона по имени
        Stream<OKTMOGroup> reg = data.getRegionGroups().values().stream().filter(x -> region.equals(x.getName()));
        // выделение группы
        OKTMOGroup group = reg.findFirst().get();
        // получение списка населенных пунктов
        List<Place> places = findAllPlacesInGroup(group);
        Map<String, Long> a = places.stream().collect(Collectors.groupingBy(Place::getStatus, Collectors.counting()));
        return a;
    }
}
