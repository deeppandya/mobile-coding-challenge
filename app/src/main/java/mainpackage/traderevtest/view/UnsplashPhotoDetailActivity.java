package mainpackage.traderevtest.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.view.animation.AccelerateInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import mainpackage.traderevtest.R;
import mainpackage.traderevtest.adapter.UnsplashPhotoViewPagerAdapter;
import mainpackage.traderevtest.iview.UnsplashPhotoDetailView;
import mainpackage.traderevtest.model.UnsplashPhoto;
import mainpackage.traderevtest.utils.Constants;
import mainpackage.traderevtest.utils.GlideApp;

public class UnsplashPhotoDetailActivity extends AppCompatActivity implements UnsplashPhotoDetailView {

    public static final String EXTRA_CIRCULAR_REVEAL_X = "EXTRA_CIRCULAR_REVEAL_X";
    public static final String EXTRA_CIRCULAR_REVEAL_Y = "EXTRA_CIRCULAR_REVEAL_Y";
    private View rootLayout;
    private int revealX, revealY;

    private ViewPager pager;
    private ArrayList<UnsplashPhoto> unsplashPhotos=new ArrayList<>();
    private int currentPage = 1;
    private int position = 0;
    private Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_unsplash_photo_detail);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);


        final Intent intent = getIntent();
        if (intent.hasExtra(Constants.PHOTOS)) {
            unsplashPhotos = intent.getParcelableArrayListExtra(Constants.PHOTOS);
        }
        if (intent.hasExtra(Constants.CURRENT_PAGE)) {
            currentPage = intent.getIntExtra(Constants.CURRENT_PAGE, 1);
        }
        if (intent.hasExtra(Constants.POSITION)) {
            position = intent.getIntExtra(Constants.POSITION, 0);
        }

        setViews();

        setToolbar();

        setViewActions(intent, savedInstanceState);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    unRevealActivity();
                }else{
                    finish();
                }
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setViewActions(Intent intent, Bundle savedInstanceState) {
        if (savedInstanceState == null && Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP &&
                intent.hasExtra(EXTRA_CIRCULAR_REVEAL_X) &&
                intent.hasExtra(EXTRA_CIRCULAR_REVEAL_Y)) {

            rootLayout.setVisibility(View.INVISIBLE);

            revealX = intent.getIntExtra(EXTRA_CIRCULAR_REVEAL_X, 0);
            revealY = intent.getIntExtra(EXTRA_CIRCULAR_REVEAL_Y, 0);


            ViewTreeObserver viewTreeObserver = rootLayout.getViewTreeObserver();
            if (viewTreeObserver.isAlive()) {
                viewTreeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                    @Override
                    public void onGlobalLayout() {
                        revealActivity(revealX, revealY);
                        rootLayout.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                    }
                });
            }

        } else {
            rootLayout.setVisibility(View.VISIBLE);
        }

        pager.setAdapter(new UnsplashPhotoViewPagerAdapter(this, unsplashPhotos,this));
        pager.post(() -> pager.setCurrentItem(position));
    }

    private void setViews() {
        rootLayout = findViewById(R.id.root_layout);
        pager = findViewById(R.id.viewpager);
    }

    private void setToolbar() {
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeAsUpIndicator(getResources().getDrawable(R.drawable.ic_back));
        }
    }

    protected void revealActivity(int x, int y) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            float finalRadius = (float) (Math.max(rootLayout.getWidth(), rootLayout.getHeight()) * 1.1);

            Animator circularReveal = ViewAnimationUtils.createCircularReveal(rootLayout, x, y, 0, finalRadius);
            circularReveal.setDuration(500);
            circularReveal.setInterpolator(new AccelerateInterpolator());

            rootLayout.setVisibility(View.VISIBLE);
            circularReveal.start();
        } else {
            finish();
        }
    }

    protected void unRevealActivity() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            finish();
        } else {
            float finalRadius = (float) (Math.max(rootLayout.getWidth(), rootLayout.getHeight()) * 1.1);
            Animator circularReveal = ViewAnimationUtils.createCircularReveal(
                    rootLayout, revealX, revealY, finalRadius, 0);

            circularReveal.setDuration(500);
            circularReveal.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    rootLayout.setVisibility(View.INVISIBLE);
                    finish();
                }
            });

            circularReveal.start();
        }
    }

    @Override
    public void onBackPressed() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            unRevealActivity();
        }else{
            super.onBackPressed();
        }
    }

    @Override
    public void setActionBarForPhoto(UnsplashPhoto unsplashPhoto) {
        ImageView imgUser = toolbar.findViewById(R.id.img_user);
        TextView tvUsername = toolbar.findViewById(R.id.tv_username);
        TextView tvLikes = toolbar.findViewById(R.id.tv_likes);

        GlideApp.with(UnsplashPhotoDetailActivity.this)
                .load(unsplashPhoto.getUser().getProfileImage().getMedium())
                .into(imgUser);

        tvUsername.setText(String.format(getString(R.string.username_text), unsplashPhoto.getUser().getName() != null ? unsplashPhoto.getUser().getName() : ""));
        tvLikes.setText(String.valueOf(unsplashPhoto.getLikes()));
    }
}
