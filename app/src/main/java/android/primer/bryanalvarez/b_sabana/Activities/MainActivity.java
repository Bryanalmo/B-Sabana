package android.primer.bryanalvarez.b_sabana.Activities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.primer.bryanalvarez.b_sabana.Adapters.PagerAdapter;
import android.primer.bryanalvarez.b_sabana.R;
import android.primer.bryanalvarez.b_sabana.Utils.Util;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    private SharedPreferences prefs;
    private int id;

    private MenuItem menuItemAyuda;
    private MenuItem menuItemNoticias;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        prefs = getSharedPreferences("Preferences", Context.MODE_PRIVATE);
        Util.id = Util.getuserIdPrefs(prefs);
        Util.usuario = Util.getuserUsuarioPrefs(prefs);
        Util.celular = Util.getuserCelularPrefs(prefs);


        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        tabLayout.addTab(tabLayout.newTab().setText("Bienestar Sabana"));
        tabLayout.addTab(tabLayout.newTab().setText("Ser Sabana"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final ViewPager viewPager= (ViewPager) findViewById(R.id.viewPager);
        PagerAdapter adapter = new PagerAdapter(getSupportFragmentManager(), tabLayout.getTabCount());

        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                viewPager.setCurrentItem(position);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        prefs = getSharedPreferences("Preferences", Context.MODE_PRIVATE);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        this.menuItemAyuda = menu.findItem(R.id.menu_ayuda);
        this.menuItemNoticias = menu.findItem(R.id.menu_info);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.menu_logout:
                logOut();
                return true;
            case R.id.menu_forget_logout:
                removeSharedPreferences();
                logOut();
                return true;
            case R.id.menu_ayuda:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage("Parece que estas teniendo problemas, cuentanos de que se trata y podremos ayudarte");
                builder.setTitle("Soporte");
                builder.setPositiveButton("Contáctar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        sendMail();
                    }
                });
                builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();

                return true;
            case R.id.menu_info:
                AlertDialog.Builder builderInfo = new AlertDialog.Builder(this);
                builderInfo.setMessage("Desarollado por:"+"\n"+"Bryan Alvarez Monroy"+"\n"+"Ingenieria Informatica"+"\n"+"Universidad de La Sabana");
                builderInfo.setTitle("B-Sabana 1.0");
                AlertDialog dialogInfo = builderInfo.create();
                dialogInfo.show();
            default:return super.onOptionsItemSelected(item);
        }

    }

    private void logOut(){
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    private void sendMail(){
        Intent intentMail = new Intent(Intent.ACTION_SEND, Uri.parse(Util.EMAIL_CONTACTO));
        intentMail.setType("plain/text");
        intentMail.putExtra(Intent.EXTRA_SUBJECT, Util.ASUNTO_EMAIL);
        intentMail.putExtra(Intent.EXTRA_TEXT, Util.MENSAJE_EMAIL);
        intentMail.putExtra(Intent.EXTRA_EMAIL, new String[]{Util.COPIA_A_EMAIL, Util.EMAIL_CONTACTO});
        startActivity(Intent.createChooser(intentMail, "Elige cliente de correo"));
    }

    private void removeSharedPreferences(){
        Util.deleteMailandPass(prefs);
    }




}
