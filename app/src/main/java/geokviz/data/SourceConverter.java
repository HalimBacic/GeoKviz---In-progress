package geokviz.data;

import androidx.room.TypeConverter;

import geokviz.Source;

public class SourceConverter {

    @TypeConverter
    public static String sourceToString(Source source)
    {
        String str="";
        str+=source.getId()+" "+source.getName();
        return str;
    }

    @TypeConverter
    public Source stringToSource(String str)
    {
        String[] splited = str.split(" ");
        return new Source(splited[0],splited[1]);
    }
}
