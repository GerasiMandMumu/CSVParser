package mainpkg;


import mainpkg.oktmo.OKTMOAnalyzer;
import mainpkg.oktmo.OKTMOData;
import mainpkg.oktmo.OKTMOGroup;
import mainpkg.oktmo.OKTMOReader;
import mainpkg.settlement.Place;

import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {

    // имя файла
    private final static String fileName = "data.csv";
    // считывание НП
    public final static String regExp1 = "(\\d\\d);(\\d\\d\\d);(\\d\\d\\d);(\\d\\d\\d);(\\d);(\\d);" +
            "([-№/\\dа-я]+[-\\d№\\sа-я]*) ([-/№А-Я][-/А-Я№\\sа-я\\d]+)"; //?
    // название содержит меньше 6 букв и заканчиваются на -ово
    private final static String regExp2 = "^.{0,2}ово$";
    // названия, которые начинаются и заканчиваются на одну и ту же согласную букву
    private final static String regExp3 = "(?i)^([^аеёиоуыэюя]).+\\1$";
    // группы
    public final static String regExp4 = "(\\d\\d);(\\d\\d\\d);(\\d\\d\\d);(\\d\\d\\d);(\\d);(\\d);([А-Яа-я]+.*?);";

    public static void main(String[] args){

        OKTMOReader reader = new OKTMOReader();
        OKTMOData data = new OKTMOData();
        OKTMOAnalyzer analyzer = new OKTMOAnalyzer();

        reader.readGroup(fileName, data, regExp4, regExp1);

//        String place = analyzer.findMostPopularPlaceName(data, "Муниципальные образования Тульской области");
//        System.out.println(place);

        System.out.println(analyzer.printStatusTableForRegion(data, "Муниципальные образования Тульской области"));


        //OKTMOGroup q = data.getDistrictGroups().get(70628L); // получили группу районов для Тульской области
        //System.out.println(analyzer.findAllPlacesInGroup(q).size());
        //System.out.println(analyzer.findAllPlacesInGroup(q));


//        OKTMOGroup q = data.getMunicipalityGroups().get(70628101L); // муниципальное образование
//        System.out.println(q.getPlaces());

//        Place place = new Place(70628101001L, "Киреевск", "г");
//        q.addPlaces(70628101001L, place);
//        System.out.println(q.getPlaces());

        //data.printGroups();

        // читаем из файла
        //reader.readPlaces(fileName, data, regExp1);
        //reader.readPlaces(fileName, data);

        //System.out.println("Несортированные-----------------------------------");
        //data.printPlaces(); // несортированные места

        //System.out.println("\nСортированные-----------------------------------");
        //data.copyPlaces(); // копируем
        //data.sortPlaces(); // сортируем
        //data.printSortedPlaces(); // сортированный по имени список

        //data.printStatuses();

//        System.out.println("Название содержит меньше 6 букв и заканчивается на -ово-----------------");
//        analyzer.searchPlaces(data, regExp2, 0);
//        System.out.println("\nНазвания, которые начинаются и заканчиваются на одну и ту же согласную букву-----------");
//        analyzer.searchPlaces(data, regExp3, Pattern.UNICODE_CASE);

    }
}
