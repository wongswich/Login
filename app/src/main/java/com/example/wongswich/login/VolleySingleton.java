package com.example.wongswich.login;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by wongswich on 20/01/16.
 */
public class VolleySingleton {

    private static VolleySingleton mVolleySingleton=null;
    private RequestQueue mRequestqueue;

    private VolleySingleton(Context context){
        mRequestqueue= Volley.newRequestQueue(context);
    }
    public static   VolleySingleton getInstance(Context context){
        if(mVolleySingleton==null){
            mVolleySingleton=new VolleySingleton(context);
        }
        return mVolleySingleton;
    }
    public RequestQueue getmRequestQueue(){
        return  mRequestqueue;
    }
}
