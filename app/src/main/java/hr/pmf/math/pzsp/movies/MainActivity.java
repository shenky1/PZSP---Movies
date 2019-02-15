package hr.pmf.math.pzsp.movies;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import hr.pmf.math.pzsp.movies.listeners.AnimationListenerAdapter;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.ivHollywood) ImageView ivHollywood;
    @BindView(R.id.ivLogo) ImageView ivLogo;
    @BindView(R.id.btnSearch) Button btnSearch;
    @BindView(R.id.btnTopRated) Button btnNowPlaying;
    @BindView(R.id.btnNowPlaying) Button btnTopRated;

    @BindView(R.id.etSearch) EditText etSearch;
    @BindView(R.id.llSearch) LinearLayout llSearch;

    @OnClick(R.id.btnSearch)
    public void search() {
        String text = etSearch.getText().toString();
        if (text.isEmpty()) {
            Toast.makeText(this, "There is no query", Toast.LENGTH_SHORT).show();
        } else {
            Intent intent = new Intent(MainActivity.this, MovieListActivity.class);
            intent.putExtra("searchText", text);
            intent.putExtra("type", "search");
            startActivity(intent);
        }
    }

    @OnClick(R.id.btnTopRated)
    public void getTopRated() {
        startActivity("top_rated");
    }

    @OnClick(R.id.btnNowPlaying)
    public void getNowPlaying() {
        startActivity("now_playing");
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        final Animation animationScale = AnimationUtils.loadAnimation(this, R.anim.scale);
        final Animation animationTranslate = AnimationUtils.loadAnimation(this, R.anim.translate);
        final Animation animationFadeIn = AnimationUtils.loadAnimation(this, R.anim.fadein);
        animationScale.setStartOffset(500);

        animationScale.setAnimationListener(new AnimationListenerAdapter() {
            @Override
            public void onAnimationStart(Animation animation) {
                super.onAnimationStart(animation);
                ivHollywood.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                llSearch.setVisibility(View.VISIBLE);
                btnSearch.setVisibility(View.VISIBLE);
                btnNowPlaying.setVisibility(View.VISIBLE);
                btnTopRated.setVisibility(View.VISIBLE);
                llSearch.startAnimation(animationFadeIn);
                btnSearch.startAnimation(animationFadeIn);
            }
        });

        ivLogo.startAnimation(animationTranslate);
        ivHollywood.startAnimation(animationScale);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    public void startActivity(String type) {
        Intent intent = new Intent(MainActivity.this, MovieListActivity.class);
        intent.putExtra("type", type);
        startActivity(intent);
    }
}
