package mainpackage.traderevtest.view;

import android.annotation.TargetApi;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Rect;
import android.os.Build;
import android.os.Handler;
import android.os.Parcelable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.transition.Explode;
import android.transition.Fade;
import android.transition.Transition;
import android.transition.TransitionManager;
import android.transition.TransitionSet;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import mainpackage.traderevtest.R;
import mainpackage.traderevtest.adapter.UnsplashPhotoAdapter;
import mainpackage.traderevtest.databinding.ActivityMainBinding;
import mainpackage.traderevtest.listener.InfiniteScrollListener;
import mainpackage.traderevtest.model.UnsplashPhoto;
import mainpackage.traderevtest.viewmodel.UnsplashPhotosViewModel;

public class MainActivity extends AppCompatActivity implements UnsplashPhotoAdapter.OnItemClickListener {
    private static final String PER_PAGE = "30";
    private static final String ORDER_BY = "latest";
    private static final int NUM_COLS = 3;
    private ArrayList<UnsplashPhoto> unsplashPhotos;
    private UnsplashPhotoAdapter mUnsplashPhotoAdapter;
    private ActivityMainBinding mActivityMainBinding;
    private int currentPage = 1;

    private UnsplashPhotosViewModel unsplashPhotosViewModel;
    private GridLayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mActivityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        unsplashPhotosViewModel = ViewModelProviders.of(this).get(UnsplashPhotosViewModel.class);
        mLayoutManager = new GridLayoutManager(this, 2);
        mActivityMainBinding.recyclerView.setLayoutManager(mLayoutManager);
        //mActivityMainBinding.recyclerView.addItemDecoration(new DividerItemDecoration(MainActivity.this, DividerItemDecoration.VERTICAL));
        unsplashPhotos = new ArrayList<>();
        mUnsplashPhotoAdapter = new UnsplashPhotoAdapter(unsplashPhotos, this);
        mActivityMainBinding.recyclerView.setAdapter(mUnsplashPhotoAdapter);
        mActivityMainBinding.recyclerView.setHasFixedSize(true);
        mActivityMainBinding.recyclerView.setItemViewCacheSize(30);
        mActivityMainBinding.recyclerView.setDrawingCacheEnabled(true);
        mActivityMainBinding.recyclerView.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
        addDataToList(currentPage);

        mActivityMainBinding.recyclerView.addOnScrollListener(new InfiniteScrollListener() {
            @Override
            public void onLoadMore() {
                addDataToList(currentPage);
            }
        });
    }

    private void addDataToList(int page) {
        mActivityMainBinding.itemProgressBar.setVisibility(View.VISIBLE);

        new Handler().postDelayed(() -> unsplashPhotosViewModel.getUnsplashPhotos(String.valueOf(page), PER_PAGE, ORDER_BY).observe(MainActivity.this, unsplashPhotosFromApi -> {
            if (unsplashPhotosFromApi != null) {
                unsplashPhotos.addAll(unsplashPhotosFromApi);
                mUnsplashPhotoAdapter.notifyDataSetChanged();
                mActivityMainBinding.itemProgressBar.setVisibility(View.GONE);
            }
        }), 1500);
        currentPage++;
    }

    @Override
    public void onItemClick(View view, UnsplashPhoto unsplashPhoto,int position) {
        ActivityOptionsCompat options = ActivityOptionsCompat.
                makeSceneTransitionAnimation(this, view, "transition");
        int revealX = (int) (view.getX() + view.getWidth() / 2);
        int revealY = (int) (view.getY() + view.getHeight() / 2);

        Intent intent = new Intent(this, UnsplashPhotoDetailActivity.class);
        intent.putExtra(UnsplashPhotoDetailActivity.EXTRA_CIRCULAR_REVEAL_X, revealX);
        intent.putExtra(UnsplashPhotoDetailActivity.EXTRA_CIRCULAR_REVEAL_Y, revealY);
        intent.putExtra("currentPage",currentPage);
        intent.putExtra("position",position);
        intent.putParcelableArrayListExtra("data",unsplashPhotos);

        ActivityCompat.startActivity(this, intent, options.toBundle());
    }
}
