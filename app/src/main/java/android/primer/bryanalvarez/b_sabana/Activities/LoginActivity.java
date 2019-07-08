package android.primer.bryanalvarez.b_sabana.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.primer.bryanalvarez.b_sabana.R;
import android.primer.bryanalvarez.b_sabana.Utils.Util;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class LoginActivity extends AppCompatActivity {

    private SharedPreferences prefs;

    private EditText editTextEmail;
    private EditText editTextPassword;
    private Switch switchRemember;
    private Button buttonLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        prefs = getSharedPreferences("Preferences", Context.MODE_PRIVATE);

        bindUI();

        setCredentialsifExist();

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = editTextEmail.getText().toString();
                String password = editTextPassword.getText().toString();

                ConsultarDatos consultarDatos = new ConsultarDatos();
                consultarDatos.execute("http://"+Util.IP+"/CursoAndroid/consulta.php?usuario="+editTextEmail.getText().toString()+"&contrasena="+editTextPassword.getText().toString());
                //consultarDatos.execute("http://"+Util.IP+"/CursoAndroid/consultaEventos.php");
                try {
                    consultarDatos.onPostExecute(consultarDatos.get());

                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
                String id = consultarDatos.usuario;
                if(id != null){
                    if(login(email,password)){
                        goToMain();
                        safeOnPreferences(email,password);
                        Toast.makeText(getApplicationContext(),"Exitoso", Toast.LENGTH_LONG).show();
                    }
                }else{
                    Toast.makeText(getApplicationContext(),"Usuario o contraseña incorrectos", Toast.LENGTH_LONG).show();
                }

            }
        });

    }

    private void bindUI(){

        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextPassword= (EditText) findViewById(R.id.editTextPassword);
        switchRemember = (Switch) findViewById(R.id.switchRemember);
        buttonLogin = (Button) findViewById(R.id.buttonLogin);
    }

    private void setCredentialsifExist(){
        String email = Util.getuserMailPrefs(prefs);
        String password = Util.getuserPasswordPrefs(prefs);
        if(!TextUtils.isEmpty(email) && !TextUtils.isEmpty(password)){
            editTextEmail.setText(email);
            editTextPassword.setText(password);
            switchRemember.setChecked(true);
        }

    }

    private boolean login(String email, String password){
        if(!isValidEmail(email)){
            Toast.makeText(this,"Email invalido, intente de nuevo", Toast.LENGTH_LONG).show();
            return false;
        }else if(!isValidPassword(password)){
            Toast.makeText(this,"Contraseña invalida, debe tener más de 4 caracteres", Toast.LENGTH_LONG).show();
            return false;
        }else{
            return true;
        }
    }

    private void safeOnPreferences(String email, String password){
        if(switchRemember.isChecked()){
            SharedPreferences.Editor editor = prefs.edit();
            editor.putString("Email",email);
            editor.putString("Pass",password);
            editor.putInt("id",Util.id);
            editor.putString("usuario", Util.usuario);
            editor.putString("celular", Util.celular);
            editor.commit();
            editor.apply();
        }

    }

    private boolean isValidEmail(String email){
        return !TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private boolean isValidPassword(String password){
        return password.length() >= 4;
    }

    private void goToMain(){
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.putExtra("id",Util.id);
        startActivity(intent);
    }

    private class ConsultarDatos extends AsyncTask<String, Void, String> {
        String usuario;
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
                usuario = ja.getJSONObject(0).getString("nombres") + " "+ ja.getJSONObject(0).getString("apellidos");
                Util.setCelular(ja.getJSONObject(0).getString("celular"));
                Util.setId(ja.getJSONObject(0).getInt("idUsuario"));
                Util.setUsuario(usuario);
            } catch (JSONException e) {
                e.printStackTrace();
            }

         }
    }


}
