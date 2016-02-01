package com.example.wongswich.login.Mensajeria;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.util.Log;

/**
 * Created by wongswich on 31/01/16.
 */
public  class Mensajes extends DialogFragment {
    private String mensaje;
    private int  tipo;

    public Mensajes(){}

    public Dialog onCreateDialog(Bundle savedInstanceState){
        super.onCreateDialog(savedInstanceState);
        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
        builder.setTitle("Información");
        builder.setMessage(getMensaje());
        return builder.create();
    }

    public void setMensaje(String mensaje){

        this.mensaje=mensaje;
    }
    public String getMensaje(){
        return mensaje;
    }
    public void mostrarLogInformacion(String str){
        Log.i("INFO", str);
    }
    public void mostrarLogError(String str){
        Log.e("ERROR", str);
    }
    public void mostrarLogAdvertencia(String str){
        Log.w("ALERTA", str);
    }
}
