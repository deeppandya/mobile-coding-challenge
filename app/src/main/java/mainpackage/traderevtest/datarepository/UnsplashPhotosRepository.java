package mainpackage.traderevtest.datarepository;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.util.Log;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import mainpackage.traderevtest.BuildConfig;
import mainpackage.traderevtest.model.UnsplashPhoto;
import mainpackage.traderevtest.networkcalls.UnsplashPhotoService;

/**
 * Created by deepp on 2017-11-28.
 */

@Singleton
public class UnsplashPhotosRepository {

    private UnsplashPhotoService unsplashPhotoService;

    @Inject
    UnsplashPhotosRepository(UnsplashPhotoService unsplashPhotoService) {
        this.unsplashPhotoService = unsplashPhotoService;
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

