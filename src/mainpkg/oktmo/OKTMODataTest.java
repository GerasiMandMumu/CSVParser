package mainpkg.oktmo;

import junit.framework.TestCase;
import mainpkg.settlement.Place;
import org.junit.Test;

public class OKTMODataTest extends TestCase {

    @Test
    public void testLengthPlaces() {

        OKTMOReader reader = new OKTMOReader();
        OKTMOData data = new OKTMOData();
        reader.readPlaces("data.csv", data); // читаем из файла

        long length = data.getLength();
        int num = 157665;
        assertEquals(num, length);
    }

    @Test
    public void testParamsPlaces(){

        OKTMOReader reader = new OKTMOReader();
        OKTMOData data = new OKTMOData();
        reader.readPlaces("data.csv", data); // читаем из файла

        // 10
        int position = 11;
        Place place = data.getPlaces().get(position);
        String name = place.getName();
        String status = place.getStatus();
        long code = place.getCode();
        assertEquals("Серебренниково", name);
        assertEquals("с", status);
        assertEquals(1601417106, code);

        // Последний
        position = (int)data.getLength() - 1;
        place = data.getPlaces().get(position);
        name = place.getName();
        status = place.getStatus();
        code = place.getCode();
        assertEquals("Биробиджан", name);
        assertEquals("г", status);
        assertEquals(99701000001L, code);
    }

}