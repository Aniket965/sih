package com.example.android.electracksih;

/**
 * Created by NITANT SOOD on 31-03-201
 */

import android.content.Context;
import android.os.AsyncTask;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.Scanner;
/**
 * Created by shruti on 30-03-2018.
 */

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.Scanner;

/**
 * Created by NITANT SOOD on 20-03-2018.
 */

public class predicted_task extends AsyncTask<String,Void,String> {
    String urlstr;
    Context context;
    public onSpecificStateChangeListener mListener;
    interface onSpecificStateChangeListener{
        void onStateChanged(String string);
    }
    predicted_task(Context context,String urls,onSpecificStateChangeListener listener){
        this.context=context;
        this.urlstr = urls;
        this.mListener=listener;
    }
    @Override
    protected String doInBackground(String... strings) {
        String s=strings[0];


        URL url;
        HttpURLConnection httpsURLConnection=null;

        try {
             String urlString="172.28.24.85:5000/predictpower?month=6";

            url = new URL(urlString);


            httpsURLConnection = (HttpURLConnection) url.openConnection();
            httpsURLConnection.setRequestMethod("GET");

            httpsURLConnection.connect();
            InputStream inputStream = httpsURLConnection.getInputStream();

            String result = "";
            Scanner scanner = new Scanner(inputStream);
            while (scanner.hasNext()){
                result = result.concat(scanner.next());
            }
            return result;
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return "1";
        } catch (ProtocolException e) {
            e.printStackTrace();
            return "2";
        } catch (IOException e) {
            e.printStackTrace();
            return "3";
        }

        finally {
            if( httpsURLConnection!= null) // Make sure the connection is not null.
                httpsURLConnection.disconnect();
        }
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
    }
}