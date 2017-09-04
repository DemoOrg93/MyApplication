package com.example.prakriti.myapplication;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Toast;

import com.example.prakriti.myapplication.Adapter.CustomAdapter;
import com.example.prakriti.myapplication.Pojo.MyData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static android.R.attr.id;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {
     RecyclerView recyclerView;
     GridLayoutManager gridLayoutManager;
     CustomAdapter adapter;
     List<MyData> data_list;

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v =  inflater.inflate(R.layout.fragment_main, container, false);
        perform(v);
        return  v;


    }

    public  void perform(View v){

        new ProductsAsyncTask().execute();


    }
    class ProductsAsyncTask extends AsyncTask<String, String, String> {
        ProgressDialog progressDialog;
        int flag = 0;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(getContext());
            progressDialog.setMessage("Plz wait...");
            progressDialog.setCancelable(false);
            progressDialog.show();
        }
        @Override
        protected String doInBackground(String... params) {
            HashMap<String, String> loginhashmap = new HashMap<>();
            JsonParser jsonParser = new JsonParser();
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder().url("http://192.168.100.57/script.php?id=").build();
            try {


                    Response response = client.newCall(request).execute();


                    JSONArray array = new JSONArray(response.body().string());


                    for (int i = 0; i < array.length(); i++) {

                        JSONObject object = array.getJSONObject(i);

                        MyData data = new MyData(object.getInt("id"), object.getString("name"),
                                object.getString("description"), object.getString("image"), object.getString("price"));

                        data_list.add(data);
                        Log.e("swikriti", "sonika");

                    }

                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    System.out.println("End of Content");
                }
                return null;
            }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            super.onPostExecute(s);
            progressDialog.dismiss();


            recyclerView = getView().findViewById(R.id.recycler_view);
            gridLayoutManager = new GridLayoutManager(getContext(), 1);
            recyclerView.setLayoutManager(gridLayoutManager);
            recyclerView.setHasFixedSize(true);
            recyclerView.setNestedScrollingEnabled(false);

            adapter = new CustomAdapter(getContext(), data_list);
            recyclerView.setAdapter(adapter);

            adapter.notifyDataSetChanged();



        }};
        }
