package geokviz;

import java.util.ArrayList;

public class Question {

    public String question;
    public String correct;
    public ArrayList<String> incorrect;

    public Question(String question, String correct, ArrayList<String> incorrect) {
        this.question = question;
        this.correct = correct;
        this.incorrect = incorrect;
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
}
