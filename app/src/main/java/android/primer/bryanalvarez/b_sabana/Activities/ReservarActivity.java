package android.primer.bryanalvarez.b_sabana.Activities;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.primer.bryanalvarez.b_sabana.Fragments.SerSabanaFragment;
import android.primer.bryanalvarez.b_sabana.Models.Evento;
import android.primer.bryanalvarez.b_sabana.Utils.Util;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.primer.bryanalvarez.b_sabana.R;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.concurrent.ExecutionException;


public class ReservarActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText editTextFecha;
    private EditText editTextHora;
    private ImageButton imageButtonFecha;
    private ImageButton imageButtonHora;
    private Spinner spinner;
    private Button buttonReservar;

    private int idDepartamento;
    private ArrayList<String> material;

    public final Calendar c = Calendar.getInstance();

    //Variables para obtener la fecha
    final int mes = c.get(Calendar.MONTH);
    final int dia = c.get(Calendar.DAY_OF_MONTH);
    final int anio = c.get(Calendar.YEAR);
    final int hora = c.get(Calendar.HOUR_OF_DAY);
    final int minuto = c.get(Calendar.MINUTE);

    private static final String CERO = "0";
    private static final String GUION = "-";
    private static final String DOS_PUNTOS = ":";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservar);

        idDepartamento = getIntent().getExtras().getInt("id");

        editTextFecha = (EditText) findViewById(R.id.et_mostrar_fecha_picker);
        editTextHora= (EditText) findViewById(R.id.et_mostrar_hora_picker);
        imageButtonFecha = (ImageButton) findViewById(R.id.ib_obtener_fecha);
        imageButtonHora= (ImageButton) findViewById(R.id.ib_obtener_hora);
        spinner = (Spinner) findViewById(R.id.spinnerMaterial);
        buttonReservar = (Button) findViewById(R.id.buttonReservarMaterial);

        imageButtonFecha.setOnClickListener(this);
        imageButtonHora.setOnClickListener(this);
        buttonReservar.setOnClickListener(this);


        TraerMaterial traerMaterial= new TraerMaterial();
        traerMaterial.execute("http://"+Util.IP+"/CursoAndroid/consultaMaterial.php?idDepartamento="+idDepartamento);
        try {
            traerMaterial.onPostExecute(traerMaterial.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        material = traerMaterial.materiales;
        spinner.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,material));

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ib_obtener_fecha:
                obtenerFecha();
                break;
            case R.id.ib_obtener_hora:
                obtenerHora();
                break;
            case R.id.buttonReservarMaterial:
                CrearReserva crearReserva= new CrearReserva();
                String fecha = editTextFecha.getText() + " " + editTextHora.getText();
                int id = Util.id;
                crearReserva.execute("http://"+Util.IP+"/CursoAndroid/registroReserva.php?fecha="+editTextFecha.getText()+"&hora="+editTextHora.getText()+"&idUsuario="+Util.id+"&material="+spinner.getSelectedItem().toString());
                Toast.makeText(this,"Tu reserva se ha registrado en la pestaña de Reservas!", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(this,MainActivity.class);
                startActivity(intent);
                break;
        }
    }

    private void obtenerHora() {
        TimePickerDialog recogerHora = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                //Formateo el hora obtenido: antepone el 0 si son menores de 10
                String horaFormateada =  (hourOfDay < 10)? String.valueOf(CERO + hourOfDay) : String.valueOf(hourOfDay);
                //Formateo el minuto obtenido: antepone el 0 si son menores de 10
                String minutoFormateado = (minute < 10)? String.valueOf(CERO + minute):String.valueOf(minute);
                //Muestro la hora con el formato deseado
                editTextHora.setText(horaFormateada + DOS_PUNTOS + minutoFormateado);
            }
            //Estos valores deben ir en ese orden
            //Al colocar en false se muestra en formato 12 horas y true en formato 24 horas
            //Pero el sistema devuelve la hora en formato 24 horas
        }, hora, minuto, false);
        recogerHora.show();
    }


    private void obtenerFecha() {
        DatePickerDialog recogerFecha = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                final int mesActual = month + 1;
                //Formateo el día obtenido: antepone el 0 si son menores de 10
                String diaFormateado = (dayOfMonth < 10)? CERO + String.valueOf(dayOfMonth):String.valueOf(dayOfMonth);
                //Formateo el mes obtenido: antepone el 0 si son menores de 10
                String mesFormateado = (mesActual < 10)? CERO + String.valueOf(mesActual):String.valueOf(mesActual);
                //Muestro la fecha con el formato deseado
                editTextFecha.setText(year + GUION + mesFormateado + GUION+ diaFormateado);

            }
        },anio, mes, dia);
        recogerFecha.show();
    }


    private class TraerMaterial extends AsyncTask<String, Void, String> {
        ArrayList<String> materiales;

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
                materiales = new ArrayList<>();
                int length = ja.length();
                for (int i = 0; i <= length; i++) {
                    String material = ja.getJSONObject(i).getString("nombre");
                    materiales.add(material);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }

    private class CrearReserva extends AsyncTask<String, Void, String> {
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
        }
    }


}
