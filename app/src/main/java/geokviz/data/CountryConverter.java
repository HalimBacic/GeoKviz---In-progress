package geokviz.data;

import androidx.room.TypeConverter;

import java.util.ArrayList;

public class CountryConverter {

    @TypeConverter
    public static String arrayToString(ArrayList<String> list)
    {
        String returnString = "";

        for (String str:list) {
            returnString+=" "+str;
        }

        return returnString;
    }

    @TypeConverter
    public static ArrayList<String> stringToArray(String allinone)
    {
        ArrayList<String> list = new ArrayList<>();
        if (allinone != null) {
            String[] strings = allinone.split(" ");
            for (String str : strings) {
                list.add(str);
            }
            return list;
        }

        return null;
    }
}
