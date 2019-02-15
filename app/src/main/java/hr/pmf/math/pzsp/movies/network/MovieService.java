package hr.pmf.math.pzsp.movies.network;


import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MovieService {

    @GET("search/movie?api_key=878a5b3ed03069904813407bb82f93b3")
    Call<MoviesResponse> searchMovies(@Query("query") String query);

    @GET("movie/top_rated?api_key=878a5b3ed03069904813407bb82f93b3")
    Call<MoviesResponse> getTopRatedMovies();

    @GET(" movie/now_playing?api_key=878a5b3ed03069904813407bb82f93b3")
    Call<MoviesResponse> getNowPlaying();

}