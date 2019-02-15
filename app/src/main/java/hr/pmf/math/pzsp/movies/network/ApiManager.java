package hr.pmf.math.pzsp.movies.network;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class ApiManager {

    public static final String API_ENDPOINT = "https://api.themoviedb.org/3/";
    public static final String POSTER_ENDPOINT = "http://image.tmdb.org/t/p/w300";

    private static final Gson GSON = new GsonBuilder()
            .create();


    private static OkHttpClient client
            = new OkHttpClient.Builder()
            .addNetworkInterceptor(new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    final Request request = chain.request().newBuilder().build();
                    return chain.proceed(request);
                }
            }).build();

    private static Retrofit REST_ADAPTER = new Retrofit.Builder()
            .baseUrl(API_ENDPOINT)
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(GSON))
            .client(client).build();

    private static final MovieService MOVIE_SERVICE = REST_ADAPTER.create(MovieService.class);

    public static MovieService getService() {
        return MOVIE_SERVICE;
    }

    public static Retrofit getRestAdapter() {
        return REST_ADAPTER;
    }
}
