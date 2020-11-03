package wonton.jodamoney;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import org.joda.money.BaseCurrencyUnitDataProvider;
import org.joda.money.CurrencyUnit;
import org.joda.money.FileCurrencyUnitDataProvider;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Initialization on Android device, must call init methods after
 * {@code Application.attachBaseContext()}, and cannot be instantiated.
 * Initialization should be done only once, so it is advised that just do
 * it once in {@code Application.onCreate()}.
 */
public class JodaMoneyAndroid {

    /**
     * Application Context for Asset Manager, it is best to assign it
     * to {@code ContextWrapper.getApplicationContext()} or just {@code Application}.
     */
    public static Context applicationContext = null;
    /**
     * Initialization have been done identifier.
     */
    private static final AtomicBoolean isInitialized = new AtomicBoolean(false);

    /**
     * cannot be instantiated.
     */
    private JodaMoneyAndroid() {
        throw new AssertionError();
    }

    /**
     * Default initializer, just use {@code DefaultCurrencyUnitDataProvider} which will
     * statically initialized in {@code CurrencyUnit}.
     *
     * @param context Application Context, not null
     */
    public static void init(@NonNull Context context) {
        if (isInitialized.get()) {
            return;
        }

        isInitialized.set(true);
        applicationContext = context;
        if (CurrencyUnit.registeredCurrencies().size() < 1) {
            Log.e("JodaMoneyAndroid", "Initialization failed");
            isInitialized.set(false);
        }
        applicationContext = null;
    }

    /**
     * File initializer, you can use other csv to add custom {@code CurrencyUnit} after default initializer.
     * The format of data csv can be referred from bundled {@code /assets/MoneyData.csv}.
     *
     * @param context     Application Context, not null
     * @param fileName    the file to load, not null
     * @param isNecessary whether or not the file is necessary
     */
    public static void init(@NonNull Context context, @NonNull String fileName, boolean isNecessary) {
        if (isInitialized.get()) {
            return;
        }

        isInitialized.set(true);
        applicationContext = context;
        if (CurrencyUnit.registeredCurrencies().size() < 1) {
            Log.e("JodaMoneyAndroid", "Initialization failed");
            isInitialized.set(false);
        }
        try {
            new FileCurrencyUnitDataProvider(applicationContext, fileName).registerCurrencies();
        } catch (Exception e) {
            if (isNecessary) {
                Log.e("JodaMoneyAndroid", "Initialization failed");
                e.printStackTrace();
                isInitialized.set(false);
            }
        }
        applicationContext = null;
    }

    /**
     * Custom provider initializer, you can use your custom provider which
     * extends {@code CurrencyUnitDataProvider} to perform custom actions after default initializer.
     *
     * @param context  Application Context, not null
     * @param provider the custom provider, not null
     */
    public static void init(@NonNull Context context, @NonNull BaseCurrencyUnitDataProvider provider) {
        if (isInitialized.get()) {
            return;
        }

        isInitialized.set(true);
        applicationContext = context;
        try {
            provider.registerCurrencies();
            if (CurrencyUnit.registeredCurrencies().size() < 1) {
                Log.e("JodaMoneyAndroid", "Initialization failed");
                isInitialized.set(false);
            }
        } catch (Exception e) {
            Log.e("JodaMoneyAndroid", "Initialization failed");
            e.printStackTrace();
            isInitialized.set(false);
        }
        applicationContext = null;
    }
}
