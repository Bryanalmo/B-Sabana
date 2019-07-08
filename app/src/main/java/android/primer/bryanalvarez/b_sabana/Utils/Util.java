package android.primer.bryanalvarez.b_sabana.Utils;

import android.content.SharedPreferences;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by nayar on 2/04/2018.
 */

public class Util {
    public static String usuario;
    public static String celular;
    public static int id;

    public static void setCelular(String celular) {
        Util.celular = celular;
    }
    public static void setUsuario(String usuario) {
        Util.usuario = usuario;
    }

    public static void setId(int id) {
        Util.id = id;
    }

    public static String IP="b-sabana.000webhostapp.com";

    public static String EMAIL_CONTACTO = "bryanalmo@unisabana.edu.co";
    public static String ASUNTO_EMAIL = "Problemas con B-Sabana";
    public static String MENSAJE_EMAIL = "Hola! He tenido problemas usando la app."+ "\n"+ " La falla que presenta es la siguiente:";
    public static String COPIA_A_EMAIL = "soporte@unisabanaedu.onmicrosoft.com";

    public static String getuserUsuarioPrefs(SharedPreferences preferences){
        return preferences.getString("usuario","");
    }
    public static String getuserCelularPrefs(SharedPreferences preferences){
        return preferences.getString("celular","");
    }
    public static int getuserIdPrefs(SharedPreferences preferences){
        return preferences.getInt("id",0);
    }
    public static String getuserMailPrefs(SharedPreferences preferences){
        return preferences.getString("Email","");
    }
    public static String getuserPasswordPrefs(SharedPreferences preferences){
        return preferences.getString("Pass","");
    }
    public static void deleteMailandPass(SharedPreferences preferences){
        SharedPreferences.Editor editor = preferences.edit();
        editor.remove("Email");
        editor.remove("Pass");
        editor.apply();
    }
    public static String downloadUrl(String myurl) throws IOException {
        Log.i("URL",""+myurl);
        myurl = myurl.replace(" ","%20");
        InputStream is = null;
        // Only display the first 500 characters of the retrieved
        // web page content.
        int len = 5500;

        try {
            URL url = new URL(myurl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(10000 /* milliseconds */);
            conn.setConnectTimeout(15000 /* milliseconds */);
            conn.setRequestMethod("GET");
            conn.setDoInput(true);
            // Starts the query
            conn.connect();
            int response = conn.getResponseCode();
            Log.d("respuesta", "The response is: " + response);
            is = conn.getInputStream();

            // Convert the InputStream into a string
            String contentAsString = readIt(is, len);
            return contentAsString;

            // Makes sure that the InputStream is closed after the app is
            // finished using it.
        } finally {
            if (is != null) {
                is.close();
            }
        }
    }

    public static String readIt(InputStream stream, int len) throws IOException, UnsupportedEncodingException {
        Reader reader = null;
        reader = new InputStreamReader(stream, "UTF-8");
        char[] buffer = new char[len];
        reader.read(buffer);
        return new String(buffer);
    }

}
