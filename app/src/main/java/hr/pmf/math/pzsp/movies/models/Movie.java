package hr.pmf.math.pzsp.movies.models;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

public class Movie implements Parcelable {

    private String id;

    private String title;

    private String voteAverage;

    private String voteCount;

    private String posterPath;

    private String releaseDate;

    private String description;

    private Uri imageUri;

    public Movie() {

    }

    public Movie(String id, String title, String voteAverage, String voteCount, String posterPath,
                 String releaseDate, String description, Uri imageUri) {
        this.id = id;
        this.title = title;
        this.voteAverage = voteAverage;
        this.voteCount = voteCount;
        this.posterPath = posterPath;
        this.releaseDate = releaseDate;
        this.description = description;
        this.imageUri = imageUri;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.title);
        dest.writeString(this.voteAverage);
        dest.writeString(this.voteCount);
        dest.writeString(this.posterPath);
        dest.writeString(this.releaseDate);
        dest.writeString(this.description);
        dest.writeParcelable(this.imageUri, flags);

    }

    protected Movie(Parcel in) {
        this.id = in.readString();
        this.title = in.readString();
        this.voteAverage = in.readString();
        this.voteCount = in.readString();
        this.posterPath = in.readString();
        this.releaseDate = in.readString();
        this.description = in.readString();
        this.imageUri = in.readParcelable(Uri.class.getClassLoader());

    }

    public static final Parcelable.Creator<Movie> CREATOR = new Parcelable.Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel source) {
            return new Movie(source);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(String voteAverage) {
        this.voteAverage = voteAverage;
    }

    public String getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(String voteCount) {
        this.voteCount = voteCount;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }


    public Uri getImageUri() {
        return imageUri;
    }

    public void setImageUri(Uri imageUri) {
        this.imageUri = imageUri;
    }
}
