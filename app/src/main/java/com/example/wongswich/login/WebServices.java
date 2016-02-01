package com.example.wongswich.login;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by wongswich on 20/01/16.
 */
public class WebServices {

    HttpURLConnection urlConnection = null;
    BufferedReader reader = null;
    String forecastJsonStr = null;
    private String usuario;
    private String password;
    private String url;

    //setters
    private void setUsuario(String usuario) {
        this.usuario = usuario;
    }
    private void setPassword(String password) {
        this.password = password;
    }
    private void setUrl(String url) {
        this.url = url;
    }

    //getters
    public String getUsuario() {
        return usuario;
    }
    public String getPassword() {
        return password;
    }
    public String getUrl() {
        return url;
    }

    //Constructor null
    public WebServices(){

    }


    //Métodos

    public Boolean validarConexion(Context context){
        Boolean resultado=true;
        ConnectivityManager connMgr = (ConnectivityManager)
               context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            // Operaciones http
        } else {
            // Mostrar errores
        }

        return true;
    }
    public void crearURL(String usuario, String password){
        String url="https://control.salesup.com.mx/webservices/jsonPrueba.dbsp?usuario="+usuario+"&password="+password;
    }


    public void obtenerInformacionWebServices(String url){

        try{
            URL dir=new URL(url);
            urlConnection= (HttpURLConnection) dir.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            InputStream  inputStream=urlConnection.getInputStream();
            StringBuffer buffer=new StringBuffer();
            if(inputStream!=null){
                //return null;
            }
            reader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while ((line = reader.readLine()) != null) {
                buffer.append(line + "\n");
            }
            if (buffer.length() == 0) {
                // Stream was empty.  No point in parsing.
                //return null;
            }
            forecastJsonStr = buffer.toString();

        }catch(IOException e){
            Log.e("PlaceholderFragment", "Error ", e);
            //return null;
        }finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (final IOException e) {
                    Log.e("PlaceholderFragment", "Error closing stream", e);
                }
            }
        }
        //return rootView;
    }
}
