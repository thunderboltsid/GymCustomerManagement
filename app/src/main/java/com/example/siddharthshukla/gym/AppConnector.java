package com.example.siddharthshukla.gym;

import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;

/**
 * Created by siddharthshukla on 06/04/15.
 */
public class AppConnector {
    public JSONArray GetAllCustomers(){

        // URL for getting customers from db
        String url = "http://81.4.121.185/getCustomers.php";

        // Get HttpResponseObject from url
        // Get HttpEntity from HttpResponseObject

        HttpEntity httpEntity = null;
        try {
            DefaultHttpClient httpClient = new DefaultHttpClient();
            HttpGet httpGet = new HttpGet(url);
            HttpResponse httpResponse = httpClient.execute(httpGet);
            httpEntity = httpResponse.getEntity();

        } catch (ClientProtocolException e) {
            // Signals error in Http Protocol
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Convert httpEntity into JSONArray
        JSONArray jsonArray = null;
        if (httpEntity != null){
            try {
                String entityResponse = EntityUtils.toString(httpEntity);
                Log.e("Entity Response : ", entityResponse);
                jsonArray = new JSONArray(entityResponse);


            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return jsonArray;
    }
}
