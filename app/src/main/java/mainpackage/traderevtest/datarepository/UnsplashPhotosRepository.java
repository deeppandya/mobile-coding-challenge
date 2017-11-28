package mainpackage.traderevtest.datarepository;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.util.Log;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import mainpackage.traderevtest.BuildConfig;
import mainpackage.traderevtest.model.UnsplashPhoto;
import mainpackage.traderevtest.networkcalls.UnsplashPhotoService;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by deepp on 2017-11-28.
 */

public class UnsplashPhotosRepository {

    private UnsplashPhotoService unsplashPhotoService;
    private static UnsplashPhotosRepository unsplashPhotosRepository;

    private UnsplashPhotosRepository() {
        Retrofit retrofit = new Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BuildConfig.UNSPLASH_END_POINT)
                .build();

        unsplashPhotoService = retrofit.create(UnsplashPhotoService.class);
    }

    public synchronized static UnsplashPhotosRepository getInstance() {
        if (unsplashPhotosRepository == null) {
            unsplashPhotosRepository = new UnsplashPhotosRepository();
        }
        return unsplashPhotosRepository;
    }

    public LiveData<List<UnsplashPhoto>> getUnsplashPhotos(String page, String perPage, String orderBy) {
        final MutableLiveData<List<UnsplashPhoto>> data = new MutableLiveData<>();
        unsplashPhotoService.getUnsplashPhotos(BuildConfig.UNSPLASH_CLIENT_ID, page, perPage, orderBy)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(data::setValue
                        ,error -> Log.e("Error", error.getMessage()));
        return data;
    }
}

