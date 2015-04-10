package com.example.siddharthshukla.gym;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.app.Activity;
import android.os.AsyncTask;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.Toast;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.ListView;
import android.widget.TextView;
import java.io.BufferedReader;
import java.io.Console;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class MainActivity extends ActionBarActivity {
    String firstName;
    String lastName;
    String contactNumber;
    String manager;
    ArrayList<String> managerArray = new ArrayList<String>();
    InputStream is=null;
    String result=null;
    String line=null;
    int code;



    public void viewCustomers(View view){
        Intent intent = new Intent(MainActivity.this, TableViewActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new GetAllManagersTask().execute(new AppGetManager());
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        final EditText e_firstName = (EditText) findViewById(R.id.firstName);
        final EditText e_lastName = (EditText) findViewById(R.id.familyName);
        final EditText e_contactNumber = (EditText) findViewById(R.id.phoneNumber);
        final Spinner e_manager = (Spinner) findViewById(R.id.managerSelector);
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, managerArray);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        e_manager.setAdapter(adapter);
        e_manager.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                manager = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                manager = "N.A.";
            }
        });
        final Button insert = (Button) findViewById(R.id.addCustomer);
        insert.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                firstName = e_firstName.getText().toString();
                lastName = e_lastName.getText().toString();
                contactNumber = e_contactNumber.getText().toString();
                String detailsArr[] = {firstName, lastName, contactNumber, manager};
                new InsertTask().execute(detailsArr);
                e_firstName.setText("");
                e_lastName.setText("");
                e_contactNumber.setText("");
            }
        });

    }

    public void StoreManagersInArray(JSONArray jsonArray)
    {
        for(int i=0; i<jsonArray.length();i++){
            JSONObject json = null;
            try {
                json = jsonArray.getJSONObject(i);
                managerArray.add(i, json.getString("name"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private class InsertTask extends AsyncTask<String[], Void, String>{
        @Override
        protected String doInBackground(String[]... params){
            ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
            nameValuePairs.add(new BasicNameValuePair("firstName", firstName));
            nameValuePairs.add(new BasicNameValuePair("secondName", lastName));
            nameValuePairs.add(new BasicNameValuePair("contactNumber", lastName));
            nameValuePairs.add(new BasicNameValuePair("manager", manager));
            try {
                HttpClient httpclient = new DefaultHttpClient();
                HttpPost httppost = new HttpPost("http://81.4.121.185/insertCustomers.php");
                httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                HttpResponse response = httpclient.execute(httppost);
                HttpEntity entity = response.getEntity();
                is = entity.getContent();
                Log.e("pass 1", "connection success ");
            }   catch(Exception e) {
                Log.e("Fail 1", e.toString());
                //Toast.makeText(getApplicationContext(), "Invalid IP Address", Toast.LENGTH_LONG).show();
            }
            try {
                BufferedReader reader = new BufferedReader
                        (new InputStreamReader(is,"iso-8859-1"),8);
                StringBuilder sb = new StringBuilder();
                while ((line = reader.readLine()) != null)
                {
                    sb.append(line + "\n");
                }
                is.close();
                result = sb.toString();
                Log.e("pass 2", "connection success ");
            }   catch(Exception e) {
                Log.e("Fail 2", e.toString());
                //Toast.makeText(getApplicationContext(), "Connection failure", Toast.LENGTH_LONG).show();
            }
            try {
                JSONObject json_data = new JSONObject(result);
                code=(json_data.getInt("code"));
                Log.e("Pass 3", "Insert successful... hopefully");
            }   catch(Exception e) {
                Log.e("Fail 3", e.toString());
                //Toast.makeText(getApplicationContext(), "Oops...", Toast.LENGTH_LONG).show();
            }
            return "";
        }

        @Override
        protected void onPostExecute(String manager) {

        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    private class GetAllManagersTask extends AsyncTask<AppGetManager,Long,JSONArray> {
        @Override
        protected JSONArray doInBackground(AppGetManager... params) {
            // it is executed on Background thread
            return params[0].GetAllManagers();
        }

        @Override
        protected void onPostExecute(JSONArray jsonArray) {
            StoreManagersInArray(jsonArray);
        }
    }
}
