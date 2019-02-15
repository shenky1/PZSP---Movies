package hr.pmf.math.pzsp.movies;

import android.graphics.Color;
import android.support.annotation.Nullable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;


import butterknife.BindView;
import butterknife.ButterKnife;
import hr.pmf.math.pzsp.movies.models.Movie;

public class DetailsActivity extends AppCompatActivity {

    private Movie movie;
    private static final String NO_DATA = "N/A";

    @BindView(R.id.tvTitle) TextView tvTitle;
    @BindView(R.id.tvDesc) TextView tvDesc;
    @BindView(R.id.tvNumVotes) TextView tvNumVotes;
    @BindView(R.id.tvUserRating) TextView tvUserRating;
    @BindView(R.id.ivPicture) ImageView ivPicture;
    @BindView(R.id.tvReleaseDate) TextView tvReleaseDate;
    @BindView(R.id.toolbar) Toolbar toolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        ButterKnife.bind(this);

        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setTitle("Details");

        movie = getIntent().getParcelableExtra("movie");
        tvTitle.setText(isEmpty(movie.getTitle()));
        tvDesc.setText(isEmpty(movie.getDescription()));


        tvUserRating.setText(isEmpty(movie.getVoteAverage()));
        tvNumVotes.setText(isEmpty(movie.getVoteCount()));
        tvReleaseDate.setText(isEmpty(movie.getReleaseDate()));
        ivPicture.setScaleType(ImageView.ScaleType.FIT_XY);
        Glide.with(this).load(movie.getImageUri()).asBitmap().placeholder(R.drawable.ic_person_details)
                .into(ivPicture);
    }


    private String isEmpty(String data) {
        if (data != null && data.isEmpty()) {
            return NO_DATA;
        } else {
            return data;
        }
    }
}
