package hr.pmf.math.pzsp.movies;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import hr.pmf.math.pzsp.movies.listeners.RecyclerViewClickListener;
import hr.pmf.math.pzsp.movies.models.Movie;

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.ViewHolder> {
    private List<Movie> movies;
    private Context context;
    private RecyclerViewClickListener<Movie> clickListener;

    public MoviesAdapter(Context context, RecyclerViewClickListener<Movie> clickListener) {
        this.movies = new ArrayList<>();
        this.context = context;
        this.clickListener = clickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_item_movie, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.tvTitle.setText(movies.get(position).getTitle());
        Uri uri = movies.get(position).getImageUri();
        Glide.with(context).load(uri)
                .asBitmap().placeholder(R.drawable.ic_person_details).into(holder.ivPicture);
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    protected class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_title)
        protected TextView tvTitle;

        @BindView(R.id.ivPicture)
        protected ImageView ivPicture;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);

            if (clickListener != null) {
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        clickListener.onClick(movies.get(getAdapterPosition()));
                    }
                });
            }
        }
    }

    public void addMovie(Movie m) {
        movies.add(m);
        notifyDataSetChanged();
    }

    public ArrayList<Movie> getMovies() {
        return (ArrayList<Movie>) movies;
    }

    public void setMovies(ArrayList<Movie> movies) {
        this.movies = movies;
        notifyDataSetChanged();
    }
}

