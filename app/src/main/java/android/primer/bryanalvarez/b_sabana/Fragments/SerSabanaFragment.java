package android.primer.bryanalvarez.b_sabana.Fragments;


import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.primer.bryanalvarez.b_sabana.Activities.LoginActivity;
import android.primer.bryanalvarez.b_sabana.Activities.MainActivity;
import android.primer.bryanalvarez.b_sabana.Adapters.EventoAdapter;
import android.primer.bryanalvarez.b_sabana.Models.Evento;
import android.primer.bryanalvarez.b_sabana.Splash.SplashActivity;
import android.primer.bryanalvarez.b_sabana.Utils.Util;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.telecom.PhoneAccount;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.primer.bryanalvarez.b_sabana.R;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * A simple {@link Fragment} subclass.
 */
public class SerSabanaFragment extends Fragment {

    private FloatingActionButton fab;
    private FloatingActionButton fab2;
    private RecyclerView recyclerView;
    private EventoAdapter adapter;
    private RecyclerView.LayoutManager mLayoutManager;
    public ArrayList<Evento> eventos = new ArrayList<Evento>();
    private SharedPreferences prefs;
    public TraerEventos traerEventos= new TraerEventos();
    public int cargados;

    public SerSabanaFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        //Inicializar Eventos

        View view = inflater.inflate(R.layout.fragment_ser_sabana, container, false);


        traerEventos.execute("http://"+Util.IP+"/CursoAndroid/consultaEventos.php");
        try {
            traerEventos.onPostExecute(traerEventos.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        eventos = traerEventos.eventos;
        cargados = 1;

        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        mLayoutManager = new LinearLayoutManager(getContext().getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);

        fab = (FloatingActionButton) view.findViewById(R.id.fabAdd);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mostrarAlertaParaCrearEvento("Crea tu evento!", "Añade los detalles del evento");
            }
        });
        fab2 = (FloatingActionButton) view.findViewById(R.id.fabAdd1);
        fab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                eventos.clear();
                Intent intentRecargar = new Intent(getContext().getApplicationContext(), SplashActivity.class);
                startActivity(intentRecargar);
            }
        });
        createAdapter();

         recyclerView.setAdapter(adapter);
        setHideShowFAB();
        return  view;
    }


    private void setHideShowFAB() {
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (dy > 0) {
                    fab.hide();
                    fab2.hide();
                }else if (dy < 0){
                    fab.show();
                    fab2.show();
                }
            }
        });
    }

    private void mostrarAlertaParaCrearEvento(String title, String message) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        if (title != null) builder.setTitle(title);
        if (message != null) builder.setMessage(message);

        View viewInflated = LayoutInflater.from(getContext().getApplicationContext()).inflate(R.layout.crear_evento_layout, null);
        builder.setView(viewInflated);


        final TextView numeroPersonas= (TextView) viewInflated.findViewById(R.id.personasSolicitadas);
        final EditText descripcion = (EditText) viewInflated.findViewById(R.id.descripcionEvento);
        final SeekBar seekBar = (SeekBar) viewInflated.findViewById(R.id.seekBar);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                numeroPersonas.setText(seekBar.getProgress()+"");
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
        final Spinner spinner = (Spinner) viewInflated.findViewById(R.id.spinner);
        String[] valores = {"deporte","cultura"};
        spinner.setAdapter(new ArrayAdapter<String>(getContext().getApplicationContext(), android.R.layout.simple_list_item_1,valores));




        builder.setPositiveButton("Añadir", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                int numeroPersonas = seekBar.getProgress();
                int categoria = spinner.getSelectedItemPosition() + 1;
                final String descripcionEvento = descripcion.getText().toString();
                String usuario = Util.usuario;
                String celular = Util.celular;
                int id = Util.id;
                if (descripcion.length() > 0){

                    CrearEvento crearEvento = new CrearEvento();
                    crearEvento.execute("http://"+Util.IP+"/CursoAndroid/registroEvento.php?descripcion="+descripcionEvento+"&numeroPersonas="+numeroPersonas+"&idUsuario="+id+"&idCategoria="+categoria);
                    Evento evento = new Evento(descripcionEvento,numeroPersonas,categoria,usuario,celular);
                    eventos.add(evento);
                    adapter.notifyDataSetChanged();

                }
                else {
                    Toast.makeText(getContext().getApplicationContext(), "Debes añadir una descripción para poder crear el evento!", Toast.LENGTH_LONG).show();
                }
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private class CrearEvento extends AsyncTask<String, Void, String> {
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

    private class TraerEventos extends AsyncTask<String, Void, String> {
        ArrayList<Evento> eventos;
        //int cargados;
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

            if(cargados != 1){
                JSONArray ja = null;
                try {
                    ja = new JSONArray(result);
                    eventos = new ArrayList<>();
                    int length = ja.length();
                    for (int i=0; i<=length; i++){
                        String descripcion = ja.getJSONObject(i).getString("descripcion");
                        int personasSolicitadas = ja.getJSONObject(i).getInt("personasSolicitadas");
                        int categoria = ja.getJSONObject(i).getInt("idCategoria");
                        String usuario= ja.getJSONObject(i).getString("nombres") +" "+ ja.getJSONObject(i).getString("apellidos");
                        String celularUsuario = ja.getJSONObject(i).getString("celular");
                        Evento evento = new Evento(descripcion,personasSolicitadas,categoria,usuario,celularUsuario);
                        eventos.add(evento);
                    }
                    //evento1 = ja.getJSONObject(0).getString("nombres");
                    //evento.get("nombres");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }else{

            }


        }
    }

    public void createAdapter(){

        adapter = new EventoAdapter(eventos, R.layout.recycler_view_item, new EventoAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Evento evento, int position) {

            }
        }, new EventoAdapter.OnButtonCallClickListener() {
            @Override
            public void onButtonClick(Evento evento, int position) {
                //requestPermissions(new String[]{Manifest.permission.CALL_PHONE},100);
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"+evento.getCelularUsuario()));
                startActivity(intent);
            }
        });
    }

    @Override
    public void onDestroy() {
        eventos.clear();
        super.onDestroy();
    }
}
