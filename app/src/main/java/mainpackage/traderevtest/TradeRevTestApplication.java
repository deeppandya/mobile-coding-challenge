package mainpackage.traderevtest;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

/**
 * Created by deepp on 2017-11-29.
 */

public class TradeRevTestApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        //AppInjector.init(this);
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
}
