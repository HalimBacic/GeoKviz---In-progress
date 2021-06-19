package geokviz.data;

import androidx.room.TypeConverter;
import java.util.ArrayList;
import geokviz.UserAnswerQuestion;

public class QuestionConverter {

    @TypeConverter
    public static String arrayToString(ArrayList<UserAnswerQuestion> list)
    {
        String returnString = "";

        for (UserAnswerQuestion uaq:list) {
            returnString+=uaq.getQuestion()+"-"+uaq.getAnswer()+"-"+uaq.getFlag()+"|";
        }

        return returnString;
    }

    @TypeConverter
    public static ArrayList<UserAnswerQuestion> stringToArray(String allinone)
    {
        ArrayList<UserAnswerQuestion> list = new ArrayList<>();

        if (allinone != null) {
            String[] allquest = allinone.split("\\|");
            for (String uaq : allquest) {
                String[] splitetuaq = uaq.split("-");
                UserAnswerQuestion userAnswerQuestion = new UserAnswerQuestion(splitetuaq[0],splitetuaq[1],Boolean.parseBoolean(splitetuaq[2]));
                list.add(userAnswerQuestion);
            }
            return list;
        }
        return null;
    }
}
