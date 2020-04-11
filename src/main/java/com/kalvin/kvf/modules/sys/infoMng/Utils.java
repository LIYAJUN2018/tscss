package com.kalvin.kvf.modules.sys.infoMng;

import java.util.HashMap;
import java.util.Map;

public class Utils {

    public static int getNumber(String time){
        Map<String, Integer> map = new HashMap<>();
        map.put("01", 1);
        map.put("05", 2);
        map.put("09", 3);
        map.put("13", 4);
        map.put("17", 5);
        map.put("02", 6);
        map.put("06", 7);
        map.put("10", 8);
        map.put("14", 9);
        map.put("18", 10);
        map.put("03", 11);
        map.put("07", 12);
        map.put("11", 13);
        map.put("15", 14);
        map.put("19", 15);
        map.put("04", 16);
        map.put("08", 17);
        map.put("12", 18);
        map.put("16", 19);
        map.put("20", 20);
        return map.get(time);
    }

}
