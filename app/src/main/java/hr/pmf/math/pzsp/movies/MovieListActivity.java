package hr.pmf.math.pzsp.movies;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import hr.pmf.math.pzsp.movies.listeners.RecyclerViewClickListener;
import hr.pmf.math.pzsp.movies.models.Movie;
import hr.pmf.math.pzsp.movies.network.ApiManager;
import hr.pmf.math.pzsp.movies.network.MoviesResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieListActivity extends AppCompatActivity {

    @BindView(R.id.rvMovies) public RecyclerView movies;
    @BindView(R.id.linearLayout1) public LinearLayout noMovieLayout;
    @BindView(R.id.toolbar) Toolbar toolbar;

    public MoviesAdapter adapter;
    private ProgressDialog progressDialog;
    private Call<MoviesResponse> moviesResponseCall;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_list);
        ButterKnife.bind(this);

        toolbar.setTitleTextColor(Color.WHITE);
        if(getIntent().getStringExtra("type").equals("search")) {
            toolbar.setTitle("Movies containing \"" + getIntent().getStringExtra("searchText") + "\"");
        } else if(getIntent().getStringExtra("type").equals("top_rated")) {
            toolbar.setTitle("Top rated movies");
        } else {
            toolbar.setTitle("Now in cinema");
        }
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Loading");
        progressDialog.setMessage("Please wait!");

        adapter = new MoviesAdapter(this, new RecyclerViewClickListener<Movie>() {
            @Override
            public void onClick(Movie movie) {
                Intent intent = new Intent(MovieListActivity.this, DetailsActivity.class);
                intent.putExtra("movie", movie);
                startActivityForResult(intent, 1);
            }
        });
        movies.setAdapter(adapter);

        if (savedInstanceState == null) {
            init();
        }
    }

    private void init() {
        if (isOnline()) {
            getMoviesListFromAPI();
        } else {
            showDialog(this, "Your device is not connected to the internet.");
        }
    }

    private void getMoviesListFromAPI() {
        showDialog();
        if(getIntent().getStringExtra("type").equals("search")) {
            String query = getIntent().getStringExtra("searchText");
            moviesResponseCall = ApiManager.getService().searchMovies(query);
        } else if(getIntent().getStringExtra("type").equals("top_rated")) {
            moviesResponseCall = ApiManager.getService().getTopRatedMovies();
        } else {
            moviesResponseCall = ApiManager.getService().getNowPlaying();
        }
        moviesResponseCall.enqueue(new Callback<MoviesResponse>() {
            @Override
            public void onResponse(Call<MoviesResponse> call, Response<MoviesResponse> response) {
                if (response.isSuccessful()) {
                    setMoviesList(response.body().getResults());
                } else {
                    showDialog(MovieListActivity.this, "Error while trying to receive list from server.");
                }
                hideDialog();
            }

            @Override
            public void onFailure(Call<MoviesResponse> call, Throwable t) {
                hideDialog();
                if (!call.isCanceled()) {
                    if (t instanceof UnknownHostException) {
                        showDialog(MovieListActivity.this, "Your device is not connected to the interet.");
                    } else {
                        showDialog(MovieListActivity.this, "Error while reading list from server.");
                    }
                }
            }
        });
    }

    private void setMoviesList(List<MoviesResponse.MovieData> response) {
        for (int i = 0; i < response.size(); i++) {
            Movie movie = new Movie();
            MoviesResponse.MovieData movieData = response.get(i);
            String id = response.get(i).getId();
            movie.setId(id);
            movie.setTitle(movieData.getTitle());
            movie.setVoteCount(movieData.getVoteCount());
            movie.setVoteAverage(movieData.getVoteAverage());
            movie.setPosterPath(movieData.getPosterPath());
            movie.setDescription(movieData.getDescription());
            movie.setReleaseDate(movieData.getReleaseDate());
            movie.setImageUri(Uri.parse(ApiManager.POSTER_ENDPOINT + movieData.getPosterPath()));
            adapter.addMovie(movie);
        }

        noMovieLayout.setVisibility(View.INVISIBLE);
        movies.setAdapter(adapter);
    }

    public void showDialog() {
        progressDialog.show();
    }

    public void hideDialog() {
        progressDialog.hide();
    }

    public boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnected();
    }

    public static void showDialog(Context context, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Error");
        builder.setMessage(message)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {}
                });
        AlertDialog alert = builder.create();
        alert.show();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList("movies", adapter.getMovies());
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        List<Movie> movies = savedInstanceState.getParcelableArrayList("movies");
        if (movies != null && !movies.isEmpty()) {
            noMovieLayout.setVisibility(View.INVISIBLE);
        }
        adapter.setMovies((ArrayList<Movie>) movies);
        super.onRestoreInstanceState(savedInstanceState);
    }
}
