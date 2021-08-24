package mainpkg.oktmo;

import mainpkg.Main;
import mainpkg.settlement.Place;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class OKTMOReaderTest {

    private final static String fileName = "data.csv";

    @Test
    public void readPlacesTest() {

        OKTMOReader reader = new OKTMOReader();
        OKTMOData data = new OKTMOData();
        long nano_startTime = System.nanoTime();
        reader.readPlaces(fileName, data); // читаем из файла
        long nano_endTime = System.nanoTime();
        long nanoTime1 = nano_endTime - nano_startTime;

        // с помощью регулярного выражения
        String regExp = Main.regExp1;
        reader = new OKTMOReader();
        data = new OKTMOData();
        nano_startTime = System.nanoTime();
        reader.readPlaces(fileName, data, regExp); // читаем из файла
        nano_endTime = System.nanoTime();
        long nanoTime2 = nano_endTime - nano_startTime;

        assertEquals(nanoTime1, nanoTime2);
    }

    @Test
    public void equalsPlacesTest(){
        OKTMOReader reader1 = new OKTMOReader();
        OKTMOData data1 = new OKTMOData();
        reader1.readPlaces(fileName, data1); // читаем из файла
        data1.copyPlaces(); // копируем
        data1.sortPlaces();
        ArrayList<Place> list1 = data1.getPlaces();

        String regExp = Main.regExp1;
        OKTMOReader reader2 = new OKTMOReader();
        OKTMOData data2 = new OKTMOData();
        reader2.readPlaces(fileName, data2, regExp); // читаем из файла
        data2.copyPlaces(); // копируем
        data2.sortPlaces();
        ArrayList<Place> list2 = data1.getPlaces();

        assertEquals(list1.get(0), list2.get(0));
        assertEquals(list1.get(1), list2.get(1));
        assertEquals(list1.get(2), list2.get(2));
        assertEquals(list1.get(3), list2.get(3));
    }


    @Test
    public void readGroupTest(){
        OKTMOReader reader = new OKTMOReader();
        OKTMOData data = new OKTMOData();
        reader.readGroup(fileName, data, Main.regExp4, Main.regExp1);
        assertEquals(10, data.getDistrictGroups().get(70628L).getDescendants().size());

        assertEquals("Киреевский муниципальный район", data.getDistrictGroups().get(70628L).getName());
    }
}