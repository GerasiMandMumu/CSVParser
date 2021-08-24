package mainpkg.oktmo;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import mainpkg.settlement.OKTMOLevel;
import mainpkg.settlement.Place;

public class OKTMOReader {

    public void readGroup(String fileName, OKTMOData data, String regExp1, String regExp2){
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(fileName), "cp1251"))) {
            String s, nameGroup = "", status = "", name = "", firstStage = "",
                    secondStage = "", thirdStage = "", fourthStage = "", code = "", codeGroup = "";
            OKTMOLevel levelGroup = OKTMOLevel.MUNICIPALITY;
            Pattern p1 = Pattern.compile(regExp1);
            Pattern p2 = Pattern.compile(regExp2);
            Matcher m1, m2;
            while ((s = br.readLine()) != null) {
                if(!s.contains("Населенные пункты") && !s.contains("Городские поселения") && !s.contains("Межселенн")) {
                    s = s.replace("\"", "");
                    m1 = p1.matcher(s);
                    while (m1.find()) {
                        firstStage = m1.group(1);
                        secondStage = m1.group(2);
                        thirdStage = m1.group(3);
                        fourthStage = m1.group(4);
                        nameGroup = m1.group(7);
                    }

                    // регион
                    if("000".equals(secondStage) && "000".equals(thirdStage) && "000".equals(fourthStage)){
                        levelGroup = OKTMOLevel.REGION;
                        codeGroup = firstStage;
                        OKTMOGroup group = new OKTMOGroup(nameGroup, Long.parseLong(codeGroup), levelGroup);
                        data.addRegionGroups(group);
                    }
                    // район, город, городской округ
                    else if("000".equals(thirdStage) && "000".equals(fourthStage)){
                        levelGroup = OKTMOLevel.DISTRICT;
                        codeGroup = firstStage + secondStage;
                        OKTMOGroup group = new OKTMOGroup(nameGroup, Long.parseLong(codeGroup), levelGroup);

                        TreeMap<Long, OKTMOGroup> prevGroup = data.getRegionGroups();
                        OKTMOGroup currGroup = prevGroup.get(Long.parseLong(firstStage));
                        currGroup.addDescendants(group);
                        data.addDistrictGroups(group);
                    }
                    // сельсовет, муниципальное образование
                    else if("000".equals(fourthStage)){
                        levelGroup = OKTMOLevel.MUNICIPALITY;
                        codeGroup = firstStage + secondStage + thirdStage;
                        OKTMOGroup group = new OKTMOGroup(nameGroup, Long.parseLong(codeGroup), levelGroup);

                        TreeMap<Long, OKTMOGroup> prevGroup = data.getDistrictGroups();
                        OKTMOGroup currGroup = prevGroup.get(Long.parseLong(firstStage + secondStage));
                        currGroup.addDescendants(group);
                        data.addMunicipalityGroups(group);
                    }

                    // населенный пункт
                    else{
                        // получение населенного пункта
                        m2 = p2.matcher(s);
                        while (m2.find()) {
                            code = m2.group(1) + m2.group(2) + m2.group(3) + m2.group(4);
                            status = m2.group(7);
                            name = m2.group(8);
                        }
                        Place place = new Place(Long.parseLong(code), name, status);
                        data.addPlace(place);
                        data.addStatus(status);
                        data.addPlace(place, Long.parseLong(code));

                        TreeMap<Long, OKTMOGroup> prevGroup = data.getMunicipalityGroups();
                        OKTMOGroup currGroup = prevGroup.get(Long.parseLong(firstStage + secondStage + thirdStage));
                        if(currGroup != null){
                            currGroup.addPlaces(Long.parseLong(code), place);
                        }
                    }
                }
            }
        }
        catch (IOException e) {
            e.getMessage();
        }
    }

    private int findSpace(String str){
        int index = str.indexOf(" ");
        return index;
    }

    private int findUpperWord(String str){
        int index = 0;
        for (int i = 0; i < str.length(); i++){
            if(Character.isUpperCase(str.charAt(i))){
                index = i;
                break;
            }
        }
        return index;
    }

    private String getStatus(String str, int pos){
        String status = str.substring(0, pos - 1);
        return status;
    }

    private String getName(String str, int pos){
        String name = str.substring(pos);
        return name;
    }

    public void readPlaces(String fileName, OKTMOData data, String regExp){
        Pattern p = Pattern.compile(regExp);
        String code, name,status;
        try(BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(fileName), "cp1251"))){
            String s, test;
            Matcher m;
            while( (s = br.readLine()) != null){
                if(!s.contains("Населенные пункты")) {
                    s = s.replace("\"", "");
                    m = p.matcher(s);
                    if (m.find()) {
                        // String, string ...
                        code = m.group(1) + m.group(2) + m.group(3) + m.group(4);
                        test = m.group(6);
                        status = m.group(7);
                        name = m.group(8);
                        if("2".equals(test)) {
                            Place place = new Place(Long.parseLong(code), name, status);
                            data.addPlace(place);
                            data.addStatus(status);
                        }
                    }else{
                        //System.out.println(s);
                    }
                }
            }
        }catch (IOException e){
            e.getMessage();
        }
    }

    public void readPlaces(String fileName, OKTMOData data){
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(fileName), "cp1251"))) {
            String s, status, name;
            int spaceIndex = 0, upperIndex = 0;
            long code = 0;
            while ((s = br.readLine()) != null) {
                if(!s.contains("Населенные пункты")) {
                    String[] vals = s.replace("\"", "").split(";", -1);
                    if (Character.isLowerCase(vals[6].charAt(0)) && "2".equals(vals[5])) {
                        spaceIndex = findSpace(vals[6]);
                        upperIndex = findUpperWord(vals[6]);
                        if (upperIndex != 0) {
                            status = getStatus(vals[6], upperIndex);
                            name = getName(vals[6], upperIndex);
                        } else {
                            status = getStatus(vals[6], spaceIndex);
                            name = getName(vals[6], spaceIndex);
                        }
                        code = Long.parseLong(vals[0] + vals[1] + vals[2] + vals[3]); // собираем код
                    } else {
                        continue;
                    }
                    // добавляем НП в список
                    Place place = new Place(code, name, status);
                    data.addPlace(place);
                    data.addStatus(status);
                }
            }
        }
        catch (IOException e) {
            e.getMessage();
        }
    }
}

