package geokviz;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class FlagQuestions implements Parcelable {

    private String flag;
    private String answer;
    private ArrayList<String> chars;

    public FlagQuestions(String flag, String answer, ArrayList<String> chars) {
        this.flag = flag;
        this.answer = answer;
        this.chars = chars;
    }

    protected FlagQuestions(Parcel in) {
        flag = in.readString();
        answer = in.readString();
        chars = in.createStringArrayList();
    }

    public static final Creator<FlagQuestions> CREATOR = new Creator<FlagQuestions>() {
        @Override
        public FlagQuestions createFromParcel(Parcel in) {
            return new FlagQuestions(in);
        }

        @Override
        public FlagQuestions[] newArray(int size) {
            return new FlagQuestions[size];
        }
    };

    public String getFlag() {
        return flag.toLowerCase();
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(flag);
        dest.writeString(answer);
        dest.writeStringList(chars);
    }
}
