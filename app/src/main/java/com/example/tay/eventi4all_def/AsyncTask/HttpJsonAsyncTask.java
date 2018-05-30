package com.example.tay.eventi4all_def.AsyncTask;


import android.os.AsyncTask;

import org.json.JSONArray;


import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStreamReader;
import java.io.OutputStream;

import java.net.HttpURLConnection;
import java.net.MalformedURLException;

import java.net.URL;


/**
 * Created by tay on 15/1/18.
 */

public class HttpJsonAsyncTask extends AsyncTask<JSONArray, Integer, String> {








    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(JSONArray... jsonArrays) {
        try {

            URL url = new URL("https://us-central1-eventi4all-39517.cloudfunctions.net/sendNewPush");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");

            System.out.println("JSON Enviado:\n" + jsonArrays[0].toString());

            OutputStream os = conn.getOutputStream();
            os.write(jsonArrays[0].toString().getBytes());
            os.flush();


            BufferedReader br = new BufferedReader(new InputStreamReader(
                    (conn.getInputStream())));

            String output;
            System.out.println("Salida del servidor .... \n");
            while ((output = br.readLine()) != null) {
                System.out.println(output);
            }

            conn.disconnect();

        } catch (MalformedURLException e) {

            e.printStackTrace();

        } catch (IOException e) {

            e.printStackTrace();

        }


        System.out.println("salida");
        return "";
    }



    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(String s) {



    }


    @Override
    protected void onCancelled() {
        super.onCancelled();
    }


}




































































































//PLATANO GUAPETON
