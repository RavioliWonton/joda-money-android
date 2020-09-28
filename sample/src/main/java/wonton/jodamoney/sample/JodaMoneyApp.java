package wonton.jodamoney.sample;

import androidx.multidex.MultiDexApplication;

import wonton.jodamoney.JodaMoneyAndroid;

public class JodaMoneyApp extends MultiDexApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        JodaMoneyAndroid.init(this);
    }
}
