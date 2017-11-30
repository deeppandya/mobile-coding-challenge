package mainpackage.traderevtest.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import java.util.List;

import javax.inject.Inject;

import mainpackage.traderevtest.datarepository.UnsplashPhotosRepository;
import mainpackage.traderevtest.model.UnsplashPhoto;

/**
 * Created by deepp on 2017-11-28.
 */

public class UnsplashPhotosViewModel extends ViewModel {

    private UnsplashPhotosRepository unsplashPhotosRepository;

    @Inject
    public UnsplashPhotosViewModel(UnsplashPhotosRepository unsplashPhotosRepository) {
        this.unsplashPhotosRepository=unsplashPhotosRepository;
    }

    public LiveData<List<UnsplashPhoto>> getUnsplashPhotos(String page,String perPage,String orderBy) {
        return unsplashPhotosRepository.getUnsplashPhotos(page,perPage,orderBy);
    }
}
