package com.example.prakriti.myapplication;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
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
    MycustomAdapter mAdapter;

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_main, container, false);


        perform(v);
        return v;
    }

    public void perform(View v)
    {
        new ProductAsyncTask().execute();
    }

    class ProductAsyncTask extends AsyncTask<String, String, String> {
        ProgressDialog mprogressDialog;
        RecyclerView mrecyclerView;
        int flag;
        String result = "";
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
                JSONObject jsonObject = jsonParser.performPostCI("http://aprakriti.com.np/android/script.php", loginHashMap);


                try {
                    if (jsonObject == null) {
                        flag = 1;
                    } else if (jsonObject.getString(result).equals(result)) {
                        JSONArray jsonArray=jsonObject.getJSONArray(result);
                        for(int i=0;i<jsonArray.length();i++)
                        {
                            JSONObject dataObject = jsonArray.getJSONObject(i);
                            Integer id = dataObject.getInt("id");
                            String name = dataObject.getString("name");
                            String description = dataObject.getString("details");
                            String image = dataObject.getString("image");
                            String price = dataObject.getString("price");

                            MyData myData= new MyData(id, name, price, description, image );
                            data_list.add(myData);
                            flag = 2;

                        }

                    }
                    else {
                        flag = 3;
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
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
                        Toast.makeText(getContext(), "Success", Toast.LENGTH_SHORT).show();
                        mrecyclerView = (RecyclerView) getView().findViewById(R.id.recycler_view);

                        GridLayoutManager mGrid = new GridLayoutManager(getContext(),2);
                        mrecyclerView.setLayoutManager(mGrid);
                        mrecyclerView.setHasFixedSize(true);
                        mrecyclerView.setNestedScrollingEnabled(false);

                        MycustomAdapter mAdapter = new MycustomAdapter(getContext(), data_list );
                        mrecyclerView.setAdapter(mAdapter);
                        mAdapter.notifyDataSetChanged();


                    } else {
                        Toast.makeText(getContext(), "Invalid credentials", Toast.LENGTH_SHORT).show();
                    }


    }

}

}
