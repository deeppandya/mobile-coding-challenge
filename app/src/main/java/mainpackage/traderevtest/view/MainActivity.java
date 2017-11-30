package mainpackage.traderevtest.view;

import android.app.Activity;
import android.app.Application;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import java.util.ArrayList;

import javax.inject.Inject;

import dagger.android.AndroidInjection;
import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;
import dagger.android.support.HasSupportFragmentInjector;
import mainpackage.traderevtest.R;
import mainpackage.traderevtest.adapter.UnsplashPhotoAdapter;
import mainpackage.traderevtest.listener.InfiniteScrollListener;
import mainpackage.traderevtest.model.UnsplashPhoto;
import mainpackage.traderevtest.utils.Constants;
import mainpackage.traderevtest.viewmodel.UnsplashPhotosViewModel;

import static dagger.internal.Preconditions.checkNotNull;

public class MainActivity extends AppCompatActivity implements UnsplashPhotoAdapter.OnItemClickListener {
    private static final String PER_PAGE = "30";
    private static final String ORDER_BY = "latest";
    private static final int NUM_COLS = 2;
    private ArrayList<UnsplashPhoto> unsplashPhotos;
    private UnsplashPhotoAdapter mUnsplashPhotoAdapter;
    private int currentPage = 1;
    private UnsplashPhotosViewModel unsplashPhotosViewModel;
    private RecyclerView recyclerView;
    private ProgressBar itemProgressBar;

    @Inject
    ViewModelProvider.Factory viewModelFactory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        inject();
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        unsplashPhotosViewModel = ViewModelProviders.of(this, viewModelFactory).get(UnsplashPhotosViewModel.class);

        setViews();

        setRecyclerViewForPhotos();

        addDataToList(currentPage);
    }



    private void setRecyclerViewForPhotos() {
        GridLayoutManager mLayoutManager = new GridLayoutManager(this, NUM_COLS);
        recyclerView.setLayoutManager(mLayoutManager);
        unsplashPhotos = new ArrayList<>();
        mUnsplashPhotoAdapter = new UnsplashPhotoAdapter(unsplashPhotos, this);
        recyclerView.setAdapter(mUnsplashPhotoAdapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemViewCacheSize(30);
        recyclerView.setDrawingCacheEnabled(true);
        recyclerView.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);

        recyclerView.addOnScrollListener(new InfiniteScrollListener() {
            @Override
            public void onLoadMore() {
                addDataToList(currentPage);
            }
        });
    }

    private void setViews() {
        recyclerView=findViewById(R.id.recycler_view);
        itemProgressBar=findViewById(R.id.item_progress_bar);
    }

    private void addDataToList(int page) {
        itemProgressBar.setVisibility(View.VISIBLE);

        new Handler().postDelayed(() -> unsplashPhotosViewModel.getUnsplashPhotos(String.valueOf(page), PER_PAGE, ORDER_BY).observe(MainActivity.this, unsplashPhotosFromApi -> {
            if (unsplashPhotosFromApi != null) {
                unsplashPhotos.addAll(unsplashPhotosFromApi);
                mUnsplashPhotoAdapter.notifyDataSetChanged();
                itemProgressBar.setVisibility(View.GONE);
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
        intent.putExtra(Constants.CURRENT_PAGE,currentPage);
        intent.putExtra(Constants.POSITION,position);
        intent.putParcelableArrayListExtra(Constants.PHOTOS,unsplashPhotos);

        ActivityCompat.startActivity(this, intent, options.toBundle());
    }

    private void inject() {
        Application application = getApplication();
        if(application == null) {
            application = (Application) getApplicationContext();
        }

        if (!(application instanceof HasActivityInjector)) {
            throw new RuntimeException(String.format("%s does not implement %s", application.getClass().getCanonicalName(), HasActivityInjector.class.getCanonicalName()));
        }

        AndroidInjector<Activity> activityInjector = ((HasActivityInjector) application).activityInjector();
        checkNotNull(activityInjector, "%s.activityInjector() returned null", application.getClass().getCanonicalName());

        activityInjector.inject(this);
    }
}
