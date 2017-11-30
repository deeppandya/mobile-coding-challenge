package mainpackage.traderevtest.dependecyinjection;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import mainpackage.traderevtest.view.MainActivity;

@Module
public abstract class MainActivityModule {
    @ContributesAndroidInjector(modules = FragmentBuildersModule.class)
    abstract MainActivity contributeMainActivity();
}
