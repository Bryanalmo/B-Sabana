package android.primer.bryanalvarez.b_sabana.App;

import android.app.Application;
import android.os.SystemClock;

/**
 * Created by nayar on 2/04/2018.
 */

public class MyApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        SystemClock.sleep(2000);
    }
}
