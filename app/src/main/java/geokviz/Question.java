package geokviz;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;

public class Question implements Parcelable {

    public String question;
    public String correct;
    public ArrayList<String> incorrect;
    public String hint;

    public Question(String question, String correct, ArrayList<String> incorrect,String hint) {
        this.question = question;
        this.correct = correct;
        this.incorrect = incorrect;
        this.hint = hint;
    }

    protected Question(Parcel in) {
        question = in.readString();
        correct = in.readString();
        incorrect = in.createStringArrayList();
    }

    public static final Creator<Question> CREATOR = new Creator<Question>() {
        @Override
        public Question createFromParcel(Parcel in) {
            return new Question(in);
        }

        @Override
        public Question[] newArray(int size) {
            return new Question[size];
        }
    };

    public String getHint() {
        return hint;
    }

    public void setHint(String hint) {
        this.hint = hint;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getCorrect() {
        return correct;
    }

    public void setCorrect(String correct) {
        this.correct = correct;
    }

    public ArrayList<String> getIncorrect() {
        return incorrect;
    }

    public void setIncorrect(ArrayList<String> incorrect) {
        this.incorrect = incorrect;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(question);
        dest.writeString(correct);
        dest.writeStringList(incorrect);
    }
}
