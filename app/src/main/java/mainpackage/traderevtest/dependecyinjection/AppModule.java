package mainpackage.traderevtest.dependecyinjection;

import javax.inject.Singleton;
import dagger.Module;
import dagger.Provides;
import mainpackage.traderevtest.BuildConfig;
import mainpackage.traderevtest.networkcalls.UnsplashPhotoService;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module(includes = ViewModelModule.class)
class AppModule {
    @Singleton @Provides
    UnsplashPhotoService providenUsplashPhotoService() {
        return new Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BuildConfig.UNSPLASH_END_POINT)
                .build()
                .create(UnsplashPhotoService.class);
    }
}
