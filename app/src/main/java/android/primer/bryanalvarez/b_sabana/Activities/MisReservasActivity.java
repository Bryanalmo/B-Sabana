package android.primer.bryanalvarez.b_sabana.Activities;

import android.content.DialogInterface;
import android.os.AsyncTask;
import android.primer.bryanalvarez.b_sabana.Adapters.ReservaAdapter;
import android.primer.bryanalvarez.b_sabana.Fragments.SerSabanaFragment;
import android.primer.bryanalvarez.b_sabana.Models.Evento;
import android.primer.bryanalvarez.b_sabana.Models.Reserva;
import android.primer.bryanalvarez.b_sabana.Utils.Util;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.primer.bryanalvarez.b_sabana.R;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class MisReservasActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ReservaAdapter adapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private List<Reserva> reservas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mis_reservas);

        TraerReservas traerReservas= new TraerReservas();
        traerReservas.execute("http://"+Util.IP+"/CursoAndroid/consultaReservas.php?idUsuario="+Util.id);
        try {
            traerReservas.onPostExecute(traerReservas.get());

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        reservas = traerReservas.reservas;

        recyclerView = (RecyclerView) findViewById(R.id.recyclerViewReservas);
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        mLayoutManager = new GridLayoutManager(this,2);
        recyclerView.setLayoutManager(mLayoutManager);

        adapter = new ReservaAdapter(this, reservas, R.layout.recycler_view_item_reservas, new ReservaAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Reserva reserva, int position) {

            }
        }, new ReservaAdapter.OnButtonClickListener() {
            @Override
            public void onButtonClick(final Reserva reserva, final int position) {
                BorrarReserva borrarReserva = new BorrarReserva();
                borrarReserva.execute("http://"+Util.IP+"/CursoAndroid/borrarReserva.php?idUsuario="+Util.id+"&hora="+reserva.getHora()+"&fecha="+reserva.getFecha());
                reservas.remove(position);
                adapter.notifyDataSetChanged();
                Toast.makeText(getApplication().getApplicationContext(),"Reserva eliminada",Toast.LENGTH_LONG).show();
            }
        });

        recyclerView.setAdapter(adapter);


    }

    private class TraerReservas extends AsyncTask<String, Void, String> {
        ArrayList<Reserva> reservas;
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
                reservas = new ArrayList<>();
                int length = ja.length();
                for (int i=0; i<=length; i++){
                    String nombreMaterial = ja.getJSONObject(i).getString("nombre");
                    String descripcion = ja.getJSONObject(i).getString("descripcion");
                    String hora= ja.getJSONObject(i).getString("hora");
                    String fecha = ja.getJSONObject(i).getString("fecha");
                    Reserva reserva= new Reserva(nombreMaterial,descripcion,hora,fecha);
                    reservas.add(reserva);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }

    private class BorrarReserva extends AsyncTask<String, Void, String> {
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
