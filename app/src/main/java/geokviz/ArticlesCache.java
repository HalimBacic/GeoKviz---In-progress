package geokviz;

import android.os.Parcel;
import android.os.Parcelable;
import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class ArticlesCache implements Parcelable {

    @PrimaryKey
    @NonNull
    private int id;
    private String source;
    private String author;
    private String title;
    private String description;

    public ArticlesCache(int id, String source, String author, String title, String description) {
        this.id = id;
        this.source = source;
        this.author = author;
        this.title = title;
        this.description = description;
    }


    protected ArticlesCache(Parcel in) {
        id = in.readInt();
        source = in.readString();
        author = in.readString();
        title = in.readString();
        description = in.readString();
    }

    public static final Creator<ArticlesCache> CREATOR = new Creator<ArticlesCache>() {
        @Override
        public ArticlesCache createFromParcel(Parcel in) {
            return new ArticlesCache(in);
        }

        @Override
        public ArticlesCache[] newArray(int size) {
            return new ArticlesCache[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(source);
        dest.writeString(author);
        dest.writeString(title);
        dest.writeString(description);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public static Creator<ArticlesCache> getCREATOR() {
        return CREATOR;
    }
}
