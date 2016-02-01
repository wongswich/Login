package com.example.wongswich.login;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.wongswich.login.Validacion.Conexion;

import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity  {

    /**
     * Id to identity READ_CONTACTS permission request.
     */
    private static final int REQUEST_READ_CONTACTS = 0;
    private static final String INFORMACION="INFO";
    private VolleySingleton volley;
    protected RequestQueue fRequestQueue;
    // UI references.
    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView;
    private EditText mTxtnuevo;
    private View mProgressView;
    private View mLoginFormView;
    private Button btnIngresar;
    private TextView txtDatos;
    private LinearLayout linearLayoutProgres;
    private boolean wifiActivo=false;
    private boolean movilActivo=false;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        mostrarLogInformacion("onCreate");
        //StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().permitNetwork().build());
        boolean pasa=verificarInternetActivo();
        if(pasa){
            obtenerDatosLyaout();
        }
    }


    public void webServices(final String usr, final String contrasena){
        mostrarLogInformacion("webServices");
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        String Url="https://control.salesup.com.mx/webservices/jsonPrueba.dbsp?usuario="+usr+"&contrasena="+contrasena;
        JsonObjectRequest stringRequest=new JsonObjectRequest(Request.Method.POST, Url,(String)null,
            new Response.Listener<JSONObject>() {

                @Override
                public void onResponse(JSONObject response) {
                    mostrarLogInformacion("RESPUESTA");
                    mostrarLogInformacion(response.toString());
                    mProgressView.setVisibility(View.GONE);
                    //ir a otra activdad
                }
            }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                mostrarLogInformacion("ERROR");
                mostrarLogInformacion(error.toString());

            }
        });
//        {
//
//            protected Map<String, String> getParams(){
//                mostrarLogInformacion("map");
//                Map <String, String> params=new HashMap<String, String>();
//                mostrarLogInformacion(usr.trim());
//                params.put("usuario", usr.trim());
//                params.put("contrasena", contrasena.trim());
//                mostrarLogInformacion(usr);
//                mostrarLogInformacion(contrasena);
//                return params;
//            }
//            @Override
//            public Map<String, String> getHeaders() throws AuthFailureError {
//                Map<String,String> params = new HashMap<String, String>();
//                params.put("Content-Type","text/html");
//                return params;
//            }
//        };
        requestQueue.add(stringRequest);

    } //Fin webServices
    public boolean verificarInternetActivo (){
        boolean  pasa=false;
        Conexion conexion=new Conexion();
        movilActivo=conexion.estaActivoDatos(this);
        wifiActivo=conexion.estaActivoWifi(this);
        String strmovil= (movilActivo) ? "Esta activo Movil" : "No esta activo" ;
        String strwifi= (wifiActivo) ? "Esta activo wifi" : "No esta wifi" ;
        if(movilActivo || wifiActivo){
            pasa=true;
        }else{
            mostrarMensajeSinInternet();
        }
        return pasa;
    }
    public void mostrarLogInformacion(String cadena){
        Log.i(INFORMACION, cadena);
    }
    public void mostrarMensajeSinInternet(){
            MensajesDialog msgDialog=new MensajesDialog();
            msgDialog.show(getFragmentManager(), "algo");
    }
    public Boolean validarCamposObligatorios(){
        Boolean resultado=true;
        String usr = mEmailView.getText().toString();
        String pwd = mPasswordView.getText().toString();
        mEmailView.setError(null);
        mPasswordView.setError(null);
        if(TextUtils.isEmpty(usr)){ //
            mEmailView.setError("Correo requerido");
            resultado=false;
        }else if(TextUtils.isEmpty(pwd)){ //
            mPasswordView.setError("PWd requerido");
            resultado=false;
        }
        return resultado;
    }
    public void obtenerDatosLyaout(){
        mEmailView = (AutoCompleteTextView) findViewById(R.id.email);
        mPasswordView = (EditText) findViewById(R.id.password);
        btnIngresar=(Button) findViewById(R.id.email_sign_in_button);
        linearLayoutProgres=(LinearLayout) findViewById(R.id.linearLayout);
        mProgressView=(ProgressBar) findViewById(R.id.login_progress);
        btnIngresar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String usr=mEmailView.getText().toString();
                String pss=mPasswordView.getText().toString();
                if(validarCamposObligatorios()){
                    mProgressView.setVisibility(View.VISIBLE);
                    mEmailView.setVisibility(View.GONE);
                    mPasswordView.setVisibility(View.GONE);
                    btnIngresar.setVisibility(View.GONE);
                    webServices(usr, pss);
                    mProgressView.setVisibility(View.VISIBLE);
                }



            }

        });



    }


    public static class MensajesDialog extends DialogFragment{
        @NonNull
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState){
            super.onCreateDialog(savedInstanceState);
            AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
            builder.setTitle("Error.");
            builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener(){
                public void onClick(DialogInterface dialog, int id){
                    startActivity(new Intent(android.provider.Settings.ACTION_SETTINGS));
                }
            });
            builder.setMessage("No se puede establecer conexión a internet");
            return builder.create();
        }


    }

}

