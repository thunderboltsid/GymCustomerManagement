package com.example.siddharthshukla.gym;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
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
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class TableViewActivity extends ActionBarActivity {

    private TextView responseTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table_view);
        this.responseTextView = (TextView) this.findViewById(R.id.responseTextView);
        new GetAllCustomersTask().execute(new AppConnector());
    }

    public void setTextToTextView(JSONArray jsonArray)
    {
        String s  = "";
        for(int i=0; i<jsonArray.length();i++){

            JSONObject json = null;
            try {
                json = jsonArray.getJSONObject(i);
                s = s +
                        "Name : "+json.getString("firstName")+" "+json.getString("secondName")+"\n"+
                        "Number : "+json.getString("contactNumber")+"\n"+
                        "Manager : "+json.getString("manager")+"\n\n";
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        this.responseTextView.setText(s);
    }


    private class GetAllCustomersTask extends AsyncTask<AppConnector,Long,JSONArray> {
        @Override
        protected JSONArray doInBackground(AppConnector... params) {
            // it is executed on Background thread
            return params[0].GetAllCustomers();
        }

        @Override
        protected void onPostExecute(JSONArray jsonArray) {
            setTextToTextView(jsonArray);
        }
    }
}
