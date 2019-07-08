package android.primer.bryanalvarez.b_sabana.Activities;

import android.content.Intent;
import android.os.AsyncTask;
import android.primer.bryanalvarez.b_sabana.Models.Contacto;
import android.primer.bryanalvarez.b_sabana.Utils.Util;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.primer.bryanalvarez.b_sabana.R;
import android.view.View;
import android.widget.AdapterViewFlipper;
import android.widget.Button;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutionException;

public class DepartamentoActivity extends AppCompatActivity {

    private ImageSwitcher imageSwitcher;
    private TextView descripcionDep;
    private TextView nombreContaco1;
    private TextView telefonoContaco1;
    private TextView emailContaco1;
    private TextView nombreContaco2;
    private TextView telefonoContaco2;
    private TextView emailContaco2;
    private TextView horarioContacto1;
    private TextView horarioContacto2;
    private TextView textViewTitulo;
    private Button buttonReservar;


    private int idDepartamento;
    private int[] galeria = null;
    private int posicion;
    private static final int DURACION = 4000;
    private Timer timer = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_departamento);

        bindUI();

        idDepartamento = getIntent().getExtras().getInt("id");

        if (idDepartamento == 1){
            galeria = new int[]{R.drawable.deportes_slide, R.drawable.deportes_slide2, R.drawable.deportes_slide3};
        }else if(idDepartamento == 2){
            galeria = new int[]{R.drawable.cultura_slide, R.drawable.cultura_slide2, R.drawable.cultura_slide3};
        }

        imageSwitcher.setFactory(new ViewSwitcher.ViewFactory() {
            @Override
            public View makeView() {
                ImageView imageView = new ImageView(DepartamentoActivity.this);
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                return imageView;
            }
        });
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask()
        {
            public void run()
            {
                runOnUiThread(new Runnable()
                {
                    public void run()
                    {
                        imageSwitcher.setImageResource(galeria[posicion]);
                        posicion++;
                        if (posicion == galeria.length)
                            posicion = 0;
                    }
                });
            }
        }, 0, DURACION);

        TraerDatosDep traerDatosDep = new TraerDatosDep();
        traerDatosDep.execute("http://"+Util.IP+"/CursoAndroid/consultaDepartamento.php?idDepartamento="+idDepartamento);
        try {
            traerDatosDep.onPostExecute(traerDatosDep.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        TraerContactosDep traerContactosDep = new TraerContactosDep();
        traerContactosDep.execute("http://"+Util.IP+"/CursoAndroid/consultaContactos.php?idDepartamento="+idDepartamento);

        buttonReservar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DepartamentoActivity.this, ReservarActivity.class);
                intent.putExtra("id",idDepartamento);
                startActivity(intent);

            }
        });
    }

    private void bindUI() {
        descripcionDep = (TextView) findViewById(R.id.descripcionDepartamento);
        nombreContaco1 = (TextView) findViewById(R.id.nombreContactoDepartamento);
        telefonoContaco1= (TextView) findViewById(R.id.telefonoContactoDepartamento);
        emailContaco1= (TextView) findViewById(R.id.correoContactoDepartamento);
        horarioContacto1 = (TextView) findViewById(R.id.horarioContactoDepartamento);
        nombreContaco2 = (TextView) findViewById(R.id.nombreContactoDepartamento2);
        telefonoContaco2= (TextView) findViewById(R.id.telefonoContactoDepartamento2);
        emailContaco2= (TextView) findViewById(R.id.correoContactoDepartamento2);
        horarioContacto2 = (TextView) findViewById(R.id.horarioContactoDepartamento2);
        textViewTitulo = (TextView) findViewById(R.id.textViewTitulo);
        imageSwitcher = (ImageSwitcher) findViewById(R.id.imageSwitcher);
        buttonReservar= (Button) findViewById(R.id.buttonReservar);
    }
    private class TraerDatosDep extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {

            // params comes from the execute() call: params[0] is the url.
            try {
                return Util.downloadUrl(urls[0]);
            } catch (IOException e) {
                return "Unable to retrieve web page. URL may be invalid.";
            }
        }
        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {
            JSONArray ja = null;
            try {
                ja = new JSONArray(result);
                descripcionDep.setText(ja.getJSONObject(0).getString("descripcion"));
                textViewTitulo.setText(ja.getJSONObject(0).getString("nombre"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private class TraerContactosDep extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {

            // params comes from the execute() call: params[0] is the url.
            try {
                return Util.downloadUrl(urls[0]);
            } catch (IOException e) {
                return "Unable to retrieve web page. URL may be invalid.";
            }
        }
        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {
            JSONArray ja = null;
            try {
                ja = new JSONArray(result);
                nombreContaco1.setText(ja.getJSONObject(0).getString("nombres") + " " + ja.getJSONObject(0).getString("apellidos"));
                emailContaco1.setText(ja.getJSONObject(0).getString("correo") );
                telefonoContaco1.setText(ja.getJSONObject(0).getString("celular") );
                horarioContacto1.setText(ja.getJSONObject(0).getString("horario") );

                nombreContaco2.setText(ja.getJSONObject(1).getString("nombres") + " " + ja.getJSONObject(0).getString("apellidos"));
                emailContaco2.setText(ja.getJSONObject(1).getString("correo") );
                telefonoContaco2.setText(ja.getJSONObject(1).getString("celular") );
                horarioContacto2.setText(ja.getJSONObject(1).getString("horario") );
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
