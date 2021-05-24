package geokviz;

import java.util.ArrayList;

public class FlagQuestions {

    private String flag;
    private String answer;
    private ArrayList<String> chars;

    public FlagQuestions(String flag, String answer, ArrayList<String> chars) {
        this.flag = flag;
        this.answer = answer;
        this.chars = chars;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public ArrayList<String> getChars() {
        return chars;
    }

    public void setChars(ArrayList<String> chars) {
        this.chars = chars;
    }
}
