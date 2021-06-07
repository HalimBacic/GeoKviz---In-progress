package geokviz;

import android.os.Parcel;
import android.os.Parcelable;
import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import java.util.ArrayList;
import java.util.List;

@Entity
public class User implements Parcelable {
    @PrimaryKey
    @NonNull
    private String username;
    private Integer points;
    private ArrayList<UserAnswerQuestion> questions;

    public User(String username, Integer points, ArrayList<UserAnswerQuestion> questions) {
        this.username = username;
        this.points = points;
        this.questions = questions;
    }

    protected User(Parcel in) {
        username = in.readString();
        if (in.readByte() == 0) {
            points = null;
        } else {
            points = in.readInt();
        }
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(username);
        if (points == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(points);
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getPoints() {
        return points;
    }

    public void setPoints(Integer points) {
        this.points = points;
    }

    public ArrayList<UserAnswerQuestion> getQuestions() {
        return questions;
    }

    public void setQuestions(ArrayList<UserAnswerQuestion> questions) {
        this.questions = questions;
    }


}
