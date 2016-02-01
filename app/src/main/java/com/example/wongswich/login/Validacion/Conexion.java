package com.example.wongswich.login.Validacion;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by wongswich on 31/01/16.
 */
public class Conexion {
    private boolean wifiActivo=false;
    private boolean movilActivo=false;

    public Conexion(){

    }

    public boolean estaActivoWifi(Context context){
        ConnectivityManager conectivity=(ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = conectivity.getActiveNetworkInfo();
        if(activeNetwork!=null){
            if(activeNetwork.getType()==conectivity.TYPE_WIFI){
                //Toast.makeText(context, activeNetwork.getTypeName(), Toast.LENGTH_SHORT).show();
                wifiActivo=true;
            }
        }
        return wifiActivo;
    }
    public boolean estaActivoDatos(Context context){
        ConnectivityManager conectivity=(ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = conectivity.getActiveNetworkInfo();
        if(activeNetwork!=null){
            if(activeNetwork.getType()==conectivity.TYPE_MOBILE){
                Log.i("TIPO INTER", activeNetwork.getTypeName().toString());
                Toast.makeText(context, activeNetwork.getTypeName(), Toast.LENGTH_SHORT).show();
                movilActivo=true;
            }
        }
        return movilActivo;
    }
}//Fin clase Conexion

/*
*
protected Boolean conectadoWifi(){
ConnectivityManager connectivity = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
if (connectivity != null) {
NetworkInfo info = connectivity.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
if (info != null) {
if (info.isConnected()) {
return true;
}
}
}
return false;
}


* */