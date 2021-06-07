package geokviz;

public class UserAnswerQuestion {

    private String question; //question in base
    private String answer; //user answer
    private Boolean flag; //true if correct, false if incorrect

    public UserAnswerQuestion(String question, String answer, Boolean flag) {
        this.question = question;
        this.answer = answer;
        this.flag = flag;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public Boolean getFlag() {
        return flag;
    }

    public void setFlag(Boolean flag) {
        this.flag = flag;
    }
}
