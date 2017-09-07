package com.example.prakriti.myapplication;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;


import com.example.prakriti.myapplication.Adapter.MycustomAdapter;

import com.example.prakriti.myapplication.Pojo.MyData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivityFragment extends Fragment {


    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_main, container, false);


        perform();
        return v;
    }

    public void perform()
    {
        new ProductAsyncTask().execute();
    }

    class ProductAsyncTask extends AsyncTask<String, String, String> {
        ProgressDialog mprogressDialog;
        RecyclerView mrecyclerView;
        int flag;
        List<MyData> data_list = new ArrayList<>();

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mprogressDialog = new ProgressDialog(getContext());
            mprogressDialog.setMessage("Please wait");
            mprogressDialog.setCancelable(false);
            mprogressDialog.show();

        }

        @Override
        protected String doInBackground(String... params) {
            HashMap<String, String> loginHashMap = new HashMap<>();
            JsonParser jsonParser = new JsonParser();
            JSONObject jsonObject = jsonParser.performPostCI("http://aprakriti.com.np/android/", loginHashMap);


            try {
                if (jsonObject == null) {
                    flag = 1;
                } else if (jsonObject.getString("status").equals("success")) {
                    JSONArray jsonArray=jsonObject.getJSONArray("data");
                    for(int i=0;i<jsonArray.length();i++)
                    {
                        JSONObject dataObject = jsonArray.getJSONObject(i);
                        Integer id = dataObject.getInt("id");
                        String name = dataObject.getString("name");
                        String image = dataObject.getString("image");
                        String description = dataObject.getString("description");
                        String price = dataObject.getString("price");

                        MyData myData= new MyData(id,name,image,description,price);
                        data_list.add(myData);
                        flag = 2;
                        Log.e("donkey", "sam");

                    }

                }
                else {
                    flag = 3;
                }

            } catch (JSONException e) {

            }
            return null;
        }


        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            mprogressDialog.dismiss();
            if (flag == 1) {
                Toast.makeText(getContext(), "Server/Network issue", Toast.LENGTH_SHORT).show();

            } else if (flag == 2) {
                //mAdapter.notifyDataSetChanged();
                Toast.makeText(getContext(), "Success", Toast.LENGTH_SHORT).show();
                mrecyclerView = (RecyclerView) getView().findViewById(R.id.recycler_view);

                GridLayoutManager mGrid = new GridLayoutManager(getContext(),1);
                mrecyclerView.setLayoutManager(mGrid);
                mrecyclerView.setHasFixedSize(true);
                MycustomAdapter mAdapter = new MycustomAdapter(getContext(), data_list );
                mrecyclerView.setAdapter(mAdapter);



            } else {
                Toast.makeText(getContext(), "Invalid credentials", Toast.LENGTH_SHORT).show();
            }


        }

    }

}