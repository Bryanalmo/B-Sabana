package android.primer.bryanalvarez.b_sabana.Activities;

import android.os.AsyncTask;
import android.primer.bryanalvarez.b_sabana.Adapters.NoticiasAdapter;
import android.primer.bryanalvarez.b_sabana.Models.Evento;
import android.primer.bryanalvarez.b_sabana.Models.Noticia;
import android.primer.bryanalvarez.b_sabana.Utils.Util;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.primer.bryanalvarez.b_sabana.R;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class NoticiasActivity extends AppCompatActivity {

    private ListView listView;
    private NoticiasAdapter adapter;
    private List<Noticia> noticias;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_noticias);

        TraerNoticias traerNoticias = new TraerNoticias();
        traerNoticias.execute("http://"+Util.IP+"/CursoAndroid/consultaNoticias.php");
        try {
            traerNoticias.onPostExecute(traerNoticias.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        noticias = traerNoticias.noticias;

        listView = (ListView) findViewById(R.id.listViewNoticias);
        adapter = new NoticiasAdapter(this,R.layout.list_view_item_noticias,noticias);
        listView.setAdapter(adapter);
        registerForContextMenu(listView);
    }

    private class TraerNoticias extends AsyncTask<String, Void, String> {
        ArrayList<Noticia> noticias;
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
                noticias = new ArrayList<>();
                int length = ja.length();
                for (int i=0; i<=length; i++){
                    String titulo = ja.getJSONObject(i).getString("nombre");
                    String descripcion = ja.getJSONObject(i).getString("descripcion");
                    int categoria = ja.getJSONObject(i).getInt("idDepartamento");
                    Noticia noticia= new Noticia(titulo,descripcion,categoria);
                    noticias.add(noticia);
                }
                //evento1 = ja.getJSONObject(0).getString("nombres");
                //evento.get("nombres");
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }
}
