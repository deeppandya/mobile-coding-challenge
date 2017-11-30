package mainpackage.traderevtest.dependecyinjection;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;
import mainpackage.traderevtest.viewmodel.ViewModelFactory;
import mainpackage.traderevtest.viewmodel.UnsplashPhotosViewModel;

@Module
abstract class ViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(UnsplashPhotosViewModel.class)
    abstract ViewModel bindUnsplashPhotosViewModel(UnsplashPhotosViewModel unsplashPhotosViewModel);

    @Binds
    abstract ViewModelProvider.Factory bindViewModelFactory(ViewModelFactory factory);
}
