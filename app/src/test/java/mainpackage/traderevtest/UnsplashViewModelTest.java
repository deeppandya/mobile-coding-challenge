package mainpackage.traderevtest;

import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Scheduler;
import io.reactivex.android.plugins.RxAndroidPlugins;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.schedulers.ExecutorScheduler;
import io.reactivex.plugins.RxJavaPlugins;
import mainpackage.traderevtest.datarepository.UnsplashPhotosRepository;
import mainpackage.traderevtest.model.UnsplashPhoto;
import mainpackage.traderevtest.networkcalls.UnsplashPhotoService;
import mainpackage.traderevtest.viewmodel.UnsplashPhotosViewModel;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

/**
 * Created by deeppandya on 2017-11-30.
 */

@SuppressWarnings("unchecked")
@RunWith(JUnit4.class)
public class UnsplashViewModelTest {

    private UnsplashPhotosViewModel unsplashPhotosViewModel;
    private UnsplashPhotosRepository unsplashPhotosRepository;

    @BeforeClass
    public static void setUpRxSchedulers() {
        Scheduler immediate = new Scheduler() {
            @Override
            public Disposable scheduleDirect(@NonNull Runnable run, long delay, @NonNull TimeUnit unit) {
                // this prevents StackOverflowErrors when scheduling with a delay
                return super.scheduleDirect(run, 0, unit);
            }

            @Override
            public Worker createWorker() {
                return new ExecutorScheduler.ExecutorWorker(Runnable::run);
            }
        };

        RxJavaPlugins.setInitIoSchedulerHandler(scheduler -> immediate);
        RxJavaPlugins.setInitComputationSchedulerHandler(scheduler -> immediate);
        RxJavaPlugins.setInitNewThreadSchedulerHandler(scheduler -> immediate);
        RxJavaPlugins.setInitSingleSchedulerHandler(scheduler -> immediate);
        RxAndroidPlugins.setInitMainThreadSchedulerHandler(scheduler -> immediate);
    }

    @Before
    public void setup() {
        UnsplashPhotoService unsplashPhotoService=new Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BuildConfig.UNSPLASH_END_POINT)
                .build()
                .create(UnsplashPhotoService.class);
        unsplashPhotosRepository = new UnsplashPhotosRepository(unsplashPhotoService);
        unsplashPhotosViewModel = new UnsplashPhotosViewModel(unsplashPhotosRepository);
    }

    @Test
    public void testViewModelNull() {
        assertThat(unsplashPhotosViewModel.getUnsplashPhotos("1","30","latest"), notNullValue());
    }

    @Test
    public void testRepositoryNull() {
        assertThat(unsplashPhotosRepository.getUnsplashPhotos("1","30","latest"), notNullValue());
    }
}
