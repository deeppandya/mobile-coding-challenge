package mainpackage.traderevtest;

import android.databinding.DataBindingUtil;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import mainpackage.traderevtest.databinding.ActivityMainBinding;
import mainpackage.traderevtest.listener.InfiniteScrollListener;

public class MainActivity extends AppCompatActivity {
    private List<String> mStringList;
    private int mLoadedItems = 0;
    private UnsplashPhotoAdapter mUnsplashPhotoAdapter;
    private ActivityMainBinding mActivityMainBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mActivityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(MainActivity.this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mActivityMainBinding.recyclerView.setLayoutManager(linearLayoutManager);
        mActivityMainBinding.recyclerView.addItemDecoration(new DividerItemDecoration(MainActivity.this, DividerItemDecoration.VERTICAL));
        mStringList = new ArrayList<>();
        mUnsplashPhotoAdapter = new UnsplashPhotoAdapter(mStringList);
        mActivityMainBinding.recyclerView.setAdapter(mUnsplashPhotoAdapter);
        addDataToList();

        mActivityMainBinding.recyclerView.addOnScrollListener(new InfiniteScrollListener() {
            @Override
            public void onLoadMore() {
                addDataToList();
            }
        });
    }

    private void addDataToList() {
        mActivityMainBinding.itemProgressBar.setVisibility(View.VISIBLE);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i <= 30; i++) {
                    mStringList.add("SampleText : " + mLoadedItems);
                    mLoadedItems++;
                }
                mUnsplashPhotoAdapter.notifyDataSetChanged();
                mActivityMainBinding.itemProgressBar.setVisibility(View.GONE);
            }
        }, 1500);
    }
}
