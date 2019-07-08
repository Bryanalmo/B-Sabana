package android.primer.bryanalvarez.b_sabana.Splash;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.primer.bryanalvarez.b_sabana.Activities.LoginActivity;
import android.primer.bryanalvarez.b_sabana.Activities.MainActivity;
import android.primer.bryanalvarez.b_sabana.Utils.Util;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;

public class SplashActivity extends AppCompatActivity {

    private SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        prefs = getSharedPreferences("Preferences", Context.MODE_PRIVATE);

        prefs = getSharedPreferences("Preferences", Context.MODE_PRIVATE);

        Intent intentLogin = new Intent(this, LoginActivity.class);
        Intent intentMain = new Intent(this, MainActivity.class);

        if(!TextUtils.isEmpty(Util.getuserMailPrefs(prefs)) && !TextUtils.isEmpty(Util.getuserPasswordPrefs(prefs)) ){
            Util.setUsuario(Util.getuserMailPrefs(prefs));
            startActivity(intentMain);
        }else{
            startActivity(intentLogin);
        }
        finish();
    }
}
